package com.example.dell.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        Intent showIngredientIntent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,showIngredientIntent,0);
        Log.d("Widget:","In OnReceive");
        views.setOnClickPendingIntent(R.id.show_recipe_widget,pendingIntent);

        SharedPreferences sharedPreferences = context.getSharedPreferences("RECIPE_PREF",Context.MODE_PRIVATE);
        String recipeName = sharedPreferences.getString("RECIPE_NAME","");
        String ingredients = sharedPreferences.getString("RECIPE_INGREDIENTS","");
        String ingredients_qty = sharedPreferences.getString("RECIPE_INGREDIENTS_QTY","");

        views.setTextViewText(R.id.recipe_name_widget, recipeName);
        views.setTextViewText(R.id.ingredients_name_widget, ingredients);
        views.setTextViewText(R.id.ingredients_qty_widget, ingredients_qty);
        AppWidgetManager.getInstance(context).updateAppWidget(
                new ComponentName(context, RecipeWidgetProvider.class), views);
    }
}

