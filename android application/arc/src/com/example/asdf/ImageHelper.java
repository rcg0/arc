package com.example.asdf;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class ImageHelper {

	/*bmp1 is the bigger of the two
	* you want them both overlaid from the top left corner. 
	*/
	 public Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
	        
		 	Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
	        Canvas canvas = new Canvas(bmOverlay);
	        canvas.drawBitmap(bmp1, new Matrix(), null);
	        canvas.drawBitmap(bmp2, new Matrix(), null);
	        
	        return bmOverlay;
	 }
	
}
