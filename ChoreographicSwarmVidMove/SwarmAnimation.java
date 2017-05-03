//Child class extending from Animation. It creates new instances of the Particle.

import processing.core.*;
import java.util.*;

public class SwarmAnimation extends Animation {

  ArrayList<Particle> swarm;
  private int numPart; 

  SwarmAnimation(PApplet p) {
    super();
    swarm = new ArrayList();
    setNumPart(getNumPart() + 100);

    for (int i = 0; i < numPart; i++) {
      swarm.add(new Particle(x, y, p));
    }
  }

  public void display(PApplet p) {
    super.display(p);
    for (int i = 0; i < swarm.size(); i++) {
      Particle pa = swarm.get(i);
      pa.display(p);
      pa.move(p, swarm, fdPoints);

      //If the boolean returns true remove particles and add new ones in their place
      if (pa.dead()) {
        swarm.remove(i);
        swarm.add(new Particle(x, y, p));
      }
    }
  }

  public int getNumPart() {
    return numPart;
  }

  public void setNumPart(int num) {
    numPart = num;
  }
}