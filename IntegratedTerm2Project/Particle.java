import processing.core.*;
import java.util.ArrayList;

class Particle {

  PVector loc = new PVector(0, 0);
  PVector vel = new PVector(0, 0);
  float velX, velY;
  int radius;
  float speed;
  float avoidRadius;
  float attrRadius;
  float videoDuration; //this will be the length of the video - map length of video to 0, 255 use as alpha
  float life;

  Particle(float x, float y, PApplet p) {
    loc.set(x, y);
    velX = p.random((float)-1, (float)1);
    velY = p.random((float)-1, (float)1);
    speed = 2; 
    vel.set((speed * velX), (speed * velY));
    radius = 2;
    videoDuration = (float)138.78334;
    life = 255;
  }

  //----------------------------------------------------------------------
  void move(PApplet p, ArrayList<Particle> swarm) {
    //Initialise movement by incrementing both the x and y positions
    vel.limit((float)2.0);
    loc.add(vel);

    //Reduce lifespan
    life -=0.5;
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
  //if particles are close to the frame differencing points move towards
  /* 1. get location of all particles
     2.calculate the distance between particle and fd points
  */





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
    temp.mult((float)0.01);
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