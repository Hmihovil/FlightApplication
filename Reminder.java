package info.androidhive.flightApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class Reminder extends Fragment {

	private EditText editDate,editTime,editMessage;
	private Button setButton;
	private int myYear, myMonth, myDay, myHour, myMinute,myAmPm;
	Button date;
	Button time;
	String am_pm = "";
	static final int ID_DATEPICKER = 0;
	static final int ID_TIMEPICKER = 1;
	protected static final OnClickListener datePickerButtonOnClickListener = null;
	protected static final OnClickListener timePickerButtonOnClickListener = null;
	String dateStr="";
	String timeStr="";
	String msg="";
	private SimpleDateFormat timeFormatter;
	Alarm al;
	String dateTime="";
	Date setDate=null;
	int requestCode=0;

	public Reminder(){}

	
	@Override

	public View onCreateView(LayoutInflater inflater, ViewGroup container,

			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.reminder, container, false); 


		// You need to stop user typing anyting in EditText.
		
		editDate = (EditText)rootView.findViewById(R.id.edt_date);

		editTime=(EditText)rootView.findViewById(R.id.edt_time);

		editMessage =(EditText)rootView.findViewById(R.id.edt_msg);

		setButton= (Button)rootView.findViewById(R.id.btn_set);

		date=(Button)rootView.findViewById(R.id.date_picker);

		time=(Button)rootView.findViewById(R.id.time_picker);

		timeFormatter=new SimpleDateFormat("hh:mm a");

		date.setOnClickListener(new OnClickListener() {

			public void onClick(View v) 
			{

				final Calendar c = Calendar.getInstance();
				myYear = c.get(Calendar.YEAR);
				myMonth = c.get(Calendar.MONTH);
				myDay = c.get(Calendar.DAY_OF_MONTH);
				
				//getActivity().showDialog(ID_DATEPICKER);
				
				DatePickerDialog dateDialog = new DatePickerDialog(getActivity(),
						myDateSetListener,
						myYear, myMonth, myDay);
				
				dateDialog.getDatePicker().setMinDate(System.currentTimeMillis()-600); // set min date to current date 
				dateDialog.show();
				
				

			}
		});
		
		
		time.setOnClickListener(new OnClickListener() {

	
			@Override
			public void onClick(View v) {

				final Calendar c = Calendar.getInstance();
				myHour = c.get(Calendar.HOUR_OF_DAY);
				myMinute = c.get(Calendar.MINUTE);
				
				//getActivity().showDialog(ID_TIMEPICKER);
				new TimePickerDialog(getActivity(),
						myTimeSetListener,
						myHour, myMinute, false).show();
				

			}
		});
		
		setButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				requestCode=(int)System.currentTimeMillis();
				
				// Check if all editText are filled
				if (editDate.getText().length() > 0 && editTime.getText().length() > 0 && editMessage.getText().length() > 0) 
				{
					// get text from all EditText
					msg = editMessage.getText().toString();
					dateTime = dateStr.trim()+" "+timeStr.trim();
					
					// GET DATE From dateTime String
					try
					{
						SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
						setDate=(Date)formatter.parse(dateTime);
						
						// Check if the setDate is in past. if Yes, ask for new date and time
						
						Date curDateTime = new Date(System.currentTimeMillis()); // current date and time
						
						if(setDate.before(curDateTime) || setDate.equals(curDateTime))
						{
							// alarm date is in past
							Toast.makeText(getActivity().getApplicationContext(), "Alarm date is in Past. Please set it again", Toast.LENGTH_LONG).show();
						}
						else
						{
							// initialiaze new Alarm
							al = new Alarm();
							
							// set Alarm
							al.SetAlarm(getActivity().getApplicationContext(), msg, requestCode, setDate, dateTime);
							
							// Show Toast as a Confirmation of Setting Alarm
							Toast.makeText(getActivity().getApplicationContext(), "Alarm has set", Toast.LENGTH_LONG).show();
							
							// clear all the editTexts
							editMessage.setText("");
							editDate.setText("");
							editTime.setText("");
						}
		
					}
					catch(ParseException e)
					{
						Toast.makeText(getActivity().getApplicationContext(), "Something wrong happened !!", Toast.LENGTH_LONG).show();////////
					}
					
					
				}
				else
				{
					// show toast telling user to fill all the texts
					Toast.makeText(getActivity().getApplicationContext(), "Please fill all the inputs", Toast.LENGTH_SHORT).show();////////
					
				}
				
			}
		});
		return rootView;
	}

	

	private DatePickerDialog.OnDateSetListener myDateSetListener
	= new DatePickerDialog.OnDateSetListener(){

		@Override
		public void onDateSet(DatePicker view, int year,
				int monthOfYear, int dayOfMonth) {

			dateStr=String.valueOf(dayOfMonth)+"-"+String.valueOf(monthOfYear+1)+"-"+String.valueOf(year);
			editDate.setText(dateStr);

		}
	};

	private TimePickerDialog.OnTimeSetListener myTimeSetListener
	= new TimePickerDialog.OnTimeSetListener(){

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			timeStr=String.valueOf(hourOfDay)+":"+String.valueOf(minute);
			editTime.setText(timeStr);

		}
	};

	
	
	public static String convertDate(String input)throws Exception
	{
		SimpleDateFormat df = new SimpleDateFormat( "dd-MM-yyyy HH:mm" );
		Date date=df.parse(input);
		SimpleDateFormat df1=new SimpleDateFormat( "EEEEE , dd MMMM , yyyy , h:mm aa" );
		String s=df1.format(date);
		return s;
	}

}