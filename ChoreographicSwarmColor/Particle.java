//A particle class containing behaviours  based on flocking algorithms written by Craig Reynolds and seen in 'The Nature of Code' pg.308

import processing.core.*;
import java.util.*;

final class Particle {

  private PVector loc = new PVector(0, 0);
  private PVector vel = new PVector(0, 0);
  private float velX, velY;
  public float radius, speed, life;
  private float seekVar, alignVar, centreVar, sepVar;
  private float seekDist, alignDist, centreDist, sepDist;
  int r, b, g;

  Particle(float x, float y, PApplet p) {
    loc.set(x, y);
    velX = p.random(-1f, 1f);
    velY = p.random(-1f, 1f);
    speed = 2; 
    vel.set((speed * velX), (speed * velY));
    radius = p.random(2, 5);
    life = 255; //set to 255 so can reduce slowly and particles will fade
    
    //variables for use in the individual 'rule' algorithms
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
  //'getter'functions to access the location of particles
  public float getX() { 
    return loc.x;
  }
  public float getY() { 
    return loc.y;
  }

//----------------------------------------------------------------------
  public void move(PApplet p, ArrayList<Particle> swarm, ArrayList<PVector> fdPoints) {
    
    vel.limit(speed);//limit the speed
    loc.add(vel);//add velocity to location to intiialise movement
    life -=0.5; //Reduce lifespan
    
    //call each of the algorithms here to reduce code in the swarmAnimation class
    seek(p, swarm, fdPoints);
    seperate(p, swarm);
    centre(p, swarm);
    align(p, swarm);
    bounce(p);
  }

  //----------------------------------------------------------------------
  public void display(PApplet p) {
    p.stroke(255, life);
    p.fill(r, g, b, life); //set color from the set color function below
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
  // Particles are pulled towards points of movement in video
  private void seek(PApplet p, ArrayList<Particle> swarm, ArrayList<PVector> fdPoints) {

    PVector temp = new PVector(0, 0);
    //Loop through all fdpoints and find location
    for (PVector fd : fdPoints) {
      float dist = loc.dist(fd);
      
      //If distance is less than threshold move particles towards
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
  //Particles seperate if they are too close to others
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
  //Particles move away from neighbour if they are too close
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
  //Particles attempt to take the same path as others they are close to
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

    if (loc.x > (width - radius) || loc.x < (0 + radius)) vel.x *= -1;
    if (loc.y > (height - radius) || loc.y < (0 + radius)) vel.y *= -1;
  }
 //----------------------------------------------------------------------
 //Method to setColor of particles
  void setColor(int red, int green, int blue) {
    r = red;
    g = green;
    b = blue;
  }
}