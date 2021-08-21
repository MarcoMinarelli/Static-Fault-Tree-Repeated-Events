
package cdf;

/**
 *
 * @author Minarelli
 */
public class ConstantDistribution implements CDFInterface{
    private double value;

    public ConstantDistribution(double value) {
        this.value = value;
    }

    @Override
    public double getProbability(double t) {
        return value;
    }
}
