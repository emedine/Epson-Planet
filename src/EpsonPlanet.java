
/* 
 * GPL License Copyright (c) 2011 Karsten Schmidt
 * 
 * This demo & library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * http://creativecommons.org/licenses/LGPL/2.1/
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/*
 * EPSON PLANET UPDATE
 * Copyright (c) 2013 TenTonRaygun
 * */

// import TextSpawn;
// import UserProfile;

import java.util.ArrayList;


/// xml
import javax.xml.bind.*;

import codeanticode.gsvideo.GSMovie;

/// osc stuff
import oscP5.*;

/// processing core libraries
import processing.core.*;
import processing.xml.XMLElement;

/// toxiclib for 3D
import toxi.geom.Vec3D;
import toxi.geom.mesh.Mesh3D;
import toxi.geom.mesh.SphereFunction;
import toxi.geom.mesh.SurfaceMeshBuilder;
import toxi.processing.ToxiclibsSupport;


/*
 * <ul>
 * <li>Move mouse to rotate view</li>
 * <li>Press '-' / '=' to adjust zoom</li>
 * </ul>
 * </p>
 */
@SuppressWarnings("serial")
public class EpsonPlanet extends PApplet {

	
	
	////// FULL SCREEN HANDLER
	/**
	 * Main entry point to run as application
	 */
	///*
	public static void main(String[] args) {
		PApplet.main(new String[] { "--present", "EpsonPlanet" });

	}
	
	
	
	int screenWidth = 1024;
	int screenHeight = 768;

	//Radius of our globe
	private static final int EARTH_RADIUS = 300;

	/// Image size in pixels for rendering
	private static final int IMG_SIZE = 32;
	/// Earth texture image
	private PImage earthTex;

	//Globe mesh
	private Mesh3D globe;

	//Toxiclibs helper class for rendering
	private ToxiclibsSupport gfx;

	//Camera rotation vector
	private final Vec3D camRot = new Vec3D();
	
	//Destroyer rotation vector
	private final Vec3D desRot = new Vec3D();
	// is moving
	private boolean isMoving = false;
	
	/// is keyboard enabled
	private boolean useKeyboard = false;
	//Zoom factors
	private float currZoom = 1;
	private float targetZoom = 1;

	//Render flag to show/hide labels
	private boolean showLabels = true;
	
	
	//// XML data
	XMLElement xmlFeed;
	String xmlPath = "../data/profile_data.xml";
	XMLConfig config;
	
	//// PROFILE DATA
	int numProfiles;
	int curID;
	ArrayList<String> headerList =  new ArrayList();
	ArrayList<String> nameList =  new ArrayList();
	ArrayList<String> blurbList =  new ArrayList();
	ArrayList<String> latList =  new ArrayList();
	ArrayList<String> longList =  new ArrayList();
	
	// / lat and long arrays
	/// should switch these to array lists
	float[] latArray;
	float[] longArray;
	int LatLongLength;

	// array lists for users and re-tweeters
	ArrayList<GPSMarker> GPSArray = new ArrayList();
	ArrayList<GPSMarker> UserArray = new ArrayList();
	ArrayList<GPSMarker> RTArray = new ArrayList();

	/// marker object
	GPSMarker theMarker;

	///// FONTS
	PFont HeaderFont = createFont("Arial Black",14, true); /// normal fonts
	PFont BodyFont = createFont("Arial",12, true); /// normal fonts
	PFont pfont = createFont("Arial",10, true); // use true/false for smooth/no-smooth for Control fonts
	
	
	///// COLORS
	int bgColorR = 0;
	int bgColorG = 0;
	int bgColorB = 0;
	
	//// bgrounds
	PImage bgImage;
	// String bgPathImagePath = "../data/parchment_bgroundBLK.png";
	

	// / PApplet stuff
	PApplet pApp;


	/// OSC objects
	OscP5 oscP5;
	String oscMess;
	boolean hasOsc;
	boolean doCursor;
	float oscX0;
	float oscY0;
	float oscX1;
	float oscY1;

	
	// destroyer
	Destroyer theDestroyer;
	boolean doAudio = false;
	// destroyer lat and long
	float theLat= 500;
	float theLong = 500;

