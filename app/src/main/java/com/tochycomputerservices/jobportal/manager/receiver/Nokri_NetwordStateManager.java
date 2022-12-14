package com.tochycomputerservices.jobportal.manager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tochycomputerservices.jobportal.manager.Nokri_AppLifeCycleManager;
import com.tochycomputerservices.jobportal.manager.activities.Nokri_NoIternetDialogueActivity;
import com.tochycomputerservices.jobportal.utils.Nokri_Utils;

/**
 * Created by GlixenTech on 3/21/2018.
 */

public class Nokri_NetwordStateManager extends BroadcastReceiver {
    private static  int counter = 0;
    private Nokri_AppLifeCycleManager appLifeCycleManager;
    @Override
    public void onReceive(Context context, Intent intent) {


        int status = Nokri_Utils.getConnectivityStatusString(context);
        Log.e("network reciever", "Sulod sa network reciever");
        if (!"android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {

             appLifeCycleManager = Nokri_AppLifeCycleManager.get(context);
            if(appLifeCycleManager.isForeground()) {
                if (status == Nokri_Utils.NETWORK_STATUS_NOT_CONNECTED) {

                    if (counter == 0) {
                        ++counter;

                        Nokri_Utils.disableInternetReceiver(context);
                        Intent i = new Intent(context, Nokri_NoIternetDialogueActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      /*  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
                        context.startActivity(i);
                    }
                } else {

                }

            }
        }
    }



}
