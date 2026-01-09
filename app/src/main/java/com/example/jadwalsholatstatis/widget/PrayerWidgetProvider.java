package com.example.jadwalsholatstatis.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.widget.RemoteViews;

import com.example.jadwalsholatstatis.MainActivity;
import com.example.jadwalsholatstatis.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PrayerWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.widget_prayer);
            String formattedDate = new SimpleDateFormat("EEEE, dd MMM yyyy",
                    new Locale("id", "ID")).format(new Date());
            views.setTextViewText(R.id.widgetDate, formattedDate);

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                            ? PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                            : PendingIntent.FLAG_UPDATE_CURRENT
            );
            views.setOnClickPendingIntent(R.id.widgetRoot, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