	/////// carmera vars
	double defaultCamX = 2.4999995; 
	double defaultCamY = 3.1199994;
	double theCamX = defaultCamX; // 2.4999995; /// initial camera position
	double theCamY = defaultCamY; // 3.1199994;
	double theOldCamX = theCamX;
	double theOldCamY = theCamY;
	
	DataProfile dataProfile;
	
	
	///////// CHROMELESS CONFIGURATION
	
	public void init() {
        if(frame!=null){
          frame.removeNotify();//make the frame not displayable
          frame.setResizable(true);
          frame.setUndecorated(true);
          println("frame is at "+frame.getLocation());
          frame.addNotify();
        }
      super.init();
  }
	
	//// POPUP WINDOW
	PopupObject thePopUp;
	int videoCounter = 0; /// current video
	boolean isVideoPlaying = false;
	
	public void setup() {
		// size(1024, 768);
		/// size(1024, 768, P2D);
		size(1024, 768, OPENGL);
		// size(1440, 900, OPENGL);
		// size(screenWidth, screenHeight, OPENGL); /// have to hard code it if running a standalone
		
		smooth();
		
		// load earth texture image
		// earthTex = loadImage("../data/earth_4096.jpg"); 
		earthTex = loadImage("../data/earth_4096_blue.jpg");  //../data/earth_outlines.jpg"); //
		// earthTex = loadImage("../data/earth_outlines.png");
		// bgImage = loadImage(bgPathImagePath);

		// build a sphere mesh with texture coordinates
		// sphere resolution set to 36 vertices
		globe = new SurfaceMeshBuilder(new SphereFunction()).createMesh(null, 36, EARTH_RADIUS);
		// compute surface orientation vectors for each mesh vertex
		// this is important for lighting calculations when rendering the mesh
		globe.computeVertexNormals();

		// setup helper class (assign to work with this applet)
		gfx = new ToxiclibsSupport(this);
		
		/// add the OSC listener object
		oscP5 = new OscP5(this,8000);

		// initPoly();
		
		// init our instance of the data profile
		dataProfile =  DataProfile.getInstance();
		// set the papplet so we can get to it from any class
		// instead of passing it back and forth like a potato
		dataProfile.pApp = this; 
		
		/// init the popup object
		/// wait to parse the XML
		/// before initializing the video
		thePopUp = new PopupObject();
		
		//// LOAD XML ///////
		loadXML();
		

	}
	
	public void draw() {

		background(bgColorR, bgColorG, bgColorB);
		// image(bgImage, 512, 384);
		

		renderGlobe();
		
		//*
		if(thePopUp.isVideoPlaying == true){
		   // thePopUp.playVideo();
			// println("LAYING VIDEO" + thePopUp.isVideoPlaying);
		   thePopUp.drawVideo();
		 }
		
		//*/
		

	}
	
	////////////////////////////////////////////////////////
	///////// FOR SOME REASON MOVIE EVENT HAS TO BE IN MAIN
	////////////////////////////////////////////////////////
	public void movieEvent(GSMovie curMovie) {
		  thePopUp.curMovie.read();  
		  //// pApp.println("movie event : " + curMovie);
	}
	
	////// VIDEO FUNCTIONS ////////////////
	public void switchVideo(){
		thePopUp.isVideoPlaying = false;
		thePopUp.stopVideo();
		/// change the path of the video
		/// have to pass an id since can't switch
		/// on a string
		thePopUp.switchCurVideo(videoCounter); 
	}

