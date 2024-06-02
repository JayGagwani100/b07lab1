// Online Java Compiler
// Use this editor to write, compile and run your Java code online
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
public class Polynomial {
    double[] coefficients;
    int[] exponents;
    public Polynomial(){
        this.coefficients = new double[]{};
    	this.exponents = new int[]{0};
    }
    public Polynomial(double[] coeff, int[] exp){
        this.coefficients = coeff;
	    this.exponents = exp;
    }
  public Polynomial filterZeroCoefficients(double[] coeff, int[] exp) {
    // Count the number of non-zero coefficients
    int nonZeroCount = 0;
    int len = coeff.length;
    for (int i = 0; i < len; i++) {
        if (coeff[i] != 0.0) {
            nonZeroCount++;
        }
    }
    double[] updatedcoef = new double[nonZeroCount];
    int[] updatedexp = new int[nonZeroCount];
    int j = 0;
    for (int i = 0; i < len; i++) {
        if(coeff[i] != 0.0){
            updatedcoef[j] = coeff[i];
            updatedexp[j] = exp[i];
            j++;
        }
    }
    return new Polynomial(updatedcoef, updatedexp);
  }
    public Polynomial add(Polynomial Other){
    	int thisArrayLen = this.coefficients.length;
    	int otherLen = Other.coefficients.length;
	    double[] combinedCoeff = new double[thisArrayLen + otherLen];
	    int[] combinedexpo = new int[thisArrayLen + otherLen];
	    System.arraycopy(this.coefficients, 0, combinedCoeff, 0, thisArrayLen);
	    System.arraycopy(Other.coefficients, 0, combinedCoeff, thisArrayLen, otherLen);
	    System.arraycopy(this.exponents, 0, combinedexpo, 0, thisArrayLen);
	    System.arraycopy(Other.exponents, 0, combinedexpo, thisArrayLen, otherLen);
	    for(int i = 0; i < thisArrayLen + otherLen; i++){
	        for(int j = i + 1; j < thisArrayLen + otherLen; j++){
	            if(combinedexpo[i] == combinedexpo[j]){
	                combinedCoeff[i] += combinedCoeff[j];
	                combinedexpo[j] = 0;
	                combinedCoeff[j] = 0;
	            }
	        }
	    }
	    return filterZeroCoefficients(combinedCoeff, combinedexpo);
    }
    public double evaluate(double x){
        int length = this.coefficients.length;
        double result = 0;
        for(int i = 0; i < length; i++){
            result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }
        return result;
    }
    public boolean hasRoot(double root){
        return evaluate(root) == 0;
    }
    public Polynomial multiply(Polynomial Other){
	int thisArrayLen = this.coefficients.length;
    	int otherLen = Other.coefficients.length;
	int z = 0;
	double[] initialMultiCoeff = new double[thisArrayLen*otherLen];
	int[] initialMultiExpo = new int[thisArrayLen*otherLen];
	for(int i = 0; i < thisArrayLen; i++){
	for(int j = 0; j < otherLen; j++){
	initialMultiCoeff[z] = this.coefficients[i]*Other.coefficients[j];
	initialMultiExpo[z] = this.exponents[i] + Other.exponents[j];
	z++;
	}
	}

	for(int i = 0; i < thisArrayLen * otherLen; i++){
	        for(int j = i + 1; j < thisArrayLen * otherLen; j++){
	            if(initialMultiExpo[i] == initialMultiExpo[j]){
	                initialMultiCoeff[i] += initialMultiCoeff[j];
	                initialMultiExpo[j] = 0;
	                initialMultiCoeff[j] = 0;
	            }
	        }
	    }
	    return filterZeroCoefficients(initialMultiCoeff, initialMultiExpo);
    }
public Polynomial(File file) throws FileNotFoundException {
    Scanner scanner = new Scanner(file);
    String polynomialString = scanner.nextLine().trim();
    scanner.close();

    List<Double> coeffList = new ArrayList<>();
    List<Integer> expList = new ArrayList<>();

    // Use a regular expression to match coefficients and exponents
    String regex = "([-+]?\\d*\\.?\\d*)(?:x(?:\\^(\\d+))?)?";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(polynomialString);

    while (matcher.find()) {
        String coefficientStr = matcher.group(1);
        String exponentStr = matcher.group(2);

        double coefficient = coefficientStr == null || coefficientStr.isEmpty() ? 1 : Double.parseDouble(coefficientStr);
        int exponent = exponentStr == null || exponentStr.isEmpty() ? 0 : Integer.parseInt(exponentStr);

        coeffList.add(coefficient);
        expList.add(exponent);
    }

    // Convert lists to arrays
    this.coefficients = new double[coeffList.size()];
    this.exponents = new int[expList.size()];

    for (int i = 0; i < coeffList.size(); i++) {
        this.coefficients[i] = coeffList.get(i);
        this.exponents[i] = expList.get(i);
    }
}

public void saveToFile(String fileName) throws IOException {
    FileWriter writer = new FileWriter(fileName);
    for (int i = 0; i < coefficients.length; i++) {
        if (coefficients[i] != 0) {
            if (coefficients[i] > 0 && i != 0) {
                writer.write("+");  // Add plus sign for positive coefficients (except the first term)
            }
            if (coefficients[i] < 0) {
                writer.write("-");  // Write minus sign for negative coefficients
            }
            if (Math.abs(coefficients[i]) != 1 || exponents[i] == 0) {
                writer.write(String.valueOf(Math.abs(coefficients[i])));
            }
            if (exponents[i] != 0) {
                writer.write("x");
                if (exponents[i] != 1) {
                    writer.write(String.valueOf(exponents[i]));  // Write exponent if it's not 1
                }
            }
        }
    }
    writer.close();
}
}