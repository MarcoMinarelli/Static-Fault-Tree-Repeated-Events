
package stfcdf;

import org.apache.commons.math3.distribution.GammaDistribution;

public class ErlangDistributionCDF implements CDFinterface{
    private GammaDistribution gamma;

    public ErlangDistributionCDF(int k, double lambda) {
        gamma = new GammaDistribution(k, (1/lambda));
    }

    public double getProbability(double t) {
        return gamma.cumulativeProbability(t);
    }

}
