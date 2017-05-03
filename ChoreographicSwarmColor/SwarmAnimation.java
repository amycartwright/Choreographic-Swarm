//Child class extending from Animation. It creates new instances of the Particle.

import processing.core.*;
import java.util.*;

public class SwarmAnimation extends Animation {

  ArrayList<Particle> swarm;
  private int numPart; 
  public int maxPart = 150;

//----------------------------------------------------------------------
//'getter' & 'setter' functions to access the number of parts
  public int getNumPart() { 
    return numPart;
  }

  public void setNumPart(int num) { 
    numPart = num;
  }
//----------------------------------------------------------------------
  SwarmAnimation(PApplet p) {
    super(); //do not override animation constructor, add to it
    swarm = new ArrayList<Particle>();
    setNumPart(getNumPart() + maxPart); //set num oof parts using functions above

    //add particles to swarm arrayList
    for (int i = 0; i < numPart; i++) {
      swarm.add(new Particle(x, y, p));
    }
  }

  public void display(PApplet p, VideoData video) {
    super.display(p, video); //do not override animation display, add to it
    //loop over particles addressing each one
    for (int i = 0; i < swarm.size(); i++) {
      Particle pa = swarm.get(i);
      //get x & y locations of particles
      int x = (int)pa.getX();
      int y = (int)pa.getY();
      //get r,g,b colours of video at x and y locations of particles
      int red = video.getRed(x, y, p);
      int green = video.getGreen(x, y, p);
      int blue = video.getBlue(x, y, p);
      pa.setColor(red, green, blue); //set colours of video
      pa.display(p);
      pa.move(p, swarm, fdPoints);

      //If the boolean returns true remove particles and add new ones in their place
      if (pa.dead()) {
        swarm.remove(i);
        swarm.add(new Particle(x, y, p));
      }
    }
  }
}