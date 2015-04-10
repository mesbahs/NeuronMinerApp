package com.thesis.neuronplot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View; 
import android.widget.AdapterView; 
import android.widget.ArrayAdapter; 
import android.widget.AdapterView.OnItemClickListener;

public class Swc extends FragmentActivity implements AsyncResponse{
    ListView listView ;
    String value="";
    List<String> list = new ArrayList<String>();
    Apirequest asyncTask =new Apirequest();
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swc);
        value=getIntent().getStringExtra("Request");
        asyncTask.execute("http://10.0.2.2:5000/SwcService?id="+value);
        asyncTask.delegate = this;
        getActionBar().setDisplayHomeAsUpEnabled(true);
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        
        

        
        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {

                  
                  // ListView Clicked item value
                  String  itemValue    = (String) listView.getItemAtPosition(position);
                     
                 Intent myIntent = new Intent(Swc.this, MorphMeasures.class);
                 myIntent.putExtra("Request",itemValue );
                 myIntent.putExtra("NeuronID",value);
             	 Swc.this.startActivity(myIntent); 
            	
            	
             
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
            	Intent myIntent = new Intent(Swc.this, StartPage.class);
   			 
            	Swc.this.startActivity(myIntent);
               
                break;
            }
            case R.id.charts: {
            	Intent myIntent = new Intent(Swc.this, Charts.class);
      			 
            	Swc.this.startActivity(myIntent);
               
                break;
            }
            case R.id.similarity: {
            	Intent myIntent = new Intent(Swc.this, PieChartActivity.class);
     			 
            	Swc.this.startActivity(myIntent);
               
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
    public void CallUrl() throws IOException
	{
		

		//super.loadUrl("file:///android_asset/www/3dneuron.html");
		
		
	}

	@Override
	public void processFinish(String result) {

        JSONArray data;      
        
        try {
			JSONObject jobject = new JSONObject(result);
			
			String notes = jobject.getString("FileDepot");
			data = new JSONArray(notes);
			 for(int i=0; i<data.length(); i++){
				String rs=data.getString(i);
				rs=rs.substring(2,rs.length()-2);
				list.add(rs);
             }
		
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
			          android.R.layout.simple_list_item_1, android.R.id.text1, list);


			        // Assign adapter to ListView
			        listView.setAdapter(adapter);
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("ProcessRegister", "JSONException", e);
		}
        
		
	}

}
