/**
 * 1）在manifests中特别注册的服务，intenfilter和meta-data均为特定值
 * 2）由系统服务AccountManagerService负责启动，AccountManagerService在AccountManager.addAccount时会被启动
 * 3）主要作用是在onBind中指定AccountAuthenticatorActivity的实现类，以便系统调用
 * <p>
 * <br/>Copyright (C), 2013-2018, udinic
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Mar，2013
 * @author udinic
 * @version 1.0
 */
package org.xottys.userinterface.Authenticate;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class MyAuthenticatorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("AuthenticatorService start");
        MyAuthenticator authenticator = new MyAuthenticator(this);

        return authenticator.getIBinder();
    }
}
