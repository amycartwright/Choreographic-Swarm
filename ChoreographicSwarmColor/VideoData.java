//Class for running the video and extracting the movement data
import processing.core.*;
import java.util.*;
import processing.video.*;

public class VideoData {

  Movie video; //new instance of Movie class
  PImage prev; //new instance of Image class
  public int myWidth, myHeight;
  private float threshold;

  VideoData(PApplet p) {
    myWidth = 1080;
    myHeight = 720;
    video = new Movie(p, "try.mov"); //Initialize video object

    //create an image using the previous frame
    prev = p.createImage(myWidth, myHeight, p.RGB); 
    video.loop();
    threshold = 60;
  }

  public void draw(PApplet p, ArrayList<PVector> fdPoints) {
    if (video.available()) {
      //copy the prev frame onto current frame
      prev.copy(video, 0, 0, video.width, video.height, 0, 0, prev.width, prev.height);
      prev.updatePixels(); //update image pixels
      video.read();
      video.loadPixels();
      prev.loadPixels();
      p.image(video, 0, 0); //display video
      p.loadPixels();
      video.volume(0);

      frameDiff(fdPoints, p); //run the frameDiff function
    }
  }

  void update(PApplet p) {
    p.updatePixels(); //update video pixels
  }

  //----------------------------------------------------------------------
  //Method to calculate the difference between current frame and previous and store in an array. Method is only useful within this class so has been declared private
  private void frameDiff(ArrayList <PVector> fdPoints, PApplet p) {

    fdPoints.clear();

    // Loop through every pixel
    for (int x = 0; x < video.width; x++ ) {
      for (int y = 0; y < video.height; y++ ) {
        int loc = x + y * video.width;
        // current color 
        int currentColor = video.pixels[loc]; //pixel array
        float r1 = p.red(currentColor);
        float g1 = p.green(currentColor);
        float b1 = p.blue(currentColor);
        // previous frame color
        int prevColor = prev.pixels[loc];
        float r2 = p.red(prevColor);
        float g2 = p.green(prevColor);
        float b2 = p.blue(prevColor);

        //calculate distance2 between previous and current color
        float diff = distSq(r1, g1, b1, r2, g2, b2); 

        // if the difference is larger than threshold2 add the x, y point so fdPoints PVector
        if (diff > threshold*threshold) {
          fdPoints.add(new PVector(x, y));
        }
      }
    }
  }

  //----------------------------------------------------------------------
  //functions to get the r,g,b color from the x, y locations within the video
  int getRed(int x, int y, PApplet p) {
    return (int)p.red(video.get(x, y));
  }

  int getGreen(int x, int y, PApplet p) {
    return (int)p.green(video.get(x, y));
  }

  int getBlue(int x, int y, PApplet p) {
    return (int)p.blue(video.get(x, y));
  }

  //----------------------------------------------------------------------
  //Method to calculate the differences between frames
  private float distSq(float x1, float y1, float z1, float x2, float y2, float z2) {
    float diff = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) +(z2-z1)*(z2-z1);
    return diff;
  }
}