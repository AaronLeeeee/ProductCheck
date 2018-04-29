package com.check.gf.gfapplication.base;

import android.os.Bundle;

/**
 * BaseActivity 接口
 *
 * @author nEdAy
 */
interface IBaseComponent {
    /**
     * 绑定渲染视图的布局文件
     *
     * @return 布局文件资源id
     */
    int bindLayout();

    void getIntentData();

    /**
     * 初始化控件
     */
    void initView(Bundle savedInstanceState);
}
