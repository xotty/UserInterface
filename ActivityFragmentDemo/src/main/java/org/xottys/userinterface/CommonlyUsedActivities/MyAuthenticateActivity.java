/**
 * 本例和Authenticate包一起演示了Android账号认证管理机制，其中包括AccountAuthenticatorActivity的用法：
 * 1)获取AccountManager的实例：AccountManager.get(this)
 * 2)调用AccountManager的相关方法：addAccount、getAccountsByType、removeAccount、renameAccount等等
 * 3)处理上述异步方法的返回结果
 * addAccount内部实现过程为：
 * 1）AccountManager通过Binder机制与系统服务AccountManagerService进行通信，启动MyAuthenticatorService
 * 2）通过MyAuthenticatorService启动MyAuthenticator中的addAccount方法
 * 3）携带全部账号类型信息跳转到AuthenticatorActivity
 * 4）呈现登录(Sign in)页面和一个注册按钮以便能跳转到注册(Sign up)页面
 * 5) 调用服务器账号验证程序，获取其返回的token
 * 6）若返回的token为null则认证失败，否则本地添加账号或成功登录
 * <p>
 * <br/>Copyright (C), 2013-2018, udinic
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Mar. 2013
 *
 * @author udinic,xotty@163.com
 * @version 1.0
 */
package org.xottys.userinterface.CommonlyUsedActivities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.xottys.userinterface.Authenticate.AccountGeneral;
import org.xottys.userinterface.R;

import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static org.xottys.userinterface.Authenticate.AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;

public class MyAuthenticateActivity extends Activity {
    private static final String TAG = "Authenticate";
    private static final String STATE_DIALOG = "state_dialog";
    private static final String STATE_INVALIDATE = "state_invalidate";

