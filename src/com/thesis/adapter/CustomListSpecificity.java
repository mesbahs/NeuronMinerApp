package com.thesis.adapter;

import java.net.URL;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.thesis.neuronplot.R;
import com.thesis.neuronplot.SpecificitySimilarity;

import Com.thesis.utils.ImageLoader;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.neuronplot.listviewImage;
public class CustomListSpecificity extends BaseAdapter{   
    String [] result;
    Context context;
    String [] imageId;
    String [] species;
      private static LayoutInflater inflater=null;
    public CustomListSpecificity(SpecificitySimilarity mainActivity, String[] prgmNameList, String[] prgmImages, String[] Species) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        imageId=prgmImages;
        species=Species;
        
         inflater = ( LayoutInflater )context.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView txt;
        ImageView img;
        TextView txt1;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;       
             rowView = inflater.inflate(R.layout.neuronlistsingle, null);
             holder.txt=(TextView) rowView.findViewById(R.id.txt);
             holder.img=(ImageView) rowView.findViewById(R.id.img);
             holder.txt1=(TextView) rowView.findViewById(R.id.txt1);
             
         holder.txt.setText(result[position]);
         holder.txt1.setText(species[position]);
         URL url;
	
			 
			 String image_url = imageId[position];
	         
		        // ImageLoader class instance
		        ImageLoader imgLoader = new ImageLoader(context);
		         
		        // whenever you want to load an image from url
		        // call DisplayImage function
		        // url - image url to load
		        // loader - loader image, will be displayed before getting image
		        // image - ImageView 
		        int loader = R.drawable.loading;
		        imgLoader.DisplayImage(image_url, loader, holder.img);
         
   
         //holder.img.loadUrl(imageId[position]);     
           
        return rowView;
    }

}
