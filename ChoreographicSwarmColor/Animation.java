//Functional parent class which takes the data from frame differencing and allocated it for use in child classes

import processing.core.*;
import java.util.*;

//abstract class as this class contains methods that are declared but contain no implementation
abstract class Animation {

  public float x, y;
  ArrayList<PVector> fdPoints; 

  public void display(PApplet p, VideoData video) {
    //loop through arrayList holding movement data and assign each an x and y
    for (PVector po : fdPoints) { 
      x = po.x;
      y = po.y;
    }
  }

  public void setPoints(ArrayList<PVector> po, PApplet p) {
    //set points from the po arraylist to the fdPoints arraylist
    fdPoints = po;
  }
}