package com.example.mohamedaitbella.fronthouse;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText userName;
    EditText password;
    Button button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);
        button=(Button)findViewById(R.id.button);
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