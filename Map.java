package info.androidhive.flightApplication;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;

public class Map extends Fragment{  //removed ONCLICKLISTENER IMPLEMETATION
	
	public Map(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       View rootView = inflater.inflate(R.layout.map, container, false);
        
       Button show = (Button) rootView.findViewById(R.id.map);
       
       Button show1 = (Button) rootView.findViewById(R.id.button1);
       
       Button show2 = (Button) rootView.findViewById(R.id.button2);
       
       Button show3 = (Button) rootView.findViewById(R.id.button3);
       
       show.setOnClickListener(new OnClickListener (){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(getActivity(),Second.class);
			startActivity(i);
			
		}});
       
       show1.setOnClickListener(new OnClickListener (){

   		@Override
   		public void onClick(View v) {
   			// TODO Auto-generated method stub
   			Intent ii = new Intent(getActivity(),Firest.class);
   			startActivity(ii);
   			
   		}});
       
       show2.setOnClickListener(new OnClickListener (){

      		@Override
      		public void onClick(View v) {
      			// TODO Auto-generated method stub
      			Intent iii = new Intent(getActivity(),Third.class);
      			startActivity(iii);
      			
      		}});
       
       show3.setOnClickListener(new OnClickListener (){

     		@Override
     		public void onClick(View v) {
     			// TODO Auto-generated method stub
     			Intent iii = new Intent(getActivity(),Mapping.class);
     			startActivity(iii);
     			
     		}});
      
        return rootView;
    }
}
