
package stfcdf;
       
public class UniformDistributionCDF implements CDFinterface{
    double a; //start period ---- private?
    double b; //end period
    
    public UniformDistributionCDF(double a, double b) {
        this.a = a;
        this.b = b;
        if(a>b){
            throw new IllegalArgumentException("Invalid range: b<a");
        }
    }
    
    public double getProbability(double t){
        double cdf = 0;
        if(t>a && t<b){
            cdf = (t-a) / (b-a); //a<t<b
        }else
            if(t>b){
                cdf = 1;
            }    
        return cdf;
    }
}    
