
package cdf;

import org.apache.commons.math3.distribution.ExponentialDistribution;
/**
 * 
 * @author Barbuzzi
 */

public final class ExponentialDistributionCDF implements CDFInterface{
    private ExponentialDistribution exp;
    
    public ExponentialDistributionCDF(double lambda){
        exp = new ExponentialDistribution(lambda);
    }

   
    @Override
    public double getProbability(double t) {
        return exp.cumulativeProbability(t);
    }   
}
