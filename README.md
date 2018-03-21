## Introduction
This is a quick-and-dirty application which intended to scrobble the output of lastexport.py directly to last.fm using the lastfm-java library (available on Github) for teh purposes of migrating scrobbles from an old account to a new account.  I abandoned this project when I discovered a server-side limitation on historical last.fm scrobbling which severely impairs the usefulness of such a project.

## Last-fm Limitations
Last.fm will not allow scrobbling of any play which is older than two weeks.  This is a hard server-side limitation which seems to be well-known and isn't going away anytime soon.

## Status
I'm considering revisiting this project in the future in order to add functionality to replace timestamps of all plays over two weeks old with a date of 'current date - 14 days' in order to allow scrobbles of all historical plays in an old last.fm account, albeit with obviously inaccurate timestamps.
