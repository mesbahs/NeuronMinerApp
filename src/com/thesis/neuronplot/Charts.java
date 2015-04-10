package com.thesis.neuronplot;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

//Show the list of charts available
public class Charts extends Activity{
	 ListView listView ;
	 List<String> chartlist=new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
    setContentView(R.layout.charts);
    
    listView = (ListView) findViewById(R.id.chart);
    chartlist.add("Scatter Chart");
    chartlist.add("Line Chart");
    chartlist.add("Pie Chart");
    getActionBar().setDisplayHomeAsUpEnabled(true);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
	          android.R.layout.simple_list_item_1, android.R.id.text1, chartlist);


	        // Assign adapter to ListView
	        listView.setAdapter(adapter);
	        
	
  listView.setOnItemClickListener(new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view,
           int position, long id) {
            
            // ListView Clicked item value
            String  itemValue    = (String) listView.getItemAtPosition(position);
            if (itemValue=="Scatter Chart")
            {
            	 Intent myIntent = new Intent(Charts.this, ScatterChartActivity.class);
            	 Charts.this.startActivity(myIntent);
            }
            else if (itemValue=="Line Chart")
            { 
            	Intent myIntent = new Intent(Charts.this, ChartLine.class);
			  Charts.this.startActivity(myIntent);
            	
            }
            else if(itemValue=="Pie Chart")
            {
            	Intent myIntent = new Intent(Charts.this, PieChartActivity.class);
 			   Charts.this.startActivity(myIntent);
            	
            }
            
       
        }

   }); 
  
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
	           	Intent myIntent = new Intent(Charts.this, StartPage.class);
	  			 
	           	Charts.this.startActivity(myIntent);
	              
	               break;
	           }
	           case R.id.charts: {
	           	Intent myIntent = new Intent(Charts.this, Charts.class);
	     			 
	           	Charts.this.startActivity(myIntent);
	              
	               break;
	           }
	           case R.id.similarity: {
	           	Intent myIntent = new Intent(Charts.this, PieChartActivity.class);
	    			 
	           	Charts.this.startActivity(myIntent);
	              
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
	 

}
