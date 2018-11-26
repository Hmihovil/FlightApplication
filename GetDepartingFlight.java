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


public class GetDepartingFlight extends Fragment  {

	String answer;

	private ListView GetAllDepartingListView;

	private ArrayList<Flight> DepartingFlightList; // list of arriving flights (FLIGHT.java)
	boolean searchOn = false ;
	private EditText searchField;
	private ProgressBar pbHeaderProgress;
	private  static final int refreshTime = 60 * 100 ; 
	final Handler handler = new Handler();
	 Runnable refresh = new Runnable() {

         @Override
         public void run() {
       	  new GetAllDepartingTask().execute(new ConnectorDeparting()); //Load Data
             handler.postDelayed( this, refreshTime );
         }
     }; 

	public GetDepartingFlight(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.get_departing_flight, container, false);

		this.GetAllDepartingListView = (ListView) rootView.findViewById(R.id.GetAllDepartingList);

		this.searchField = (EditText) rootView.findViewById(R.id.EditText_searchDepartingFlight);

		this.pbHeaderProgress = (ProgressBar) rootView.findViewById(R.id.prg);


		ConnectivityManager cm = (ConnectivityManager) getActivity().getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork) {
			if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				answer="You are connected to the server through a WiFi Network";
			if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				answer="You are connected to the server through a Mobile Network";
		    // initiate AsyncTask here
	           new GetAllDepartingTask().execute(new ConnectorDeparting());
	           
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
					if(DepartingFlightList != null)
						GetAllDepartingListView.setAdapter(new GetAllDepartingListViewAdapter(DepartingFlightList,getActivity()));
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
		if (getActivity() != null)  //solved crash
		{
			if (searchOn == false && jsonArray != null)
			{
				setUpFlightDepartingList(jsonArray);
			}
		}

	}

	public void setUpFlightDepartingList(JSONArray jArray)
	{
		this.DepartingFlightList = new ArrayList<Flight>();  // initialising new arrayist

		//iterate through jsonArray to make arraylist of Flights
		for (int i = 0; i < jArray.length(); i++) {
			try 
			{
				// adding Flight object to arraylist
				// passing JSONObject into c
				this.DepartingFlightList.add(new Flight(jArray.getJSONObject(i)));

			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Log.i("GETARRIVING LIST", "Length of Arraylist :"+this.DepartingFlightList.size());

		// passing ArrayList of arrivingflights to constructor of ListViewAdapter
		this.GetAllDepartingListView.setAdapter(new GetAllDepartingListViewAdapter(this.DepartingFlightList,getActivity()));
		this.searchField.clearFocus();
		//handler.postDelayed(refresh , 60 * 1000 );
	}

	// sort the array based on input from editText
	public void sortArrayList(CharSequence cs)
	{
		ArrayList<Flight> sorted = new ArrayList<Flight>();
		if (this.DepartingFlightList != null )
		{
			for (int i = 0; i < this.DepartingFlightList.size(); i++) {
				Flight f = this.DepartingFlightList.get(i);
				if(f.getCity().toLowerCase().contains(cs.toString().toLowerCase()))
					sorted.add(f);

				if(f.getFlightNumber().toLowerCase().contains(cs.toString().toLowerCase()))
					sorted.add(f);
			}
			if (sorted.size() > 0 )
				this.GetAllDepartingListView.setAdapter(new GetAllDepartingListViewAdapter(sorted,getActivity()));
		}
		else
			Toast.makeText(getActivity().getApplicationContext(), "No data available, please try to check Internet connection", Toast.LENGTH_LONG).show();
	}



	private class GetAllDepartingTask extends AsyncTask<ConnectorDeparting,Long,JSONArray>
	{
		@Override
		protected JSONArray doInBackground(ConnectorDeparting... params) {

			// it is executed on Background thread

			return params[0].GetAllDeparting();
		}

		@Override
		protected void onPostExecute(JSONArray jsonArray) {

			pbHeaderProgress.setVisibility(View.GONE);

			setListAdapter(jsonArray);

		}
	}

}



