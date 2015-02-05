package ihm;

public class TestAngle {

	public static void main(String[] args) {
		
        //double dx = arrivee.x - depart.x;
        //double dy = arrivee.y - depart.y;
		
        double dx = 10000000;
        double dy = -1;
        System.out.println(Math.round(
        		Math.toDegrees(Math.atan2(dy, dx)+2*Math.PI)%360/5));
 

	}

}
