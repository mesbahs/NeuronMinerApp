package com.thesis.neuronplot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.thesis.adapter.CustomList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Similarity extends Activity implements AsyncResponse{
	String neuronID="";
	String SwcName="";
	ListView listView ;
	 Context context;
	 Apirequest asyncTask =new Apirequest();
	List<String> list = new ArrayList<String>();
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        //Handle when activity is recreated like on orientation Change
    
        setContentView(R.layout.neuronlistview);
      
        neuronID=getIntent().getStringExtra("NeuronID");
        SwcName=getIntent().getStringExtra("SwcName");
        asyncTask.execute("https://servicehub.mpdl.mpg.de:443/SimilarNeuron?id="+neuronID+"&number=10");
        asyncTask.delegate = this;
        getActionBar().setDisplayHomeAsUpEnabled(true);
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
      

        
        // ListView Item Click Listener
       
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
            	Intent myIntent = new Intent(Similarity.this, StartPage.class);
   			 
            	Similarity.this.startActivity(myIntent);
               
                break;
            }
            case R.id.charts: {
            	Intent myIntent = new Intent(Similarity.this, Charts.class);
      			 
            	Similarity.this.startActivity(myIntent);
               
                break;
            }
            case R.id.similarity: {
            	Intent myIntent = new Intent(Similarity.this, PieChartActivity.class);
     			 
            	Similarity.this.startActivity(myIntent);
               
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
	
	
	public void CallUrl(final String[] web, String[] imageUrl, String[] Species) throws IOException
	{
		

    	  listView.setAdapter(new CustomList(this, web, imageUrl,Species));
    	  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                                      int position, long id) {
            	  Intent myIntent = new Intent(Similarity.this, MorphMeasures.class);
            	
                  myIntent.putExtra("Request",SwcName );
                  myIntent.putExtra("NeuronID",web[+ position]);
                  Similarity.this.startActivity(myIntent); 
                   }
          });
		
		
	}
	
	@Override
	public void processFinish(String result) {
		JSONArray data;   
        String[] neuronID = new String[10];
        String[] species = new String[10];
        String[] archive= new String[10];
        String[] url= new String[10];
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        
        try {
			JSONObject jobject = new JSONObject(result);
			
			String notes = jobject.getString("SimilarNeurons");
			
			data = new JSONArray(notes);
			for(int i=0;i<data.length();i++){
			    JSONObject eachData = data.getJSONObject(i);
			    neuronID[i]=eachData.getString("neuronID");
			    species[i]=eachData.getString("species");
			    archive[i]=eachData.getString("archive");
			    url[i]="http://neuromorpho.org/neuroMorpho/images/imageFiles/"+eachData.getString("archive")+"/"+eachData.getString("neuronID")+".png";
			    list.add(eachData.getString("neuronID"));
			    
			    //list.add();
			}
			CallUrl(neuronID,url,species);
			
			       
			
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			Log.d("ProcessRegister", "JSONException", e);
		}
        
        //JSONArray data = obj.getJSONArray("data");
              
            

		
	}
	
}
