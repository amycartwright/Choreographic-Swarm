import processing.core.*;
import java.util.*;

class Animation {

  int x, y;

  ArrayList<Point> points;

  void display(PApplet p) {
    //loop through points array and assign each x and y a place 
    for (Point po : points) { 
      this.x = po.x;
      this.y = po.y;
    }
  }

  void setPoints(ArrayList<Point> po, PApplet p) {
    points = po;
    //for (Point pnt : points) p.print("(" + pnt.x + ", " + pnt.y + ") ");
    //p.println();
  }
}