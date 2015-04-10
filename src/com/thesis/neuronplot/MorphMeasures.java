
package com.thesis.neuronplot;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;


// This Activity is used for showing the morphological measurements.
public class MorphMeasures extends FragmentActivity implements AsyncResponse{
	private TextView TextNeuron;
	private TextView  TextAge;
	private TextView  TextVolume;
	private TextView Textwidth;
	private TextView Textdepth;
	private TextView TextBifurcations;
	private TextView Textlenght;
	private TextView Textsurface;
	private TextView Textheight;
	private Button btnproceed;
	private Button specificity;
	String neuronID="";
	String value="";
	WebView webView;
	Apirequest asyncTask =new Apirequest();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.neuronprofile);
		value=getIntent().getStringExtra("Request");
		neuronID=getIntent().getStringExtra("NeuronID");
		
		asyncTask.execute("https://servicehub.mpdl.mpg.de:443/SwcMorph?id="+value);
		asyncTask.delegate = this;
		getActionBar().setDisplayHomeAsUpEnabled(true);
		webView =(WebView) findViewById(R.id.webview); 
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
  
        webView.loadUrl("http://neuromorpho.org/neuroMorpho/rotatingImages/"+neuronID+".CNG.gif");
       

		TextNeuron=(TextView) findViewById(R.id.TextView01);
		TextAge=(TextView) findViewById(R.id.TextView03);
		TextVolume=(TextView) findViewById(R.id.TextView05);
		Textwidth=(TextView) findViewById(R.id.TextView07);
		Textdepth=(TextView) findViewById(R.id.TextView09);
		TextBifurcations=(TextView) findViewById(R.id.TextView11);
		Textlenght=(TextView) findViewById(R.id.TextView13);
		Textsurface=(TextView) findViewById(R.id.TextView15);
		Textheight=(TextView) findViewById(R.id.TextView17);
		addListenerOnButtonProceed();
		
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
            	Intent myIntent = new Intent(MorphMeasures.this, StartPage.class);
   			 
            	MorphMeasures.this.startActivity(myIntent);
               
                break;
            }
            case R.id.charts: {
            	Intent myIntent = new Intent(MorphMeasures.this, Charts.class);
      			 
            	MorphMeasures.this.startActivity(myIntent);
               
                break;
            }
            case R.id.similarity: {
            	Intent myIntent = new Intent(MorphMeasures.this, PieChartActivity.class);
     			 
            	MorphMeasures.this.startActivity(myIntent);
               
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
	
   public void addListenerOnButtonProceed() {
		 
		
	   btnproceed = (Button) findViewById(R.id.similar);
	   specificity=(Button) findViewById(R.id.specificity);
	 
	   btnproceed.setOnClickListener(new View.OnClickListener() {
	 
		  
		  public void onClick(View v) {
		
		    Intent myIntent = new Intent(MorphMeasures.this, Similarity.class);
		    myIntent.putExtra("NeuronID", neuronID);
		    myIntent.putExtra("SwcName", value);
		    MorphMeasures.this.startActivity(myIntent);
		   
			}
			
		  
	 
		});
	   specificity.setOnClickListener(new View.OnClickListener() {
			 
			  
			  public void onClick(View v) {
			
			    Intent myIntent = new Intent(MorphMeasures.this, searchSpecificity.class);
			    myIntent.putExtra("NeuronID", neuronID);
			    myIntent.putExtra("SwcName", value);
			    MorphMeasures.this.startActivity(myIntent);
			   
				}
				
			  
		 
			});
	  }

		@Override
		public void processFinish(String result) {
			JSONArray data;      
            try {
				JSONObject jobject = new JSONObject(result);
				String notes = jobject.getString("FileDepotMorph");
				data = new JSONArray(notes);
				for(int i=0;i<data.length();i++){
				    JSONObject eachData = data.getJSONObject(i);
				    
				    TextNeuron.setText(eachData.getString("neuron"));
				    TextAge.setText(eachData.getString("age"));
				    TextVolume.setText(eachData.getString("volume"));
				    Textwidth.setText(eachData.getString("width"));
				    Textdepth.setText(eachData.getString("depth"));
				    TextBifurcations.setText(eachData.getString("number_of_bifurcations"));
				    Textlenght.setText(eachData.getString("length"));
				    Textsurface.setText(eachData.getString("surface"));
				    Textheight.setText(eachData.getString("height"));

				}
				
				


				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.d("ProcessRegister", "JSONException", e);
			}
            
            //JSONArray data = obj.getJSONArray("data");
                 
			
		}

	
}
