package com.check.gf.gfapplication.activity;

import android.app.Activity;
import android.os.Bundle;

import com.check.gf.gfapplication.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TImage;

import java.util.ArrayList;


public class PicResultActivity extends Activity {
    ArrayList<TImage> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_layout);
        images = (ArrayList<TImage>) getIntent().getSerializableExtra("images");
        showImg();
    }

    private void showImg() {
        SimpleDraweeView imageView1 = findViewById(R.id.riv_pic);
    }
}
