package cn.edu.gdmec.android.mobileguard25.m1home;


import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import cn.edu.gdmec.android.mobileguard25.R;
import cn.edu.gdmec.android.mobileguard25.m1home.adapter.HomeAdapter;
import cn.edu.gdmec.android.mobileguard25.m2theftguard.dialog.InterPasswordDialog;
import cn.edu.gdmec.android.mobileguard25.m2theftguard.dialog.SetUpPasswordDialog;
import cn.edu.gdmec.android.mobileguard25.m2theftguard.utils.MD5Utils;

public class HomeActivity extends AppCompatActivity{
    private long mExitTime;
    private SharedPreferences msharedPreferences;
    protected DevicePolicyManager policyManager;
    protected ComponentName componentName;

    public HomeActivity(DevicePolicyManager policyManager, ComponentName componentName) {
        this.policyManager = policyManager;
        this.componentName = componentName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        msharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        GridView gv_home = (GridView) findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(HomeActivity.this));
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (isSetUpPassword()) {
                            showInterPswdDialog();
                        } else {
                            showSetUpPswDialog();
                        }
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case  4:
                        break;
                    case  5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;

                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent keyEvent ) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) >2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
                mExitTime = System.currentTimeMillis();
            }else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode,keyEvent);
    }

    //policyManager=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
    //componentName=new ComponentName(this,MyDeviceAdminRr.class);
    //boolean active=policyManager.isAdminActive(componentName);
    //if(!active){
    //Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
    //intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
    //intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,"获取少管");
    //startActivity(intent);

    //}
    //}


    private  void showSetUpPswDialog(){
        final SetUpPasswordDialog setUpPasswordDialog = new SetUpPasswordDialog(HomeActivity.this);
        setUpPasswordDialog.setMyCallBack(new SetUpPasswordDialog.MyCallBack(){
            @Override
            public  void ok(){
                String firstPwsd = setUpPasswordDialog.mFirstPWDET.getText().toString().trim();
                String affirmPwsd = setUpPasswordDialog.mAffirmET.getText().toString().trim();
                if(!TextUtils.isEmpty(firstPwsd)&&!TextUtils.isEmpty(affirmPwsd)){
                    if(firstPwsd.equals(affirmPwsd)){
                        savePswd(affirmPwsd);
                        setUpPasswordDialog.dismiss();
                        showInterPswdDialog();
                    }else{
                        Toast.makeText(HomeActivity.this,"两次密码不一样",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(HomeActivity.this, "密码不为空！", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void cancle() {

            }


            @Override
            public void cancel(){
                setUpPasswordDialog.dismiss();
            }

            @Override
            public void confirm() {

            }
        });
        setUpPasswordDialog.setCancelable(true);
        setUpPasswordDialog.show();
    }

    private  void showInterPswdDialog(){
        final String password = getPassword();
        final InterPasswordDialog mInPswdDialog = new InterPasswordDialog(HomeActivity.this);
        mInPswdDialog.setMyCallBack(new InterPasswordDialog.MyCallBack(){
            @Override
            public  void confirm(){
                if(TextUtils.isEmpty(mInPswdDialog.getPassword())){
                    Toast.makeText(HomeActivity.this,"密码不为空",Toast.LENGTH_LONG).show();
                }else if(password.equals(MD5Utils.encode(mInPswdDialog.getPassword()))){
                    mInPswdDialog.dismiss();
                    Toast.makeText(HomeActivity.this,"可以进入手机防盗模块",Toast.LENGTH_LONG).show();
                }else {
                    mInPswdDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "密码有误，请重新输入", Toast.LENGTH_LONG).show();
                }
            }



            @Override
            public void cancel(){
                mInPswdDialog.dismiss();
            }
        });
        mInPswdDialog.setCancelable(true);
        mInPswdDialog.show();
    }
    private void savePswd(String affirmPwsd){
        SharedPreferences.Editor edit = msharedPreferences.edit();
        edit.putString("PhoneAntiTheftPWD",MD5Utils.encode(affirmPwsd));
        edit.apply();
    }
    private  String getPassword(){
        String password = msharedPreferences.getString("PhoneAntiTheftPWD",null);
        if(TextUtils.isEmpty(password)){
            return "";
        }
        return password;
    }
    private boolean isSetUpPassword() {
        String password = msharedPreferences.getString("PhoneAntiTheftPWD", null);
        return !TextUtils.isEmpty(password);

    }
}

