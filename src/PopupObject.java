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
	String theVideoPath = "";
	int videoCounter = 0;
	int vidWidth = 960;
	int vidHeight = 540;
	int vidX = (1024 - vidWidth)/2;
	int vidY = (768 - vidHeight)/2;
	
	
	// text data
	int curDataX = 0;
	int curDataY = 100;
	int curDataBoxW = 380;
	int curDataBoxH = 400;
	// int textPosX = 400;
	int textPosY = 100;
	int curDataMargin = 10;
	// 
	String theHeader = "";
	String theName = "";
	String theText = "";
	
	PFont HeaderFont; /// normal fonts
	PFont NameFont;// use true/false for smooth/no-smooth for Control fonts
	PFont BodyFont;
	
	//// bgrounds
	PImage bgImage;
	String bgPathImagePath = "../data/readout_background_top.png";
	int bgImgW = 390;
	int bgImgH = 242;

	public PopupObject() {
		theDataProfile = theDataProfile.getInstance();
		pApp = theDataProfile.pApp;
		
		HeaderFont = pApp.createFont("Neutra Display",22, true); /// normal fonts
		NameFont = pApp.createFont("Helvetica", 18, true); // use true/false for smooth/no-smooth for Control fonts
		BodyFont = pApp.createFont("Helvetica-Oblique", 16, true); /// normal fonts
		
		
		/*
		HeaderFont = pApp.createFont("Arial Black",18, true); /// normal fonts
		BodyFont = pApp.createFont("Arial Italic", 14, true); /// normal fonts
		NameFont = pApp.createFont("Arial",14, true); // use true/false for smooth/no-smooth for Control fonts
		*/
		
		
		
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
		
		/* 
	    String tBody;
	    tBody = theName + "\n" + "\n" + theText;
	    */
		
	    pApp.fill(0);
	    // pApp.rect(curDataX, curDataY, curDataBoxW, curDataBoxH);
	    // pApp.fill(255);
	    pApp.textFont(HeaderFont);
	    pApp.text(theHeader, curDataX + curDataMargin, curDataY + textPosY +curDataMargin, curDataBoxW - curDataMargin, curDataBoxH);
	    
	    pApp.textFont(NameFont);
	    pApp.text(theName, curDataX + curDataMargin, curDataY + textPosY +curDataMargin * 3.5f, curDataBoxW - curDataMargin, curDataBoxH);
	    
	    //*
	    pApp.textFont(BodyFont);
	    pApp.text(theText, curDataX + curDataMargin, curDataY + textPosY + curDataMargin * 8, curDataBoxW - curDataMargin, curDataBoxH);
		//*/
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
		
		  // pApp.image(curMovie, curDataX + vidWidth/2 + curDataMargin, curDataY + vidHeight/2 + curDataMargin, 415, 233);;

		  pApp.lights();
		  pApp.stroke(255);
		  pApp.fill(0);
		  pApp.rect(0, 0, 1024, 768);
		  pApp.image(curMovie, vidX + vidWidth/2, vidY + vidHeight/2, vidWidth, vidHeight);	  
	  
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
