
package cdf;

import org.apache.commons.math3.distribution.GammaDistribution;

/**
 * 
 * @author Barbuzzi
 */


public final class ErlangDistributionCDF implements CDFInterface{
    private GammaDistribution gamma;

    public ErlangDistributionCDF(int k, double lambda) {
        gamma = new GammaDistribution(k, (1/lambda));
    }

    @Override
    public double getProbability(double t) {
        return gamma.cumulativeProbability(t);
    }
}
