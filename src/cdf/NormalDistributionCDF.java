
package cdf;

import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * 
 * @author Barbuzzi
 */

public final class NormalDistributionCDF implements CDFInterface{
    private NormalDistribution normal;

    public NormalDistributionCDF(double mu, double sigma) {
        normal = new NormalDistribution(mu, sigma);
    }

    public double getProbability(double t) {
        return normal.cumulativeProbability(t);
    }
}
