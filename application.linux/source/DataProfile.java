// package src;

import processing.core.PApplet;

public class DataProfile {
	
	private static DataProfile instance = null;
	
	// / make sure graphics exist
	PApplet pApp;
	//
    int fillColor1;
    int fillColor2;
    //
	 int maxFollowers = 10;
	 int maxFriends = 10;
	 int maxFavorites = 10;
	
   public float w_start = new Float(-0.310364); // Longtitude start (largest longitude value)
	

   protected DataProfile() {
      // Exists only to defeat instantiation.
   }
   public static DataProfile getInstance() {
      if(instance == null) {
         instance = new DataProfile();
      }
      return instance;
   }
   
   public PApplet getApplet(){
	    return pApp;
	   
   }
   //////

}
