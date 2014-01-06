package com.OffByOne.roygbiv;

import com.OffByOne.roygbiv.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class HighScores extends Activity {
	
	static TextView one_Name  = null;
	static TextView one_Num   = null;
	
	static TextView two_Name  = null;
	static TextView two_Num   = null;
	 
	static TextView three_Name  = null;
	static TextView three_Num   = null;
	 
	static TextView four_Name  = null;
	static TextView four_Num   = null;
	 
	static TextView five_Name  = null;
	static TextView five_Num   = null;
	 
	static TextView six_Name   = null;
	static TextView six_Num    = null;
	 
	static TextView seven_Name  = null;
	static TextView seven_Num   = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_scores);
		
		
		one_Name   =  (TextView)findViewById(R.id.hs_1stName);
		one_Num    =  (TextView)findViewById(R.id.hs_1stScore);
		           
		two_Name   = (TextView)findViewById(R.id.hs_2ndName);
		two_Num    = (TextView)findViewById(R.id.hs_2ndScore);
		           
		three_Name = (TextView)findViewById(R.id.hs_3rdName);
		three_Num  = (TextView)findViewById(R.id.hs_3rdScore);
		           
		four_Name  = (TextView)findViewById(R.id.hs_4thName);
		four_Num   = (TextView)findViewById(R.id.hs_4thScore);
		           
		five_Name  = (TextView)findViewById(R.id.hs_5thName);
		five_Num   = (TextView)findViewById(R.id.hs_5thScore);
		           
		six_Name   = (TextView)findViewById(R.id.hs_6thName);
		six_Num    = (TextView)findViewById(R.id.hs_6thScore);
		           
		seven_Name = (TextView)findViewById(R.id.hs_7thName);
		seven_Num  = (TextView)findViewById(R.id.hs_7thScore);
		
		Bundle extras = getIntent().getExtras();
		String[] theNames = null;
		int[] theNums = null; 
		if (extras != null)
		{
			 theNames = extras.getStringArray("names");
			theNums = extras.getIntArray("nums");
		}
		
		
		if(theNames != null && theNums != null)
		{
			HS_Entry[] scoresArray = new HS_Entry[theNames.length];
			
			for(int i=0; i<scoresArray.length; i++)
			{
				scoresArray[i] = new HS_Entry(theNames[i], theNums[i]); 
				//scoresArray[i].setScore(theNums[i]); 
			}
			updateHSviews(scoresArray);
		}
	}
	
	private void updateHSviews(HS_Entry[] thisArray)
	{
		one_Name.setText(thisArray[0].getName());
		one_Num.setText(Roygbiv.intToString(thisArray[0].getScore()));
		          
		two_Name.setText(thisArray[1].getName());
		two_Num.setText(Roygbiv.intToString(thisArray[1].getScore()));
		          
		three_Name.setText(thisArray[2].getName());
		three_Num.setText(Roygbiv.intToString(thisArray[2].getScore()));
		          
		four_Name.setText(thisArray[3].getName());
		four_Num.setText(Roygbiv.intToString(thisArray[3].getScore()));
		          
		five_Name.setText(thisArray[4].getName());
		five_Num.setText(Roygbiv.intToString(thisArray[4].getScore()));
		          
		six_Name.setText(thisArray[5].getName());
		six_Num.setText(Roygbiv.intToString(thisArray[5].getScore()));
		          
		seven_Name.setText(thisArray[6].getName());
		seven_Num.setText(Roygbiv.intToString(thisArray[6].getScore()));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_high_scores, menu);
		return true;
	}

}
