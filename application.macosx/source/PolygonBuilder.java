// package src;

import processing.core.PApplet;
import processing.core.PGraphics;

public class PolygonBuilder extends PApplet {

	int theOpacity;
	int theFillColor;

	// / make sure graphics exist
	PGraphics g;

	// // number vertices, xpos, ypos, length, rotation
	//
	PolygonBuilder() {
		// g = theG;
	}

	void doPolygon(PApplet app, int n, float cx, float cy, float r, float rot, int objOpacity, int theColor) {
		theOpacity = objOpacity;

		theFillColor = theColor;

		float angle = new Float(360.0 / n);

		app.fill(theFillColor);
		// app.stroke(0, rnd * 225, 12);
		app.strokeWeight(0);

		app.beginShape();
		for (int i = 0; i < n; i++) {
			app.vertex(cx + r * cos(radians(angle * i)), cy + r * sin(radians(angle * i)));
		}

		//
		// rotate(rot);
		app.endShape(CLOSE);

	}

	void update(float rot) {
		println("aaaaa");
		// rotate(rot);
		translate(0 - 60, 0 - 60);

	}
}