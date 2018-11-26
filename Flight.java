package info.androidhive.flightApplication;

import org.json.JSONException;
import org.json.JSONObject;

public class Flight {
	
	
	private String FlightNumber;
	private String city;
	private String dateOfFlight;
	private String type;
	private String time;
	private String status;
	
	public Flight(JSONObject object)
	{
		// we will get all the details of Flight from JSONObject and set the values of the Object
		try
		{
			//this.FlightID = object.getInt("F_Id");
			this.FlightNumber = object.getString("F_Id");
			this.city = object.getString("City");
			this.time = object.getString("Time");
			this.status = object.getString("Status");
			
			// convert Date String into Date 
			this.dateOfFlight =object.getString("Date");
			
			
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		
	}


	public String getFlightNumber() {
		return FlightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		FlightNumber = flightNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDateOfFlight() {
		return dateOfFlight;
	}

	public void setDateOfFlight(String dateOfFlight) {
		this.dateOfFlight = dateOfFlight;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGate() {
		return time;
	}

	public void setGate(String gate) {
		this.time = gate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}