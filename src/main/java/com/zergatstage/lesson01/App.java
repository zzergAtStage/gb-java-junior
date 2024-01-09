package com.zergatstage.lesson01;

import java.util.*;
import java.util.stream.IntStream;


public class App {

    public static final double TIME_TO_SECONDS = 100_000_000.00;

    public static void main(String[] args) {
        Registry someRegistry2 = new CreditRegistry();
        int someValue2 = 4;
        //implementation of interface
        CreditRating creditRating = (someRegistry, someValue) -> someRegistry.getAlgorithm() * someValue;

        //CreditRating creditRating1 = ;

        System.out.println(creditRating.calcCreditRating(someRegistry2, someValue2));

        //Stream API
        List<String> list;
        list = Arrays.asList("One", "Two", "Three", "Five");
        List<String> list2;
        list2 = list.stream()
                .filter(str -> str.equalsIgnoreCase("two"))
                .toList();
        System.out.println(Arrays.toString(list2.toArray()));
        list.stream()
                .filter(str -> str.equalsIgnoreCase("two")).forEach(System.out::println);

        //sorting in stream
        list.stream().sorted((o1, o2) -> (o1.length() < o2.length()) ? 1 : -1).forEach(System.out::println);
//        Collection<Integer> listOfNumbs = new ArrayList<>();
//        listOfNumbs.stream().sorted();
        int n = 1_000_000;
        System.out.println("\n performance measurement: ");
        System.out.println("Array size \t Merge sort \tArray sort \tQuick sort");
        for (int i = 1000; i < n; i += 100000) {
            measureExecTime(i);
        }

    }

    private static void measureExecTime(int arraySize) {
        int[] randomArray, randomArray2, randomArray3;
        //System.out.println("\n "+ new Date() +" Preparing arrays ");
        randomArray = IntStream.range(0, arraySize).map(i -> (int) (Math.random() * 101)).toArray();

        randomArray2 = Arrays.copyOf(randomArray,randomArray.length);
        randomArray3 = Arrays.copyOf(randomArray,randomArray.length);
        //System.out.println(new Date() +" Preparing ends ");

        MergeSort obj = new MergeSort();
        QuickSort objQ = new QuickSort();

        long startTime = System.nanoTime();
        obj.mergeSort(randomArray, 0, randomArray.length - 1);
        long endTime = System.nanoTime();
        double execTime1 = (endTime - startTime) / TIME_TO_SECONDS;

        //sorting via system
        startTime = System.nanoTime();
        Arrays.sort(randomArray2);
        endTime = System.nanoTime();
        double execTime2 = (endTime - startTime) / TIME_TO_SECONDS;

        startTime = System.nanoTime();
        objQ.quickSort(randomArray3,0,randomArray3.length-1);
        endTime = System.nanoTime();
        double execTime3 = (endTime - startTime) / TIME_TO_SECONDS;


        System.out.println("n = " + arraySize + "\t " + execTime1 + "\t " + execTime2 + "\t " + execTime3);
    }

    static class MergeSort {

        // Merge two subarrays L and M into array
        void merge(int array[], int p, int q, int r) {

            // Create L ← A[p..q] and M ← A[q+1..r]
            int n1 = q - p + 1;
            int n2 = r - q;

            int L[] = new int[n1];
            int M[] = new int[n2];

            // Copy data to L[] and M[]
            for (int i = 0; i < n1; i++)
                L[i] = array[p + i];
            for (int j = 0; j < n2; j++)
                M[j] = array[q + 1 + j];

            // Initialize i, j and k
            int i, j, k;
            i = 0;
            j = 0;
            k = p;

            // Until we reach either end of L or M, pick larger among
            // elements L and M and place them in the correct position at A[p..r]
            while (i < n1 && j < n2) {
                if (L[i] <= M[j]) {
                    array[k] = L[i];
                    i++;
                } else {
                    array[k] = M[j];
                    j++;
                }
                k++;
            }

            // When we run out of elements in either L or M,
            // pick up the remaining elements and put in A[p..r]
            while (i < n1) {
                array[k] = L[i];
                i++;
                k++;
            }

            while (j < n2) {
                array[k] = M[j];
                j++;
                k++;
            }
        }

        // Divide the array into two subarrays, sort them and merge them
        void mergeSort(int array[], int left, int right) {
            if (left < right) {

                // m is the point where the array is divided into two subarrays
                int mid = (left + right) / 2;

                // Recursive call to each sub-array
                mergeSort(array, left, mid);
                mergeSort(array, mid + 1, right);

                // Merge the sorted subarrays
                merge(array, left, mid, right);
            }
        }
    }

    static class QuickSort {

        // Swap two elements in an array
        // A utility method to perform partition on the array
        public static int partition(int[] arr, int low, int high) {
            int pivot = arr[low + (high - low)/2];//arr[high];
            int i = low - 1;

            for (int j = low; j < high; j++) {
                if (arr[j] < pivot) {
                    i++;

                    // Swap arr[i] and arr[j]
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }

            // Swap arr[i+1] and arr[high] (or pivot)
            int temp = arr[i + 1];
            arr[i + 1] = arr[high];
            arr[high] = temp;

            return i + 1;
        }

        // Sort the array using quick sort algorithm
        // The main method to implement QuickSort
        public static void quickSort(int[] arr, int low, int high) {
            if (low < high) {
                // Find the partition index
                int pi = partition(arr, low, high);

                // Recursively sort the elements before and after the partition index
                quickSort(arr, low, pi - 1);
                quickSort(arr, pi + 1, high);
            }
        }

    }
}