//Insert header, title, my name, date, description of program, known bugs

import processing.video.*;

Animation a;

Movie video;
PImage prev; 
int myWidth, myHeight;
float threshold;
int i;

ArrayList<Point> points= new ArrayList<Point>();

void setup() {
  size(640, 432);
  background(0);
  a = new Swarm(this);
  myWidth = 640;
  myHeight = 432;
  video = new Movie(this, "dogsEditedSmall.mov");

  /*create an image using the previous frame*/
  prev = createImage(myWidth, myHeight, RGB); 
  video.loop();
  threshold = 60; //increase for less particles
}

void movieEvent(Movie video) {
  /*Copy the previous image onto the current image*/
  prev.copy(video, 0, 0, video.width, video.height, 0, 0, prev.width, prev.height);
  prev.updatePixels();
  video.read();
}

void draw() {

  /*Minimise draw here, move code into a method at the bottom of the page that can be called in draw */

  video.loadPixels();
  prev.loadPixels();
  image(video, 0, 0);
  loadPixels();

  frameDiff();

  a.setPoints(points, this); //call to set the points from the array list
  updatePixels(); //update video pixels
  background(0); //eliminates the video being seen
  a.display(this); //display the animation
  
  //println(video.duration());
}

//----------------------------------------------------------------------
//Method to calculate the difference between current frame and previous and store in an array
void frameDiff(){
  
  /* Begin loop to walk through every pixel*/
  for (int x = 0; x < video.width; x++ ) {
    for (int y = 0; y < video.height; y++ ) {
      int loc = x + y * video.width;
      /* current color */
      color currentColor = video.pixels[loc]; //pixel array
      float r1 = red(currentColor);
      float g1 = green(currentColor);
      float b1 = blue(currentColor);
      /*previous frame color*/
      color prevColor = prev.pixels[loc];
      float r2 = red(prevColor);
      float g2 = green(prevColor);
      float b2 = blue(prevColor);

      /*calculate distance2 between previous and current color*/
      float diff = distSq(r1, g1, b1, r2, g2, b2); 

      /* if the difference is larger than threshold2 then change the pixel color to white 
       otherwise make it black*/
      if (diff > threshold*threshold) {
        pixels[loc] = color(255);
        Point p = new Point(x, y);
        points.add(p);
      } else {
        pixels[loc] = color(0);
      }
    }
  }
}

//----------------------------------------------------------------------
//Method to calculate the differences between frames
float distSq(float x1, float y1, float z1, float x2, float y2, float z2) {
  float diff = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) +(z2-z1)*(z2-z1);
  return diff;
}