	//// end video functions

	
	/////////////////////////////////////////////
	// //init the location array 
	///// with ip addresses from the DB
	/////////////////////////////////////////////
	/////// PARSE XML DATA /////////////
	///////////////////////////////////////
	public void loadXML(){
		try {
			xmlFeed = new XMLElement(this,xmlPath );
			initXMLObject();
		} catch(Exception e) {
			pApp.println("unable to parse xml: " + e);
		}
	}
	public void initXMLObject(){
	
		numProfiles = xmlFeed.getChildCount();
		println("NUM PROFILES: " + numProfiles);
		for (int i=0; i<numProfiles; i++) {
			XMLElement profile = xmlFeed.getChild(i);
			///* 
			try{
				headerList.add(profile.getChild(0).getContent());
				nameList.add(profile.getChild(1).getContent());
				blurbList.add(profile.getChild(2).getContent());
				// videoPathList.add(profile.getChild(3).getContent());
				thePopUp.videoPath.add(profile.getChild(3).getContent());
				latList.add(profile.getChild(4).getContent());
				longList.add(profile.getChild(5).getContent());
				
				pApp.println("Title= " + profile.getChild(0).getContent());
				pApp.println("Name= " + profile.getChild(1).getContent());
				pApp.println("Blurb= " + profile.getChild(2).getContent());
				// pApp.println("video = " +  profile.getChild(3).getContent());
				pApp.println("Address = " + profile.getChild(4).getContent());
				// pApp.println("long = " + profile.getChild(5).getContent());
				//pApp.println(" ");
				pApp.println(" ");
			} catch (Exception e){
				println("XML init error: " + e);
			}
			
		
		}
		/// now that the popup video array has data, init the video
		thePopUp.initVideo();
		/// convert the lat and long string to floats
		initLocations();

	}
	
	//////// end XML parse
	/////////////////////////////
	
		public void initLocations() {
			//*
			try {

				// / set length of arrays
				LatLongLength = numProfiles;
				// init our marker handler
				latArray = new float[LatLongLength];
				longArray = new float[LatLongLength];

				// // let's print this mother out
				for (int i = 0; i < LatLongLength; i++) {

					String theLat = latList.get(i);
					String theLong = longList.get(i);
					// println(results.getJSONObject(i).getString("lat"));
					float lt = new Float(theLat);
					float lo = new Float(theLong);
					latArray[i] = lt;
					longArray[i] = lo;
					
				}

			} catch (Exception e) {
				println("Error init locations: " + e);
			}

			//*/
			initMarkers();
			initDestroyer(); 
		}
		
		//////////////////////////
		///// SET MARKERS ///////////
		private void initMarkers() {

			for (int i = 0; i< latList.size(); i++){
				
				// theMarker = new GPSMarker(lo,lt);
				theMarker = new GPSMarker(longArray[i], latArray[i]);
				theMarker.computePosOnSphere(EARTH_RADIUS);
				GPSArray.add(theMarker);
				theMarker.doInitSpawn();
				/// add all data to user profile
				theMarker.theID = i;
		
			}
		}

		///////////////
	////set destroyer
		public void initDestroyer() {
			
			//// init the destroyer
			//// placeholder lat and long
			float lt = new Float(4.6);
			float lo = new Float(-74.0833);
			
			try {
				theDestroyer = new Destroyer(lo, lt);
				theDestroyer.computePosOnSphere(EARTH_RADIUS);
			
			} catch(Exception e){
				println(e);
			}
		}




