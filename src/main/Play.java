package main;

import java.util.Date;

public class Play {
	/*
	 * The Play class is an abstraction of a play, as in "this song was played at this time".
	 */

	//Unix timestamp
	private int mTimeStamp;
	private String mTrackTitle;
	private String mArtist;
	private String mAlbum;
	private String mTrackMusicBrainzID;
	private String mArtistMusicBrainzID;
	private String mAlbumMusicBrainzID;
	
	private boolean mScrobbled = false;
	
	private Play(int pTimeStamp, String pTrackTitle, String pArtist, String pAlbum, String pTrackMusicBrainzID, String pArtistMusicBrainzID, String pAlbumMusicBrainzID)
	{
		mTimeStamp = pTimeStamp;
		mTrackTitle = pTrackTitle;
		mArtist = pArtist;
		mAlbum = pAlbum;
		mTrackMusicBrainzID = pTrackMusicBrainzID;
		mArtistMusicBrainzID = pArtistMusicBrainzID;
		mAlbumMusicBrainzID = pAlbumMusicBrainzID;
	}
	
	public static Play createFromFileLine(String pFileLine)
	{
    	//Split the line by tabs
		String[] lineParts = pFileLine.split("\t");
		int unixTimeStamp;
    	String trackTitle;
    	String artist;
    	String album = null;
    	String trackMusicBrainzID = null;
    	String artistMusicBrainzID = null;
    	String albumMusicBrainzID = null;
    	
    	//Assign each split piece accordingly so that we have a readily-readable representation of each piece of info
    	unixTimeStamp = Integer.parseInt(lineParts[0]);
    	trackTitle = lineParts[1];
    	artist = lineParts[2];

    	//The following parts are optional
    	if(lineParts.length >= 4)
    		album = lineParts[3];
    	if(lineParts.length >= 5)
    		trackMusicBrainzID = lineParts[4];
    	if(lineParts.length >= 6)
    		artistMusicBrainzID = lineParts[5];
    	if(lineParts.length >= 7)
    		albumMusicBrainzID = lineParts[6];
    	
    	return new Play(unixTimeStamp, trackTitle, artist, album, trackMusicBrainzID, artistMusicBrainzID, albumMusicBrainzID);
	}

	
	public String createFileLine()
	{
		return String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s", this.getTimeStamp(), this.getTrackTitle(), this.getArtist(), this.getAlbum(), this.getTrackMusicBrainzID(), this.getArtistMusicBrainzID(), this.getAlbumMusicBrainzID());
	}
	
	public String toString()
	{
		return String.format("Timestamp: %s | Title: %s | Artist: %s | Album: %s | MB ID: %s | Timestamp as Date: %s", this.getTimeStamp(), this.getTrackTitle(), this.getArtist(), this.getAlbum(), this.getTrackMusicBrainzID(), this.getTimeStampAsDate());
	}
	
	public boolean getScrobbled()
	{
		return mScrobbled;
	}
	
	public void setScrobbled(boolean pScrobbled)
	{
		mScrobbled = pScrobbled;
	}
	
	public int getTimeStamp()
	{
		return this.mTimeStamp;
	}
	
	public Date getTimeStampAsDate()
	{
		return new Date((long)this.getTimeStamp()*1000);
	}
	
	public String getArtist()
	{
		return this.mArtist;
	}
	
	public String getTrackTitle()
	{
		return this.mTrackTitle;
	}
	
	public String getAlbum()
	{
		return this.mAlbum != null ? this.mAlbum : "";
	}
	
	public String getTrackMusicBrainzID()
	{
		return this.mTrackMusicBrainzID != null ? this.mTrackMusicBrainzID : "";
	}
	
	public String getArtistMusicBrainzID()
	{
		return this.mArtistMusicBrainzID != null ? this.mArtistMusicBrainzID : "";
	}
	
	public String getAlbumMusicBrainzID()
	{
		return this.mAlbumMusicBrainzID != null ? this.mAlbumMusicBrainzID : "";
	}
}
