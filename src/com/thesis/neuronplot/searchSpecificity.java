package com.thesis.neuronplot;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class searchSpecificity extends Activity{
	String neuronID="";
	String SwcName="";
	private EditText editAll;
	private EditText editBranch;
	private EditText editBifurcation;
	private Button btnproceed;
	int all=0; 
	int branch=0;
	int bifurcation=0;
	 Context context;
	List<String> list = new ArrayList<String>();
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchspecificity);
        neuronID=getIntent().getStringExtra("NeuronID");
        SwcName=getIntent().getStringExtra("SwcName");
        editAll=(EditText) findViewById(R.id.editall);
        editBranch=(EditText) findViewById(R.id.editbranch);
        editBifurcation=(EditText) findViewById(R.id.editBifurcation);
        editAll.setText("0");
        editBranch.setText("0");
        editBifurcation.setText("0");
        addListenerOnButtonProceed();
        
        


       
    }
	public void addListenerOnButtonProceed() {
		 
		
		   btnproceed = (Button) findViewById(R.id.similar);
		 
		   btnproceed.setOnClickListener(new View.OnClickListener() {
		 
			  
			public void onClick(View v) {
			 if (editAll.getText().toString()!="" && editBranch.getText().toString()!="" && editBifurcation.getText().toString()!=""){
				 all=Integer.parseInt(editAll.getText().toString());
				 branch=Integer.parseInt(editBranch.getText().toString());
				 bifurcation=Integer.parseInt(editBifurcation.getText().toString());
				 if(all+branch+bifurcation==100){
			
			    Intent myIntent = new Intent(searchSpecificity.this, SpecificitySimilarity.class);
			    myIntent.putExtra("NeuronID", neuronID);
			    myIntent.putExtra("allP",editAll.getText().toString());
			    myIntent.putExtra("branchP",editBranch.getText().toString());
			    myIntent.putExtra("bifurcationP",editBifurcation.getText().toString());
			    myIntent.putExtra("SwcName", SwcName);
			    searchSpecificity.this.startActivity(myIntent);}
				 else
				 {
					 Toast.makeText(getApplicationContext(), "Please select correct values. The sum should be 100. Forexample: 50,30,20 ",
							   Toast.LENGTH_LONG).show();
				 }
			   
				}
				 else
				 {
					 Toast.makeText(getApplicationContext(), "Please select correct values. The sum should be 100. Forexample: 50,30,20 ",
							   Toast.LENGTH_LONG).show();
				 }
				
			  }
		 
			});
	}

}
