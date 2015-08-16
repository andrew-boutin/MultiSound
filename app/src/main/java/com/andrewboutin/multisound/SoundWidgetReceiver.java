package com.andrewboutin.multisound;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.RemoteViews;

/**
 * Created by Andrew on 8/16/2015.
 */
public class SoundWidgetReceiver extends BroadcastReceiver {
    private static int clickCounter = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("pl.looksok.intent.action.PLAYSOUND")){
            playSound(context);
        }
    }

    private void playSound(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widgetlayout);
        //remoteViews.setImageViewResource(R.id.playButton, getImageToSet());

        clickCounter++;
        remoteViews.setTextViewText(R.id.playButton, "Click Count: " + clickCounter);

        //REMEMBER TO ALWAYS REFRESH YOUR BUTTON CLICK LISTENERS!!!
        remoteViews.setOnClickPendingIntent(R.id.playButton, SoundWidgetProvider.buildButtonPendingIntent(context));

        SoundWidgetProvider.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
    }
}
