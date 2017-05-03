//Functional parent class which takes the data from frame differencing and allocated it for use in child classes

import processing.core.*;
import java.util.*;

abstract class Animation {

  float x, y;
  ArrayList<PVector> fdPoints;

  public void display(PApplet p) {
    //loop through points array and assign each x and y a place 
    for (PVector po : fdPoints) { 
      x = po.x;
      y = po.y;
    }
    
  }

  public void setPoints(ArrayList<PVector> po, PApplet p) {
    fdPoints = po;
    //for (Point pnt : points) p.print("(" + pnt.x + ", " + pnt.y + ") ");
    //p.println();
  }
}