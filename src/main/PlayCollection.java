package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PlayCollection {
	/*
	 * This class is an abstraction of a list of plays to scrobble.
	 */
	
	private Play[] plays;
	
	private PlayCollection(Play[] pPlays)
	{
		plays = pPlays;
	}
	
	//Factory method which constructs a PlayCollection from the provided file
	public static PlayCollection readPlaysFromFile(File pPlayFile) throws IOException
	{
		//Use an ArrayList for initial population, because we don't know yet how many Plays to expect in the file
		ArrayList<Play> readingPlays = new ArrayList<Play>();
		BufferedReader br = new BufferedReader(new FileReader(pPlayFile));
		String currentLine;
		
	    while ((currentLine = br.readLine()) != null)
	    {
	    	readingPlays.add(Play.createFromFileLine(currentLine));
	    }
	    
	    //Convert the ArrayList to an array for efficiency, and return a new instance of PlayCollection
	    return new PlayCollection(readingPlays.toArray(new Play[readingPlays.size()]));
	}
	
	//Writes all of the Plays which have not yet been scrobbled to a file, which can then be read to try scrobbling again.
	public File writeRemainingPlays(String pPath) throws FileNotFoundException
	{
		File outFile = new File(pPath);
		PrintWriter writer = new PrintWriter(outFile);
		
		for (Play loopPlay : plays)
		{
			if(!loopPlay.getScrobbled())
			{
				writer.println(loopPlay.createFileLine());
			}
		}
		
		writer.close();
		
		return outFile;
	}
	
	public int getPlayCount()
	{
		return plays.length;
	}
	
	public Play getPlay(int index)
	{
		return plays[index];
	}
}
