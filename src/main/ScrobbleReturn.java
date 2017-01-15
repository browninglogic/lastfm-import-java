package main;

import de.umass.lastfm.scrobble.ScrobbleResult;

public class ScrobbleReturn {
	//ScrobbleReturn encapsulates the functionality required in relation to ScrobbleResult
	
	private ScrobbleResult mScrobbleResult;
	
	public ScrobbleReturn(ScrobbleResult pScrobbleResult)
	{
		mScrobbleResult = pScrobbleResult;
	}
	
	public boolean successfulScrobble()
	{
		return mScrobbleResult.isSuccessful() && !mScrobbleResult.isIgnored();
	}
	
	public String getProblemReadout()
	{
		String problemInfo = String.format("isSuccessful: %s\r\nisIgnored: %s\r\n",  mScrobbleResult.isSuccessful(), mScrobbleResult.isIgnored());
		
		if(!mScrobbleResult.isSuccessful())
		{
			problemInfo += String.format("Error Code: %s\r\nError Message: %s", mScrobbleResult.getErrorCode(), mScrobbleResult.getErrorMessage());
			
		}
		
		if(mScrobbleResult.isIgnored())
		{
			problemInfo += String.format("Ignored Message Code: %s\r\nIgnore Message: %s", mScrobbleResult.getIgnoredMessageCode(), mScrobbleResult.getIgnoredMessage());
		}
		
		return problemInfo;
	}

}
