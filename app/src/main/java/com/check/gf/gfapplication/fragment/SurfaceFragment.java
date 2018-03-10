package com.check.gf.gfapplication.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.adapter.CheckResultAdapter;
import com.check.gf.gfapplication.loader.FrescoImageLoader;
import com.check.gf.gfapplication.model.IncomeCheck;
import com.check.gf.gfapplication.model.SurfaceInfo;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * 外观
 *
 * Created by wqd on 2018/1/10.
 */

public class SurfaceFragment extends Fragment {

    private static final String TAG = SurfaceFragment.class.getSimpleName();

    private Button mTakePictureBt;
    private List<String> mPath = new ArrayList<>();

    private GalleryConfig mGalleryConfig;
    private IHandlerCallBack mIHandlerCallBack;
    private ListView mListView;
    private CheckResultAdapter mCheckResultAdapter;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;


    public static SurfaceFragment newInstance(IncomeCheck incomeCheck) {

        Bundle args = new Bundle();

        SurfaceFragment fragment = new SurfaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_surface, container, false);
        mListView = (ListView) layout.findViewById(R.id.lv_view);
        mCheckResultAdapter = new CheckResultAdapter(getActivity());
        mListView.setAdapter(mCheckResultAdapter);
        mTakePictureBt = (Button) layout.findViewById(R.id.bt_take_picture);
        mTakePictureBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    takePicture();
                }
        });

        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initGalleryConfig();

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

    private void initGalleryConfig() {
        mIHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
                Log.i(TAG, "onStart: 开启");

            }

            @Override
            public void onSuccess(List<String> photoList) {
                Log.i(TAG, "onSuccess: 返回数据");
                mPath.clear();
                for (String s : photoList) {
                    Log.i(TAG, s);
                    mPath.add(s);
                }
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: 结束");
            }

            @Override
            public void onError() {
                Log.i(TAG, "onError: 出错");
            }
        };

        mGalleryConfig = new GalleryConfig.Builder()
                .imageLoader(new FrescoImageLoader(this.getContext()))    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(mIHandlerCallBack)     // 监听接口（必填）
                .provider("com.yancy.gallerypickdemo.fileprovider")   // provider(必填)
                .pathList(mPath)                         // 记录已选的图片
                .multiSelect(true, 20)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .crop(false)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(false, 1, 1, 500, 500)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();
    }

    private void takePicture() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);

        } else {
            GalleryPick.getInstance().setGalleryConfig(mGalleryConfig).open(this.getActivity());
        }
    }
}
