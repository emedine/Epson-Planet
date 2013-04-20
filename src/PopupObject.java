import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.video.*;
import processing.xml.*;


public class PopupObject {
	// pApp data
	DataProfile theDataProfile;
	PApplet pApp;
	
	// video data
	Movie myMovie0;
	Movie myMovie1;
	Movie myMovie2;
	Movie myMovie3;
	Movie myMovie4;
	Movie myMovie5;
	Movie myMovie6;
	Movie myMovie7;
	Movie myMovie8;
	Movie myMovie9;
	Movie myMovie10;
	Movie myMovie11;
	Movie curMovie;

	boolean isVideoPlaying = false;
	String theVideoPath = "";
	int videoCounter = 0;
	ArrayList<String> videoPath =  new ArrayList(); /// have to do this since cant' switch on string

	
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
		myMovie0 = new Movie(pApp, videoPath.get(0));
		myMovie1 = new Movie(pApp, videoPath.get(1));
		myMovie2 = new Movie(pApp, videoPath.get(2));	
		myMovie3 = new Movie(pApp, videoPath.get(3));
		myMovie4 = new Movie(pApp, videoPath.get(4));
		myMovie5 = new Movie(pApp, videoPath.get(5));		
		myMovie6 = new Movie(pApp, videoPath.get(6));
		myMovie7 = new Movie(pApp, videoPath.get(7));
		myMovie8 = new Movie(pApp, videoPath.get(8));
		/*
		myMovie9 = new Movie(pApp, videoPath.get(9));
		
		myMovie10 = new Movie(pApp, videoPath.get(10));	
		myMovie11 = new Movie(pApp, videoPath.get(11));	
		*/		
	}	
	public void switchCurVideo(int videoID){
		// videoCounter = tCounter;
		
		int vID = videoID;
		try{
		// curMovie.pause();
		} catch (Exception e){
		pApp.println("Can't stop current movie: " + e);	
		}

		switch(vID ) {
		
		  case 0: 
		    pApp.println("myMovie0");
			myMovie0 = new Movie(pApp, videoPath.get(0));
		    curMovie = myMovie0;
		    break;
		    
		  case 1:
			  pApp.println("myMovie1"); 
			  myMovie1 = new Movie(pApp, videoPath.get(1));
			  curMovie = myMovie1;
		    break;
		    
		  case 2:
			  pApp.println("myMovie2"); 
			  myMovie2 = new Movie(pApp, videoPath.get(2));	
			  curMovie = myMovie2;
		    break;	    
		    
		  case 3:
			  pApp.println("myMovie3"); 
			  myMovie3 = new Movie(pApp, videoPath.get(3));
			  curMovie = myMovie3;
		    break;	    
		    
		  case 4:
			  pApp.println("myMovie4"); 
			  myMovie4 = new Movie(pApp, videoPath.get(4));
			  curMovie = myMovie4;
		    break;	    
		  case 5:
				myMovie5 = new Movie(pApp, videoPath.get(5));
			  curMovie = myMovie5;
		    break;
		    
		  case 6:
			  pApp.println("myMovie6"); 
			  myMovie6 = new Movie(pApp, videoPath.get(6));
			  curMovie = myMovie6;
		    break;	    
		    
		    
		  case 7:
			  pApp.println("myMovie7"); 
			  myMovie7 = new Movie(pApp, videoPath.get(7));
			  curMovie = myMovie7;
		    break;	    
		    
		  case 8:
			  pApp.println("myMovie8"); 
			  myMovie8 = new Movie(pApp, videoPath.get(8));
			  curMovie = myMovie8;
		    break;    
		    
		  case 9:
			  pApp.println("myMovie9"); 
			  myMovie8 = new Movie(pApp, videoPath.get(8));
			  curMovie = myMovie9;
		    break;   	    
		    
		    
		  default:
			  pApp.println("Zulu"); 
		    break;
		}
		

		try{

			// myMovie = new Movie(pApp, videoPath.get(videoCounter));
			// pApp.println("videoCounter: " + videoCounter + " " + videoPath.get(videoCounter));
			// curMovie.play();  //plays the movie once
			// curMovie.loop();  //plays the movie over and over
			 
			 
		} catch (Exception e){
			pApp.println("can't init video: " + e);
		}
		
	}
	
	public void stopVideo(){
		try{
			
		/// stop all videos
		pApp.println("Stop VIDEO");

		myMovie1.stop();
		} catch (Exception e){
			pApp.println("Can't stop video: " + e);
			
		}
	}
	
	public void playVideo(){
		if(isVideoPlaying == true){
			try{
				//// use switch statement to play correct movie
				
				curMovie.play();
				// end switch
				pApp.image(curMovie, curDataX + curDataMargin, curDataY + curDataMargin); 
			} catch (Exception e){
				pApp.println("Video error: " + e);
			}
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
