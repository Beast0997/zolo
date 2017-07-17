package zolostays.in.zolostays;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by gulshan on 15/07/17.
 */

public class Constant {




    public static boolean isOnline(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Log.v("SR", "Internet Connection Not Present");
            return false;
        }
    }
}
