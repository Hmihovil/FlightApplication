package info.androidhive.flightApplication;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

@SuppressLint("NewApi")
public class Mapping extends Activity{  //removed ONCLICKLISTENER IMPLEMETATION
	
	CheckBox checkBox;
	CheckBox checkBox2;
	CheckBox checkBox3;
	CheckBox checkBox4;
	BitmapDrawable bit;
	BitmapDrawable bit2;
	BitmapDrawable bit3;
	BitmapDrawable bit4;
	Button btn;
	Button btn2;
	Button btn3;
	Button btn4;
	
	public Mapping(){}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapping);
       
		getActionBar().setHomeButtonEnabled(true);//logo app 
		
       this.checkBox=(CheckBox) findViewById(R.id.checkBox);
       this.checkBox2=(CheckBox)findViewById(R.id.checkBox2);
       this.checkBox3=(CheckBox) findViewById(R.id.checkBox3);
       this.checkBox4=(CheckBox) findViewById(R.id.checkBox4);

       this.btn=(Button)  findViewById(R.id.button);
       this.btn2=(Button) findViewById(R.id.button2);
       this.btn3=(Button) findViewById(R.id.button3);
       this.btn4=(Button) findViewById(R.id.button4);
           //plan=(ImageView)findViewById(R.id.imageView);

           Resources r =this.getResources();
           bit= (BitmapDrawable)r.getDrawable(R.drawable.pin1);
           bit2= (BitmapDrawable)r.getDrawable(R.drawable.pin2);
           bit3= (BitmapDrawable)r.getDrawable(R.drawable.pin3);
           bit4= (BitmapDrawable)r.getDrawable(R.drawable.pin4);

           btn.setVisibility(View.INVISIBLE);
           btn2.setVisibility(View.INVISIBLE);
           btn3.setVisibility(View.INVISIBLE);
           btn4.setVisibility(View.INVISIBLE);
   
           
           CheckedListener listener = new CheckedListener(); // define the class Checked Listener
           checkBox.setOnCheckedChangeListener(listener);
           checkBox2.setOnCheckedChangeListener(listener);
           checkBox3.setOnCheckedChangeListener(listener);
           checkBox4.setOnCheckedChangeListener(listener);

       
    }
	


	// Inside class which handles OnChecked Stated of checkbox
	class CheckedListener implements OnCheckedChangeListener
	{

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean checked) {
			
			int id= buttonView.getId();  // gives id of checkedBox
			
			switch(id)
		    {
		        case R.id.checkBox:
		        	if (checked)

		        {btn.setVisibility(View.VISIBLE);}
		        else if(checked==false)
		        {btn.setVisibility(View.INVISIBLE);}
		            break;


		        case R.id.checkBox2:if(checked )
		        {btn2.setVisibility(View.VISIBLE);}
		         else if(checked==false)
		         {btn2.setVisibility(View.INVISIBLE);}
		            break;


		        case R.id.checkBox3:
		         if(checked)
		        {btn3.setVisibility(View.VISIBLE);}
		         else if(checked==false)
		        {btn3.setVisibility(View.INVISIBLE);}
		            break;


		        case R.id.checkBox4:
		         if(checked)
		        {btn4.setVisibility(View.VISIBLE);}
		         else if(checked==false)
		         {btn4.setVisibility(View.INVISIBLE);}
		            break;

		    }
			
			
		}
		
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
			new AlertDialog.Builder(Mapping.this)
				.setTitle("Contact us")
				.setMessage("Feel free to give any comment or feedback for this application.\n \nE-mail us: \n FlightApp.feedback@gmail.com ")
				.create().show();
				
		}
		return super.onOptionsItemSelected(item);
	}	
	
	
}
