package com.thesis.neuronplot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.DroidGap;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;




public class DrawPlot extends Activity{
	
	
	WebView webView; 
 
	ListView listView;
	String neuronID="";
	List<String> list = new ArrayList<String>();
    @SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://10.0.2.2:5000/ginjang_ndb/neuron_profiler_report/objDisplayNeuron?url=%2Fginjang_ndb%2Fneuron_profiler_report%2F&id=cef26f9d-df6f-4dc8-958e-13c01e471da4"));
        startActivity(browserIntent);
       
    

      
    }

    	public void CallUrl() throws IOException
	{
		
		//FileWriter file = new FileWriter("/mnt/external_sd/myjson.json");
		//file.write(response);
		//file.flush();
       // file.close();
		//String value=getActivity().getIntent().getStringExtra("Xkey");
		//String value2=getActivity().getIntent().getStringExtra("Ykey");
		//super.loadUrl("file:///android_asset/www/3dneuron.html");
		//super.loadUrl("javascript:window.onload = function(){setValue(\""+value+"\",\""+value2+"\");};");
		
	}
	/*protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	        	json=data.getStringExtra("result");
	        	Toast.makeText(getBaseContext(), json, Toast.LENGTH_LONG).show();
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        }
	    }
	}//onActivityResult
*/
	 public static String GET(String url){
	        InputStream inputStream = null;
	        String result = "";
	        try {
	 
	            // create HttpClient
	            HttpClient httpclient = new DefaultHttpClient();
	 
	            // make GET request to the given URL
	            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
	 
	            // receive response as inputStream
	            inputStream = httpResponse.getEntity().getContent();
	 
	            // convert inputstream to string
	            if(inputStream != null)
	                result = convertInputStreamToString(inputStream);
	            else
	                result = "Did not work!";
	 
	        } catch (Exception e) {
	            Log.d("InputStream", e.getLocalizedMessage());
	        }
	        ///////////////////////////////////for d3.js
	        String rs=result.substring(result.indexOf("[")+1);
	        rs=rs.substring(0,rs.length()-1);
	        rs="["+rs;
	        //////////////////////////
	        return rs;
	    }
	 
	    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
	        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	        String line = "";
	        String result = "";
	        while((line = bufferedReader.readLine()) != null)
	            result += line;
	 
	        inputStream.close();
	        return result;
	 
	    }
	 
	    public boolean isConnected(){
	        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
	            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	            if (networkInfo != null && networkInfo.isConnected()) 
	                return true;
	            else
	                return false;   
	    }
	    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... urls) {
	 
	            return GET(urls[0]);
	        }
	        // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(String result) {
	           
	            
	          
	           
	            result= result.replace("\\", "");
	           
	            try {
					CallUrl();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         
				  
	            
	       }
	    }

}
