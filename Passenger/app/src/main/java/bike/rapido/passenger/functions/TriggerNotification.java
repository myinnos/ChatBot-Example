package bike.rapido.passenger.functions;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by myinnos on 25/08/17.
 */

public class TriggerNotification {

    public static void notify(final Activity activity, String triggerMessage) {

        String email = "driver@gmail.com";
        String TRIGGER_URL = "<DOMAIN>/otherprojects/rapido/trigger.php?title="
                + triggerMessage + "&email=" + email;

        StringRequest stringRequest = new StringRequest(TRIGGER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }
}
