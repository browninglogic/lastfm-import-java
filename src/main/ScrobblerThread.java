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

import de.umass.lastfm.scrobble.ScrobbleResult;
import gui.LastFMImportGUI;

public class ScrobblerThread implements Runnable {

	private LastFMImportGUI mMainGUI;
	private File mLastExportFile;
	private String mLastfmUsername;
	private String mLastfmPassword;
	private boolean mScrobblePlays;
	
	public ScrobblerThread(LastFMImportGUI pMainGUI, File pLastExportFile, String pLastfmUsername, String pLastfmPassword, boolean pScrobblePlays)
	{
		mMainGUI = pMainGUI;
		mLastExportFile = pLastExportFile;
		mLastfmUsername = pLastfmUsername;
		mLastfmPassword = pLastfmPassword;
		mScrobblePlays = pScrobblePlays;
	}

	@Override
	public void run() {
		try 
		{
			ScrobbleClient scrobbler = null;
			ScrobbleReturn scrobbleResult;
			PlayCollection plays;
			Play currentPlay;
			int playCount;

			//Populate the PlayCollection with the play information located in the user-specified file
			plays = PlayCollection.readPlaysFromFile(mLastExportFile);
			playCount = plays.getPlayCount();
			
			//Connect to Last.fm if told to scrobble plays
			//TODO Implement functionality to package API key with JAR, but load from config when running from source
			if(mScrobblePlays)
			{
				scrobbler = ScrobbleClient.connect(mLastfmUsername, mLastfmPassword, "eeaa367912b60dc6f6d6481c01718448", "30b5c86f50209767daef7e9cfab50d2d");
			}
			
			//for(int i = playCount - 1; i > playCount - 100; i--)
			for(int i = 0; i < playCount - 1; i++)
			//int i = playCount - 20;
			{
				currentPlay = plays.getPlay(i);
				
				mMainGUI.appendLine(String.format("%s play %s of %s", mScrobblePlays ? "Scrobbling" : "Displaying", i + 1, playCount));
				mMainGUI.appendLine(currentPlay.toString());
				
				//Only scrobble if we were told to do so
				if(mScrobblePlays)
				{
					scrobbleResult = scrobbler.scrobble(currentPlay);
					
					if(!scrobbleResult.successfulScrobble())
					{
						//In the case of failure, tell the user what we know about what happened before halting
						mMainGUI.appendLine(String.format("Unsuccessful scrobble.  Halting\r\n%s", scrobbleResult.getProblemReadout()));
						break;
					}
				}
			}
			
			Thread.yield();
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