	///////////////////////////////
	/// GLOBE RENDERING
	////////////////////////////////
	private void renderGlobe(){
		// smoothly interpolate camera rotation
		// to new rotation vector based on mouse position
		// each frame we only approach that rotation by 25% (0.25 value)
		
		lights();
		/* this looks pink
		ambientLight(255, 0, 0);
		specular(255, 0, 0);
		*/
		// store default 2D coordinate system
		pushMatrix();
		// switch to 3D coordinate system and rotate view based on mouse
		translate(width / 2, height / 2, 0);
		
		///// CHECK FOR MOUSE INPUT TO SET CAMERA
		if (mousePressed) {
			camRot.interpolateToSelf(new Vec3D(mouseY * 0.01f, mouseX * 0.01f, 0),0.25f / currZoom);
			// println("MOUSEX: " + mouseX);
			// println("MouseY " + mouseY);
			theCamX = camRot.x;
			theCamY = camRot.y;
			

		///// CHECK FOR OSC INPUT TO SET CAMERA
		} else if (hasOsc == true) {
			/// map(value, low1, high1, low2, high2)
			
			println("rotate dammit!");
			float newX = map(oscX0, 0, 1, 0, screenWidth); ///// maps our input to 1024
			float oscY = map(oscY0, 0, 1, 0, screenHeight); ///// 
			camRot.interpolateToSelf(new Vec3D(oscY * 0.01f, newX * 0.01f, 0),0.25f / currZoom);
			theCamX = camRot.x;
			theCamY = camRot.y;
			// rotateX(camRot.x);
			// rotateY(camRot.y);
			// currZoom += (targetZoom - currZoom) * 0.25;
			//*/
			
		} 

		theOldCamX = theCamX;
		theOldCamY = theCamY;
		
		hasOsc = false; ///switch off osc input until we get another osc signal
		float newCamX = map(new Float(theCamX), 0,7,2,4); // narrow the range of vertical camera movement
		
		currZoom += (targetZoom - currZoom) * 0.25;
		// theCamX = newCamX;
		
		rotateX(new Float(theCamX));
		rotateY(new Float(theCamY));

		// apply zoom factor
		scale(currZoom);
		// compute the normalized camera position/direction
		// using the same rotation setting as for the coordinate system
		// this vector is used to figure out if images are visible or not
		// (see below)
		Vec3D camPos = new Vec3D(0, 0, 1).rotateX(new Float(theCamX)).rotateY(new Float(theCamY)); /// changed from cam.x and cam.y
		camPos.normalize();
		noStroke();
		fill(255);
		// use normalized UV texture coordinates (range 0.0 ... 1.0)
		textureMode(NORMAL);
		// draw earth
		gfx.texturedMesh(globe, earthTex, true);
		
		

		////////////////////////////////////////
		// /// SET GPS MARKERS ON THE SPHERE
		
		// check marker position
		for (int i = 0; i < GPSArray.size(); i++) {
			GPSArray.get(i).updateScreenPos(this, camPos);
		}
		// check destroyer position
		if(isVideoPlaying == false){
			//if(thePopUp.isVideoPlaying == true){
			theDestroyer.updateScreenPos(this, camPos);
		}
		
		/////////////////////////////////////////
		// switch back to 2D coordinate system
		popMatrix();
		// disable depth testing to ensure anything drawn next
		// will always be on top/infront of the globe
		hint(DISABLE_DEPTH_TEST);
		// draw images centered around the given positions
		imageMode(CENTER);

		// now that they're in position, draw them
		for (int i = 0; i < GPSArray.size(); i++) {
			GPSArray.get(i).drawAsImage(IMG_SIZE * currZoom * 0.9f, showLabels);
		}
		// draw the destroyer
		try{
			theDestroyer.drawAsImage(this, IMG_SIZE * currZoom * 0.9f, showLabels);
		} catch (Exception e){
			println("Cant draw destroyer" + e);
		}
		setDestroyer();
		////////////////////////////////////////
		// restore (default) depth testing
		hint(ENABLE_DEPTH_TEST);
	}
	
	

