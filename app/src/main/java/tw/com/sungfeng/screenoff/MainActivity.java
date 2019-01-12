package tw.com.sungfeng.screenoff;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.os.PowerManager.PARTIAL_WAKE_LOCK;
import static android.os.PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK;
import static android.os.PowerManager.SCREEN_BRIGHT_WAKE_LOCK;
import static android.os.PowerManager.SCREEN_DIM_WAKE_LOCK;

public class MainActivity extends AppCompatActivity {
    static final String LOG_TAG = "ScreenOffActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        turnScreenOffAndExit();
    }

    /**
     * Turns the screen off and locks the device, provided that proper rights
     * are given.
     *
     * @param context
     *            - The application context
     */
    static void turnScreenOff(final Context context) {
        DevicePolicyManager policyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName adminReceiver = new ComponentName(context,ScreenOffAdminReceiver.class);
        boolean admin = policyManager.isAdminActive(adminReceiver);
        if (admin) {
            Log.e(LOG_TAG, "Going to sleep now.");
            policyManager.lockNow();
        } else {
            Log.e(LOG_TAG, "Not an admin");
            Toast.makeText(context, R.string.device_admin_not_enabled,Toast.LENGTH_LONG).show();
        }
    }

    private void turnScreenOffAndExit() {
        // first lock screen
        turnScreenOff(getApplicationContext());

        // then provide feedback
        ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(50);

        // schedule end of activity
        final Activity activity = this;
        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    /* ignore this */
                }
                activity.finish();
            }
        };
        t.start();
    }
}
