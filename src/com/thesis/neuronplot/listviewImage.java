package com.thesis.neuronplot;

import com.thesis.adapter.CustomList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;

public class listviewImage extends Activity {
  ListView list;
  Context context;
  String[] web = {
    "Google Plus",
      "Twitter",
      "Windows",
      "Bing",
      "Itunes",
      "Wordpress",
      "Drupal"
  } ;
  String[] imageUrl = {
		  "http://neuromorpho.org/neuroMorpho/images/imageFiles/Acsady/cZI_1.png",
		  "http://neuromorpho.org/neuroMorpho/images/imageFiles/Acsady/cZI_1.png",
		  "http://neuromorpho.org/neuroMorpho/images/imageFiles/Acsady/cZI_1.png",
		  "http://neuromorpho.org/neuroMorpho/images/imageFiles/Acsady/cZI_1.png",
		  "http://neuromorpho.org/neuroMorpho/images/imageFiles/Acsady/cZI_1.png",
		  "http://neuromorpho.org/neuroMorpho/images/imageFiles/Acsady/cZI_1.png",
		  "http://neuromorpho.org/neuroMorpho/images/imageFiles/Acsady/cZI_1.png"
  };
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.neuronlistview);
    context=this;
   
    list=(ListView)findViewById(R.id.list);
   
       /* list.setAdapter(new CustomList(this, web, imageUrl));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(listviewImage.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                }
            });*/
  }
}
