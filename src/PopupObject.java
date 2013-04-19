import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import processing.video.*;
import processing.xml.*;


public class PopupObject {
	// pApp data
	DataProfile theDataProfile;
	PApplet pApp;
	
	// video data
	Movie myMovie;
	boolean isVideoPlaying = false;
	String theVideoPath = "";
	int videoCounter = 0;

	
	// text data
	int curDataX = 100;
	int curDataY = 100;
	int curDataBoxW = 200;
	int curDataBoxH = 200;
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

	public PopupObject() {
		theDataProfile = theDataProfile.getInstance();
		pApp = theDataProfile.pApp;
		
		HeaderFont = pApp.createFont("Arial Black",14, true); /// normal fonts
		BodyFont = pApp.createFont("Arial",12, true); /// normal fonts
		pfont = pApp.createFont("Arial",10, true); // use true/false for smooth/no-smooth for Control fonts

	}



//////////////////////////////
	//// TEXT DATA ////////////
	
	public void doTextReadout(int theID){
		
		headerData = theName;
	    curData = "\n" + theText;
	    // text(curDataHeader, curDataX + (showingDataMarginX *10), curDataY + showingDataMarginY);
	    // textSize(12);
	    pApp.fill(0);
	    pApp.rect(curDataX, curDataY, curDataBoxW, curDataBoxH);
	    pApp.fill(255);
	    pApp.textFont(HeaderFont);
	    pApp.text(headerData, curDataX +curDataMargin, curDataY + curDataMargin, curDataBoxW - curDataMargin, curDataBoxH);
	    pApp.textFont(BodyFont);
	    pApp.text(curData, curDataX +curDataMargin, curDataY + curDataMargin, curDataBoxW - curDataMargin, curDataBoxH);
		
	}
	
	
	
	
	
/////// VIDEO DATA /////////////
	
	public void initVideo(int tCounter){
		videoCounter = tCounter;
		try{

			// myMovie = new Movie(pApp, videoPathList.get(videoCounter));
			// pApp.println("videoCounter: " + videoCounter + " " + videoPathList.get(videoCounter));
			myMovie.play();  //plays the movie once
			// myMovie.loop();  //plays the movie over and over
			 
			 
		} catch (Exception e){
			pApp.println("can't init video: " + e);
		}
		
	}
	
	public void playVideo(){
		if(isVideoPlaying == true){
			try{
				//// use switch statement to play correct movie
				
				
				// end switch
				pApp.image(myMovie, 300, 200); 
			} catch (Exception e){
				pApp.println("Video error: " + e);
			}
		}
		
	}

	public void stopVideo(){
		try{
			
		/// stop all videos
			
		myMovie.stop();
		} catch (Exception e){
			pApp.println("Can't stop video: " + e);
			
		}
	}


	// Called every time a new frame is available to read
	// /*
	public void movieEvent(Movie m) {
		// pApp.println("Reading");
	    m.read();
	}
	
	// end video
	////////////////
	
	
//// end class /////////
}
