package com.thesis.neuronplot;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.thesis.adapter.CustomListSpecificity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class SpecificitySimilarity extends Activity implements AsyncResponse {
	String neuronID="";
	String SwcName="";
	String all="";
	String branch="";
	String bifurcation="";
	ListView listView ;
	Context context;
	 Apirequest asyncTask =new Apirequest();
	List<String> list = new ArrayList<String>();
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.neuronlistview);
        neuronID=getIntent().getStringExtra("NeuronID");
        SwcName=getIntent().getStringExtra("SwcName");
        all=getIntent().getStringExtra("allP");
        branch=getIntent().getStringExtra("branchP");
        bifurcation=getIntent().getStringExtra("bifurcationP");
        asyncTask.execute("https://servicehub.mpdl.mpg.de:443/SimilarSpecificityNeuron?id="+neuronID+"&all="+all+"&branch="+branch+"&bifurcation="+bifurcation+"&number=10");
        asyncTask.delegate = this;
        listView = (ListView) findViewById(R.id.list);
      

        
        // ListView Item Click Listener
       
    }public void CallUrl(final String[] web, String[] imageUrl, String[] Species) throws IOException
	{
		

    	  listView.setAdapter(new CustomListSpecificity(this, web, imageUrl,Species));
    	  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                                      int position, long id) {
            	  Intent myIntent = new Intent( SpecificitySimilarity.this, MorphMeasures.class);
            	
                  myIntent.putExtra("Request",SwcName );
                  myIntent.putExtra("NeuronID",web[+ position]);
                  SpecificitySimilarity.this.startActivity(myIntent); 
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
