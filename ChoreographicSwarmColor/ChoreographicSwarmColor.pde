///////////////////////////////////////////////   Choreographic Swarm   //////////////////////////////////////////////
/*Created by: Amy Cartwright
 Year: 2017
 
 A program that creates a swarm of particles that are dictated by movement data from a pre-recorded video. A starting point
 for research into computational choreography.
 
 Known bugs: 
 1. The particles always first appear in the top left corner of the screen. They should appear at location of movement.
 */

import processing.video.*;

Animation a; //declare new instance of Animation class
VideoData video; //declare new instance of VideoData class

ArrayList<PVector> fdPoints = new ArrayList<PVector>(); //New arrayList of PVectors storing movement data

void setup() {
  size(1080, 720); //for best results set to size of video
  background(0); //black
  a = new SwarmAnimation(this); //initialize Animation object
  video = new VideoData(this); //initialize VideoData object
}

void draw() {
  
  video.draw(this, fdPoints); //call the draw function from the video class
  a.setPoints(fdPoints, this); //call to set the points from the array list
  video.update(this); //call the update function from the VideoData class
  background(0); //draws over the video
  a.display(this, video); //display the animation
}