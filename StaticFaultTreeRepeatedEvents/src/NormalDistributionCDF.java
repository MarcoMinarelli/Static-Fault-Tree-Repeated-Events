
package stfcdf;

import org.apache.commons.math3.distribution.NormalDistribution;

public class NormalDistributionCDF implements CDFinterface{
    private NormalDistribution normal;

    public NormalDistributionCDF(double mu, double sigma) {
        normal = new NormalDistribution(mu, sigma);
    }

    public double getProbability(double t) {
        return normal.cumulativeProbability(t);
    }
}
