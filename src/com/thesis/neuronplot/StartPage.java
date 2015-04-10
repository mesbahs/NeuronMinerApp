package com.thesis.neuronplot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;


public class StartPage extends FragmentActivity {
	private Button btnProceed;
	private Button btnImport;
	 static final int PICK_CONTACT_REQUEST = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startpage);
		addListenerOnButtonProceed();
		addListenerOnButtonImport();
		
		
	}
	public void addListenerOnButtonProceed() {
		 
		
		btnProceed = (Button) findViewById(R.id.btnproceed);
	 
		btnProceed.setOnClickListener(new View.OnClickListener() {
	 
		  
		  public void onClick(View v) {
			
			
		   Intent myIntent = new Intent(StartPage.this, Charts.class);
			 
		   StartPage.this.startActivity(myIntent);
		   
			}
			
		  
	 
		});
	  }
	public void addListenerOnButtonImport() {
		 
		
		btnImport = (Button) findViewById(R.id.btnimport);
	 
		btnImport.setOnClickListener(new View.OnClickListener() {
	 
		  
		  public void onClick(View v) {
			  
			  Intent myIntent = new Intent(StartPage.this, PieChartActivity.class);
			  StartPage.this.startActivity(myIntent);
			}
			
		  
	 
		});
	  }
	
 

}
