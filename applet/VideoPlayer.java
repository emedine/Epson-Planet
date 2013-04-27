
/**
 * GSVideo movie reverse example.
 *
 * The GSMovie.speed() method allows to 
 * change the playback speed. Use negative
 * values for backwards playback. Note that
 * not all video formats support backwards
 * playback. This depends on the underlying
 * gstreamer plugins used by gsvideo. For
 * example, the theora codec does support 
 * backward playback, but not so the H264 
 * codec, at least in its current version.
 * 
 */

import codeanticode.gsvideo.*;
import processing.core.*;

public class VideoPlayer{
	// pApp data
	DataProfile theDataProfile;
	PApplet pApp;

	GSMovie myMovie;

	
	public VideoPlayer() {
		theDataProfile = theDataProfile.getInstance();
		pApp = theDataProfile.pApp;
	 
	  // VideoTester/lib/natives/macosx32
	 
		GSVideo.localGStreamerPath = "/Users/gst/libraries/gstreamer/macosx";
		  // GSVideo.localGStreamerPath = "VideoTester/lib/natives/macosx32";
		  myMovie = new GSMovie(pApp, "../video/EpsonVignettes1.mov");
	}

	
	public void drawVideo() {
	  if (myMovie.ready()) {

	     

	  }
	  pApp.image(myMovie, 320, 240, 320, 240); 
	}  
	///*
	
	public void startVideo(){
		myMovie.play();
		pApp.println("PLAY");
	}
	public void stopVideo(){
		myMovie.stop();
		pApp.println("PStopL");
		
	}
	public void keyPressed() {
		if (pApp.key == 'p') {
			myMovie.play();
			pApp.println("PLAY");
		}
		if (pApp.key == 's') {
			myMovie.stop();
			pApp.println("PLAY");
		}
		if (pApp.key == 'l') {
			
		}
		
	}
	//*/
}