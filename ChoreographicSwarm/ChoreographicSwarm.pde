///////////////////////////////////////////////   Choreographic Swarm   //////////////////////////////////////////////
/*Created by: Amy Cartwright
 Year: 2017
 
 A program that creates a swarm of particles that are dictated by movement data from a pre-recorded video. A starting point
 for research into computational choreography.
 
 Known bugs: 
 1. The particles always first appear in the top left corner of the screen. They should appear at location of movement.
 2. The particles appear at two points of movement. They should appear at all points of movement.
 
 */

import processing.video.*;

Animation a;
Movie video;
PImage prev; 
int myWidth, myHeight;
float threshold;
int i;

ArrayList<PVector> fdPoints= new ArrayList<PVector>();

void setup() {
  size(640, 432);
  background(0);
  a = new SwarmSystem(this);
  myWidth = 640;
  myHeight = 432;
  video = new Movie(this, "dogsEditedSmall.mov");

  /*create an image using the previous frame*/
  prev = createImage(myWidth, myHeight, RGB); 
  video.loop();
  threshold = 60; 
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

  a.setPoints(fdPoints, this); //call to set the points from the array list
  updatePixels(); //update video pixels
  background(0); //eliminates the video being seen
  a.display(this); //display the animation
}





//----------------------------------------------------------------------
//Method to calculate the difference between current frame and previous and store in an array
void frameDiff() {
  
  fdPoints.clear();

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
        fdPoints.add(new PVector(x, y));
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

//----------------------------------------------------------------------
//New Algorithm
/* Idea - To color the particles using the average color of the pixels from the video fdpoints
 Steps 1. Loop over the fdPoints vector containing the movment data
       2. Make a new 3 point vector to store the r, g, b color values
       3. Find the average color of pixels by adding together and dividing by total
       4. Store average in color variable
       5. Return color variable to be used when displaying particle
 */


//void partColor() {


//   for(int i = 0; i < fdPoints.size(); i++){
//     PVector temp = new PVector (0, 0, 0);  
//     temp.add(pixels[i]);
//            int x = (temp[0].x + temp[1].x + temp[2].x) / 3;
//            int y = (temp[0].y + temp[1].y + temp[2].y) / 3;
//            color c = image.pixels().color(x,y);
//            return c;
           


//   }
//}