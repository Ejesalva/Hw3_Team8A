package com.example.elijah.hw3_team8a;

//android.permission.INTERNET
//android.permission.

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new GetDataTask().execute("http://www.google.com");
        //new GetImage().execute("http://www.google.com/images/srpr/logo11w.png");

        //new GetDataTaskApache().execute("http://rss.cnn.com/rss/edition_support.rss");
        //new GetImageApache().execute("http://www.google.com/images/srpr/logo11w.png");

        RequestParams params = new RequestParams("GET", "http://www.google.com/images/srpr/logo11w.png");
        params.addParam("Key1", "Value1");

        new GetDataParamsHttpURL().execute(params);

    }

    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    //HTTP URL Connection
    private class GetDataTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params){
            try {
                StringBuilder sb = new StringBuilder();
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = "";
                while((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                reader.close();
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String results) {
            Log.d("demo", results);
        }
    }

    private class GetImage extends AsyncTask<String, Void, Bitmap>{
        protected Bitmap doInBackground(String... params){
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                Bitmap bitmap = BitmapFactory.decodeStream(con.getInputStream());
                return bitmap;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap results) {

            //TODO: Populate ImageView
            //ImageView iv = (ImageView) findViewById(R.id._____) //TODO

            if(results != null) {
                //iv.setImageBitmap(result);
            }
        }
    }

    //HTTP URL With Params
    private class GetDataParamsHttpURL extends AsyncTask<RequestParams, Void, String>{
        protected String doInBackground(RequestParams... params){
            BufferedReader reader = null;
            try {
                HttpURLConnection con = params[0].setupConnection();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(String results) {
            Log.d("demo", results);
        }
    }

    //HTTP URL With Params - POST
	/*
	private class GetDataParamsHttpPost extends AsyncTask<RequestParams, Void, String>{
		protected String doInBackground(RequestParams... params){
			BufferedReader reader = null;
			try {
					URL url = new URL(params[0].baseUrl);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					con.setDoOutput(true);
					OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
					writer.write(params[0].getEncodedParams());
					writer.flush();

					reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
					StringBuilder sb = new StringBuilder();
					String line = "";
					while((line = read.readline()) != null){
						sb.append(line + "\n");
					}
				return sb.toString();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(reader != null) {
						reader.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		protected void onPostExecute(String results) {
			Log.d("demo", results);
		}
	}
	*/


}
