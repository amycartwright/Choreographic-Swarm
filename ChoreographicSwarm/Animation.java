//Functional parent class which takes the data from frame differencing and allocated it for use in child classes

import processing.core.*;
import java.util.*;

class Animation {

  float x, y;

  ArrayList<PVector> fdPoints;

  void display(PApplet p) {
    //loop through points array and assign each x and y a place 
    for (PVector po : fdPoints) { 
      this.x = po.x;
      this.y = po.y;
    }
  }

  void setPoints(ArrayList<PVector> po, PApplet p) {
    fdPoints = po;
    //for (Point pnt : points) p.print("(" + pnt.x + ", " + pnt.y + ") ");
    //p.println();
  }
}