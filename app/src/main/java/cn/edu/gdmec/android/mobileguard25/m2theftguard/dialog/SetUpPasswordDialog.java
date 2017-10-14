package cn.edu.gdmec.android.mobileguard25.m2theftguard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.edu.gdmec.android.mobileguard25.R;


public class SetUpPasswordDialog extends Dialog implements View.OnClickListener {
    private TextView mTitleTV;
    public EditText mFirstPWDET;
    public EditText mAffirmET;
    private MyCallBack myCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.setup_password_dialog);
        super.onCreate(savedInstanceState);
        initView();

    }


    public SetUpPasswordDialog(@NonNull Context context) {
        super(context, R.style.dialog_custom);
    }

    private void initView() {
        mTitleTV = findViewById(R.id.tv_setuppwd_title);
        mFirstPWDET = findViewById(R.id.et_firstpwd);
        mAffirmET = findViewById(R.id.et_affirm_password);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);

    }



     void setmTitleTV(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitleTV.setText(title);
        }
    }

public void setMyCallBack(MyCallBack myCallBack){
    this.myCallBack = myCallBack;
}


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ok:
                System.out.print("SetupPasswordDialog");
                myCallBack.ok();
                break;
            case R.id.btn_cancel:
                myCallBack.cancle();
                break;
        }

    }
    public interface MyCallBack{
        void ok();
        void cancle();
        void cancel();
        void confirm();
    }
}
