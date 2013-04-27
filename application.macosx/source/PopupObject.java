import java.util.ArrayList;


import codeanticode.gsvideo.*;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.xml.*;


public class PopupObject {
	// pApp data
	DataProfile theDataProfile;
	PApplet pApp;
	
	// video data
	ArrayList<String> videoPath =  new ArrayList(); /// have to do this since cant' switch on string
	ArrayList<GSMovie> theMoviesArray =  new ArrayList(); /// have to do this since cant' switch on string
	
	GSMovie curMovie;

	boolean isVideoPlaying = false;
	boolean isVisible = false;
	String theVideoPath = "";
	int videoCounter = 0;
	int vidWidth = 1024;
	int vidHeight = 768;
	int vidX;
	int vidY;
	
	
	// text data
	int curDataX = 500;
	int curDataY = 50;
	int curDataBoxW = 434;
	int curDataBoxH = 400;
	// int textPosX = 400;
	int textPosY = 100;
	int curDataMargin = 10;
	// 
	String theName = "";
	String theText = "";
//// showing data
	String headerData = "";
	String curData = "";
			
	PFont HeaderFont; /// normal fonts
	PFont BodyFont;
	PFont pfont;// use true/false for smooth/no-smooth for Control fonts
	
	//// bgrounds
	PImage bgImage;
	String bgPathImagePath = "../data/readout_background_top.png";
	int bgImgW = 434;
	int bgImgH = 242;

	public PopupObject() {
		theDataProfile = theDataProfile.getInstance();
		pApp = theDataProfile.pApp;
		
		HeaderFont = pApp.createFont("Arial Black",18, true); /// normal fonts
		BodyFont = pApp.createFont("Arial",14, true); /// normal fonts
		pfont = pApp.createFont("Arial",10, true); // use true/false for smooth/no-smooth for Control fonts
		
		
		
		bgImage = pApp.loadImage(bgPathImagePath);
		/// initialize all video paths
		/// initVideo();
	}
	
	public void drawPopup(int theID){
		
		pApp.lights();
		doTextReadout(theID);
		// drawVideo();
	
	}



//////////////////////////////
	//// TEXT DATA ////////////
	
	public void doTextReadout(int theID){

		pApp.image(bgImage, curDataX + bgImgW/2, curDataY + bgImgH/2);
		
		headerData = theName;
	    curData = "\n" + "\n" + theText;

	    
	   
	    pApp.fill(0);
	    // pApp.rect(curDataX, curDataY, curDataBoxW, curDataBoxH);
	    // pApp.fill(255);
	    pApp.textFont(HeaderFont);
	    // 			(str, x1, y1, x2, y2)
	    // pApp.text(headerData, curDataX + curDataMargin, curDataY + curDataMargin, curDataBoxW - curDataMargin, curDataBoxH);
	    pApp.text(headerData, curDataX + curDataMargin, curDataY + textPosY +curDataMargin, curDataBoxW - curDataMargin, curDataBoxH);
	    pApp.textFont(BodyFont);
	    // 			(str, x1, y1, x2, y2)
	    // pApp.text(curData, curDataX + curDataMargin, curDataY + curDataMargin, curDataBoxW - curDataMargin, curDataBoxH);
	    pApp.text(curData, curDataX + curDataMargin, curDataY + textPosY + curDataMargin, curDataBoxW - curDataMargin, curDataBoxH);
	}
	

/////// VIDEO DATA /////////////

	
	public void initVideo(){
		
		GSVideo.localGStreamerPath = "/Users/gst/libraries/gstreamer/macosx";
		for (int i=0; i<videoPath.size(); i++){
			GSMovie tMov = new GSMovie(pApp, videoPath.get(i));
			theMoviesArray.add(tMov);
			
		}
		
		curMovie = theMoviesArray.get(0); /// myMovie0;
	}	
	
	
	public void drawVideo() {

		if (!isVideoPlaying) {

			stopVideo();
			
			} else {
 
				  
		  }
		
		  // pApp.image(curMovie, curDataX + vidWidth/2 + curDataMargin, curDataY + vidHeight/2 + curDataMargin, 415, 233);
		  pApp.lights();
		  pApp.image(curMovie, vidX + vidWidth/2 + curDataMargin, vidY + vidHeight/2 + curDataMargin, vidWidth, vidHeight);
		  
	  
	} 
	
	public void startVideo(){
		
		try{
			// pApp.println("START VIDEO" + isVideoPlaying);
			isVideoPlaying = true;
			curMovie.play();
		
		} catch (Exception e){
			pApp.println("Can't stop video: " + e);
			
		}
	}
	
	public void stopVideo(){
		
		try{
			// pApp.println("Stop VIDEO" + isVideoPlaying);
			isVideoPlaying = false;
			curMovie.stop();
		} catch (Exception e){
			pApp.println("Can't stop video: " + e);
			
		}
	}
	
	
	public void switchCurVideo(int videoID){
		// videoCounter = tCounter;
		int vID = videoID;
		curMovie = theMoviesArray.get(vID);

		
	}
	
	

	// end video
	////////////////
	
	
//// end class /////////
}
