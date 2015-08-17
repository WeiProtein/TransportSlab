/*
 * Timothy Koh
This is the graphing portion of the data from analytical, euler, and monte-carlo methods
8/29/14
 */

package transportslab;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import javax.swing.JComponent;
import javax.swing.JFrame;
//packages needed for the graphing components


public class TransportSlabGraph extends JComponent{
    int xTopLeft = 150;
    int yTopLeft = 150;
    int lengthGraph = 980;
    int heightGraph = 420;
    int ybase = 570;
    int xbase = 1130;
    //specifying the corners of the "graph"
    int numOfTicks = 10;
    double delta_y = (ybase-yTopLeft)/10;
    double delta_x = (xbase-xTopLeft)/10;
    int num_slices = 100;
    double numParticles = 10000;
    //above two variables are taken into the TransportSLab test class 
    double x_slide = (lengthGraph/(num_slices+1));
    double y_slide = heightGraph/numParticles;
    double thickness = 1;
    private double yTickValue;
    private double xTickValue;
    double x1;
    double x2;
    double y1Ana;
    double y2Ana;
    double y1Eul;
    double y2Eul;
    double y1Mnt;
    double y2Mnt;
    //variables needed to keep soft coding
    
    TransportSlab test = new TransportSlab(numParticles, thickness, 5, num_slices);
    double[] nAnalytical = test.analytical();
    double[] nEuler = test.euler();
    double[] nMonteCarlo = test.monteCarlo();
    double[] a = new double[num_slices + 1];
    
    
    
    public void paintComponent(Graphics g){
        
        Graphics2D g2 = (Graphics2D) g;
        Rectangle box = new Rectangle(xTopLeft, yTopLeft, lengthGraph, heightGraph);
        g2.draw(box);
        //draws the largest rectangle acting as the axis
        Rectangle box2 = new Rectangle(1130,0,150,150);
        g2.draw(box2);
        //draws the smaller rectangle acting as the legend box
        g2.drawString("Legend",1170,20);
        //title of the legend
        g2.drawString("Analytic: ",1140,50);
        //draws text Analytic starting 1140,50
        Line2D.Double redLine = new Line2D.Double(1220,50,1250,50);
        g2.setColor(Color.RED);
        g2.draw(redLine);
        //draw red line acting as a key in the legend
        g2.setColor(Color.BLACK);
        //change draw color back to black
        g2.drawString("Euler: ",1140,100);
        Line2D.Double blueLine = new Line2D.Double(1220,100,1250,100);
        g2.setColor(Color.BLUE);
        g2.draw(blueLine);
        //draw blue line acting as a key in the legend
        g2.setColor(Color.BLACK);
        //change draw color back to black
        g2.drawString("Monte-Carlo: ",1140,140);
        Line2D.Double greenLine = new Line2D.Double(1220,140,1250,140);
        g2.setColor(Color.GREEN);
        g2.draw(greenLine);
        //draw green line acting as a key in the legend
        g2.setColor(Color.BLACK);
        //change draw color back to black
        g2.drawString("TransportSLab",((2*yTopLeft)+lengthGraph)/2, 75);
        //gives title of graph
        g2.drawString("Position (cm)",((2*yTopLeft)+lengthGraph)/2, 645);
        //gives x label of graph
        AffineTransform orig = g2.getTransform();
        g2.rotate(-Math.PI/2);
        g2.drawString("Number of Particles (N(x))", -360, 65);
        g2.setTransform(orig);
        //gives y label of graph while rotating text 90 degrees
        
        
        for(int i = 0; i < numOfTicks + 1; i++)
        {
           Line2D.Double segment = new Line2D.Double((xTopLeft-10),(yTopLeft + i*delta_y),xTopLeft,(yTopLeft + i*delta_y));
           g2.draw(segment);
           //drawing the tick marks at specified intervals
           
           yTickValue = numParticles -((numParticles*i)/10);
           //determining values to be placed at the tick marks
           
           String yAxisValue =  "";
           yAxisValue = String.valueOf(yTickValue);
           g2.drawString(yAxisValue, xTopLeft-60, (int) (yTopLeft+(i*delta_y)));
           //converting to String and placing in the correct position next to the tick
        
           Line2D.Double segment2 = new Line2D.Double((xTopLeft + i*delta_x),(ybase+10),(xTopLeft + i*delta_x),ybase);
           g2.draw(segment2);
           //drawing the tick marks at specified intervals
           
           xTickValue = ((thickness*i)/10);
           //determining values to be placed at the tick marks
           
           String xAxisValue =  "";
           xAxisValue = String.valueOf(xTickValue);
           g2.drawString(xAxisValue, (int) (xTopLeft + i*delta_x),(ybase+30));
           //converting to String and placing in the correct position next to the tick
        }
        
        
        for (int i = 1; i < num_slices+1; i++)
       {
           
           
           x1 = ((i-1)* x_slide) + xTopLeft;
           x2 = (i * x_slide) + xTopLeft;
           //predetermined x coordinates for each point. They remain the same regardless of the method used
           y1Ana = ((numParticles - nAnalytical[i-1])*(y_slide)) + yTopLeft;
           y2Ana = ((numParticles - nAnalytical[i])*(y_slide)) + yTopLeft;
           //calculating the yvalues for Analytical while scaling to the given axes
           y1Eul = ((numParticles - nEuler[i-1])*(y_slide)) + yTopLeft;
           y2Eul = ((numParticles - nEuler[i])*(y_slide)) + yTopLeft;
           //calculating the yvalues for Euler while scaling to the given axes
           y1Mnt = ((numParticles - nMonteCarlo[i-1])*(y_slide)) + yTopLeft;
           y2Mnt = ((numParticles - nMonteCarlo[i])*(y_slide)) + yTopLeft;
           //calculating the yvalues for Monte-Carlo while scaling to the given axes



           
           Line2D.Double analytical = new Line2D.Double(x1,y1Ana,x2,y2Ana);
           g2.setColor(Color.RED);
           g2.draw(analytical);
           //connecting each of the points with a red line
           
           
           Line2D.Double euler = new Line2D.Double(x1,y1Eul,x2,y2Eul);
           g2.setColor(Color.BLUE);
           g2.draw(euler);
           //connecting each of the points with a blue line

           Line2D.Double monteCarlo = new Line2D.Double(x1,y1Mnt,x2,y2Mnt);
           g2.setColor(Color.GREEN);
           g2.draw(monteCarlo);
           //connecting each of the points with a green line
            

       }
        
    }
    
    
}
