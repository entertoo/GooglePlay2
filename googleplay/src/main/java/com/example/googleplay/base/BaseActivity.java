package com.example.googleplay.base;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.googleplay.activity.MainActivity;
import com.example.googleplay.utils.UIUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 公共属性
 * 公共方法
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private List<AppCompatActivity> activities = new LinkedList<>();
    private long mPreTime;

    // 最上层的Activity
    private Activity mCurrentActivity;

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPermission();

        // 初始化传递的参数等等
        init();

        // 初始化View
        initView();

        // 初始ActionBar
        initActionBar();

        // 初始化Data
        initData();

        // 初始化监听器
        initListener();

        activities.add(this);
    }

    private void getPermission() {
        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // if not get permission
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                System.out.println("shouldShowRequestPermissionRationale: true");
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(this, "Permission not get", Toast.LENGTH_SHORT).show();

            } else {
                System.out.println("shouldShowRequestPermissionRationale: false");
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    protected void onDestroy() {
        activities.remove(this);
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    System.out.println("onRequestPermissionsResult: true");

                } else {
                    System.out.println("onRequestPermissionsResult: false");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * 完全退出
     */
    public void exit() {
        // 结束所有的activity
        for (AppCompatActivity activity : activities) {
            activity.finish();
        }
    }

    /**
     * 获取焦点的时候保存当前Activity
     */
    @Override
    protected void onResume() {
        mCurrentActivity = this;
        super.onResume();
    }

    /**
     * 连续双击退出
     */
    @Override
    public void onBackPressed() {
        if (this instanceof MainActivity) {
            // 如果两次操作时间超过2秒
            if ((System.currentTimeMillis() - mPreTime) > 2000) {
                Toast.makeText(UIUtils.getContext(), "再点击一次退出", Toast.LENGTH_SHORT).show();
                mPreTime = System.currentTimeMillis();
                return;
            }
        }
        super.onBackPressed();// finish();
    }

    /**
     * 初始化传递的参数等等
     */
    public void init() {
        // TODO Auto-generated method stub

    }

    /**
     * 初始化视图，必须实现的方法不知道具体实现，让其子类实现
     */
    public abstract void initView();

    /**
     * 初始化ActionBar
     */
    public void initActionBar() {
        // TODO Auto-generated method stub

    }

    /**
     * 初始化数据
     */
    public void initData() {
        // TODO Auto-generated method stub

    }

    /**
     * 设置监听器
     */
    public void initListener() {
        // TODO Auto-generated method stub

    }
}
