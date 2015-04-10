package com.thesis.googledrive;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.DriveContentsResult;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.plus.Plus;

/**
 * Android Drive Quickstart activity. This activity takes a photo and saves it
 * in Google Drive. The user is prompted with a pre-made dialog which allows
 * them to choose the file location.
 */
public class DriveConnection extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener {
	private static final int RC_SIGN_IN = 0;

	  /* Client used to interact with Google APIs. */
	  private GoogleApiClient mGoogleApiClient;

	  /* A flag indicating that a PendingIntent is in progress and prevents
	   * us from starting further intents.
	   */
	  private boolean mIntentInProgress;

	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GoogleApiClient instance
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
       

       
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // Connected to Google Play services!
        // The good stuff goes here.
    	Toast.makeText(getBaseContext(), "Connected to Google Play services",
				Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
    	Toast.makeText(getBaseContext(), "nothing happend",
				Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
    	  if (!mIntentInProgress && result.hasResolution()) {
    	    try {
    	      mIntentInProgress = true;
    	      startIntentSenderForResult(result.getResolution().getIntentSender(),
    	          RC_SIGN_IN, null, 0, 0, 0);
    	    } catch (SendIntentException e) {
    	      // The intent was canceled before it was sent.  Return to the default
    	      // state and attempt to connect to get an updated ConnectionResult.
    	      mIntentInProgress = false;
    	      mGoogleApiClient.connect();
    	    }
    	  }
    	}
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
    	  if (requestCode == RC_SIGN_IN) {
    	    mIntentInProgress = false;

    	    if (!mGoogleApiClient.isConnecting()) {
    	      mGoogleApiClient.connect();
    	    }
    	  }
    	}

    @Override
    protected void onStart() {
        super.onStart();
        // more about this later
            mGoogleApiClient.connect();
        
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}