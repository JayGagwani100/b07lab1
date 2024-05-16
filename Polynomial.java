public class Polynomial {
    double[] coefficients;
    public Polynomial(){
        this.coefficients = new double[]{0};
    }
    public Polynomial(double[] array){
        this.coefficients = array;
    }
    public Polynomial add(Polynomial argument){
       int minLength = Math.min(this.coefficients.length, argument.coefficients.length);
        double[] resultCoefficients = new double[Math.max(this.coefficients.length, argument.coefficients.length)];
        
        // Add coefficients from both polynomials
        for (int i = 0; i < minLength; i++) {
            resultCoefficients[i] = this.coefficients[i] + argument.coefficients[i];
        }
        
        // Copy remaining coefficients from longer polynomial
        if (this.coefficients.length > minLength) {
            System.arraycopy(this.coefficients, minLength, resultCoefficients, minLength, resultCoefficients.length - minLength);
        } else if (argument.coefficients.length > minLength) {
            System.arraycopy(argument.coefficients, minLength, resultCoefficients, minLength, resultCoefficients.length - minLength);
        }
        
        return new Polynomial(resultCoefficients);
    }
    public double evaluate(double x){
        int length = this.coefficients.length;
        double result = 0;
        for(int i = 0; i < length; i++){
            result += this.coefficients[i] * Math.pow(x, i);
        }
        return result;
    }
    public boolean hasRoot(double root){
        return evaluate(root) == 0;
    }
}