	////////////////////////////////
	///////// DESTROYER 
	////////////////////////////////////

	
	public void setDestroyer() {
		// convert cur mouse pos to lat and long
		// map(value, low1, high1, low2, high2)
		
		
		if (doCursor == true){
			// theLat = map(oscY1, 1, 0, 0, 90);
			// theLong = map(oscX1, 0, 1, -180, 180);
			
			/// change osc values to mouse-parsed
			float newOscY = map(oscY1, 0, 1, 0, screenHeight);
			float newOscX = map(oscX1, 0, 1, 0, screenWidth);
	    	
		
			desRot.interpolateToSelf(new Vec3D(newOscX * 0.51f, newOscY * 0.51f, 0),0.25f / currZoom);
			theLat = desRot.y; // map(mouseY, 0, screenHeight, 0, 90);
			theLong = desRot.x; // map(mouseX, 0, screenWidth, -180, 180);
			
			

		} else {
			// theLat = map(mouseY, 600, 0, 0, 90);
			// theLong = map(mouseX, 200, 800, -180, 180);
		
		}
		if (!mousePressed && useKeyboard == true) {
			
			///*
			desRot.interpolateToSelf(new Vec3D(mouseX * 0.51f, mouseY * 0.51f, 0),0.25f / currZoom);
			theLat = desRot.y; // map(mouseY, 0, screenHeight, 0, 90);
			theLong = desRot.x; // map(mouseX, 0, screenWidth, -180, 180);
			//*/
			
		}
		doCursor = false;
		
		/*
		 * theCamX = camRot.x;
			theCamY = camRot.y;
			*/
		
		// 

		theDestroyer.setSpherePosition(theLong, theLat); 
		theDestroyer.computePosOnSphere(EARTH_RADIUS);

		//// CHECK FOR INTERSECTION with other markers
		boolean isPopupVisible = false;
		for(int i=0; i<GPSArray.size(); i++){
		// for(int i=0; i<2; i++){
			
			/// DESTROYER LAT AND LONG
			float dlat = theDestroyer.theLat;
			float dlong = theDestroyer.theLong; //theDestroyer.theLat;
			
			//// MARKER LAT AND LONG
			float mlat = GPSArray.get(i).theLat;  
			float mlong = GPSArray.get(i).theLong;
			// println("dlat " + dlat + " mlat: " + mlat);
			// println("dlong " + dlong + " mlong: " + mlong);
			//// check to see if the destroyer is within the range of the current lat and long
			/// if (dlat >= (mlat -1) && dlat <= (mlat + 1) &&  dlong <= (mlong + 1) && dlong >= (mlong - 1)){
			
			
			//// CHECK INTERSECTION of MARKER AND DESTROYER
			GPSMarker tMark = GPSArray.get(i);
			if(isPopupVisible == false && theDestroyer.pos.y <= (tMark.pos.y + 10) && theDestroyer.pos.y >= (tMark.pos.y -10) && theDestroyer.pos.x <= (tMark.pos.x + 10) && theDestroyer.pos.x >= (tMark.pos.x -10) ){
				
				
				tMark.doHit();

				 //// init popup data
				thePopUp.theName = "";
				thePopUp.theText = "";
				
				thePopUp.theHeader = headerList.get(tMark.theID);
				thePopUp.theName = nameList.get(tMark.theID);
				thePopUp.theText = blurbList.get(tMark.theID);
				// thePopUp.theVideoPath = videoPathList.get(tMark.theID);
				
			    
				/// swtich video if it's different than the current
				if(curID != tMark.theID){
					// println("Cur ID: " + curID + " new ID: " + tMark.theID);
					/// stop current, switch
					thePopUp.stopVideo();
					thePopUp.switchCurVideo(tMark.theID);
					curID = tMark.theID;
				} 

			//// showing popup data
				thePopUp.drawPopup(tMark.theID);
				isPopupVisible = true;
				
			}

		}
		
		

	}

	
	/////////////////////////////////
	//////// OSC INPUT //////////////
	/////////////////////////////////
	
