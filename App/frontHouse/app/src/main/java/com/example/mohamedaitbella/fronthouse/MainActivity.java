package com.example.mohamedaitbella.fronthouse;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText userName;
    EditText password;
    Button button, sending;

    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);
        button=(Button)findViewById(R.id.button);
        sending = (Button)findViewById(R.id.send);

        FirebaseMessaging.getInstance().subscribeToTopic("Official");

        final Task task =
                (FirebaseInstanceId.getInstance().getInstanceId());

        final Activity main = this;
        task.addOnSuccessListener(main, new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                InstanceIdResult result = (InstanceIdResult)task.getResult();
                token = result.getToken();
                Log.d("GET_ID", "" +token );
            }
        });

    }

    public void clickMe(View view ){
         userName.getText().toString();


         String url = "http://knightfinder.com/WEBAPI/Login.aspx";

         APICall apiCall = new APICall();
         JSONObject result = null;

         try {
              result = apiCall.execute(url, "{login:\""+userName+"\",password:\""+password+"\"}").get();
              Log.d("CHECK", "Result = " + result);
         }catch (Exception e){
              Log.d("Debug: API_Call", e.getMessage());
              return;
         }

         int userId = -1;


         try
         {
              userId = result.getInt("EmployeeID");
         }
         catch (Exception e){
              Log.d("Debug: Get Emp ID", e.getMessage());
              return;
         }

         if(userId > 0) {
              Intent intent = new Intent(MainActivity.this, Main2Activity.class);
              startActivity(intent);
         }
         else{
              AlertDialog.Builder builder = new AlertDialog.Builder(this);

              builder.setMessage("Incorrect user/password. Please try again.");
              AlertDialog dialog = builder.create();
              dialog.show();
         }
    }

    public void sending(View view){

        Send send = new Send();

        send.execute("https://fcm.googleapis.com/fcm/send", "{\"to\":\"/topics/Testing\",\"notification\": {\"title\": \"This is the title\",\"text\": \"Did you make it?!\",\"click_action\": \"MainActivity\"}}", token );



    }
}

class APICall extends AsyncTask<String, String, JSONObject> {

    public APICall(){

    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override       //https://stackoverflow.com/questions/2938502/sending-post-data-in-android
    protected JSONObject doInBackground(String... params){

        String urlString = params[0];   // URL being called
        String data = params[1];    // Data to post

        Log.d("INPUT CHECK", "URL:" + urlString + ", post: " + data);


        try{
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setDoInput(true);     // Allows input to url site
            urlConnection.setDoOutput(true);    // Allows output from url site
            urlConnection.setRequestProperty("Content-Type", "application/json");   //Self explanatory
            urlConnection.setRequestProperty("Accept", "application/json");         //"               "
            urlConnection.setRequestMethod("POST");                                 //"               "
            Log.i("message","hello world");

            //---------------- JSON Post Sequence -------------------------------------------
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            Log.i("traffic", "here");
            wr.write(data);
            wr.flush();
            //-------------------------------------------------------------------------------

            JSONObject json = new JSONObject();
            StringBuilder sb = new StringBuilder();
            int HttpResult = urlConnection.getResponseCode();   // Self explanatory
            if(HttpResult == HttpURLConnection.HTTP_OK)
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while((line = br.readLine()) != null)   //Should run once for login
                    //json = new JSONObject(line);
                    sb.append(line + "\n");
            }

            // Returning what is wanted from executing of the AsyncTask
            return new JSONObject(sb.toString());
        }catch (Exception e){
            Log.d("CHECK CALL", e.getMessage());
        }

        return null;
    }
    protected void onPostExecute(JSONObject result){
        if(result != null)
            Log.d("MyDebug", result.toString());
        super.onPostExecute(result);
    }
}

class Send extends AsyncTask<String, String, Boolean>{

    public Send(){

    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... params){


        String urlString = params[0];   // URL being called
        String data = params[1];    // Data to post
        String key = params[2];

        Log.d("INPUT CHECK", "URL:" + urlString + ", post: " + data + "key: " + key );

        try{
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setDoInput(true);     // Allows input to url site
            urlConnection.setDoOutput(true);    // Allows output from url site
            urlConnection.setRequestProperty("Content-Type", "application/json");   //Self explanatory
            urlConnection.setRequestProperty("Accept", "application/json");         //"               "
            urlConnection.setRequestProperty("Authorization", key);
            urlConnection.setRequestMethod("POST");                                 //"               "
            Log.i("message","hello world");

            //---------------- JSON Post Sequence -------------------------------------------
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            Log.i("traffic", "here");
            wr.write(data);
            wr.flush();
            //-------------------------------------------------------------------------------

            int HttpResult = urlConnection.getResponseCode();   // Self explanatory
            if(HttpResult == HttpURLConnection.HTTP_OK)
                return true;
            else
                return false;
        }catch (Exception e){
            Log.d("CHECK CALL", e.getMessage());
        }

        return false;

    }

    protected void onPostExecute(Boolean result){
        if(result != null)
            Log.d("MyDebug", result.toString());
        super.onPostExecute(result);
    }
}