    private AccountManager mAccountManager;
    private AlertDialog mAlertDialog;
    private boolean mInvalidate, mRemove, mRename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authenticator);
        mAccountManager = AccountManager.get(this);
        //登录或注册
        findViewById(R.id.btnAddAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewAccount(AccountGeneral.ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS);
            }
        });
        //显示全部当前类型账号
        findViewById(R.id.btnGetAuthToken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRemove = false;
                mRename = false;
                showAccountPicker(AUTHTOKEN_TYPE_FULL_ACCESS, false);
            }
        });
        //给账号改名
        findViewById(R.id.btnRenameAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRemove = false;
                mRename = true;
                showAccountPicker(AUTHTOKEN_TYPE_FULL_ACCESS, false);
                // getTokenForAccountCreateIfNeeded(AccountGeneral.ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS);
            }
        });

        //清空缓存中的账号Token，使其失效
        findViewById(R.id.btnInvalidateAuthToken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRemove = false;
                mRename = false;
                showAccountPicker(AUTHTOKEN_TYPE_FULL_ACCESS, true);
            }
        });

        //删除账号
        findViewById(R.id.btnRemoveAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRemove = true;
                mRename = false;
                showAccountPicker(AUTHTOKEN_TYPE_FULL_ACCESS, false);
            }
        });

        if (savedInstanceState != null) {
            boolean showDialog = savedInstanceState.getBoolean(STATE_DIALOG);
            boolean invalidate = savedInstanceState.getBoolean(STATE_INVALIDATE);
            if (showDialog) {
                showAccountPicker(AUTHTOKEN_TYPE_FULL_ACCESS, invalidate);
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            outState.putBoolean(STATE_DIALOG, true);
            outState.putBoolean(STATE_INVALIDATE, mInvalidate);
        }
    }

    /**
     * Add new account to the account manager
     */
    private void addNewAccount(String accountType, String authTokenType) {
        System.out.println("Main 1");
        //添加账号为异步方法，
        final AccountManagerFuture<Bundle> future = mAccountManager.addAccount(accountType, authTokenType, null, null, this, new AccountManagerCallback<Bundle>() {
            //账号添加完成后执行本回调方法
            @Override
            public void run(AccountManagerFuture<Bundle> future) {
                try {
                    Bundle bnd = future.getResult();

                    String str = (String) bnd.get("action");
                    if (str.equals("Login Succeed"))
                        showMessage(getResources().getString(R.string.signin_succeed));
                    else if (str.equals("Register Succeed"))
                        showMessage(getResources().getString(R.string.signup_succeed));

                    Log.d(TAG, "MyAuthenticateActivity > AddNewAccount Bundle is " + bnd);
                    System.out.println("Main 2-----" + str);
                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                    System.out.println("Main 3");
                }
            }
        }, null);
    }

    /**
     * Show all the accounts registered on the account manager. Request an auth token upon user select.
     */
    private void showAccountPicker(final String authTokenType, final boolean invalidate) {
        mInvalidate = invalidate;
        final Account availableAccounts[] = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);

        if (availableAccounts.length == 0) {
            Toast.makeText(this, "No accounts", Toast.LENGTH_SHORT).show();
        } else {
            //获得所有账号的name
            String name[] = new String[availableAccounts.length];
            for (int i = 0; i < availableAccounts.length; i++) {
                name[i] = availableAccounts[i].name;
            }

            //将name显示到Account picker中去
            mAlertDialog = new AlertDialog.Builder(this).setTitle("Pick Account").setAdapter(new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, name), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (invalidate)
                        invalidateAuthToken(availableAccounts[which], authTokenType);
                    else
                        getExistingAccountAuthToken(availableAccounts[which], authTokenType);
                }
            }).create();
            mAlertDialog.show();
        }
    }

    /**
     * Get the auth token for an existing account on the AccountManager
     *
     */
    private void getExistingAccountAuthToken(Account account, String authTokenType) {
        final AccountManagerFuture<Bundle> future = mAccountManager.getAuthToken(account, authTokenType, null, this, null, null);
        System.out.println("Main 21-----");
        if (mRemove) {
            removeExistingAccount(account);
        } else if (mRename) {
            renameAccount(account, "newName");
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bundle bnd = future.getResult();

                        final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                        showMessage((authtoken != null) ? "SUCCESS!\ntoken: " + authtoken : "FAIL");
                        Log.d(TAG, "GetToken Bundle is " + bnd);
                    } catch (Exception e) {
                        e.printStackTrace();
                        showMessage(e.getMessage());
                    }
                }
            }).start();
        }
    }

    //给账户改名
    private void renameAccount(final Account account, String newName) {
        final AccountManagerFuture<Account> future = mAccountManager.renameAccount(account, newName, new AccountManagerCallback<Account>() {
            //账号名称修改完成后执行本回调方法
            @Override
            public void run(AccountManagerFuture<Account> future) {
                try {
                    Account mAccount = future.getResult();
                    showMessage("账号改名成功:" + mAccount.name);
                    Log.d(TAG, "MyAuthenticateActivity > newAccount is " + account);
                    System.out.println("Main 31-----");
                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                    System.out.println("Main 32");
                }
            }
        }, null);
    }

    //删除账户
    private void removeExistingAccount(final Account account) {
        final AccountManagerFuture<Bundle> future = mAccountManager.removeAccount(account, this, new AccountManagerCallback<Bundle>() {
            //账号删除完成后执行本回调方法
            @Override
            public void run(AccountManagerFuture<Bundle> future) {
                try {
                    Bundle bnd = future.getResult();
                    if((boolean)bnd.get(KEY_BOOLEAN_RESULT))
                        showMessage("删除账号成功:" + account.name);
                    else
                        showMessage("删除账号失败:" + account.name);

                    Log.d(TAG, "MyAuthenticateActivity > RemoveAccount, Bundle is " + bnd);
                    System.out.println("Main 51-----");
                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                    System.out.println("Main 52");
                }
            }
        }, null);
    }

    /**
     * Invalidates the auth token for the account
     *
     */
    private void invalidateAuthToken(final Account account, String authTokenType) {
        final AccountManagerFuture<Bundle> future = mAccountManager.getAuthToken(account, authTokenType, null, this, null, null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle bnd = future.getResult();

                    final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                    mAccountManager.invalidateAuthToken(account.type, authtoken);
                    showMessage(account.name + " invalidated");
                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                }
            }
        }).start();
    }

    /**
     * Get an auth token for the account.
     * If not exist - add it and then return its auth token.
     * If one exist - return its auth token.
     * If more than one exists - show a picker and return the select account's auth token.
     *
     */
    private void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType) {
        final AccountManagerFuture<Bundle> future = mAccountManager.getAuthTokenByFeatures(accountType, authTokenType, null, this, null, null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        Bundle bnd;
                        try {
                            bnd = future.getResult();
                            final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                            showMessage(((authtoken != null) ? "SUCCESS!\ntoken: " + authtoken : "FAIL"));
                            Log.d(TAG, "GetTokenForAccount Bundle is " + bnd);

                        } catch (Exception e) {
                            e.printStackTrace();
                            showMessage(e.getMessage());
                        }
                    }
                }
                , null);
    }

    //用Toast显示信息
    private void showMessage(final String msg) {
        if (TextUtils.isEmpty(msg))
            return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
