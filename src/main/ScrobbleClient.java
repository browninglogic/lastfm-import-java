package main;

import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import de.umass.lastfm.scrobble.ScrobbleData;
import de.umass.lastfm.scrobble.ScrobbleResult;

public class ScrobbleClient {
	/*
	 * ScrobbleClient aims to provide a layer of abstraction between the 
	 * lastfm-import-java application and the lastfm-java library
	 */
	
	Session mCurrentSession;
	
	private ScrobbleClient(Session pSession)
	{
		mCurrentSession = pSession;
	}
	
	public static ScrobbleClient connect(String pUsername, String pPassword, String pAPIKey, String pAPISecret)
	{
		Session connectingSession = Authenticator.getMobileSession(pUsername, pPassword, pAPIKey, pAPISecret);
		
		return new ScrobbleClient(connectingSession);
	}
	
	public ScrobbleReturn scrobble(Play pPlay)
	{
		ScrobbleData playData = ScrobbleClient.createScrobbleData(pPlay);
		ScrobbleResult scrobbleResult;
		ScrobbleReturn scrobbleReturn;
		
		//Scrobble the track to Last.fm
		scrobbleResult = Track.scrobble(playData, mCurrentSession);
		
		scrobbleReturn = new ScrobbleReturn(scrobbleResult);
		
		//If we consider this to be a successful scrobble, then mark the Play object accordingly
		if(scrobbleReturn.successfulScrobble())
		{
			pPlay.setScrobbled(true);
		}
		
		return scrobbleReturn;
	}
	
	private static ScrobbleData createScrobbleData(Play pPlay)
	{
		ScrobbleData sd = new ScrobbleData(pPlay.getArtist(), pPlay.getTrackTitle(), pPlay.getTimeStamp());
		String album = pPlay.getAlbum();
		String musicBrainzID = pPlay.getTrackMusicBrainzID();
		
		if(album.isEmpty())
		{
			sd.setAlbum(album);
		}
		
		if(musicBrainzID.isEmpty())
		{
			sd.setMusicBrainzId(musicBrainzID);
		}
		
		return sd;
	}
}