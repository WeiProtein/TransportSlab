/*
 * Timothy Koh
This program deals with radiation transport where particles move through a medium.
Various methods are used to approximate the number of particles that are able to move 
/through the material
8/22/14
*/

package transportslab;

import java.util.Random;
//import random class package

/**
 *
 * @author Wei-Ming
 */
public class TransportSlab {

    private double No;
    private int num_slices;
    private double sigma_t;
    private double T;
    private double delta_x;
    //the above 5 variables are used in the constructor
    private double delta_N;
    private double temp_N;
    double[] n_a;
    double[] n_e;
    double[] n_m;
    //the above 3 arrays will need to be called in other methods
    private double a;
    private double bc;
    private double d;
    private double b1_a;
    private double b2_a;
    private double b1_e;
    private double b2_e;
    private double b2_m;
    private double b1_m;
    //instance variables to be used in various methods
    
    // @param double initpart, double thickness, double sigmaT, int numberslices
    // @return void
    public TransportSlab (double initpart, double thickness, double sigmaT, int numberslices)
    {
        No = initpart;
        T = thickness;
        sigma_t = sigmaT;
        num_slices = numberslices;
        //the inputs in this constructor need to be globalized to be called in other methods
        delta_x = (double) T/num_slices;
        System.out.println("particles: " + No);
        System.out.println("thickness: " + T);
        System.out.println("sigma_T: " + sigma_t);
        System.out.println("number of slices: " + num_slices);
        // declaring numbers for reference to the user
    }
    
    // @param void
    // @return n_a
    public double[] analytical(){
        n_a = new double[num_slices + 1];
        //create new array to store number of values that survive
        for (int i = 0; i < n_a.length; i++)
        {
            n_a[i] = (double) (No*Math.exp(-sigma_t*delta_x*i));
            //analytic formula for number of particles that survive in each section
        }
      
        return n_a;
        //returns the number of particles that survive in each section
    }
    
    // @param void
    // @return n_e
    public double[] euler (){
        n_e = new double[num_slices + 1];
        //create new array to store number of values that survive
        n_e[0] = No;
        //declare initial value before iterating using below formula
        for (int i = 1; i < n_e.length; i++)
        {
            delta_N = (double) (-n_e[i-1]*sigma_t*delta_x);
            //euler formula for number of particles that survive each section
            temp_N = n_e[i-1] + delta_N;
            n_e[i] = temp_N;
            //store the values in the array
            
        }
  
        return n_e;
        //returns the number of particles that survive in each section
    }
    
    // @param void
    // @return n_m
    public double[] monteCarlo(){
        n_m = new double[num_slices + 1];
        //create new array to store number of values that survive
        n_m[0] = No;
        //declare initial value before iterating using below formula
        double probability = sigma_t*delta_x;
        //probability value of particle interacting
        double num_particles = No;
        // creating variable to keep count of number of values that survive
        for (int i = 1; i < num_slices + 1; i++)
            //iterates through the sections
        {
            for (int j = 0; j < num_particles; j++)
                //iterates through the number of particles
            {
                Random rand = new Random();
                double collide = rand.nextDouble();
                //generates random numbers to compare to probability value
                if (collide <= probability)
                {
                    num_particles = num_particles - 1;
                    //if particle collides take it out of the total number of particles
                }              
            }
            n_m[i] = num_particles;
            //store the value in the array for each section
        }
        
        return n_m;
        //returns the number of particles that survive in each section
    }
    
    
    public void leastSquaresSigma(){
        a = num_slices + 1;
        //element a in the A matrix
                
        for (int i = 0;  i < num_slices + 1; i++)
            {
                bc = bc + delta_x*i; 
                //elements b and c in the A matrix
                d = d + (delta_x*i)*(delta_x*i);
                //element d in the A matrix
                b1_a = b1_a + Math.log(n_a[i]);
                //1st element in the B matrix for analytic data points              
                b2_a = b2_a + ((delta_x*i)*Math.log(n_a[i]));
                //2nd element in the B matrix for analytic data points
                b1_e = b1_e + Math.log(n_e[i]);
                //1st element in the B matrix for euler data points               
                b2_e = b2_e + ((delta_x*i)*Math.log(n_e[i]));
                //2nd element in the B matrix for euler data points
                b1_m = b1_m + Math.log(n_m[i]);
                //1st element in the B matrix for monte carlo data points               
                b2_m = b2_m + ((delta_x*i)*Math.log(n_m[i]));
                //2nd element in the B matrix for monte carlo data points
            }
        
        System.out.println("Sigma t Analytical: " + -((-bc/((a*d)-(bc*bc)))*(b1_a) + (a/((a*d)-(bc*bc)))*b2_a));
        System.out.println("Sigma t Euler: " + -((-bc/((a*d)-(bc*bc)))*(b1_e) + (a/((a*d)-(bc*bc)))*b2_e));
        System.out.println("Sigma t Monte Carlo: " + -((-bc/((a*d)-(bc*bc)))*(b1_m) + (a/((a*d)-(bc*bc)))*b2_m));
        //above 3 formulas calculate for the 2nd element in the X matrix which is the sigma t value.
        //the value itself is negative and must be negated to give the proper sigma t value.
        //the inverse of the A matrix is taken into account in the above math.
        
    }
    
}
    