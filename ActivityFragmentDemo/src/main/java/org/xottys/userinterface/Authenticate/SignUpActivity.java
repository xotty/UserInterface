/**
 * 账号注册(sign up)界面，由AuthenticatorActivity进入
 * 用异步方式获取服务器token，然后将其与账号名称、密码等一并打包返回给AuthenticatorActivity
 * <p>
 * <br/>Copyright (C), 2013-2018, udinic
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:SignUpActivity
 * <br/>Date:Mar，2013
 * @author udinic
 * @version 1.0
 */
package org.xottys.userinterface.Authenticate;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import org.xottys.userinterface.R;

import static org.xottys.userinterface.Authenticate.AccountGeneral.sServerAuthenticate;
import static org.xottys.userinterface.Authenticate.AuthenticatorActivity.ARG_ACCOUNT_TYPE;
import static org.xottys.userinterface.Authenticate.AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT;
import static org.xottys.userinterface.Authenticate.AuthenticatorActivity.PARAM_USER_PASS;
import static org.xottys.userinterface.Authenticate.AuthenticatorActivity.KEY_ERROR_MESSAGE;

public class SignUpActivity extends Activity {
    private static final String TAG = "Authenticate";
    private String mAccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAccountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);

        setContentView(R.layout.act_register);
        //已经注册过，直接返回
        findViewById(R.id.alreadyMember).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        //增加新账号
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {

        // 用异步方式去服务器验证
        new AsyncTask<String, Void, Intent>() {

            String name = ((TextView) findViewById(R.id.name)).getText().toString().trim();
            String accountName = ((TextView) findViewById(R.id.accountName)).getText().toString().trim();
            String accountPassword = ((TextView) findViewById(R.id.accountPassword)).getText().toString().trim();

            @Override
            protected Intent doInBackground(String... params) {

                Log.d(TAG,  "SignUpActivity > Started authenticating");

                String authtoken = null;
                Bundle data = new Bundle();
                try {
                    //调用去服务器注册的程序，获取token
                    authtoken = sServerAuthenticate.userSignUp(name, accountName, accountPassword, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
                    //将全部账号信息打包
                    data.putString(AccountManager.KEY_ACCOUNT_NAME, accountName);
                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, mAccountType);
                    data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
                    data.putString(PARAM_USER_PASS, accountPassword);
                    data.putBoolean(ARG_IS_ADDING_NEW_ACCOUNT,true);
                } catch (Exception e) {
                    data.putString(KEY_ERROR_MESSAGE, e.getMessage());
                }

                //将注册结果放入Bundle
                final Intent res = new Intent();
                res.putExtras(data);
                return res;
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                } else {
                    //将Bundle附属的intent放入返回结果中
                    setResult(RESULT_OK, intent);

                    finish();
                }
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
