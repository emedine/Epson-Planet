// package src;

import processing.core.PApplet;
import processing.core.PShape;
import toxi.geom.Vec2D;
import toxi.geom.Vec3D;
import toxi.math.MathUtils;

public class Destroyer {
	protected Vec2D gps;

	protected Vec3D pos;
	protected Vec2D screenPos = new Vec2D();
	protected boolean showDest = true;
	protected boolean isVisible;
	
	public float theLong;
	public float theLat;
	public float theRotation = 0;

	// 
	DataProfile theDataProfile;
	PApplet pApp;
	


	// / pass the lat and long into the GPS marker
	public Destroyer(float tLong, float tLat) {

		// set the gps data
		//theLong = tLong;
		// theLat = tLat;
		this.gps = new Vec2D(tLong, tLat);
		theDataProfile = theDataProfile.getInstance();
		pApp = theDataProfile.pApp;


	}

	/**
	 * Computes the position of the image on a sphere of the given radius.
	 * 
	 * @param earthRadius
	 * @return position in cartesian space
	 */
	public Vec3D computePosOnSphere(int earthRadius) {
		theRotation +=1;
		if(theRotation >=360){
			theRotation = 0;
		}
		// build a spherical position from lat/lon and convert into XYZ space
		pos = new Vec3D(earthRadius, MathUtils.radians(gps.x) + MathUtils.PI, MathUtils.radians(gps.y)).toCartesian();
		return pos;
	}
	
	/**
	 * Draws image at computed position in space.
           true, show in front
	 */
	public void drawAsImage(PApplet app, float size, boolean showLabel) {
		
		/*
		  float rnd1 = pApp.random(1);
		  float rnd2 = pApp.random(1);
		  float rnd3 = pApp.random(1);
		 
		 //  int fillColor = pApp.color((int) (rnd1*125), (rnd2*125),(rnd3*255)); 
		  int theAlpha = Math.round(rnd2*255);
		   */
		  if (isVisible) {
			  int lineWidth = 30;
			  int tSpacing = 5;
			  pApp.fill(255);
			/// do numbers
			  pApp.text(theLong, screenPos.x + 8, screenPos.y + 23);
			  pApp.text(" " + theLat, screenPos.x + 8, screenPos.y + 33);
			  
			  pApp.fill(0);
			  pApp.stroke(255);
			  pApp.strokeWeight(1);
				
				/// trim x
				pApp.line(screenPos.x, screenPos.y - tSpacing, screenPos.x + lineWidth/2, screenPos.y -tSpacing);
				pApp.line(screenPos.x, screenPos.y + tSpacing, screenPos.x + lineWidth/2, screenPos.y + tSpacing);
				pApp.ellipse(screenPos.x, screenPos.y, lineWidth/2, lineWidth/2);
				
				/// crosshair
				pApp.fill(0,0,0,0);
				pApp.stroke(255);
				// do horiz
				pApp.line(screenPos.x-lineWidth/2, screenPos.y, screenPos.x + lineWidth, screenPos.y);
				/// do vert
				pApp.line(screenPos.x, screenPos.y-lineWidth/2, screenPos.x, screenPos.y + lineWidth);
				// do circle
				// pApp.ellipse(screenPos.x-lineWidth, screenPos.y-lineWidth, lineWidth/2, lineWidth/2);
				// pApp.ellipse(screenPos.x-lineWidth/2, screenPos.y-lineWidth/2, lineWidth/2, lineWidth/2);
				pApp.ellipse(screenPos.x, screenPos.y, lineWidth, lineWidth);
				//*/
				
				
			    // thePoly.doPolygon(app, 5, screenPos.x, screenPos.y, 12, 12, theAlpha, fillColor);
		  } else {
			  // thePoly.doPolygon(app, 3, screenPos.x, screenPos.y, 8, 8, 125, fillColor);
		  }
		 /// thePoly.rotate(pApp.radians(theRotation));
	}
	
	//// toggles visibility from the interface
	public void toggleVisibility(){
		if(showDest){
			showDest = false;
		} else if(!showDest){
			showDest = true;
			
		}
		
	}

	public void setSpherePosition(float tLong, float tLat){
		this.gps = new Vec2D(tLong, tLat);
		theLong = tLong;
		theLat = tLat;
	}

	/// checks to see if the image is in front or behind of the sphere
	public void updateScreenPos(PApplet app, Vec3D camPos) {
		screenPos.set(app.screenX(pos.x, pos.y, pos.z),app.screenY(pos.x, pos.y, pos.z));

		float dot = pos.getNormalized().dot(camPos);
		if(dot > 0.6 && showDest == true){
			isVisible = true;
		} else {
			isVisible = false;
		}
		// isVisible = dot > 0.6;
	}
}
