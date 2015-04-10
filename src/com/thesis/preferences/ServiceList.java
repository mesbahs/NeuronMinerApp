package com.thesis.preferences;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.thesis.models.DatabaseHandler;
import com.thesis.models.ServiceModel;
import com.thesis.neuronplot.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ServiceList extends Activity {
	    ListView listView ;
	    List<ServiceModel> list = new ArrayList<ServiceModel>();
	    List<String> servicelist=new ArrayList<String>();
	    
	    DatabaseHandler db = new DatabaseHandler(this);
	    @Override
		public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.servicelist);
	        
	        listView = (ListView) findViewById(R.id.servicelist);
	        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	        list=db.getAllservices();
	        for (ServiceModel src : list) {
	        	servicelist.add(src.getKey());
	        }
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
			          android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, servicelist);


			        // Assign adapter to ListView
			        listView.setAdapter(adapter);
			        
			
	        listView.setOnItemClickListener(new OnItemClickListener() {

	              @Override
	              public void onItemClick(AdapterView<?> parent, View view,
	                 int position, long id) {
	                
	            	  int itemPosition     = position;
	                  
	                  // ListView Clicked item value
	                  String  itemValue    = (String) listView.getItemAtPosition(position);
	                  
	                  
	                  Toast.makeText(getBaseContext(), db.getUrlById(itemValue), 20).show();
            	/*  try {
						CallUrl();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
	       		  
	            	
	             
	              }

	         }); 
	        
	    }
	    
}
