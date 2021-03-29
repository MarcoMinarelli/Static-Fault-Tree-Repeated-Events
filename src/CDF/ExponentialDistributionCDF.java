
package CDF;

import org.apache.commons.math3.distribution.ExponentialDistribution;

public final class ExponentialDistributionCDF implements CDFInterface{
    private ExponentialDistribution exp;
    
    public ExponentialDistributionCDF(double lambda){
        exp = new ExponentialDistribution(lambda);
    }

   
    public double getProbability(double t) {
        return exp.cumulativeProbability(t);
    }
    
    
    
    
}
