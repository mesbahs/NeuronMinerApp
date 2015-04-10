package com.thesis.models;


public class ServiceModel {
    
    //private variables
    int _id;
    String _key;
    String _url;
     
    // Empty constructor
    public ServiceModel(){
         
    }
    // constructor
    public ServiceModel(int id, String key, String _url){
        this._id = id;
        this._key = key;
        this._url = _url;
    }
     
    // constructor
    public ServiceModel(String key, String _url){
        this._key = key;
        this._url = _url;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
     
    // getting key
    public String getKey(){
        return this._key;
    }
     
    // setting key
    public void setKey(String key){
        this._key = key;
    }
     
    // getting url
    public String getUrl(){
        return this._url;
    }
     
    // setting url
    public void setUrl(String url){
        this._url = url;
    }
}