package com.example.masathai.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    }

    public static int calculateMode(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array is null or empty");
        }

        Map<Integer, Integer> frequencyMap = new HashMap<>();

        // Count the frequency of each number
        for (int number : numbers) {
            frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) + 1);
        }

        // Find the mode (number with the highest frequency)
        int mode = 0;
        int maxFrequency = 0;

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int currentNumber = entry.getKey();
            int currentFrequency = entry.getValue();

            if (currentFrequency > maxFrequency) {
                maxFrequency = currentFrequency;
                mode = currentNumber;
            }
        }

        return mode;
    }

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

    public static int getAge(LocalDate dob){
        LocalDate curDate = LocalDate.now();
        return Period.between(dob, curDate).getYears();
    }
}
