/**
 * 演示Activity生命骤周期用到的工具类，通过反射方式获取Activity栈内容(无序)
 * 按照"栈顶-中间-栈底"的顺序输出其中Activity的名称
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name: FragmentGeneral
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.ActivityLifecycle;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppGlobal extends Application {
    private static AppGlobal instance;
    private final String TAG = "UserInterface";
    private ActivityManager manager;

    public static AppGlobal getInstance() {
        return instance;
    }

    private List<Activity> getActivitiesByApplication(Application application) {
        List<Activity> list = new ArrayList<>();
        try {
            Class<Application> applicationClass = Application.class;
            Field mLoadedApkField = applicationClass.getDeclaredField("mLoadedApk");
            mLoadedApkField.setAccessible(true);
            Object mLoadedApk = mLoadedApkField.get(application);
            Class<?> mLoadedApkClass = mLoadedApk.getClass();
            Field mActivityThreadField = mLoadedApkClass.getDeclaredField("mActivityThread");
            mActivityThreadField.setAccessible(true);
            Object mActivityThread = mActivityThreadField.get(mLoadedApk);
            Class<?> mActivityThreadClass = mActivityThread.getClass();
            Field mActivitiesField = mActivityThreadClass.getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            Object mActivities = mActivitiesField.get(mActivityThread);
            // 注意这里一定写成Map，低版本这里用的是HashMap，高版本用的是ArrayMap
            if (mActivities instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<Object, Object> arrayMap = (Map<Object, Object>) mActivities;
                int seq = 0;
                for (Map.Entry<Object, Object> entry : arrayMap.entrySet()) {
                    Object value = entry.getValue();
                    Class<?> activityClientRecordClass = value.getClass();
                    Field activityField = activityClientRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Object o = activityField.get(value);
                    list.add((Activity) o);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }

    public String createtActivityStackInfo() {

        List<ActivityManager.RunningTaskInfo> runningTasks = manager.getRunningTasks(5);
        String base = runningTasks.get(0).baseActivity.getShortClassName();
        base = base.substring(1);
        String top = runningTasks.get(0).topActivity.getShortClassName();
        top = top.substring(1);
        int swithcNumber = runningTasks.get(0).numActivities;
        List<Activity> list = null;
        String str = "";
        StringBuilder midStr = new StringBuilder("");
        switch (swithcNumber) {
            case 0:
                break;
            case 1:
                str = top;
                break;
            case 2:
                str = top + "\n\u21E7\n" + base;
                break;
            default:
                boolean topflag = false;
                boolean baseflag = false;
                list = getActivitiesByApplication(this);
                for (Activity aty : list) {
                    if (!aty.isFinishing()) {
                        if (!aty.getLocalClassName().equals("SingleInstanceActivity")) {
                            if (aty.getLocalClassName().equals(top)) {
                                if (topflag)
                                    midStr.append(aty.getLocalClassName() + "/");
                                else
                                    topflag = true;
                            } else if (aty.getLocalClassName().equals(base)) {
                                if (baseflag)
                                    midStr.append(aty.getLocalClassName() + "/");
                                else
                                    baseflag = true;
                            } else
                                midStr.append(aty.getLocalClassName() + "/");
                        }
                    }
                }
                if (midStr.length() > 0) {
                    midStr.deleteCharAt(midStr.length() - 1);
                }
                str = top + "\n\u21E7\n(" + midStr + ")\n\u21E7\n" + base;
                break;
        }
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
            Log.v(TAG, "Task ID:"+runningTaskInfo.id + "---Activity数量:" + runningTaskInfo.numActivities + "---Base:" + runningTaskInfo.baseActivity.getShortClassName() + "---Top:" + runningTaskInfo.topActivity.getShortClassName());
        }
        return ("Task ID：" + runningTasks.get(0).id + "          当前Activity数量：" + runningTasks.get(0).numActivities + "\n\n\n" + str);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    }
}
