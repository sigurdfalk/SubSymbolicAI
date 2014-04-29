package utilities;

import java.util.Random;

/**
 * User: sigurd
 * Date: 31.03.14
 * Time: 18:23
 */
public class Numbers {

    public static final double clampDouble(double value, double min, double max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }

        return value;
    }

    public static double getRandomDouble(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }

    public static int getRandomInteger(int min, int max) {
        Random random = new Random();
        return min + random.nextInt(max - min);
    }

    public static void sleepThread(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
