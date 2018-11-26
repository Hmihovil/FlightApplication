package info.androidhive.flightApplication;


import java.util.ArrayList;
import org.json.JSONArray;
import android.app.Fragment;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.content.Context;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONException;


public class GetArrivingFlight extends Fragment  {
	
	private String answer; // store which network phone is connected to
	
	private ListView GetAllArivingListView; // For showing flights in list
	
	private ArrayList<Flight> ArrivingFlightList; // list of arriving flights (FLIGHT.java)
	boolean searchOn = false ; // tells if user is searching for any particular flight
	
	private EditText searchField;// store the value of flight to be searched
	
	private ProgressBar pbHeaderProgress;//to show progress of app. usually when getting data from server
	private  static final int refreshTime = 60 * 100 ; 
	final Handler handler = new Handler();
	Runnable refresh = new Runnable() {
         @Override
         public void run() {
       	  new GetAllArrivingTask().execute(new ConnectorArriving()); //Load Data
             handler.postDelayed( this, refreshTime );
         }
     }; 
	public GetArrivingFlight(){} // constructor method
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
       View rootView = inflater.inflate(R.layout.get_arriving_flight, container, false);
       
       this.GetAllArivingListView = (ListView) rootView.findViewById(R.id.GetAllArrivingList);
       
       this.searchField = (EditText) rootView.findViewById(R.id.EditText_searchArrivingFlight);
       
       this.pbHeaderProgress = (ProgressBar) rootView.findViewById(R.id.prg);
       
    // CHECKING IF Phone is connected or not
       ConnectivityManager cm = (ConnectivityManager) getActivity().getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork) {
           if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
           	answer="You are connected to the server through a WiFi Network";
           if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
           	answer="You are connected to the server through a Mobile Network";
           
           // initiate AsyncTask here
           new GetAllArrivingTask().execute(new ConnectorArriving());
           
           handler.postDelayed( refresh , refreshTime);
           
        // show the progress bar
           pbHeaderProgress.setVisibility(View.VISIBLE);
           
       } 		
		else
			answer = "No Internet Connection,please switch on mobile Data/Wi-fi to try connect to the server";
		Toast.makeText(getActivity().getApplicationContext(), answer, Toast.LENGTH_LONG).show();
		// NEW FIGURE DFD _28 April 
		handler.postDelayed( refresh , refreshTime);
       
       
     
     //set TextChange Listener to it 
       this.searchField.addTextChangedListener(new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			if(s.length() > 0)
			{
				sortArrayList(s);
				searchOn = true ; 
			}
			else
			{
				if(ArrivingFlightList != null )
					GetAllArivingListView.setAdapter(new GetAllArrivingListViewAdapter(ArrivingFlightList,getActivity()));
				searchOn = false ;
				
			}
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	});
       
    // Hide the keyboard
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return rootView;
    }
	
  @Override
public void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	handler.removeCallbacks(refresh) ; 
}
  @Override
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	handler.postDelayed( refresh , refreshTime);
}
	public void  setListAdapter(JSONArray jsonArray)
	    {
	    	if (getActivity() != null)  //solved Slid menu crash
	    	{
	    		if (searchOn == false && jsonArray != null) //solved off net crash
	    		{
	    		setUpFlightArrivingList(jsonArray);
	    		}
			}
	   	 	
	    }
	
	   public void setUpFlightArrivingList(JSONArray jArray)
	    {
	    	this.ArrivingFlightList = new ArrayList<Flight>();// initialising new arrayist
	    	
	    	//iterate through jsonArray to make arraylist of Flights
	    	for (int i = 0; i < jArray.length(); i++) {
	    		try 
	    		{
	    			// adding Flight object to arraylist
	    			// passing JSONObject into c
					this.ArrivingFlightList.add(new Flight(jArray.getJSONObject(i)));
					
				}
	    		catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    	
	    	Log.i("GETARRIVING LIST", "Length of Arraylist :"+this.ArrivingFlightList.size());
	    	
	    	// passing ArrayList of arrivingflights to constructor of ListViewAdapter
	   	 	this.GetAllArivingListView.setAdapter(new GetAllArrivingListViewAdapter(this.ArrivingFlightList,getActivity()));
	   	 	this.searchField.clearFocus();
	   	
	    }
	   
	   // sort the array based on input from editText
	    public void sortArrayList(CharSequence cs)
	    {
	    	ArrayList<Flight> sorted = new ArrayList<Flight>();
	    	if (this.ArrivingFlightList != null )
	    	{
	    	
	    	for (int i = 0; i < this.ArrivingFlightList.size(); i++) {
				Flight f = this.ArrivingFlightList.get(i);
				if(f.getCity().toLowerCase().contains(cs.toString().toLowerCase()))
					sorted.add(f);
				
				if(f.getFlightNumber().toLowerCase().contains(cs.toString().toLowerCase()))
					sorted.add(f);
			}
	    	
	    	if (sorted.size() > 0 )
	   	 	this.GetAllArivingListView.setAdapter(new GetAllArrivingListViewAdapter(sorted,getActivity()));
	    } 	
	    	else 
	    	Toast.makeText(getActivity().getApplicationContext(), "No data available, please try to check Internet connection", Toast.LENGTH_LONG).show();
	    }
	 // STARTing 
	   private class GetAllArrivingTask extends AsyncTask<ConnectorArriving,Long,JSONArray>
	   {
		// RUNS ON BACKGROUNG THREAD
	       @Override
	       protected JSONArray doInBackground(ConnectorArriving... params) {

	           // it is executed on Background thread
	            return params[0].GetAllArriving();
	       }

	       @Override
	       protected void onPostExecute(JSONArray jsonArray) {
	    	// hide the progress spinner
	    	   pbHeaderProgress.setVisibility(View.GONE);
	    	
	        setListAdapter(jsonArray);
	     

	       }
	   }
		
	    }

    
    
	
	