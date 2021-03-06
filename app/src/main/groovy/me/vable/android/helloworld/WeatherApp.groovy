package me.vable.android.helloworld

import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import android.view.Display
import android.view.WindowManager
import android.widget.Toast

class WeatherApp extends Application {

    String weatherData = ''

    SharedPreferences mPref;    //user preferences, originally singleton


    private static WeatherApp instance; //A singleton instance of the application class for easy access in other places
    private static Context sContext;    //Keeps a reference of the application context, in case "not inside an Activity but need the context"
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        instance.initializeInstance()   // initialize the singleton
    }

    @Override
    public void onTerminate() {
        super.onTerminate();        // Do your application wise Termination task
    }
    /**
     * @return WeatherApp singleton instance
     */
    public static synchronized WeatherApp getInstance() {
        return instance;
    }
    /**
     * use " YourApplication.getInstance().getContext() " anywhere,   even not in an activity
     */
    public static Context getContext() {
        return sContext;
    }

    /**
     * do your app’s version wise specialized task
     * or may be something that you need to be done before the creation of your first activity
     * e.g. screenConfiguration() method to determine is a  Tab ? Phone?
     */

    private void initializeInstance() {
        // initiate the context
        sContext = getApplicationContext();
        // set application wise preference
        mPref = getApplicationContext().getSharedPreferences("Global_Pref", MODE_PRIVATE);
        // init MyVolley for async communication between client/server
        MyVolley.init(this)
        // Do your application wise initialization task
        screenConfiguration();
    }
    /**
     *  determining the size of ScreenWidth and ScreenHeight
     * and it also helpful if you like to know the device is a [ tab or phone ]
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)       //last param prevents the compilation error where minSdkVersion < 13
    public void screenConfiguration() {
        Configuration config = getResources().getConfiguration();
        boolean isTab = (config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;


        Point size = new Point();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        int deviceScreenWidth;
        int deviceScreenHeight;

        try {
            display.getSize(size);
            deviceScreenWidth = size.x;
            deviceScreenHeight = size.y;
        } catch (NoSuchMethodError e) {
            deviceScreenWidth = display.getWidth();
            deviceScreenHeight = display.getHeight();
        }
    }

    /**
     *   isFirstRun()    if the app runs for the first time.
     *          e.g. you get to show the user the EULA (End User License Agreement) page for the first time.
     */
    public boolean isFirstRun() {
        return mPref.getBoolean("is_first_run", true);  // return true if the app is running for the first time
    }
    /**
     *  setRunned()   after firstRun , set a flag after first run
     */
    public void setRunned() {
        // after a successful run, call this method to set first run false
        SharedPreferences.Editor edit = mPref.edit();
        edit.putBoolean("is_first_run", false);
        edit.commit();
    }


    /**
     * To Simplify Toast (float msg) defination
     * @param msg
     */

    public static void show(String msg){
        Toast.makeText(getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showL(String msg){
        Toast.makeText(getInstance(), msg, Toast.LENGTH_LONG).show();
    }

    public static void show(int msg){
        Toast.makeText(getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showL(int msg){
        Toast.makeText(getInstance(), msg, Toast.LENGTH_LONG).show();
    }
}