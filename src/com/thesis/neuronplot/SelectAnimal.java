package com.thesis.neuronplot;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class SelectAnimal extends Activity implements 
       OnChartValueSelectedListener {

   private PieChart mChart;
   ArrayList<String> xVals = new ArrayList<String>();

   protected String[] mParties = new String[] {
           "A.m.", "Elephant", "Gerbil", "Human", "Mouse", "Rat", "unknown","unknown2","unknown3"
   };


   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
               WindowManager.LayoutParams.FLAG_FULLSCREEN);
       setContentView(R.layout.activity_piechart);

       
       mChart = (PieChart) findViewById(R.id.chart1);
       new HttpAsyncTask().execute("http://10.0.2.2:5000/PieService");
       getActionBar().setDisplayHomeAsUpEnabled(true);

       // change the color of the center-hole
//       mChart.setHoleColor(Color.rgb(235, 235, 235));
       
   }

  

   

   private void setData(ArrayList<String> Xval, ArrayList<Float> Yval) {

       ArrayList<Integer> Myval = new ArrayList<Integer>();
       Myval.add(138);
       Myval.add(76);
       Myval.add(52);
       Myval.add(1714);
       Myval.add(441);
       
       ArrayList<Entry> yVals1 = new ArrayList<Entry>();

       // IMPORTANT: In a PieChart, no values (Entry) should have the same
       // xIndex (even if from different DataSets), since no values can be
       // drawn above each other.
       for (int i = 0; i < Yval.size()-1; i++) {
           yVals1.add(new Entry((float) (Yval.get(i)), i));
       }

       ArrayList<String> xVals = new ArrayList<String>();

       for (int i = 0; i < Yval.size()-1; i++)
           xVals.add(Xval.get(i));

       PieDataSet set1 = new PieDataSet(yVals1, "Species");
       set1.setSliceSpace(3f);
       
       // add a lot of colors

       ArrayList<Integer> colors = new ArrayList<Integer>();

       for (int c : ColorTemplate.VORDIPLOM_COLORS)
           colors.add(c);

       for (int c : ColorTemplate.JOYFUL_COLORS)
           colors.add(c);

       for (int c : ColorTemplate.COLORFUL_COLORS)
           colors.add(c);

       for (int c : ColorTemplate.LIBERTY_COLORS)
           colors.add(c);
       
       for (int c : ColorTemplate.PASTEL_COLORS)
           colors.add(c);
       
       colors.add(ColorTemplate.getHoloBlue());

       set1.setColors(colors);

       PieData data = new PieData(xVals, set1);
       mChart.setData(data);

       // undo all highlights
       mChart.highlightValues(null);

       mChart.invalidate();
       mChart.setOnChartValueSelectedListener(this);
   }

   @Override
   public void onValueSelected(Entry e, int dataSetIndex) {

       if (e == null)
           return;
       Log.i("VAL SELECTED",
               "Value: " + e.getVal() + ", xIndex: " + xVals.get(e.getXIndex())
                       + ", DataSet index: " + dataSetIndex);
       
       Intent myIntent = new Intent(SelectAnimal.this, ChartLine.class);
       myIntent.putExtra("Request",xVals.get(e.getXIndex()) );
       SelectAnimal.this.startActivity(myIntent);
	   
       
   }

   @Override
   public void onNothingSelected() {
       Log.i("PieChart", "nothing selected");
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
   private class HttpAsyncTask extends AsyncTask<String, Void, String>  {
       @Override
       protected String doInBackground(String... urls) {

           return GET(urls[0]);
       }
       // onPostExecute displays the results of the AsyncTask.
       @Override
       protected void onPostExecute(String result) {
       	result=result.substring(1,result.length()-1);

       	setContentView(R.layout.activity_piechart);;
        mChart = (PieChart) findViewById(R.id.chart1);
   	     
   	    
       	String[] lines = result.split(",");
       	
       	ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
       	ArrayList<Float> Yval = new ArrayList<Float>();
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
       		  Yval.add(Float.valueOf(line)); 
       		  Entry c1e1 = new Entry(Float.valueOf(line), count); // 0 == quarter 1
 	              valsComp1.add(c1e1);
 	              count=count+1;
       	  }
       	  
       	}
       	
       	mChart.setHoleColor(Color.rgb(235, 235, 235));

       	

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        mChart.setValueTypeface(tf);
        mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));

        mChart.setHoleRadius(50f);

        mChart.setDescription("");

        mChart.setDrawYValues(true);
        mChart.setDrawCenterText(true);

        mChart.setDrawHoleEnabled(true);

        mChart.setRotationAngle(0);

        // draws the corresponding description value into the slice
        mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);

        // display percentage values
        mChart.setUsePercentValues(true);
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        
        // mChart.setTouchEnabled(false);

        mChart.setCenterText("Species");

        setData(xVals, Yval);

        mChart.animateXY(1500, 1500);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);

 
        
			  
           
      }
   }


}
