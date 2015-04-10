package com.thesis.neuronplot;

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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.charts.ScatterChart.ScatterShape;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.thesis.adapter.NoDefaultSpinner;
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

public class ScatterChartActivity extends Activity {

    private ScatterChart mChart;
    private NoDefaultSpinner spinner1;
	String value="volume";
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scatterchart);
        getActionBar().setDisplayHomeAsUpEnabled(true);
       new HttpAsyncTask().execute("https://servicehub.mpdl.mpg.de:443/ScatterChart?id=volume");
        
       

        mChart = (ScatterChart) findViewById(R.id.chart1);
        mChart.setDescription("");

        


        

       // Legend l = mChart.getLegend();
        //l.setPosition(LegendPosition.RIGHT_OF_CHART);
        

       // YLabels yl = mChart.getYLabels();
        

       // XLabels xl = mChart.getXLabels();
      

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scatter, menu);
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
				  new HttpAsyncTask().execute("https://servicehub.mpdl.mpg.de:443/ScatterChart?id="+value);
				
			  }
			 
			  @Override
			  public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			  }
			 
			});
	  }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionSave: {
                //mChart.saveToGallery("title"+System.currentTimeMillis());
                mChart.saveToPath("title" + System.currentTimeMillis(), "");
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
        	 setContentView(R.layout.activity_scatterchart);
            
         

             mChart = (ScatterChart) findViewById(R.id.chart1);
             mChart.setDescription(" "+value+"(Y axis)-age(X axis )");
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
        	
            ArrayList<ScatterDataSet> dataSets = new ArrayList<ScatterDataSet>();
            
            //dataSets.add(setComp2);

           
            
            ScatterDataSet set1 = new ScatterDataSet(valsComp1, value);
            set1.setScatterShape(ScatterShape.CIRCLE);
            set1.setColor(ColorTemplate.COLORFUL_COLORS[2]);

            set1.setScatterShapeSize(8f);
            

           
            dataSets.add(set1); // add the datasets
            
            
            // create a data object with the datasets
            ScatterData data = new ScatterData(xVals, dataSets);
            XLabels xl = mChart.getXLabels();
            xl.setPosition(XLabelPosition.BOTTOM); // set the position
            // set the textsize
            xl.setSpaceBetweenLabels(5);
            
            mChart.setData(data);
            mChart.animateX(3000);
            mChart.invalidate();
            //LineChartData data = new LineChartData();
            //data.setLines(lines);
            //LineData data=new LineData();
            //chart1.setData(data);
            

           // chart=(LineChartView) findViewById(R.id.chart);
            //LineChartView chart = new LineChartView(getBaseContext());
            //chart.setLineChartData(data);

            
    	
            
            //JSONArray data = obj.getJSONArray("data");
                  
                

         
			  
            
       }
    }
	

}
