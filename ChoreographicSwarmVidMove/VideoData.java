//Class for running the video and extracting the movement data
import processing.core.*;
import java.util.*;
import processing.video.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class VideoData {

  Movie video;
  PImage prev; 
  int myWidth, myHeight;
  float threshold;

  VideoData(PApplet p) {
    myWidth = 1080;
    myHeight = 720;
    video = new Movie(p, "try.mov");

    /*create an image using the previous frame*/
    prev = p.createImage(myWidth, myHeight, p.RGB); 
    video.loop();
    threshold = 60;
  }

  public void draw(PApplet p, ArrayList<PVector> fdPoints) {
    if (video.available()) {
      prev.copy(video, 0, 0, video.width, video.height, 0, 0, prev.width, prev.height);
      prev.updatePixels();
      video.read();

      video.loadPixels();
      prev.loadPixels();
      //p.image(video, 0, 0);
      p.loadPixels();
      video.volume(0);

      frameDiff(fdPoints, p);

      //p.println(video.duration());
    }
  }

  void update(PApplet p) {
    p.updatePixels(); //update video pixels
  }

  //----------------------------------------------------------------------
  //Method to calculate the difference between current frame and previous and store in an array. Method is only useful within this class so has been declared private
  private void frameDiff(ArrayList <PVector> fdPoints, PApplet p) {

    fdPoints.clear();

    /* Begin loop to walk through every pixel*/
    for (int x = 0; x < video.width; x++ ) {
      for (int y = 0; y < video.height; y++ ) {
        int loc = x + y * video.width;
        /* current color */
        int currentColor = video.pixels[loc]; //pixel array
        float r1 = p.red(currentColor);
        float g1 = p.green(currentColor);
        float b1 = p.blue(currentColor);
        /*previous frame color*/
        int prevColor = prev.pixels[loc];
        float r2 = p.red(prevColor);
        float g2 = p.green(prevColor);
        float b2 = p.blue(prevColor);

        /*calculate distance2 between previous and current color*/
        float diff = distSq(r1, g1, b1, r2, g2, b2); 

        /* if the difference is larger than threshold2 then change the pixel color to white 
         otherwise make it black*/
        if (diff > threshold*threshold) {
          //p.pixels[loc] = p.color(255);
          fdPoints.add(new PVector(x, y));
        } else {
          // p.pixels[loc] = p.color(0);
        }
      }
    }
  }

  //----------------------------------------------------------------------
  //Method to calculate the differences between frames
  private float distSq(float x1, float y1, float z1, float x2, float y2, float z2) {
    float diff = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) +(z2-z1)*(z2-z1);
    return diff;
  }

  //----------------------------------------------------------------------
  //New Algorithm
  /* Idea - To color the particles using the average color of the pixels from the video fdpoints
   Steps 1. Loop over the fdPoints vector containing the movment data
   2. Make a new array list to store the r, g, b color values
   3. Find the average color of pixels by adding together and dividing by total
   4. Store average in color variable
   */

 //int partColor(ArrayList<PVector> fdPoints, PImage prev, PApplet p) {

 //   for (int i = 0; i < fdPoints.size(); i++) {
 //     ArrayList<Integer> pts = new ArrayList<Integer>(i);  
 //     int x = (pts.get(0) + pts.get(1) + pts.get(2)) / 3;
 //     int y = (pts.get(0) + pts.get(1) + pts.get(2)) / 3;
 //     return int c = video.pixels.x;
 //   }
 // }
}