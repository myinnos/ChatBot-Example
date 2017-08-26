package bike.rapido.driver;

import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

import bike.rapido.driver.activity.MainActivity;
import bike.rapido.driver.functions.RememberPreferences;

/**
 * Created by myinnos on 24/08/2017.
 */

public class AppBaseApplication extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Remember Preferences initialization
        RememberPreferences.init(getApplicationContext(), BuildConfig.APPLICATION_ID);

        turnOnStrictMode();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.None)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
                .init();
        OneSignal.sendTag("email", "driver@gmail.com");

    }

    public class ExampleNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {

        @Override
        public void notificationReceived(OSNotification notification) {

            Log.d("TEST_NOTIFY", notification.payload.body);
            if (MainActivity.instance != null) {
                MainActivity.instance.setDataFromNotification(notification.payload.body);
            }
        }
    }

    private void turnOnStrictMode() {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectCustomSlowCalls() // API level 11, to use with StrictMode.noteSlowCode
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .penaltyFlashScreen() // API level 11
                .build());

        // not really performance-related, but if you use StrictMode you might as well define a VM policy too
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects() // API level 1
                .penaltyLog()
                .build());
    }
}


