/**
 * 1）AccountAuthenticatorActivity的子类，呈现登录界面，可以跳转注册页面
 * 2）处理登录操作，去服务器获取token
 * 3) 根据token确定注册登录结果，若失败则本地显示错误信息
 * 4）注册登录成功则添加账户或示登录验证成功，将成功结果传递给Authenticator
 * <p>
 * <br/>Copyright (C), 2013-2018, udinic
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Mar，2013
 * @author udinic
 * @version 1.0
 */
package org.xottys.userinterface.Authenticate;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.xottys.userinterface.R;

import static org.xottys.userinterface.Authenticate.AccountGeneral.sServerAuthenticate;

public class AuthenticatorActivity extends AccountAuthenticatorActivity {
    private static final String TAG = "Authenticate";
    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";

    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";

    public final static String PARAM_USER_PASS = "USER_PASS";

    private final int REQ_SIGNUP = 1;

    private AccountManager mAccountManager;
    private String mAuthTokenType;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        mAccountManager = AccountManager.get(getBaseContext());
        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
        mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        System.out.println("AuthenticatorActivity1--"+accountName+"--"+mAuthTokenType);

        if (mAuthTokenType == null)
            mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;

        if (accountName != null) {
            ((TextView)findViewById(R.id.accountName)).setText(accountName);
        }

        //处理登录(sign in)信息
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit();
            }
        });

        //跳转到注册(sign up)页面
        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Since there can only be one AuthenticatorActivity, we call the sign up activity, get his results,
                // and return them in setAccountAuthenticatorResult(). See finishLogin().

                Intent signup = new Intent(getBaseContext(), SignUpActivity.class);
                signup.putExtras(getIntent().getExtras());
                startActivityForResult(signup, REQ_SIGNUP);
            }
        });
    }

    //处理注册页面返回的信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // The sign up activity returned that the user has successfully created an account
        if (requestCode == REQ_SIGNUP && resultCode == RESULT_OK) {
            //成功获取账号注册的Token后执行
            //finishLogin(data);
            onAuthenticationResult(data);
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    //账户登录结果处理第一步：用异步方式获取服务器Token
    public void submit() {

        final String userName = ((TextView) findViewById(R.id.accountName)).getText().toString();
        final String userPass = ((TextView) findViewById(R.id.accountPassword)).getText().toString();

        final String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);

        new AsyncTask<String, Void, Intent>() {

            @Override
            protected Intent doInBackground(String... params) {

                Log.d( TAG,  "AuthenticatorActivity > Started authenticating");

                String authtoken = null;
                Bundle data = new Bundle();
                try {
                    //调用服务器登录方法获取token
                    authtoken = sServerAuthenticate.userSignIn(userName, userPass, mAuthTokenType);

                    data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                    data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
                    data.putString(PARAM_USER_PASS, userPass);
                    data.putBoolean(ARG_IS_ADDING_NEW_ACCOUNT,false);

                } catch (Exception e) {
                    data.putString(KEY_ERROR_MESSAGE, e.getMessage());
                }

                final Intent res = new Intent();
                res.putExtras(data);
                return res;
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                } else {
                    onAuthenticationResult(intent);
                }
            }

            @Override
            protected void onCancelled() {
                // If the action was canceled (by the user clicking the cancel
                // button in the progress dialog), then call back into the
                // activity to let it know.
                onAuthenticationCancel();
            }
        }.execute();
    }
    /**账号登录注册处理第二步，判断是否成功
     * Called when the authentication process completes
     * @param intent include authentication token returned by the server, or NULL if
     *            authentication failed.
     */
    public void onAuthenticationResult(Intent intent) {
        String authToken=intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
        //验证是否成功取决于token值
        boolean success = ((authToken != null) && (authToken.length() > 0));
        Log.i(TAG, "onAuthenticationResult: "+ success );

        //验证成功，执行收尾程序
        if (success) {
                finishLogin(intent);
       //验证不成功，显示错误信息
        } else {
            Log.e(TAG, "onAuthenticationResult: failed to authenticate");
            if (intent.getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
                // "Please enter a valid username/password.
                Toast.makeText(this.getApplicationContext(), getText(R.string.login_activity_loginfail_text_both), Toast.LENGTH_LONG).show();
            } else {
                // "Please enter a valid password." (Used when the
                // account is already in the database but the password
                // doesn't work.)
                Toast.makeText(this.getApplicationContext(), getText(R.string.login_activity_loginfail_text_pwonly), Toast.LENGTH_LONG).show();

            }
        }
    }

    public void onAuthenticationCancel() {
        Log.i(TAG, "onAuthenticationCancel()");

        // Our task is complete, so clear it out
        //mAuthTask = null;

        // Hide the progress dialog
        //hideProgress();
    }

    //账号登录注册处理第三步，成功后在AccountMangager中添加账号或显示登录验证成功
    private void finishLogin(Intent intent) {
        Log.d(TAG,  "AuthenticatorActivity > finishLogin");
        System.out.println("AuthenticatorActivity2");
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        //如果是新增账号则在AccountMangager中添加账号
        if (intent.getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            Log.d(TAG , "AuthenticatorActivity > finishLogin > addAccountExplicitly");
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authtokenType = mAuthTokenType;

            // Creating the account on the device and setting the auth token we got
            // (Not setting the auth token will cause another call to the server to authenticate the user)
            mAccountManager.addAccountExplicitly(account, accountPassword, null);
            mAccountManager.setAuthToken(account, authtokenType, authtoken);
            intent.putExtra("action","Register Succeed");
        }
        //如果旧账号成功登录
        else {
           intent.putExtra("action","Login Succeed");
            Log.d(TAG, "AuthenticatorActivity > finishLogin > succeed："+account+"／"+accountPassword);
           // mAccountManager.setPassword(account, accountPassword);
        }

        //这是验证成功结果，返回调用者Authenticator，进一步传递到MyAuthenticateActivity
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

}