	public void oscEvent(OscMessage theOscMessage) {
		 // print the address pattern of the received OscMessage
		String addr = theOscMessage.addrPattern();
	    

	    print("### received an osc message.");
		println("tag type: "+theOscMessage.typetag());
		println("addr type: " + theOscMessage.addrPattern()); // it was lowercase in the documentation
		
		/// we have to check for init OSC values
		/// so the mouse doesn't override it on 
		/// globe and cursor postion
		if(addr.indexOf("/EpsonPlanet/xy1") !=-1){ 
			hasOsc = true;
			println(hasOsc);
		}
		if(addr.indexOf("/EpsonPlanet/xy2") !=-1){ 
			hasOsc = true;
			println(hasOsc);
		}
		
		
		if(theOscMessage.checkTypetag("i")) {
			 if(addr.equals("/EpsonPlanet/fader1")){ 
			   int valI = theOscMessage.get(0).intValue();
			   
			 } 
		}

		 
		/// check for 2 FLOATS
		if(theOscMessage.checkTypetag("ff")) {
			float val0 = theOscMessage.get(0).floatValue();
			float val1 = theOscMessage.get(1).floatValue();
			// hasOsc == true
			println("FF type: " + val0 + " " + val1);
			try {
			   if(addr.equals("/EpsonPlanet/xy1")){ 
			    	println("Do globe " + val0);
			    	oscX0 = new Float(val0);
			    	oscY0 = new Float(val1);
			    }
			    else if(addr.equals("/EpsonPlanet/xy2")){ 
			    	float val2 = theOscMessage.get(0).floatValue();
					float val3 = theOscMessage.get(1).floatValue();
			    	doCursor = true;
			    	oscX1 = new Float(val2);
			    	oscY1 = new Float(val3);
			    }
			} catch (Exception e){
				println("can't run real floats");
			}
			
		}
		/// check for ONE FLOAT
		/// thanks stupid oscP5
		if(theOscMessage.checkTypetag("f")) {
			/// set up strings for the 2 values because stupid OSC
			String str0 = theOscMessage.toString();
			String str1 = theOscMessage.toString();

			try{
			
			 // println(" VALUE 0: "+theOscMessage.get(0).floatValue());
			   if(addr.equals("/EpsonPlanet/fader1")){ 
				   // targetZoom = max(targetZoom - 0.1f, 0.5f);
				   // targetZoom = min(targetZoom + 0.1f, 1.9f);
				   float val0 = theOscMessage.get(0).floatValue();
				   println("DO ZOOM " + addr + " " + val0);
				   targetZoom = map(val0, 0,1,0.5f, 1.9f);
				   
			   	} 
			    else if(addr.equals("/1/fader2")){ 
			    	println("v2 " + str0);
			    }
			    else if(addr.equals("/1/xy1")){ 
			    	
			    }
			    else if(addr.equals("/EpsonPlanet/toggle1")){ 
			    	println("toggle visibility");
			    	theDestroyer.toggleVisibility();
			    	
			    }
			    else if(addr.equals("/EpsonPlanet/resetGlobeButt")){ 
			    	println("reset position");
			    	theCamX = defaultCamX; 
			    	theCamY = defaultCamY;
			    	targetZoom = 1;

			    	
			    }
			    else if(addr.equals("/EpsonPlanet/playVidButt")){ 
			    	println("play vid");
			    	thePopUp.startVideo();
			    	
			    }
			    else if(addr.equals("/EpsonPlanet/stopVidButt")){ 
			    	println("pause vid");
			    	thePopUp.stopVideo();

			    	
			    }
			    else if(addr.equals("/EpsonPlanet/rotary1")){ 
			    	int v = parseInt(theOscMessage.get(0).floatValue());
			    	println("R: " + v + " " + str0);
			    	bgColorR = v;
			    }
			    else if(addr.equals("/EpsonPlanet/rotary2")){ 
			    	int v = parseInt(theOscMessage.get(0).floatValue());
			    	println("G: " + v + " " + str0);
			    	bgColorG = v;
			    }
			    else if(addr.equals("/EpsonPlanet/rotary3")){ 
			    	int v = parseInt(theOscMessage.get(0).floatValue());
			    	println("B: " + v + " " + str0);
			    	bgColorB = v;
			    }
			} catch (Exception e){
				println(" osc error: " + e);
			}
		
		
		
		 }

		  /// control x and y globe
		  
		  /// control x and y destroyer
	}

	///////////////////////////////
	//////// keyboard input
	///////////////////////////////
	
	public void keyPressed() {
		if (key == '-') {
			targetZoom = max(targetZoom - 0.1f, 0.5f);
		}
		if (key == '=') {
			targetZoom = min(targetZoom + 0.1f, 1.9f);
		}
		if (key == 'l') {
			showLabels = !showLabels;
		}
		if(key == 'k'){
			if(useKeyboard == false){
				useKeyboard = true;
			} else {
				useKeyboard = false;
				
			}
			
		}
		/// switches video
		if(key == 't'){

			thePopUp.videoCounter ++;
			if(thePopUp.videoCounter >= thePopUp.videoPath.size()){
				thePopUp.videoCounter = 0;
				//println("COUNTER: " + videoCounter + " " + videoPaths[videoCounter]);
			}
			thePopUp.switchCurVideo(thePopUp.videoCounter);
			
		}
		/// play video
		if (key == 'p') {

			thePopUp.isVideoPlaying = true;
			thePopUp.startVideo();
		}
		/// swap video
		if (key == 's') {
			
			
			thePopUp.isVideoPlaying = false;
			thePopUp.stopVideo();
		}
	}
	
	// pApp.init();
	

	// /
}