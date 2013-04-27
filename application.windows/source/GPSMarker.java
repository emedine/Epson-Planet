// package src;

import java.util.Date;

import processing.core.PApplet;
import toxi.geom.Vec2D;
import toxi.geom.Vec3D;
import toxi.math.MathUtils;


public class GPSMarker {
	protected Vec2D gps;

	protected Vec3D pos;
	protected Vec2D screenPos = new Vec2D();
	protected boolean isVisible;
	
	public float theLong;
	public float theLat;
	
	/// temp colors
	int fillColor1 = 255;
	int fillColor2 = 0;
	int fillColor3 = 0;
	
	// set colors
	int fillColorR = 255;
	int fillColorG = 0;
	int fillColorB = 0;
	
	
	int size1 = 35;
	int size2 = 12;
	
	int setSize = 35;
	
	int theID;
	
	//
	DataProfile dataProfile;
	//
	PApplet pApp;
	
	public int tAlpha = 185;

	// / pass the lat and long into the GPS marker
	public GPSMarker(float tLong, float tLat) {
	
		dataProfile = DataProfile.getInstance();
		pApp = dataProfile.pApp;
		theLong = tLong;
		theLat = tLat;
		
		/// pApp.println(theLong);
		// set the gps data
		this.gps = new Vec2D(theLong, theLat);

			//
		/*
		int[] numbers = { 90, 150, 30 };
		int a = numbers[0] + numbers[1]; // Sets variable a to 240
		int b = numbers[1] + numbers[2]; // Sets variable b to 180
		*/
		//  20,30,40,62,63,60,61,64,46

	}

	/**
	 * Computes the position of the image on a sphere of the given radius.
	 * 
	 * @param earthRadius
	 * @return position in cartesian space
	 */
	public Vec3D computePosOnSphere(int earthRadius) {
		// build a spherical position from lat/lon and convert into XYZ space
		pos = new Vec3D(earthRadius, MathUtils.radians(gps.x) + MathUtils.PI, MathUtils.radians(gps.y)).toCartesian();
		return pos;
	}
	public void doHit(){
		// tAlpha = 255;
	    fillColor2 = 255;
	    size1 = 45;

	}
	public void doInitSpawn(){
		// tAlpha = 255;
	    fillColor2 = 255;
	    fillColor3 = 255;
	    size1 = 65;

	}
	/**
	 * Draws image at computed position in space.
	 * 
	 * @param app
	 *            parent applet
	 * @param size
	 *            image size
	 * @param showLabel
	 *            true, to draw image label
	 */
	public void drawAsImage(float size, boolean showLabel) {
		
		/// this resets the color gradually if the marker has been hit
		if(fillColor1 <= fillColorR){
			fillColor1 +=20;
			// size1 = 12;
		} else if (fillColor1 >= fillColorR){
			fillColor1 -=20;
			// size1 -=1;
			// tAlpha ++;
			
		}
		if(fillColor2 <= fillColorG){
			fillColor2 +=20;
			
		} else if(fillColor2 >= fillColorG){
			fillColor2 -=20;
			// size1 -=1;

		}
		if(fillColor3 <= fillColorB){
			fillColor3 += 20;

		} else if(fillColor3 >= fillColorB){
			fillColor3 -=20;
			//size1 -=1;
			// tAlpha ++;
			
		}
		if(size1 >= setSize){
			size1 -= 1;
		} else {
			size1 = setSize;
		}
		if (isVisible) {
			// fillColor1 = color((int) (rnd1*255), (rnd2*255),(rnd*255)); // color((int) (rnd1*255), (rnd2*255),(rnd*255)); 
			/// fillColor1 = int((int)(rnd1*255));
			
			// app.noStroke();
			pApp.fill(fillColor1, fillColor2, fillColor3, tAlpha-75);
			pApp.ellipse(screenPos.x, screenPos.y, size1, size1);
			pApp.fill(fillColor1, fillColor2, fillColor3, tAlpha);
			pApp.ellipse(screenPos.x, screenPos.y, size2, size2);
		}

	}
	
	public void updateScreenPos(PApplet app, Vec3D camPos) {
		screenPos.set(app.screenX(pos.x, pos.y, pos.z), app.screenY(pos.x, pos.y, pos.z));

		float dot = pos.getNormalized().dot(camPos);
		
		// if it's 'small', then it's 'behind' the globe
		isVisible = dot > 0.66;
	}
	
	///
}
