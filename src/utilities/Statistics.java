package utilities;

/**
 * User: sigurd
 * Date: 28.03.14
 * Time: 14:30
 */
public class Statistics {
    public static double getMean(double[] values) {
        double total = 0.0;

        for (double value : values) {
            total += value;
        }

        return total / (double) values.length;
    }

    public static double getVariance(double[] values) {
        double mean = getMean(values);
        double temp = 0;

        for (double value : values) {
            temp += Math.pow(value - mean, 2);
        }

        return temp / (double) values.length;
    }

    public static double getStandardDeviation(double[] values) {
        return Math.sqrt(getVariance(values));
    }

    public static double getSum(double[] values) {
        double sum = 0.0;

        for (double value : values) {
            sum += value;
        }

        return sum;
    }
}
