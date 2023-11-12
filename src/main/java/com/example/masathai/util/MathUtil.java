package com.example.masathai.util;

import java.util.Arrays;

public class MathUtil {
    public static int getPercentage(int score){
        System.out.println();
        return score * 100 / 20;
    }
    
    public static int calculateMean(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return (int) sum / arr.length;
    };

    public static int calculateMedian(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }

        // Sort the array
        Arrays.sort(arr);

        // Check if the array length is odd or even
        int middleIndex = arr.length / 2;
        if (arr.length % 2 == 0) {
            // If even, average the two middle elements
            double middleValue1 = arr[middleIndex - 1];
            double middleValue2 = arr[middleIndex];
            return (int) ((middleValue1 + middleValue2) / 2.0);
        } else {
            // If odd, return the middle element
            return (int) arr[middleIndex];
        }
    };
    public static int calculateStandardDeviation(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }

        // Calculate the mean
        double mean = calculateMean(arr);

        // Calculate the sum of squared differences from the mean
        double sumSquaredDifferences = 0;
        for (int num : arr) {
            sumSquaredDifferences += Math.pow(num - mean, 2);
        }

        // Calculate the variance and then the standard deviation
        double variance = sumSquaredDifferences / arr.length;
        return (int) Math.sqrt(variance);
    }
}
