package com.a21zhewang.constructionapp.receiver;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.a21zhewang.constructionapp.ui.IndexActivity;
import com.a21zhewang.constructionapp.ui.IndexActivity;
import com.a21zhewang.constructionapp.ui.LoginActivity;
import com.a21zhewang.constructionapp.ui.LoginActivity2;
import com.a21zhewang.constructionapp.ui.aqsc.AqscxqActivity;
import com.a21zhewang.constructionapp.ui.check.Act_Procedural;
import com.a21zhewang.constructionapp.ui.gtxtiu.GtxtxqActivity;
import com.a21zhewang.constructionapp.ui.gxys.GxysxqActivity;
import com.a21zhewang.constructionapp.ui.jygz.Act_Jygz;
import com.a21zhewang.constructionapp.ui.notice.Act_Notice;
import com.a21zhewang.constructionapp.ui.rcjd.Act_Rcjd;
import com.a21zhewang.constructionapp.ui.risk.Act_RiskReport;
import com.a21zhewang.constructionapp.ui.task.Act_TaskList;
import com.a21zhewang.constructionapp.ui.tzgg.TzggxqActivity;
import com.a21zhewang.constructionapp.ui.wdjc.WdjcActivity2;
import com.a21zhewang.constructionapp.ui.wdjc.WdjcxqActivity3;
import com.a21zhewang.constructionapp.ui.wmsg.WmsgxqActivity;
import com.a21zhewang.constructionapp.ui.zljd.ZljdxqActivity;
import com.a21zhewang.constructionapp.utils.AppShortCutUtil;
import com.a21zhewang.constructionapp.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;
import static android.content.Context.ACTIVITY_SERVICE;

public class PushMessageReceiver extends JPushMessageReceiver {
    public static int count = 0;
    private static final String TAG = "PushMessageReceiver";


