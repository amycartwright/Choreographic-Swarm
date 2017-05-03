//A particle swarm based on flocking algorithms written by Craig Reynolds and seen in 'The Nature of Code' pg.308

import processing.core.*;
import java.util.*;

class Particle {

  PVector loc = new PVector(0, 0);
  PVector vel = new PVector(0, 0);
  float velX, velY;
  int radius;
  float speed;
  float life;

  Particle(float x, float y, PApplet p) {
    loc.set(x, y);
    velX = p.random((float)-1, (float)1);
    velY = p.random((float)-1, (float)1);
    speed = 2; 
    vel.set((speed * velX), (speed * velY));
    radius = 2;
    life = 255;
  }

  //----------------------------------------------------------------------
  void move(PApplet p, ArrayList<Particle> swarm, ArrayList<PVector> fdPoints) {
    //Initialise movement by incrementing both the x and y positions
    vel.limit((float)2.0);
    loc.add(vel);

    //Reduce lifespan
    life -=0.5;
    seek(p, swarm, fdPoints);
    seperate(p, swarm);
    centre(p, swarm);
    align(p, swarm);
    bounce(p);
  }

  //----------------------------------------------------------------------
  void display(PApplet p) {
    p.stroke(255, life);
    p.fill(255, life);
    p.ellipseMode(p.RADIUS);
    p.ellipse(loc.x, loc.y, radius, radius);
  }

  //----------------------------------------------------------------------
  //Find out if the particles is dead or alive. If dead return true, else return false
  boolean dead() {
    if (life < 0.0) return true;
    else return false;
  }

  //----------------------------------------------------------------------
  //New Algorithm
  /* Idea - Particles are pulled towards points of movement in video
   Steps 1. Creat a temporary empty vector
         2. Loop through all fdpoints
         3. Find location of all fdpoints
         4. Calculate distance between location of particles and location of fdPoints
         5. If distance is less than threshold move particle towards fdPoints
   
   */

  void seek(PApplet p, ArrayList<Particle> swarm, ArrayList<PVector> fdPoints){
    
    p.println(fdPoints.size());
    
    PVector temp = new PVector(0, 0);
 

    for(int i = 0; i < fdPoints.size(); i++){
      PVector fd = fdPoints.get(i);
      float dist = loc.dist(fd);
      
      if(dist < 20 && dist > 0){
        PVector acc = fd.sub(loc);
        acc.normalize();
        acc.mult(2.0f); //MAGIC NO
        temp.add(acc);
      }
      
    }
   vel.add(temp);

  }


  //----------------------------------------------------------------------
  void seperate(PApplet p, ArrayList<Particle> swarm) {
    PVector temp = new PVector(0, 0);

    for (int i = 0; i < swarm.size(); i++) {
      Particle neighbour = swarm.get(i);
      float dist = loc.dist(neighbour.loc);
      //if the distance is greater than 0 and less than 20 minus neighbours position from current position
      if (dist < 20 && dist > 0) {
        temp.sub(PVector.sub(neighbour.loc, loc));
      }
    }
    temp.mult((float)0.07);
    vel.add(temp);
  }

  //----------------------------------------------------------------------
  void centre(PApplet p, ArrayList<Particle> swarm) {
    PVector temp = new PVector(0, 0);
    int count = 0;

    for (int i = 0; i < swarm.size(); i++) {
      Particle neighbour = swarm.get(i);
      float dist = loc.dist(neighbour.loc);
      //if the distance is greater than 0 and less than 20 minus neighbours position from current position
      if (dist < 40 && dist > 0) {
        temp.add(neighbour.loc);
        count++;
      }
    }
    if (count == 0) return;
    temp.div(count);
    temp.sub(loc);
    temp.mult((float)0.001);
    vel.add(temp);
  }

  //----------------------------------------------------------------------
  void align(PApplet p, ArrayList<Particle> swarm) {
    PVector temp = new PVector(0, 0);
    int count = 0;

    for (int i = 0; i < swarm.size(); i++) {
      Particle neighbour = swarm.get(i);
      float dist = loc.dist(neighbour.loc);
      //if the distance is greater than 0 and less than 20 minus neighbours position from current position
      if (dist < 30 && dist > 0) {
        temp.add(neighbour.vel);
        count++;
      }
    }

    if (count == 0) return;
    temp.div(count);
    temp.mult((float)0.3);
    vel.add(temp);
  }


  //----------------------------------------------------------------------
  //Method to allow particles to bounce when they hit edge of window
  void bounce(PApplet p) {
    int width = p.width;
    int height = p.height;

    if (loc.x > (width - radius) || loc.x < (0 + radius)) vel.x *= -1;
    if (loc.y > (height - radius) || loc.y < (0 + radius)) vel.y *= -1;
  }
}