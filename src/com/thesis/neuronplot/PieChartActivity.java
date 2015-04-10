package com.thesis.neuronplot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;
import java.util.ArrayList;


public class PieChartActivity extends Activity implements 
       OnChartValueSelectedListener, AsyncResponse{

   private PieChart mChart;
   ArrayList<String> xVals = new ArrayList<String>();

   Apirequest asyncTask =new Apirequest();


   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
               WindowManager.LayoutParams.FLAG_FULLSCREEN);
       setContentView(R.layout.activity_piechart);

       
       mChart = (PieChart) findViewById(R.id.chart1);
       asyncTask.execute("https://servicehub.mpdl.mpg.de:443/PieService");
       asyncTask.delegate = this;
       getActionBar().setDisplayHomeAsUpEnabled(true);

       // change the color of the center-hole
//       mChart.setHoleColor(Color.rgb(235, 235, 235));
       
   }

  
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu, menu);
       
       return true;
   }
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {

       switch (item.getItemId()) {
           case R.id.home: {
           	Intent myIntent = new Intent(PieChartActivity.this, StartPage.class);
  			 
           	PieChartActivity.this.startActivity(myIntent);
              
               break;
           }
           case R.id.charts: {
           	Intent myIntent = new Intent(PieChartActivity.this, Charts.class);
     			 
           	PieChartActivity.this.startActivity(myIntent);
              
               break;
           }
           case R.id.similarity: {
           	Intent myIntent = new Intent(PieChartActivity.this, PieChartActivity.class);
    			 
           	PieChartActivity.this.startActivity(myIntent);
              
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
   private void setData(ArrayList<String> Xval, ArrayList<Float> Yval) {

      
       ArrayList<Entry> yVals1 = new ArrayList<Entry>();

       // IMPORTANT: In a PieChart, no values (Entry) should have the same
       // xIndex (even if from different DataSets), since no values can be
       // drawn above each other.
       for (int i = 0; i < Yval.size(); i++) {
           yVals1.add(new Entry((float) (Yval.get(i)), i));
       }

       ArrayList<String> xVals = new ArrayList<String>();

       for (int i = 0; i < Yval.size(); i++)
           xVals.add(Xval.get(i));

       PieDataSet set1 = new PieDataSet(yVals1, "Species");
       set1.setSliceSpace(10f);
       
       // add a lot of colors

       ArrayList<Integer> colors = new ArrayList<Integer>();

//       for (int c : ColorTemplate.VORDIPLOM_COLORS)
//           colors.add(c);
//       
//       for (int c : ColorTemplate.LIBERTY_COLORS)
//           colors.add(c);
//       
//       for (int c : ColorTemplate.PASTEL_COLORS)
//           colors.add(c);
////       
//       for (int c : ColorTemplate.JOYFUL_COLORS)
//           colors.add(c);
//       
       for (int c : ColorTemplate.COLORFUL_COLORS)
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
       
       Intent myIntent = new Intent(PieChartActivity.this, Neurons.class);
       myIntent.putExtra("Request",xVals.get(e.getXIndex()) );
	   PieChartActivity.this.startActivity(myIntent);
	   
       
   }

   @Override
   public void onNothingSelected() {
       Log.i("PieChart", "nothing selected");
   }

   

@Override
public void processFinish(String result) {
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
  	mChart.setValueTextColor(Color.BLACK);
  	mChart.setValueTextSize(7f);

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
  

	// TODO Auto-generated method stub
	
}


}
