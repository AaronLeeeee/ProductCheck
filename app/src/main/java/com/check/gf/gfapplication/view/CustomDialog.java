package com.check.gf.gfapplication.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.check.gf.gfapplication.R;
import com.flyco.dialog.widget.base.BaseDialog;

/**
 * time   : 2018/5/2
 * desc   :
 */
public class CustomDialog extends BaseDialog<CustomDialog> {
    private ClearEditText et_equipment_no_second;
    private Button btn_start;
    private Context mContext;
    private int mPosition;
    private String mEquipmentNo;

    private OnStartQueryEquipmentListener mCallback;

    public CustomDialog(Context context, int position, String equipmentNo) {
        super(context);
        mContext = context;
        mCallback = (OnStartQueryEquipmentListener) context;
        mPosition = position;
        mEquipmentNo = equipmentNo;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        // showAnim(new Swing());
        // dismissAnim(this, new ZoomOutExit());

        View inflate = View.inflate(mContext, R.layout.dialog_custom_edit, null);
        et_equipment_no_second = inflate.findViewById(R.id.et_equipment_no_second);
        btn_start = inflate.findViewById(R.id.btn_start);

        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        et_equipment_no_second.setText(mEquipmentNo);
        btn_start.setOnClickListener(v -> {
            String equipmentNoSecond = et_equipment_no_second.getText().toString().trim();
            if (TextUtils.isEmpty(equipmentNoSecond)) {
                ToastUtils.showShort("请先输入次要检验单号");
            } else {
                mCallback.onStartQueryEquipmentListener(mPosition, equipmentNoSecond);
            }
            dismiss();
        });
    }


    public interface OnStartQueryEquipmentListener {
        void onStartQueryEquipmentListener(int position, String equipmentNoSecond);
    }

}
