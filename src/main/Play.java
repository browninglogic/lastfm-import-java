package main;

public class Play {
	/*
	 * The Play class is an abstraction of a play, as in "this song was played at this time".
	 */

	private int unixTimeStamp;
	private String trackTitle;
	private String artist;
	private String album;
	private String trackMusicBrainsID;
	private String artistMusicBrainsID;
	private String albumMusicBrainsID;
	
	private boolean scrobbled;
	private String fileLine;
	
	private Play(int pTimeStamp, String pTrackTitle, String pArtist, String pAlbum, String pTrackMusicBrainsID, String pArtistMusicBrainsID, String pAlbumMusicBrainsID, String pFileLine)
	{
		unixTimeStamp = pTimeStamp;
		trackTitle = pTrackTitle;
		artist = pArtist;
		album = pAlbum;
		trackMusicBrainsID = pTrackMusicBrainsID;
		artistMusicBrainsID = pArtistMusicBrainsID;
		albumMusicBrainsID = pAlbumMusicBrainsID;
		fileLine = pFileLine;
	}
	
	public static Play createFromFileLine(String pFileLine)
	{
    	//Split the line by tabs
		String[] lineParts = pFileLine.split("\t");
		int unixTimeStamp;
    	String trackTitle;
    	String artist;
    	String album = null;
    	String trackMusicBrainsID = null;
    	String artistMusicBrainsID = null;
    	String albumMusicBrainsID = null;
    	
    	//Assign each split piece accordingly so that we have a readily-readable representation of each piece of info
    	unixTimeStamp = Integer.parseInt(lineParts[0]);
    	trackTitle = lineParts[1];
    	artist = lineParts[2];

    	if(lineParts.length >= 4)
    		album = lineParts[3];
    	if(lineParts.length >= 5)
    		trackMusicBrainsID = lineParts[4];
    	if(lineParts.length >= 6)
    		artistMusicBrainsID = lineParts[5];
    	if(lineParts.length >= 7)
    		albumMusicBrainsID = lineParts[6];
    	
    	return new Play(unixTimeStamp, trackTitle, artist, album, trackMusicBrainsID, artistMusicBrainsID, albumMusicBrainsID, pFileLine);
	}
	
	public String toString()
	{
		return String.format("Timestamp: %s | Title: %s | Artist: %s | Album: %s | MB ID: %s", unixTimeStamp, trackTitle, artist, album, trackMusicBrainsID);
	}
	
	public boolean hasBeenScrobbled()
	{
		return scrobbled;
	}
	
	public String getFileLine()
	{
		return fileLine;
	}
	
	//TODO Write a scrobble method, which will take a session object, put together the necessary data, scrobble it, and set scrobbled to true
}
