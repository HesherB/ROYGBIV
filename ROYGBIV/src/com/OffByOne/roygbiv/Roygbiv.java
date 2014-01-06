package com.OffByOne.roygbiv;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.OffByOne.roygbiv.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



public class Roygbiv extends Activity
{
	private static final int numColors = 7;
	private static final int EXIT = 10;
	private static final int WON = 20;
	private static final int NEWHS = 30;
	
	private static String HIGHSCORES = "ROYGBIV_HS";
	HS_Entry[] scoresArray = new HS_Entry[numColors];
	

	private int numMoves;
	private String playerName = "Me";
	
    int[] buttonOrder = new int[numColors]; 
    int[] lastMoveOrder = new int[numColors];
    
    View[] buttons = new View[numColors];
    View[] lastMoveViews = new View[numColors];
    
    View showLast = null;
    View showSolve = null;
    View showMenu = null;
    
    ArrayList<Button> permissions = new ArrayList<Button>();
    
    LinearLayout gameArea = null;
    LinearLayout lastMovePanel = null;
    LinearLayout solutionPanel = null;
    RelativeLayout bottomPanel = null;
   	
   	Random randNums = new Random();
   	
   

   	private void initHS()
   	{
   		
   		scoresArray[0] = new HS_Entry("Red Surefoot", 10);
   		scoresArray[1] = new HS_Entry("Oliver Fellfast", 20);
   		scoresArray[2] = new HS_Entry("Yakiri Wanderlust", 30);
   		scoresArray[3] = new HS_Entry("Grubtooth the Lazy", 40);
   		scoresArray[4] = new HS_Entry("Belladonna Tingewood", 50);
   		scoresArray[5] = new HS_Entry("Ignatio the Denizen", 60);
   		scoresArray[6] = new HS_Entry("Viktor Caveshade", 70);
   		
   	}

   	@Override
    protected void onCreate(Bundle savedInstanceState)
   	{
   		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roygbiv);
        
        gameArea =		(LinearLayout)findViewById(R.id.ROYGBIV);
        lastMovePanel = (LinearLayout)findViewById(R.id.LastMove);
        solutionPanel = (LinearLayout)findViewById(R.id.Solution);
        bottomPanel = 	(RelativeLayout)findViewById(R.id.BottomPanel);

       	buttons[0]= 	(View)findViewById(R.id.Button01);//r
       	buttons[1]=		(View)findViewById(R.id.Button02);//o
       	buttons[2]=		(View)findViewById(R.id.Button03);//y
       	buttons[3]=		(View)findViewById(R.id.Button04);//g
       	buttons[4]=		(View)findViewById(R.id.Button05);//b
       	buttons[5]= 	(View)findViewById(R.id.Button06);//i
       	buttons[6]=		(View)findViewById(R.id.Button07);//v
       	
       	lastMoveViews[0]= 		(View)findViewById(R.id.LastRed);   //r
       	lastMoveViews[1]=		(View)findViewById(R.id.LastOrange);//o
       	lastMoveViews[2]=		(View)findViewById(R.id.LastYellow);//y
       	lastMoveViews[3]=		(View)findViewById(R.id.LastGreen); //g
       	lastMoveViews[4]=		(View)findViewById(R.id.LastBlue);  //b
       	lastMoveViews[5]= 		(View)findViewById(R.id.LastIndigo);//i
       	lastMoveViews[6]=		(View)findViewById(R.id.LastViolet);//v
       	
       	//get bottom panel buttons
       	showLast = (Button)findViewById(R.id.ShowLast);
       	showSolve = (Button)findViewById(R.id.ShowSolve);
       	showMenu = (Button)findViewById(R.id.ShowMenu);
       	
