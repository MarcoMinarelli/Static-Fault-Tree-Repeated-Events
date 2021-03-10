
package stfcdf;

import org.apache.commons.math3.distribution.ExponentialDistribution;

public class ExponentialDistributionCDF implements CDFinterface{
    private ExponentialDistribution exp;
    
    public ExponentialDistributionCDF(double lambda){
        exp = new ExponentialDistribution(lambda);
    }

   
    public double getProbability(double t) {
        return exp.cumulativeProbability(t);
    }
    
    
    
    
}
