package com.OffByOne.roygbiv;

public class HS_Entry {

	private String name;
	private int score;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public HS_Entry(String myName, int num)
	{
		name = myName;
		score = num;
	}
	
	public HS_Entry()
	{
		name = "Anon";
		score = -1;
	}
	
	public void swapHS(HS_Entry otherEntry)
	{
		String tempString = this.getName();
		int tempScore = this.getScore();
		
		this.setName(otherEntry.getName());
		this.setScore(otherEntry.getScore());
		
		otherEntry.setName(tempString);
		otherEntry.setScore(tempScore);
	}
	
	public static int worstHS(HS_Entry[] thisArray)
	{
		int worstScore = 0;
		for(int i=0; i<thisArray.length; i++)
		{
			if(thisArray[i].getScore() > worstScore)
			{
				worstScore = thisArray[i].getScore();
			}
		}
		
		return worstScore;
	}
	
	public static int worstHSposition(HS_Entry[] thisArray)
	{
		int worstScore = 0; 
		int worstScorePosition = 0;
		for(int i=0; i<thisArray.length; i++)
		{
			if(thisArray[i].getScore() > worstScore)
			{
				worstScore = thisArray[i].getScore();
				worstScorePosition = i;
			}
		}
		
		return worstScorePosition;
	}
	
	public static void sortHSarray(HS_Entry[] thisArray)
	{
		for(int i=0; i<thisArray.length; i++)
		{
			for(int j=i+1; j<thisArray.length; j++)
			{
				if(thisArray[i].getScore() > thisArray[j].getScore())
				{
					thisArray[i].swapHS(thisArray[j]);
				}
			}
		}
	}
	
	
}
