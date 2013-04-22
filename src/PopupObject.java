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
	GSMovie myMovie0;
	GSMovie myMovie1;
	GSMovie myMovie2;
	GSMovie myMovie3;
	GSMovie myMovie4;
	GSMovie myMovie5;
	GSMovie myMovie6;
	GSMovie myMovie7;
	GSMovie myMovie8;
	GSMovie myMovie9;
	GSMovie myMovie10;
	GSMovie myMovie11;
	
	GSMovie curMovie;

	boolean isVideoPlaying = false;
	boolean isVisible = false;
	String theVideoPath = "";
	int videoCounter = 0;
	int vidWidth = 415;
	int vidHeight = 233;
	
	
	// text data
	int curDataX = 500;
	int curDataY = 100;
	int curDataBoxW = 446;
	int curDataBoxH = 400;
	// int textPosX = 400;
	int textPosY = 233;
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
	String bgPathImagePath = "../data/popup_bground.png";

	public PopupObject() {
		theDataProfile = theDataProfile.getInstance();
		pApp = theDataProfile.pApp;
		
		HeaderFont = pApp.createFont("Arial Black",14, true); /// normal fonts
		BodyFont = pApp.createFont("Arial",12, true); /// normal fonts
		pfont = pApp.createFont("Arial",10, true); // use true/false for smooth/no-smooth for Control fonts
		
		
		
		// bgImage = pApp.loadImage(bgPathImagePath);
		/// initialize all video paths
		initVideo();
	}
	
	public void drawPopup(int theID){
		
		// pApp.lights();
		doTextReadout(theID);
		drawVideo();
	
	}



//////////////////////////////
	//// TEXT DATA ////////////
	
	public void doTextReadout(int theID){

		/// pApp.image(bgImage, 223, 315);
		
		headerData = theName;
	    curData = "\n" + theText;
	    // text(curDataHeader, curDataX + (showingDataMarginX *10), curDataY + showingDataMarginY);
	    // textSize(12);
	    pApp.fill(0);
	    pApp.rect(curDataX, curDataY, curDataBoxW, curDataBoxH);
	    pApp.fill(255);
	    pApp.textFont(HeaderFont);
	    pApp.text(headerData, curDataX + curDataMargin, curDataY + textPosY + curDataMargin, curDataBoxW - curDataMargin, curDataBoxH);
	    pApp.textFont(BodyFont);
	    pApp.text(curData, curDataX + curDataMargin, curDataY + textPosY + curDataMargin, curDataBoxW - curDataMargin, curDataBoxH);
		
	}
	
	
	
	
	
/////// VIDEO DATA /////////////

	
	public void initVideo(){
		GSVideo.localGStreamerPath = "/Users/gst/libraries/gstreamer/macosx";
		
		myMovie0 = new GSMovie(pApp, "../video/EpsonVignettes3.mov");
		myMovie1 = new GSMovie(pApp, "../video/EpsonVignettes1.mov");
		myMovie2 = new GSMovie(pApp, "../video/EpsonVignettes2.mov");
		myMovie3 = new GSMovie(pApp, "../video/EpsonVignettes3.mov");
		myMovie4 = new GSMovie(pApp, "../video/EpsonVignettes1.mov");
		myMovie5 = new GSMovie(pApp, "../video/EpsonVignettes2.mov");	
		myMovie6 = new GSMovie(pApp, "../video/EpsonVignettes3.mov");
		myMovie7 = new GSMovie(pApp, "../video/EpsonVignettes1.mov");
		myMovie8 = new GSMovie(pApp, "../video/EpsonVignettes2.mov");
		myMovie9 = new GSMovie(pApp, "../video/EpsonVignettes3.mov");	
		myMovie10 = new GSMovie(pApp, "../video/EpsonVignettes1.mov");
		myMovie11 = new GSMovie(pApp, "../video/EpsonVignettes3.mov");
		//*/	
		curMovie = myMovie0;
	}	
	
	
	public void drawVideo() {

		  
		  if (isVideoPlaying == true) {
		   
		      // Setting the speed should be done only once,
		      // this is the reason for the if statement.
		     
			  // myMovie0.goToEnd();
		      // -1 means backward playback at normal speed.
			  // myMovie0.speed(-1.0f);
		      // Setting to play again, since the movie stop
		      // playback once it reached the end.
			 
		  } else {
			  stopVideo();
		  }
		  pApp.image(curMovie, curDataX + vidWidth/2 + curDataMargin, curDataY + vidHeight/2 + curDataMargin, 415, 233);

		  
		} 
	
	public void startVideo(){
		try{

		// pApp.println("START VIDEO" + isVideoPlaying);
		
		curMovie.play();
		} catch (Exception e){
			pApp.println("Can't stop video: " + e);
			
		}
	}
	
	public void stopVideo(){
		try{

		// pApp.println("Stop VIDEO" + isVideoPlaying);

		curMovie.stop();
		} catch (Exception e){
			pApp.println("Can't stop video: " + e);
			
		}
	}
	
	
	public void switchCurVideo(int videoID){
		// videoCounter = tCounter;
		int vID = videoID;
		///*
		switch(vID) {
		
		  case 0: 
		    pApp.println("myMovie0");
			
		    curMovie = myMovie0;
		    break;
		    
		  case 1:
			  pApp.println("myMovie1"); 
			
			  curMovie = myMovie1;
		    break;
		    
		  case 2:
			  pApp.println("myMovie2"); 
			 
			  curMovie = myMovie2;
		    break;	    
		    
		  case 3:
			  pApp.println("myMovie3"); 
			 
			  curMovie = myMovie3;
		    break;	    
		    
		  case 4:
			  pApp.println("myMovie4"); 
			 
			  curMovie = myMovie4;
		    break;	    
		  case 5:
				
			  curMovie = myMovie5;
		    break;
		    
		  case 6:
			  pApp.println("myMovie6"); 
			 
			  curMovie = myMovie6;
		    break;	    
		    
		    
		  case 7:
			  pApp.println("myMovie7"); 
			 
			  curMovie = myMovie7;
		    break;	    
		    
		  case 8:
			  pApp.println("myMovie8"); 
			  
			  curMovie = myMovie8;
		    break;    
		    
		  case 9:
			  pApp.println("myMovie9"); 
			  
			  curMovie = myMovie9;
		    break;   	    
		    
		    
		  default:
			  pApp.println("Zulu"); 
		    break;
		   
		}
		
		
		
	}
	
	

	// end video
	////////////////
	
	
//// end class /////////
}
