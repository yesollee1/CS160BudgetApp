package cs160.UILayer;

import cs160.dataLayer.Budget;

import android.app.Application;
import android.content.res.Configuration;

import java.util.Calendar;
import java.util.Date;

public class OnCreateApplication extends Application {
    private static Date lastDate;
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int thisYear = cal.get(Calendar.YEAR);
        int thisMonth = cal.get(Calendar.MONTH);
        cal.setTime(lastDate);
        int lastYear = cal.get(Calendar.YEAR);
        int lastMonth = cal.get(Calendar.MONTH);
        if (thisYear > lastYear || thisMonth > lastMonth) {
            Budget.resetBudget(this);
        }
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}