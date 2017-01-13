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

public class ScrobblerThread implements Runnable {

	private LastFMImportGUI mainGUI;
	private File lastExportFile;
	private String lastfmUsername;
	private String lastfmPassword;
	
	public ScrobblerThread(LastFMImportGUI pMainGUI, File pLastExportFile, String pLastfmUsername, String pLastfmPassword)
	{
		mainGUI = pMainGUI;
		lastExportFile = pLastExportFile;
		lastfmUsername = pLastfmUsername;
		lastfmPassword = pLastfmPassword;
	}

	@Override
	public void run() {
		try 
		{
			PlayCollection plays = PlayCollection.readPlaysFromFile(lastExportFile);
			int playCount = plays.getPlayCount();
			
			for(int i = 0; i <= playCount; i++)
			{
				mainGUI.appendLine(String.format("Play %s of %s", i, playCount));
				mainGUI.appendLine(plays.getPlay(i).toString());
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
