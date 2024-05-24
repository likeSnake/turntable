package net.nice.turntable.activity;

import java.util.ArrayList;

public class MyTest {

        public static void main(String[] args) {
            int[] numbers = {10, 25, 5, 15, 30};

            int[] maxAndSecondMax = findMax(numbers);

            System.out.println("最大: " + maxAndSecondMax[0]);
            System.out.println("第二大: " + maxAndSecondMax[1]);
        }


    public static int[] findMax(int[] numbers) {
        int max = numbers[0];
        int Max2 = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                Max2 = max;
                max = numbers[i];
            } else if (numbers[i] > Max2) {
                Max2 = numbers[i];
            }
        }

        int[] result = {max, Max2};
        return result;
    }

}
