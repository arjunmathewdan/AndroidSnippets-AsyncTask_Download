package com.example.asynctaskactivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.System;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

    public class AsyncTaskActivity extends Activity implements OnClickListener {
    Button btn;
    String mytxt = "";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        btn = (Button) findViewById(R.id.button1);
                    //because we implement OnClickListener we only have to pass "this" (much easier)
        btn.setOnClickListener(this);
    }

    public void onClick(View view){
        //detect the view that was "clicked"
        switch(view.getId())
        {
          case R.id.button1:
              new LongOperation().execute("");
          break;

        }

    }

    private class LongOperation extends AsyncTask<String, Void, String> {

          @Override
          protected String doInBackground(String... params) {                        
                        try {
                        	
                            // Create a URL for the desired page
                            //URL url = new URL("http://wordpress.org/extend/plugins/about/readme.txt");
                        	URL url = new URL("http://dl.dropbox.com/u/150409479/example.kml");
                        	
                            // Read all the text returned by the server
                            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                            String str;
                            while ((str = in.readLine()) != null) {
                                // str is one line of text; readLine() strips the newline character(s)
                            	mytxt += str;
                            	mytxt += "\n";
                            }
                            in.close();
                        } catch (MalformedURLException e) {
                        } catch (IOException e) {
                        }

                return "Executed";
          }      

          @Override
          protected void onPostExecute(String result) {
                TextView txt = (TextView) findViewById(R.id.output);
                txt.setText(mytxt);
//                txt.setText("Executed"); // txt.setText(result);
                //might want to change "executed" for the returned string passed into onPostExecute() but that is upto you

                FileOutputStream fos;
                try {
					fos = openFileOutput("example.kml", Context.MODE_PRIVATE);
					fos.write(mytxt.getBytes());
					fos.close();

					
					Toast.makeText(
							AsyncTaskActivity.this, 
							"example.kml" + " saved", 
							Toast.LENGTH_LONG).show();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
          
          }

          @Override
          protected void onPreExecute() {
          }

          @Override
          protected void onProgressUpdate(Void... values) {
          }
    }
}    