package info.androidhive.flightApplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class Firest extends Activity{  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firest);
		
		getActionBar().setHomeButtonEnabled(true);
		
		
		 WebView WV = (WebView)findViewById (R.id.webView1);
	        WV.loadUrl("file:///android_res/drawable/screen.png");
	        WV.getSettings().setBuiltInZoomControls(true);
	        WV.getSettings().setSupportZoom(true);
	        WV.getSettings().setBuiltInZoomControls(true);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(android.R.id.home == item.getItemId()){
			finish();
		}

		if(item.getItemId()== R.id.aboutUs)
		{
			new AlertDialog.Builder(Firest.this)
				.setTitle("Contact us")
				.setMessage("Feel free to give any comment or feedback for this application.\n \nE-mail: \n FlightApp.feedback@gmail.com ")
				.create().show();
				
		}
		
		return super.onOptionsItemSelected(item);
	}

}