       	//pull in the highscores
       	getHighScore();

            	
       	setupGame();
    }
   	

   	private void onExitClick(View view) 
   	{
   	    showDialog(EXIT);
   	}
   	
   	private void onExitClick() 
   	{
   	    showDialog(EXIT);
   	}
   	
   	
   	@Override
   	protected Dialog onCreateDialog(int id) 
   	{
   		switch (id) 
	   	{
	   		case EXIT:
	   		{
	   			// Create out AlterDialog
			   	Builder builder = new AlertDialog.Builder(this);
			   	builder.setMessage("Giving up?");
			   	builder.setCancelable(true);
			   	builder.setNegativeButton("No!", new exitCancelOnClickListener());
			   	builder.setPositiveButton("Yup", new quitOnClickListener());
			   	AlertDialog dialog = builder.create();
			   	dialog.show();
			   	break;
	   		}
	   		
	   		case WON:
	   		{
	   			// Create out AlterDialog
			   	Builder builder = new AlertDialog.Builder(this);
			   	builder.setMessage("You Won in " + numMoves + " moves!");
			   	builder.setCancelable(true);
			   	builder.setNegativeButton("Play Again!", new wonPlayAgainOnClickListener());
			   	builder.setPositiveButton("Quit", new quitOnClickListener());
			   	AlertDialog dialog = builder.create();
			   	dialog.show();
			   	break;
	   		}
	   		
	   		case NEWHS:
	   		{
	   			// Create out AlterDialog
	   			Builder builder = new AlertDialog.Builder(this);
	   			
	   			// Set an EditText view to get user input 
	   			builder.setMessage("New High Score!");
	   			final EditText input = new EditText(this);
	   			builder.setView(input);
	   			
	   			builder.setPositiveButton("Done!", new DialogInterface.OnClickListener() 
	   			{
	   				public void onClick(DialogInterface dialog, int whichButton)
	   				{ 
	   					playerName = input.getText().toString();
	   						   					
	   					//make a new HE_Entry
	   					Log.i("Player Name:", playerName);
	   					Log.i("Num Moves:", "" + numMoves);
	   					
	   					HS_Entry newChamp = new HS_Entry(playerName, numMoves);
	   					
	   					//swap it for the worst one in the array
	   					newChamp.swapHS(scoresArray[HS_Entry.worstHSposition(scoresArray)]);
	   					
	   					//sort the array
	   					HS_Entry.sortHSarray(scoresArray);
	   				
//	   					//save the info out
	   					save(HIGHSCORES, HStoString());
	   					
	   					//diplay the hall of awesome
	   					displayHighScore();
	   					
	   					//display won
	   					showDialog(WON);
	   				}
	   			});
	   			
	   			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
	   			{  
	   				public void onClick(DialogInterface dialog, int whichButton) 
	   				{    
	   					// Canceled. 
	   					//display won
	   					showDialog(WON);
	   				}
	   			});
	   			
	   			builder.show();
	   			
	   			
			   	break;
	   		}
		   	
	   	}
   	  	return super.onCreateDialog(id);
   	}
 	
   	private final class exitCancelOnClickListener implements DialogInterface.OnClickListener
   	{
	    public void onClick(DialogInterface dialog, int which)
	    {
		    Toast.makeText(getApplicationContext(), "Keep gaming!", Toast.LENGTH_LONG).show();
	    }
   	}
	
	private final class wonPlayAgainOnClickListener implements DialogInterface.OnClickListener
   	{
	    public void onClick(DialogInterface dialog, int which)
	    {
		    setupGame();
	    }
   	}

	private final class quitOnClickListener implements DialogInterface.OnClickListener
	{
	    public void onClick(DialogInterface dialog, int which) 
	    {
		    Roygbiv.this.finish();
	    }
	} 
	
	
	   	
   
	public void setupGame()
   	{
   		initPermissions();
   		clearViews(gameArea);
       	scrambleViews();
       	redisplayViews(gameArea, buttons);
       	numMoves = -1;
       	updateCounter();
   	}
   	
   	public void setupGame(View v)
   	{
   		setupGame();
   	}
   	
   	public void updateCounter()
   	{
   		numMoves++;
   		TextView counter = (TextView)findViewById(R.id.moveCounter);
   		counter.setText(String.valueOf(numMoves));
   	}
   	
   	public void scrambleViews()
   	{
   		//scrap the current butttonOrder
   		for(int i=0; i<numColors; i++)
   		{
   			buttonOrder[i] =-1;
   		}
   		
   		//setup the history context
   		int myRand;
   		Boolean[] used = new Boolean[numColors];
   		Arrays.fill(used, Boolean.FALSE);
   		
   		//populate
   		//for every spot in the layout
   		for(int i=0; i<numColors;)
   		{
   			//get a random number 0-6
   			myRand = randNums.nextInt() % numColors;
   			if(myRand<0)
   			{
   				myRand *= -1;
   			}
   			
   			//if this one isnt already in the layout
   			if (!used[myRand])
   			{
   				//add it in
   				buttonOrder[i] = myRand;
   				//check it off the list
   				used[myRand] = true;
   				//and go looking to fill the next spot
   				i++;
   			}
   		}
   	}
   	
   	public void initPermissions()
   	{
   		permissions.removeAll(permissions);
   		
   		//setup the history context
   		int myRand;
   		Boolean[] used = new Boolean[numColors];
   		Arrays.fill(used, Boolean.FALSE);
   		
   		//populate
   		//while there are more colors that haven't been assigned a permissions level
   		while(numColors > permissions.size())
   		{
   			//get a random number 0-6
   			myRand = randNums.nextInt() % numColors;
   			if(myRand<0)
   			{
   				myRand *= -1;
   			}
   			
   			//if this one hasnt been assigned yet
   			if (!used[myRand])
   			{
   				//assign it
   				permissions.add((Button)buttons[myRand]);
   				
   				//and check it off the list
   				used[myRand] = true;
   			}
   		}
   	}
	

    
    public void roygbivClick(View v)
    { 
    	clearViews(lastMovePanel);
    	redisplayViews(lastMovePanel, lastMoveViews);
    	
    	clearViews(gameArea);	
    	moveViews(v);
    	redisplayViews(gameArea, buttons);
    	updateCounter();
    	
    	if(gameWon())
    	{
    		postGame();
    	}
    }
    
    private void postGame()
    {
    	if(numMoves < HS_Entry.worstHS(scoresArray))
    	{
    		showDialog(NEWHS);
    	}
    	else
    	{
    		showDialog(WON);
    	}
    	
    }
    
   
    
    

	public int permissionsLevel(Button thisView)
    {
    	//things later (higher number index) in the array
    	//have permission to move all preceding elements
    	return permissions.indexOf(thisView);
    }
    

	public void moveViews(View clickedView)
    {
    	//cast this view as a Button object
    	Button myClickedButton = (Button)clickedView;
    	
    	//make a new array to record intermediary results
    	int[] newOrder = new int[numColors];
    	
    	//for every position in the linear layout
    	for(int i=0; i<numColors; i++)
    	{
    		//if this button is going to move
    		if(permissionsLevel(myClickedButton) >= permissionsLevel((Button)buttons[buttonOrder[i]]))
    		{
    			int search = -1;
    			
    			//find out who would belong in this spot
    			for(int offset = 1; offset <= numColors; offset++)
    			{
    				//search backwards by subtracting offset value from curr position "i"
    				search = i - offset;
    				//loop backwards around list
    				if (search < 0)
    				{
    					search += numColors;
    				}
    				
    				//if the one being searched currently is also allowed to move
    				if(permissionsLevel(myClickedButton) >= permissionsLevel((Button)buttons[buttonOrder[search]]))
    				{
    					//then the next position is filled by this button index
    					newOrder[i] = buttonOrder[search];
    					
    					//and stop searching
    					break;
    				}
    			}
    		}
    		//or just keep it the same view
    		else
    		{
    			newOrder[i] = buttonOrder[i];
    		}
    	}
    	
    	//copy the results back into the order array
    	for(int i = 0; i<numColors; i++)
    	{
    		buttonOrder[i] = newOrder[i];
    	}
    }
    
    
    public void getViewPositions()
    {
    	for(int i=0; i<numColors; i++)
    	{
    		buttonOrder[i] = gameArea.indexOfChild(buttons[i]);
    	}
    }
    
    public void clearViews(LinearLayout v)
    {
    	v.removeAllViews();
    }
    
    public void incrementViews()
    {
	   	 //all numbers +1 mod 7
	   	 for(int i=0; i<numColors; i++)
	   	 {
	   		 buttonOrder[i] = (buttonOrder[i] + 1) % numColors;
	   	 }
    }
    
    public void decrementViews()
    {
    	//all numbers -1 mod 7
	   	 for(int i=0; i<numColors; i++)
	   	 {
	   		 buttonOrder[i] = (buttonOrder[i] - 1);
	   		 if(buttonOrder[i] < 0)
	   		 {
	   			 buttonOrder[i] += numColors;
	   		 }
	   	 }
    }
    
    public void redisplayViews(LinearLayout v, View[] myViews)
    {
    	for(int i=0; i<numColors; i++)
    	{
    		v.addView(myViews[buttonOrder[i]], i);
    	}
    }
    
    public void toggleSolve(View v)
    {
    	View solution = (View)findViewById(R.id.Solution);
    	toggleVisibility(solution);
    	
    	Button myButton = (Button)v;
    	
    	if(solution.getVisibility()==(View.VISIBLE))
    	{
    		myButton.setText("Hide");
    	}
    	else
    	{
    		myButton.setText("Show");
    	}
    }
    
    public void toggleLast(View v)
    {
    	View lastMove = (View)findViewById(R.id.LastMove);
    	toggleVisibility(lastMove);
    	
    	Button myButton = (Button)v;
    	
    	if(lastMove.getVisibility()==(View.VISIBLE))
    	{
    		myButton.setText("Hide");
    	}
    	else
    	{
    		myButton.setText("Show");
    	}
    }
    
   
    public void toggleVisibility(View v)
    {
    	if (v.isShown())
    	{
    		v.setVisibility(View.INVISIBLE);
    	}
    	else
    	{
    		v.setVisibility(View.VISIBLE);
    	}
    }
    
    public void updateLast()
    {
    	clearViews(lastMovePanel);
    }
    
    public Boolean gameWon()
    {
    	for(int i=0; i<numColors; i++)
    	{
    		if(buttonOrder[i] != i)
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    
    
    //open menu panel with my button instead of phones' button
    public void onClickMenuButton(View v)
    {
    	openOptionsMenu();
    }
    
    // Initiating Menu XML file (menu.xml) 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    { 
        MenuInflater menuInflater = getMenuInflater(); 
        menuInflater.inflate(R.menu.menu, menu); 
        return true; 
    } 
    
  
    /** 
     * Event Handling for Individual menu item selected 
     * Identify single menu item by it's id 
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    { 
    	// Single menu item is selected do something 
        // Ex: launching new activity/screen or show alert message 
        switch (item.getItemId()) 
        { 
	        case R.id.menu_new_game:
	        {
	            //Toast.makeText(Roygbiv.this, "New Game is Selected", Toast.LENGTH_SHORT).show(); 
	            setupGame();
	        	return true; 
	        }
	  
	        case R.id.menu_help: 
	        {
	            //Toast.makeText(Roygbiv.this, "menu_help is Selected", Toast.LENGTH_SHORT).show(); 
	            displayHelp();
	            return true; 
	        }
	  
	        case R.id.menu_highscores: 
	        {
	            //Toast.makeText(Roygbiv.this, "menu_highscores is Selected", Toast.LENGTH_SHORT).show(); 
	            displayHighScore();
	            return true; 
	        }
	  
	        case R.id.menu_about: 
	        {
	            //Toast.makeText(Roygbiv.this, "menu_about is Selected", Toast.LENGTH_SHORT).show(); 
	            displayAbout();
	            return true; 
	        }
	  
	        case R.id.menu_exit: 
	        {
	            //Toast.makeText(Roygbiv.this, "menu_exit is Selected", Toast.LENGTH_SHORT).show(); 
	            onExitClick();	            
	        	return true; 
	        }
	  
	        case R.id.menu_cancel: 
	        {
	            //Toast.makeText(Roygbiv.this, "menu_cancel is Selected", Toast.LENGTH_SHORT).show(); 
	            return true; 
	        }
	  
	        default: 
	        {
	        	return super.onOptionsItemSelected(item); 
	        }
        }
        
    }   
    
    
    private void displayAbout()
    {
    	Intent i = new Intent(Roygbiv.this, AboutActivity.class);
    	//display about
    	startActivity(i);
    }
    
    private void displayHelp()
    {
    	Intent i = new Intent(Roygbiv.this, HelpActivity.class);
    	//display help
    	startActivity(i);
    }
    
    private void displayHighScore()
    {
    	
    	//update the hs views
		String[] names = new String[scoresArray.length];
		int[] nums = new int[scoresArray.length];
		Intent changeHS = new Intent(Roygbiv.this, HighScores.class);
		for(int i=0;i<scoresArray.length; i++)
		{
			names[i] = scoresArray[i].getName();
			nums[i] = scoresArray[i].getScore();
		}
		changeHS.putExtra("names", names);
		changeHS.putExtra("nums", nums);
			
			
		//display hall of awesome
		startActivity(changeHS);
    }
    
    private void getHighScore()
    {
    	//initHS();
    	//scoresArray[0].swapHS(scoresArray[1]);
    	//save(HIGHSCORES, HStoString());
    		
    	//String test = "Brandon 10 Kasey 10 Jesus 999  Moses 1";
        parseHighScoreString(load(HIGHSCORES));
        
        
        Log.i("read from hs", load(HIGHSCORES));

        for (int i=0; i<scoresArray.length; i++)
        {
        	Log.i("array name:", scoresArray[i].getName());
        	Log.i("array score:", "" + scoresArray[i].getScore());
       	}
        
        //sort the array
        HS_Entry.sortHSarray(scoresArray);
    	    	
    	
    }
    
    private String HStoString()
    {
    	String inWork = null;
    	for(int i=0; i<scoresArray.length; i++)
    	{	
    		if(i==0)
    		{
    			inWork = scoresArray[i].getName() + "," + scoresArray[i].getScore() + ",";
    		}
    		else
    		{
        		inWork = inWork + scoresArray[i].getName() + "," + scoresArray[i].getScore() + ",";
    		}
    	}
    	return inWork;
    }

    private void parseHighScoreString(String myString)
    {
    	String delims = "[,]+"; // use + to treat consecutive delims as one;
        
    	String[] tokens = myString.split(delims);
    	
    	for (int i=0; i<tokens.length; i++)
    	{
    		Log.i("token found:", tokens[i]);
    	}

    	
    	for(int i=0,j=0; j+1<tokens.length; i++,j+=2)
    	{
    		scoresArray[i] = new HS_Entry();
    		scoresArray[i].setName(tokens[j]);
    		if((j+1) <= tokens.length)
    		{
    			int foo = Integer.parseInt(tokens[j+1]);
    			scoresArray[i].setScore(foo);
    		}
    	}
    	
    	if (tokens.length < 2)
    	{
    		initHS();
        	save(HIGHSCORES, HStoString());
        	Log.i("sent to hs because no tokens read:", HStoString());
        	parseHighScoreString(load(HIGHSCORES));
    	}
    }
    
     private void save(String filename, String data)
    {
        try
        {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        }
        catch (Exception ex)
        {
            Log.i("Error saving file: ", ex.getLocalizedMessage());
        }
    }
            

    private String load(String filename)
    {
        try
        {
            FileInputStream fis = openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line = null, input="";
            while ((line = reader.readLine()) != null)
            {
                input += line;
            }
            reader.close();
            fis.close();
            return input;
        }
        catch (Exception ex)
        {
            Log.i("Error loading file: ", ex.getLocalizedMessage());
            return "";
        }
    }
    
    public static String intToString(int number)
    {
    	String characters = "" + number;
    	return characters;
    }

}
