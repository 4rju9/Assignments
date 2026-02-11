package app.netlify.dev4rju9.orbolmagazine_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import com.android.volley.Request;
import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {

    Queue queue;
    public static JSONObject fetchedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        makeHttpRequest();

    }

    private void makeHttpRequest () {

        queue = Queue.getInstance(this);

        queue.makeRequest(Request.Method.GET,
                getResources().getString(R.string.api),
                response -> {
                    try {
                        fetchedData = new JSONObject(response);
                    } catch (Exception e) {
                        makeToast(SplashScreen.this, "An error occurred! Try after while.");
                        makeToast(SplashScreen.this, "The api i created is hosted on github. that doesn't with JIO Network.");
                    }
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                });

    }

    public static void makeToast (Context context, String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}