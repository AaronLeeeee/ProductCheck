package com.check.gf.gfapplication.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.base.BaseActivity;
import com.check.gf.gfapplication.entity.InspectItemDetail;
import com.check.gf.gfapplication.fragment.InspectListFragment;
import com.check.gf.gfapplication.network.RxFactory;
import com.check.gf.gfapplication.utils.CommonUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 检测条目页
 *
 * @author nEdAy
 */
public class CheckDetailItemActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {

    private InspectItemDetail.DataBean mInspectItemDetail;
    private String mInspectCode;
    private String mEquipmentNo;

    private TextView tv_num_id;
    private TextView tv_num_des;
    private ImageView iv_checked;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    private SimpleDraweeView riv_pic_1;
    private SimpleDraweeView riv_pic_2;

    private EditText et_msg;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        Intent intent = getIntent();
        mInspectItemDetail = intent.getParcelableExtra(InspectListFragment.getExtra());
        mInspectCode = intent.getStringExtra(InspectListFragment.getInspectCodeExtra());
        mEquipmentNo = intent.getStringExtra(InspectListFragment.getEquipmentNoExtra());
    }


    @Override
    protected int getContentLayout() {
        return R.layout.activity_check_detail_item;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initTopBarForLeft("检测条目", getString(R.string.tx_back));
        mLoadingView = findViewById(R.id.loadView);

        tv_num_id = findViewById(R.id.tv_num_id);
        tv_num_des = findViewById(R.id.tv_num_des);
        iv_checked = findViewById(R.id.iv_checked);

        riv_pic_1 = findViewById(R.id.riv_pic_1);
        riv_pic_2 = findViewById(R.id.riv_pic_2);
        Button mTakePictureBt = findViewById(R.id.bt_take_picture);
        mTakePictureBt.setOnClickListener(v -> showActionSheet());

        Button bt_commit_msg = findViewById(R.id.bt_commit_msg);
        bt_commit_msg.setOnClickListener(v -> commitMsg());
        et_msg = findViewById(R.id.et_msg);
    }

    @Override
    protected void initData() {
        super.initData();
        tv_num_id.setText(mInspectItemDetail.getItemCode());
        tv_num_des.setText(mInspectItemDetail.getItemName());
        int checkResult = mInspectItemDetail.getCheckResult();
        iv_checked.setVisibility(checkResult != 0 ? View.VISIBLE : View.GONE);
        iv_checked.setImageResource(checkResult == 1 ? R.drawable.ic_open : R.drawable.ic_close);

        String checkContent = mInspectItemDetail.getCheckContent();
        et_msg.setText(checkContent);
        // TODO:待确定个备注是否可以修改 重新提交
        et_msg.setEnabled(!TextUtils.isEmpty(checkContent));
        List<InspectItemDetail.DataBean.PicturesBean> picturesBeans = mInspectItemDetail.getPictures();
        for (int i = 0; i < picturesBeans.size(); i++) { //TODO:待确定个数，如不确定改为动态添加布局
            String picUrl = picturesBeans.get(i).getUrl();
            if (i == 0) {
                riv_pic_1.setImageURI(picUrl);
            } else {
                riv_pic_2.setImageURI(picUrl);
            }
        }
    }

    private void refreshInfo() {
        // TODO:上传完毕 这里刷新页面么？
    }

    private void commitMsg() {
        // TODO：返回成功 但是实际备注没有被修改 需询问下
        String msg = et_msg.getText().toString().trim();
        if (TextUtils.isEmpty(msg)) {
            CommonUtils.showToast("检验结果备注为空，无法提交");
            return;
        }
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .SaveItemChkCnt(mEquipmentNo, mInspectCode, mInspectItemDetail.getItemCode(), msg),
                () ->
                        showLoading("提交中..."),
                resultObject -> {
                    if (resultObject.getResult() == 0) {
                        hideLoading();
                        refreshInfo();
                    } else {
                        SaveItemChkCntError(resultObject.getDesc());
                    }
                },
                throwable -> {
                    SaveItemChkCntError(throwable.getMessage());
                });
    }

    private void SaveItemChkCntError(String msg) {
        hideLoading();
        CommonUtils.showToast("提交失败");
        Logger.e(msg);
    }

    /**
     * 获取图片选择菜单
     */
    private void showActionSheet() {
        final String[] stringItems = {getString(R.string.pick_from_capture), getString(R.string.pick_from_gallery)};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
        dialog.isTitleShow(false).layoutAnimation(null).show();
        dialog.setOnOperItemClickL((parent, view, position, id) -> {
            File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Uri imageUri = Uri.fromFile(file);

            configCompress(takePhoto);
            configTakePhotoOption(takePhoto);

            switch (position) {
                case 0:
                    takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                    break;
                case 1:
                    takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                    break;
                default:
                    break;
            }
            dialog.dismiss();
        });
    }

    /**
     * 配置裁剪参数
     */
    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setOutputX(400).setOutputY(400);
        builder.setWithOwnCrop(false);
        return builder.create();
    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());
    }

    /**
     * 配置压缩参数
     *
     * @param takePhoto takePhoto实例
     */
    private void configCompress(TakePhoto takePhoto) {
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(102400)
                .setMaxPixel(400)
                .enableReserveRaw(true)
                .create();
        takePhoto.onEnableCompress(config, true);
    }


    /**
     * 获取TakePhoto实例
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    /**
     * 更新图片 refreshPic
     */
    private void refreshPic(String picPath) {
        if (picPath != null && !picPath.equals("")) {
            Uri uri = Uri.parse(picPath);
            riv_pic_1.setImageURI(uri);
        } else {
            riv_pic_1.setImageResource(R.drawable.icon_stub);
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        // TODO： 上传图片 数据流和其他参赛传输方式 待确定姿势 最好提供样例
        File file = new File(result.getImage().getCompressPath());
        // 创建 RequestBody，用于封装 请求RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
//        ItemChkUploadImg itemChkUploadImg = new ItemChkUploadImg();
//        itemChkUploadImg.setEquipmentNo("0001");
//        itemChkUploadImg.setInspectCode("001");
//        itemChkUploadImg.setItemCode("0001");
//        itemChkUploadImg.setUploadImg(requestFile);
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .ItemChkUploadImg(mEquipmentNo, mInspectCode, mInspectItemDetail.getItemCode(), requestFile),
                () ->
                        showLoading("上传中..."),
                resultObject -> {
                    // 更新BmobUser对象
                    if (resultObject.getResult() == 0) {
                        hideLoading();
                        refreshPic(result.getImage().getOriginalPath());
                    } else {
                        itemChkUploadImgError(resultObject.getDesc());
                    }
                },
                throwable -> itemChkUploadImgError(throwable.getMessage()));
        Logger.i("takeSuccess：" + result.getImage().getCompressPath());
    }

    private void itemChkUploadImgError(String msg) {
        hideLoading();
        CommonUtils.showToast("上传失败");
        Logger.e(msg);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        CommonUtils.showToast("获取头像失败");
        Logger.i("takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        Logger.i(getResources().getString(R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

}
