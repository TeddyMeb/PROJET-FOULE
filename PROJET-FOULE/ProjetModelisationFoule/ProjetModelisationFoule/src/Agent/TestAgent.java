
package Agent;
import point.Point;
import java.awt.Color;
/**
 *
 * @author tmebarki
 */
public class TestAgent {

    
    public static void main(String[] args) {
       Point position = new Point(1,2);
       Point objectif =new Point(5,12); 
       Agent agent1 = new Agent(position,objectif,3,5,Color.BLUE);
       System.out.println(agent1);
       for (int k=0;k<10;k++){
           agent1.avancer();
           System.out.println(agent1);
       }
    }
    
}
