//A particle class containing behaviours  based on flocking algorithms written by Craig Reynolds and seen in 'The Nature of Code' pg.308

import processing.core.*;
import java.util.*;

final class Particle {

  private PVector loc = new PVector(0, 0);
  private PVector vel = new PVector(0, 0);
  private float velX, velY;
  public float radius, speed, life, vidDuration;
  private float seekVar, alignVar, centreVar, sepVar;
  private float seekDist, alignDist, centreDist, sepDist;


  Particle(float x, float y, PApplet p) {
    loc.set(x, y);
    velX = p.random(-1f, 1f);
    velY = p.random(-1f, 1f);
    speed = 2; 
    vel.set((speed * velX), (speed * velY));
    radius = p.random(2, 5);
    vidDuration = 83.63619f;
    life = p.map(vidDuration, 0, 100, 255, 0);
    seekVar = 2.0f;
    alignVar = 0.3f;
    centreVar = 0.001f;
    sepVar = 0.07f;
    seekDist = 20;
    alignDist = 30;
    centreDist = 40;
    sepDist = 20;
  }

  //----------------------------------------------------------------------
  public void move(PApplet p, ArrayList<Particle> swarm, ArrayList<PVector> fdPoints) {
    //Initialise movement by incrementing both the x and y positions
    vel.limit(speed);
    loc.add(vel);

    //Reduce lifespan
    life -=0.1;
    seek(p, swarm, fdPoints);
    seperate(p, swarm);
    centre(p, swarm);
    align(p, swarm);
    bounce(p);
  }

  //----------------------------------------------------------------------
  public void display(PApplet p) {
    p.stroke(255, life);
    //partColor(fdPoints);
    p.fill(255, life);
    p.ellipseMode(p.RADIUS);
    p.ellipse(loc.x, loc.y, radius, radius);
  }

  //----------------------------------------------------------------------
  //Find out if the particles is dead or alive. If dead return true, else return false. Needs to 
  //be accessed by another class but I dont want to be accessable to the client
  protected boolean dead() {
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

  private void seek(PApplet p, ArrayList<Particle> swarm, ArrayList<PVector> fdPoints) {

    //p.println(fdPoints.size());

    PVector temp = new PVector(0, 0);

    for (PVector fd : fdPoints) {
      float dist = loc.dist(fd);

      if (dist < seekDist && dist > 0) {
        PVector acc = fd.sub(loc);
        acc.normalize();
        acc.mult(seekVar); 
        temp.add(acc);
      }
    }
    vel.add(temp);
  }

  //----------------------------------------------------------------------
  private void seperate(PApplet p, ArrayList<Particle> swarm) {
    PVector temp = new PVector(0, 0);

    for (Particle neighbour : swarm) {
      float dist = loc.dist(neighbour.loc);
      //if the distance is greater than 0 and less than 20 minus neighbours position from current position
      if (dist < sepDist && dist > 0) {
        temp.sub(PVector.sub(neighbour.loc, loc));
      }
    }
    temp.mult(sepVar);
    vel.add(temp);
  }

  //----------------------------------------------------------------------
  private void centre(PApplet p, ArrayList<Particle> swarm) {
    PVector temp = new PVector(0, 0);
    int count = 0;

    for (Particle neighbour : swarm) {
      float dist = loc.dist(neighbour.loc);
      //if the distance is greater than 0 and less than 20 minus neighbours position from current position
      if (dist < centreDist && dist > 0) {
        temp.add(neighbour.loc);
        count++;
      }
    }
    if (count == 0) return;
    temp.div(count);
    temp.sub(loc);
    temp.mult(centreVar);
    vel.add(temp);
  }

  //----------------------------------------------------------------------
  private void align(PApplet p, ArrayList<Particle> swarm) {
    PVector temp = new PVector(0, 0);
    int count = 0;

    for (Particle neighbour : swarm) {
      float dist = loc.dist(neighbour.loc);
      //if the distance is greater than 0 and less than 20 minus neighbours position from current position
      if (dist < alignDist && dist > 0) {
        temp.add(neighbour.vel);
        count++;
      }
    }

    if (count == 0) return;
    temp.div(count);
    temp.mult(alignVar);
    vel.add(temp);
  }

  //----------------------------------------------------------------------
  //Method to allow particles to bounce when they hit edge of window
  private void bounce(PApplet p) {
    int width = p.width;
    int height = p.height;

    if (loc.x > (width - radius)) vel.x *= -1;
    if (loc.y > (height - radius)) vel.y *= -1;
  }
}