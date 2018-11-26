package info.androidhive.flightApplication;

  

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetAllDepartingListViewAdapter extends BaseAdapter {
	
	//private JSONArray dataArray;
	private ArrayList<Flight> dataArray;
	private Activity activity;
	
	private static LayoutInflater inflater =  null;
	
	
	//public GetAllArrivingListViewAdapter(JSONArray jsonArray, Activity a) 
	
	public GetAllDepartingListViewAdapter(ArrayList<Flight> FlightArray, Activity a)  //changed to accept arraylist
	{
		
		this.dataArray = FlightArray ;
		this.activity = a;
		
		
		inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
	}
	
	@Override
	public int getCount() {
		//return this.dataArray.length();
		return this.dataArray.size();
	}
	
	@Override
	public Object getItem(int position) {
		return position;
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ListCell cell;
		if (convertView == null){
			
			convertView = inflater.inflate(R.layout.get_all_departing_list_view_cell, null);
			cell = new ListCell();
			
			cell.City = (TextView) convertView.findViewById(R.id.City);
			cell.F_Id = (TextView) convertView.findViewById(R.id.F_Id);
			cell.Time = (TextView) convertView.findViewById(R.id.Time);
			cell.Status = (TextView) convertView.findViewById(R.id.Status);
			cell.Date = (TextView) convertView.findViewById(R.id.Date);
			
			convertView.setTag(cell);
		}
		else {
			
			cell = (ListCell) convertView.getTag();
		}
		
		// Get Flight Object From arraylist based on position of listItem
		Flight flight = this.dataArray.get(position);
		cell.City.setText(flight.getCity());
		cell.F_Id.setText(""+flight.getFlightNumber());
		cell.Time.setText(flight.getGate());
		cell.Status.setText(flight.getStatus());
		cell.Date.setText(flight.getDateOfFlight());
		// Modify BackGround Color of status : 
				if (flight.getStatus().equals("On Time"))
				{
					cell.Status.setBackgroundColor(Color.parseColor("#c8ff75"));
				}
				else if (flight.getStatus().equals("Cancelled"))
				{
					cell.Status.setBackgroundColor(Color.parseColor("#ffffad"));
				}
				else if (flight.getStatus().equals("Last Call"))
				{
					cell.Status.setBackgroundColor(Color.parseColor("#ffabb7"));
				}
				else if (flight.getStatus().equals("Delay"))
				{
					cell.Status.setBackgroundColor(Color.parseColor("#e6e6e6"));
				}
				else if (flight.getStatus().equals("Early"))
				{
					cell.Status.setBackgroundColor(Color.parseColor("#ffda99"));
				}
				else
				{
					cell.Status.setBackgroundColor(Color.parseColor("#a6f0ff"));
				}
				// End 
		return convertView;
	}
	
	
	private class ListCell 
	{
		private TextView F_Id;
		private TextView City;
		private TextView Time;
		private TextView Status;
		private TextView Date;
	}

}