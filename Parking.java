package info.androidhive.flightApplication;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.os.Environment;
import android.provider.MediaStore;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import android.widget.Toast;
import android.view.View.OnClickListener;

public class Parking extends Fragment {
	
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final String imageUriKey = "ImageUri";
	
	//properties to hold the references 
	private EditText nameText;
	//private EditText LText;
	
	// directory name to store captured images 
		private static final String IMAGE_DIRECTORY_NAME = "Parking Camera";
    // file to store the image
		private Uri fileUri; 

	ImageView imgFavorite; 
	
	public Parking(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
       View rootView = inflater.inflate(R.layout.parking, container, false);
       
       imgFavorite = (ImageView)rootView.findViewById(R.id.imageView1);
        
        // Capture image , Pic click event
        
       imgFavorite.setOnClickListener(new OnClickListener() {
	         @Override
	         public void onClick(View v) 
	         {
	        	  // capture picture
	            open();
	         }
	      });
	      
		  // Checking camera availability
			if (!isDeviceSupportCamera()) {
				Toast.makeText(getActivity().getApplicationContext(),// solved by add getActivity().getApplicationContext(),
						"Sorry! Your device doesn't support camera",
						Toast.LENGTH_LONG).show();
				// will close the app if the device does't have camera
				this.getActivity().finish();
			}
	      
	      
	  // **** get references to the EditTexts *** 
	        nameText=(EditText)rootView.findViewById(R.id.nText);
			//LText=(EditText)findViewById(R.id.fText);
			
	/*create instance of SharedPreferences called setting 
	MyPrefs is the name of preferences file & 0 will allow us to write the preferences
		 */
			SharedPreferences settings=this.getActivity().getSharedPreferences("MyPrefs",0);//solved by add this.getActivity().
			
 //put in default value , save in file 
			nameText.setText(settings.getString("nameValue",""));
			//LText.setText(settings.getString("LValue",""));
			
			String ImageUriString = settings.getString(imageUriKey, null);
			if(ImageUriString != null)
			{
				Uri ImageUri  = Uri.parse(ImageUriString);
				if(ImageUri != null)
				{
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 8;
					final Bitmap bitmap = BitmapFactory.decodeFile(ImageUri.getPath(),
							options);
					if (bitmap !=null)
					{
						imgFavorite.setImageBitmap(bitmap);
					}
					else
					{
						imgFavorite.setImageResource(R.drawable.ic_cam);
					}
					
				}
			}
			
        return rootView;
    }
	
	
	@Override
	public void onStop(){
		// called when the app is about to exit 	
		super.onStop();
		
		SharedPreferences settings =this.getActivity().getSharedPreferences("MyPrefs",0);//solved by add this.getActivity().
		
		// to make changes we need to get an editor 
		SharedPreferences.Editor editior =settings.edit();
		editior.putString("nameValue",nameText.getText().toString());
		//editior.putString("LValue",LText.getText().toString());
		
		if (fileUri != null && fileUri.toString().length() > 2) //Check if Uri is not Null
			{
				editior.putString(imageUriKey, fileUri.toString());
			}
	
		editior.commit();	
	}
	

// Checking device has camera hardware or not
	  private boolean isDeviceSupportCamera() {
		if (getActivity().getApplicationContext()// solved by add getActivity().getApplicationContext(),
				.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false; }
	}

	/*
	 * Capturing Camera Image will launch camera app
	 * open() function will launch the camera to snap a picture.
	 */
	
	public void open()
	{
	      Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	      fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			// start the image capture Intent
			startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	      
	   }
	
	 // Here we store the file uri as it will be null after returning from camera
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// save file uri in bundle
		outState.putParcelable("file_uri", fileUri);
	}

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		//super.onRestoreInstanceState(savedInstanceState);

		// get the file uri
		fileUri = savedInstanceState.getParcelable("file_uri");
	}
	
	 //Receiving activity result method will be called after closing the camera  
	 @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	     
		// if the result is capturing Image
			if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
				if (resultCode == getActivity().RESULT_OK) {
					// successfully captured the image
					// display it in image view
					previewCapturedImage();
				} else if (resultCode == getActivity().RESULT_CANCELED) {
					
					//change back to Last image captured
					changeBackToLastCaptured();
					
					// user cancelled Image capture
					Toast.makeText(getActivity().getApplicationContext(),// solved by add getActivity().getApplicationContext(),,
							"User cancelled image capture", Toast.LENGTH_SHORT)
							.show();
				} else {
					
					//Change back to Last Image Captured
					changeBackToLastCaptured();
					
					// failed to capture image
					Toast.makeText(getActivity().getApplicationContext(),// solved by add getActivity().getApplicationContext(),,
							"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
							.show();
				}
			}
	   }
	 
	 //Change back to last Image
	 private void changeBackToLastCaptured()
	 {
		 SharedPreferences settings=this.getActivity().getSharedPreferences("MyPrefs",0);
		 
		 String ImageUriString = settings.getString(imageUriKey, null);
				 if(ImageUriString != null)
					{
						Uri ImageUri  = Uri.parse(ImageUriString);
						if(ImageUri != null)
						{
							BitmapFactory.Options options = new BitmapFactory.Options();
							options.inSampleSize = 8;
							final Bitmap bitmap = BitmapFactory.decodeFile(ImageUri.getPath(),
									options);
							
							if (bitmap !=null)
							{
								imgFavorite.setImageBitmap(bitmap);
							}
							else
							{
								imgFavorite.setImageResource(R.drawable.ic_cam);
							}
							
							
						}
					}
	 }
	 
	 
	 //Display image from a path to ImageView
		
		private void previewCapturedImage() {
			try {
			
				imgFavorite.setVisibility(View.VISIBLE);

				// bitmap factory
				BitmapFactory.Options options = new BitmapFactory.Options();

				// down sizing image as it throws out of memory Exception for larger img
				
				options.inSampleSize = 8;

				final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
						options);

				imgFavorite.setImageBitmap(bitmap);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}

	// Creating file uri to store images
	
	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}
	
	//return the image
	private static File getOutputMediaFile(int type) {

		// External SD card location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}

		return mediaFile;
	}

}