package tw.com.sungfeng.screenoff;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ScreenOffAdminReceiver extends DeviceAdminReceiver {
    private void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        showToast(context,context.getString(R.string.admin_receiver_status_enabled));
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        showToast(context, context.getString(R.string.admin_receiver_status_disabled));
    }
}