    @Override
    public void onMessage(Context    context, CustomMessage customMessage) {
        Log.e(TAG, "[onMessage] " + customMessage);
        Intent intent = new Intent(AppUtils.getPackageName(context));
        intent.putExtra("msg", customMessage.message);
        context.sendBroadcast(intent);
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageOpened] " + message);
        try{
            //??????????????????Activity
            Bundle bundle = new Bundle();
            bundle.putString(JPushInterface.EXTRA_NOTIFICATION_TITLE,message.notificationTitle);
            bundle.putString(JPushInterface.EXTRA_ALERT,message.notificationContent);
            Log.e(TAG, "[onMultiActionClicked] ??????????????????????????????");
//            String nActionExtra = intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);
            String nActionExtra = message.notificationExtras.toString();
            Log.d(TAG, nActionExtra);
            //????????????????????? Action ????????? extra ?????????????????????????????????
            if (nActionExtra == null || "{}".equals(nActionExtra)) {
                Log.d(TAG, "ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null");
                /**??????????????????????????????????????????????????????*/
                if (0 != PushMessageReceiver.count) {
                    //????????????
                    PushMessageReceiver.count = 0;
                    AppShortCutUtil.setCount(PushMessageReceiver.count, context);
                }
                return;
            }
                JSONObject jsonObject = new JSONObject(nActionExtra);
                String Module = jsonObject.getString("Module");
                String DataObj = jsonObject.getString("DataObj");
                JSONObject object = new JSONObject(DataObj);
                Log.d(TAG, Module);
                Intent mintent = new Intent();
                if (!isExistMainActivity(context,IndexActivity.class)) {
                    // PublicUtils.toast("?????????");
                    Bundle bun = new Bundle();
                    bun.putString("Module", Module);
//                    bun.putString("msgID", msgID);
                    mintent.setClass(context, LoginActivity2.class);
                    mintent.putExtra("obj", bun);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(mintent);
                } else {
                    if ("Quality".equals(Module)) {                    //????????????
                        String recordId = object.optString("recordId");
                        mintent.putExtra("recordID", recordId);
                        mintent.setClass(context, ZljdxqActivity.class);
                    } else if ("Safety".equals(Module)) {              //????????????
                        String recordId = object.optString("recordId");
                        mintent.putExtra("recordID", recordId);
                        mintent.setClass(context, AqscxqActivity.class);
                    } else if ("Civilization".equals(Module)) {         //????????????
                        String recordId = object.optString("recordId");
                        mintent.putExtra("recordID", recordId);
                        mintent.setClass(context, WmsgxqActivity.class);
                    } else if ("KeyExamination".equals(Module)) {       //????????????
//                        String recordID = object.optStri g("recordID");
//                        String status = "2"; //2:????????? 3???????????????
//                        String proShortName = object.optString("proShortName");
//                        String createUserName = object.optString("createUserName");
//                        int checkType = object.optInt("checkType");
//                        mintent.putExtra("recordID",recordID);
//                        mintent.putExtra("CheckStatus",status);
//                        mintent.putExtra("proShortName",proShortName);
//                        mintent.putExtra("createUserName",createUserName);
//                        mintent.putExtra("checkType",checkType); //????????????????????????
//                        mintent.setClass(context, WdjcxqActivity3.class);
                        mintent.setClass(context, WdjcActivity2.class);
                    } else if ("ProgExamination".equals(Module)) {       //????????????
                        mintent.setClass(context, Act_Procedural.class);
                    } else if ("EasyNotice".equals(Module)){   //????????????
                        mintent.setClass(context, Act_Jygz.class);
                    } else if ("Notice2".equals(Module)) {  //???????????? ?????????
                        mintent.setClass(context, TzggxqActivity.class);
                    } else if("RiskReport".equals(Module)){ //????????????
                        mintent.setClass(context, Act_RiskReport.class);
                    } else if ("Notice3".equals(Module)) {  //????????????
                        mintent.setClass(context, Act_Notice.class);
                    } else if ("DayKeySupervise".equals(Module)) {  //????????????
                        mintent.setClass(context, Act_Rcjd.class);
                    }
                      else if("TaskSupervise".equals(Module)){
                        mintent.setClass(context, Act_TaskList.class);
                    } else{
                        /**?????????????????????????????????????????????????????????????????????????????????????????????*/
                        mintent.setClass(context, IndexActivity.class);
                        mintent.putExtra("INDEX_TAG", "INDEX_TAG");
                    }
                    mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(mintent);
                }
        }catch (Throwable throwable){
            Toast.makeText(context,"??????????????????????????????",Toast.LENGTH_SHORT).show();
            /**?????????????????????????????????*/
            if (0 != PushMessageReceiver.count) {
                //????????????
                PushMessageReceiver.count = 0;
                AppShortCutUtil.setCount(PushMessageReceiver.count, context);
            }
        }
    }

    @Override
    public void onMultiActionClicked(Context context, Intent intent) {

//        if (nActionExtra.equals("my_extra1")) {
//            Log.e(TAG, "[onMultiActionClicked] ??????????????????????????????");
//        } else if (nActionExtra.equals("my_extra2")) {
//            Log.e(TAG, "[onMultiActionClicked] ??????????????????????????????");
//        } else if (nActionExtra.equals("my_extra3")) {
//            Log.e(TAG, "[onMultiActionClicked] ??????????????????????????????");
//        } else {
//            Log.e(TAG, "[onMultiActionClicked] ????????????????????????????????????");
//        }
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageArrived] " + message);
        count = count + 1;
        AppShortCutUtil.setCount(count, context);
        Log.d("-=-", "===" + count);
    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageDismiss] " + message);
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        Log.e(TAG, "[onRegister] " + registrationId);
        Intent intent = new Intent("com.jiguang.demo.message");
        intent.putExtra("rid", registrationId);
        context.sendBroadcast(intent);
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        Log.e(TAG, "[onConnected] " + isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        Log.e(TAG, "[onCommandResult] " + cmdMessage);
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
//        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context,jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
//        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context,jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
//        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context,jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
//        TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context,jPushMessage);
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }

    @Override
    public void onNotificationSettingsCheck(Context context, boolean isOn, int source) {
        super.onNotificationSettingsCheck(context, isOn, source);
        Log.e(TAG, "[onNotificationSettingsCheck] isOn:" + isOn + ",source:" + source);
    }
    //?????????????????????????????????????????????
    private boolean isExistMainActivity(Context context,Class<?> activity){
        Intent intent = new Intent(context, activity);
        ComponentName cmpName = intent.resolveActivity(context.getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // ???????????????????????????activity
            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);  //????????????????????????????????????10???activity
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // ????????????????????????
                    flag = true;
                    break;  //???????????????????????????
                }
            }
        }
        return flag;
    }
}
