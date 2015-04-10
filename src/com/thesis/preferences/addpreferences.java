package com.thesis.preferences;
import com.thesis.models.DatabaseHandler;
import com.thesis.models.ServiceModel;
import com.thesis.neuronplot.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addpreferences extends Activity implements OnClickListener{
	Button submit, exit; 
	String key, url; 
	EditText userinput, passinput; 
	DatabaseHandler db = new DatabaseHandler(this);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addservice);
		getInit();
		}
	public void getInit() { 
		submit = (Button) findViewById(R.id.submit); 
		exit = (Button) findViewById(R.id.exit); 
		userinput = (EditText) findViewById(R.id.userinput); 
		passinput = (EditText) findViewById(R.id.passinput); 
		submit.setOnClickListener(this); 
		exit.setOnClickListener(this);
		}

	

	@Override
	public void onClick(View currentButton) {
		switch (currentButton.getId()) { 
		case R.id.submit: 
			key = userinput.getText().toString(); 
			url = passinput.getText().toString(); 
			db.addService(new ServiceModel(key, url));
			Toast.makeText(this, "Details are saved", 20).show();
			break; 
		case R.id.exit: 
			finish(); 
		}

		
	}

}
