package com.thesis.neuronplot;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.thesis.adapter.NoDefaultSpinner;
import com.thesis.neuronplot.R;
import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;

//Generating line charts. Since we only have variety in Human's age we plot the charts for Human. 
public class ChartLine extends Activity {
	LineChart chart;
	String value="volume";
	String species="Human";
	private NoDefaultSpinner spinner1;
	 @Override
		public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.linechart);
	     //species=getIntent().getStringExtra("Request");
	     getActionBar().setDisplayHomeAsUpEnabled(true);
	     species=species.substring(1,species.length()-1);
	     new HttpAsyncTask().execute("https://servicehub.mpdl.mpg.de:443/LineChart?id=volume&spec="+species+"");
	     getActionBar().setDisplayHomeAsUpEnabled(true);
	    
	    
	     

        
	
	}
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.scatter, menu);
	        
	        return true;
	    }
	  @Override
	    public boolean onOptionsItemSelected(MenuItem item) {

	        switch (item.getItemId()) {
	            case R.id.actionSave: {
	                //mChart.saveToGallery("title"+System.currentTimeMillis());
	                chart.saveToPath("title" + System.currentTimeMillis(), "");
	                break;
	            }
	            case android.R.id.home:
	                NavUtils.navigateUpFromSameTask(this);
	                return true;
	            default:
	                return super.onOptionsItemSelected(item);
	            
	        }
	        return true;
	    }
	 public void addItemsOnSpinner(List<String> list){
		 
			spinner1 = (NoDefaultSpinner) findViewById(R.id.spinner);
			
			
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner1.setAdapter(dataAdapter);
			
		  }
		 
		  public void addListenerOnSpinnerItemSelection() {
			spinner1 = (NoDefaultSpinner) findViewById(R.id.spinner);
			spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
				 
				  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
					  value=String.valueOf(spinner1.getSelectedItem());
					  new HttpAsyncTask().execute("https://servicehub.mpdl.mpg.de:443/LineChart?id="+value+"&spec="+species+"");
					
				  }
				 
				  @Override
				  public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				  }
				 
				});
		  }
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
	        return result;
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
	        	result=result.substring(1,result.length()-1);
	        	 List<String> list = new ArrayList<String>();
	        	 setContentView(R.layout.linechart);
		         chart = (LineChart) findViewById(R.id.chart);
	    	     list.add("volume");
	    	     list.add("depth");
	    	     list.add("length");
	    	  
	    	     addItemsOnSpinner(list);
	    	     addListenerOnSpinnerItemSelection();
	    	     
	    	    
	        	String[] lines = result.split(",");
	        	ArrayList<String> xVals = new ArrayList<String>();
	        	ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
	        	int count=0;
	        	for (String line : lines)
	        	{
	        		
	        	  if (line.startsWith("["))
	        	  {
	        	    line=line.substring(1,line.length());
	        	   
	        	    xVals.add(line);
	        	  }
	        	  else if (line.startsWith(" ["))
	        	  {
	        		  line=line.substring(2,line.length());
	        		  xVals.add(line);
	        	  }
	        	  else
	        	  {
	        		  line=line.substring(0,line.length()-1);
	        		  Entry c1e1 = new Entry(Float.valueOf(line), count); // 0 == quarter 1
	  	              valsComp1.add(c1e1);
	  	              count=count+1;
	        	  }
	        	  
	        	}
	
	            LineDataSet setComp1 = new LineDataSet(valsComp1, value);
	            ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
	            dataSets.add(setComp1);
	            LineData data = new LineData(xVals, dataSets);
	            chart.setDescription("Mean of "+value+" according to age(X axis )");
	            chart.setData(data);
	            chart.animateX(3000);
	          
	                  
	                

	         
				  
	            
	       }
	    }


}

