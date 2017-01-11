package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import gui.LastFMImportGUI;

public class LastExportScrobbler implements Runnable {

	private LastFMImportGUI mainGUI;
	private File lastExportFile;
	private String lastfmUsername;
	private String lastfmPassword;
	
	public LastExportScrobbler(LastFMImportGUI pMainGUI, File pLastExportFile, String pLastfmUsername, String pLastfmPassword)
	{
		mainGUI = pMainGUI;
		lastExportFile = pLastExportFile;
		lastfmUsername = pLastfmUsername;
		lastfmPassword = pLastfmPassword;
	}

	@Override
	public void run() {
		this.ScrobblePlays();
	}
	
	private void ScrobblePlays()
	{
		BufferedReader br;
		int lineNumber = 0;
		int totalLines;
		String line;
		String[] lineParts;
		String unixTimeStamp;
		String track;
		String artist;
		String album;
		String trackMusicBrainsID;
		String artistMusicBrainsID;
		String albumMusicBrainsID;
		
		try 
		{
			br = new BufferedReader(new FileReader(lastExportFile));
			totalLines = countLines(lastExportFile);
			
		    while ((line = br.readLine()) != null)
		    {
		    	lineNumber++;
		    	
		    	//Clear these at the beginning of each iteration in case there's no value provided by the file for this line
		    	unixTimeStamp = "";
		    	track = "";
		    	artist = "";
		    	album = "";
		    	trackMusicBrainsID = "";
		    	artistMusicBrainsID = "";
		    	albumMusicBrainsID = "";
		    	
		    	//Split the line by tabs
		    	lineParts = line.split("\t");
		    	
		    	//Assign each split piece accordingly so that we have a readily-readable representation of each piece of info
		    	unixTimeStamp = lineParts[0];
		    	track = lineParts[1];
		    	artist = lineParts[2];

		    	if(lineParts.length >= 4)
		    		album = lineParts[3];
		    	if(lineParts.length >= 5)
		    		trackMusicBrainsID = lineParts[4];
		    	if(lineParts.length >= 6)
		    		artistMusicBrainsID = lineParts[5];
		    	if(lineParts.length >= 7)
		    		albumMusicBrainsID = lineParts[6];
		    	
		    	//Print the line number
		    	mainGUI.appendLine(String.format("Scrobbling %s of %s...", lineNumber, totalLines));
		    	mainGUI.appendLine(String.format("Timestamp: %s | Track: %s | Artist: %s | Album: %s", unixTimeStamp, track, artist, album));
		    	mainGUI.appendLine("");
		    	
		    	//Give the GUI thread time to do stuff
		    	Thread.yield();
		    }
		}
		catch (Exception ex)
		{
			//TODO Implement proper error handling
			ex.printStackTrace();
		}
	}
	
	//Count the number of lines in a provided text file
	private static int countLines(File countFile) throws IOException
	{
		FileReader fr = new FileReader(countFile);
		LineNumberReader lnr = new LineNumberReader(fr);

		lnr.skip(Long.MAX_VALUE);
		return lnr.getLineNumber();
	}

}
