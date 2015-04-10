package com.thesis.neuronplot;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View; 
import android.widget.AdapterView; 
import android.widget.ArrayAdapter; 
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;

public class Neurons extends FragmentActivity implements AsyncResponse{
    ListView listView ;
    EditText inputSearch;
    ArrayAdapter<String> adapter;
    String value="";
    Apirequest asyncTask =new Apirequest();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.neuron);
        value=getIntent().getStringExtra("Request");
        value=value.substring(1,value.length()-1);
        value=value.replaceAll(" ", "%20");


        asyncTask.execute("https://servicehub.mpdl.mpg.de:443/NeuronService?id="+value);
        
       
        asyncTask.delegate = this;
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        inputSearch=(EditText) findViewById(R.id.inputSearch);
        
        
        listView.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
                

               // ListView Clicked item value
               String  itemValue    = (String) listView.getItemAtPosition(position);
                  
        
                Intent myIntent = new Intent(Neurons.this,MorphMeasures.class);
    		    myIntent.putExtra("Request",itemValue );
    		    myIntent.putExtra("NeuronID",itemValue );
    		    Neurons.this.startActivity(myIntent);
             
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
            	Intent myIntent = new Intent(Neurons.this, StartPage.class);
   			 
            	Neurons.this.startActivity(myIntent);
               
                break;
            }
            case R.id.charts: {
            	Intent myIntent = new Intent(Neurons.this, Charts.class);
      			 
            	Neurons.this.startActivity(myIntent);
               
                break;
            }
            case R.id.similarity: {
            	Intent myIntent = new Intent(Neurons.this, PieChartActivity.class);
     			 
            	Neurons.this.startActivity(myIntent);
               
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
  
	@Override
	public void processFinish(String result) {

        JSONArray data;      
        List<String> list = new ArrayList<String>();
        try {
			JSONObject jobject = new JSONObject(result);
			String notes = jobject.getString("Neurons");
			data = new JSONArray(notes);
			 for(int i=0; i<data.length(); i++){
				String rs=data.getString(i);
				rs=rs.substring(2,rs.length()-2);
				list.add(rs);
             }
			adapter = new ArrayAdapter<String>(getBaseContext(),
			          android.R.layout.simple_list_item_1, android.R.id.text1, list);
			inputSearch.addTextChangedListener(new TextWatcher() {
	            
	            @Override
	            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	                // When user changed the Text
	                Neurons.this.adapter.getFilter().filter(cs);   
	            }
	             
	            @Override
	            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                    int arg3) {
	                // TODO Auto-generated method stub
	                 
	            }
	             
	            @Override
	            public void afterTextChanged(Editable arg0) {
	                // TODO Auto-generated method stub                          
	            }
	        });


			        // Assign adapter to ListView
			        listView.setAdapter(adapter);
		
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("ProcessRegister", "JSONException", e);
		}
        

		
	}

}
