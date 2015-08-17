/*
 * Timothy Koh
This is a test class for the TransportSlab
8/22/14
 */

package transportslab;

import java.util.Arrays;
import javax.swing.JFrame;

/**
 *
 * @author Wei-Ming
 */
public class TransportSlabTester {
    
    public static void main(String[] args) {
        
        
        TransportSlab test = new TransportSlab(10000, 1, 5, 75);
        //new object called test of class TransportSlab
        
        System.out.println(Arrays.toString(test.analytical())); 
        System.out.println(Arrays.toString(test.euler()));
        System.out.println(Arrays.toString(test.monteCarlo()));
        test.leastSquaresSigma();
        //testing each of the methods
        
        JFrame frame = new JFrame();
        frame.setSize( 1280, 720);
        frame.setTitle("Graphing the Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TransportSlabGraph component = new TransportSlabGraph();
        frame.add(component);
        frame.setVisible(true);
        
        
       
        
        
    }
    
}
