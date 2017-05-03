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
VideoData video;

ArrayList<PVector> fdPoints= new ArrayList<PVector>();

void setup() {
  size(1080, 720);
  background(0);
  a = new SwarmAnimation(this);
  video = new VideoData(this);
}

void draw() {

  video.draw(this, fdPoints);
  //call to set the points from the array list
  a.setPoints(fdPoints, this); 
  //call the update function from the VideoData class
  video.update(this);
  //eliminates the video being seen
  background(0); 
  //display the animation
  a.display(this);  
}