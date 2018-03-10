package com.check.gf.gfapplication.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.adapter.CheckResultAdapter;
import com.check.gf.gfapplication.base.BaseFragment;
import com.check.gf.gfapplication.model.SurfaceInfo;
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
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 外观
 *
 * @author nEdAy
 */

public class SurfaceFragment extends BaseFragment implements TakePhoto.TakeResultListener, InvokeListener {

    private static final String TAG = SurfaceFragment.class.getSimpleName();

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    private SimpleDraweeView riv_pic;

    private EditText et_msg;

    private ListView mListView;
    private CheckResultAdapter mCheckResultAdapter;


    public static SurfaceFragment newInstance() {
        Bundle args = new Bundle();
        SurfaceFragment fragment = new SurfaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_surface, container, false);
        mLoadingView = layout.findViewById(R.id.loadView);
        riv_pic = layout.findViewById(R.id.riv_pic);

        mListView = layout.findViewById(R.id.lv_view);
        mCheckResultAdapter = new CheckResultAdapter(getActivity());
        mListView.setAdapter(mCheckResultAdapter);
        Button mTakePictureBt = layout.findViewById(R.id.bt_take_picture);
        mTakePictureBt.setOnClickListener(v -> showActionSheet());

        Button bt_commit_msg = layout.findViewById(R.id.bt_commit_msg);
        bt_commit_msg.setOnClickListener(v -> commitMsg());
        et_msg = layout.findViewById(R.id.et_msg);
        return layout;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<SurfaceInfo> list = new ArrayList<>();
        SurfaceInfo info_1 = new SurfaceInfo();
        info_1.checkDes = "表面机加工导轨：导向面不允许有伤碰，非机加工面涂RAL9001面漆，" +
                "机加工面涂有防锈油。空心导轨：表面不允许有裂痕、伤痕、毛刺等缺陷，镀锌层不允许起皮、脱落等现象";
        info_1.isChecked = true;
        list.add(info_1);
        SurfaceInfo info_2 = new SurfaceInfo();
        info_2.checkDes = "查看LOGO-依据装箱清单梯型，查看选择木箱印刷LOGO、喷头LOGO";
        info_2.isChecked = true;
        list.add(info_2);
        SurfaceInfo info_3 = new SurfaceInfo();
        info_3.checkDes = "包装-检查木箱印刷LOGO、喷头LOGO印刷质量、木箱质量完好";
        info_3.isChecked = false;
        list.add(info_3);
        mCheckResultAdapter.bindData(list);
    }

    private void refreshInfo() {
        // TODO:刷新页面
    }

    private void commitMsg() {
        String msg = et_msg.getText().toString().trim();
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .SaveItemChkCnt("0001", "001", "0001", msg),
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
     * 显示获取头像选择菜单
     */
    private void showActionSheet() {
        final String[] stringItems = {getString(R.string.pick_from_capture), getString(R.string.pick_from_gallery)};
        final ActionSheetDialog dialog = new ActionSheetDialog(getActivity(), stringItems, null);
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
            riv_pic.setImageURI(uri);
        } else {
            riv_pic.setImageResource(R.drawable.icon_stub);
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        // 上传
        File file = new File(result.getImage().getCompressPath());
        // 创建 RequestBody，用于封装 请求RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
//        ItemChkUploadImg itemChkUploadImg = new ItemChkUploadImg();
//        itemChkUploadImg.setEquipmentNo("0001");
//        itemChkUploadImg.setInspectCode("001");
//        itemChkUploadImg.setItemCode("0001");
//        itemChkUploadImg.setUploadImg(requestFile);
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .ItemChkUploadImg("0001", "001", "0001", requestFile),
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
                throwable -> {
                    itemChkUploadImgError(throwable.getMessage());
                });
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
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
    }
}
