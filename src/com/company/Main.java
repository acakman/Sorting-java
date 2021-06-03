package com.company;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int max = 100;
        int elementCnt = 10000;
        int arrayCnt = 10000;
        Random rnd = new Random();
        int[] mergeArray = new int[elementCnt];
        int[] quickArray = new int[elementCnt];
        int[] insertionArray = new int[elementCnt];
        long mergeStartTime, mergeEndTime, quickStartTime, quickEndTime, insertionStartTime, insertionEndTime, mergeTime = 0, quickTime = 0, insertionTime = 0;
        for (int j = 0; j < arrayCnt; j++) {
            for (int i = 0; i < elementCnt; i++) {
                mergeArray[i] = rnd.nextInt(max + 1);
            }
            System.arraycopy(mergeArray, 0, quickArray, 0, 30);
            System.arraycopy(mergeArray, 0, insertionArray, 0, 30);
            mergeStartTime = System.nanoTime();
            mergeSort(mergeArray);
            mergeEndTime = System.nanoTime();
            mergeTime += mergeEndTime- mergeStartTime;
            //System.out.println("Array before quick sort:" + Arrays.toString(quickArray));
            quickStartTime = System.nanoTime();
            quickSort(quickArray, 0 , quickArray.length - 1);
            quickEndTime = System.nanoTime();
            quickTime += quickEndTime - quickStartTime;
            //System.out.println("Array after quick sort:" + Arrays.toString(quickArray));
            insertionStartTime = System.nanoTime();
            insertionSort(insertionArray);
            insertionEndTime = System.nanoTime();
            insertionTime += insertionEndTime - insertionStartTime;
        }
        mergeTime /= arrayCnt;
        quickTime /= arrayCnt;
        insertionTime /= arrayCnt;
        double ratio = (double) insertionTime / quickTime;
        System.out.println("Avg. run time of merge sort : " + mergeTime + " ns");
        System.out.println("Avg. run time of quick sort : " + quickTime + " ns");
        System.out.println("Avg. run time of insertion sort : " + insertionTime + " ns");
        System.out.println("Avg. run time of insertion sort / avg. run time of quick sort : " + ratio);
        int[] input = {1, 4, 1, 2, 7, 5, 2};
        countingSort(input, 7);
    }

    public static void mergeSort(int[] arr) {
        //System.out.println("Array before merge sort: " + Arrays.toString(arr));
        int [] cpArr = new int[arr.length];
        mergeSort(arr, cpArr, 0 , arr.length - 1);
        //System.out.println("Array after merge sort " + Arrays.toString(arr));
    }

    public static void mergeSort(int[] arr, int[] cpArr, int begin, int end) {
        if (begin < end) {
            int center = (begin + end) / 2;
            mergeSort(arr, cpArr, begin, center);
            mergeSort(arr, cpArr, center + 1, end);
            merge(arr, cpArr, begin, center + 1, end);
        }
    }

    public static void merge(int[] arr, int[] cpArr, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int tempPos = leftPos; // Initially same with left array's first index
        int elementCnt = rightEnd - leftPos + 1;
        while (leftPos <= leftEnd && rightPos <= rightEnd) { // Compare left side and right side of array and sort the cpArr accordingly
            if (arr[leftPos] <= arr[rightPos]) {
                cpArr[tempPos++] = arr[leftPos++];
            }
            else {
                cpArr[tempPos++] = arr[rightPos++];
            }
        }
        // Only one of the remaining loops can be executed
        while (leftPos <= leftEnd) // Copy remainder of the left side
            cpArr[tempPos++] = arr[leftPos++];
        while (rightPos <= rightEnd) // Copy remainder of the right side
            cpArr[tempPos++] = arr[rightPos++];
        for (int i = 0; i < elementCnt; i++, rightEnd--) // Copy the sorted part of the cpArr to arr
            arr[rightEnd] = cpArr[rightEnd];
    }

    public static void quickSort(int[] array, int start, int end) {
        if (start < end) {
            int pivotIndex = partition(array, start, end);
            quickSort(array, start, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, end);
        }
    }

    public static int pickPivot(int[] arr, int start, int end){
        Random rand = new Random();
        return rand.nextInt(end - start + 1) + start;
    }

    public static int partition(int[] arr, int start, int end) {
        //Swap randomly selected pivot with last element of array
        int pivotIndex = pickPivot(arr, start, end);
        int temp = arr[end];
        arr[end] = arr[pivotIndex];
        arr[pivotIndex] = temp;
        int pivot = arr[end];
        //System.out.println("Pivot is: " + pivot);
        //Create two sub arrays greater than pivot and smaller than pivot.
        int i = start;
        int j = start;
        for (; j < end; j++){
            if (arr[j] < pivot) {
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
            }
        }
        temp = arr[end];
        arr[end] = arr[i];
        arr[i] = temp;
        //System.out.println("Array after partition: " + Arrays.toString(arr));
        return i;
    }

    public static void insertionSort(int[] array) {
        //System.out.println("Array before insertion sort: " + Arrays.toString(array));
        int temp;
        for (int i = 1; i < array.length; i++)
        {
            for (int j = i; j > 0; j--)
            {
                if (array[j] < array[j - 1])
                {
                    temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }
        //System.out.println("Array after insertion sort: " + Arrays.toString(array));
    }

    public static void countingSort(int[] arr, int max){
        int[] countingArr = new int[max + 1];
        int[] sortedArr = new int[arr.length];
        System.out.println("Array before counting sort: " + Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            countingArr[arr[i]]++;
        }
        System.out.println("Counting array is: " + Arrays.toString(countingArr));
        for (int i = 0; i < countingArr.length; i++) {
            if (i != 0) {
                countingArr[i] += countingArr[i - 1];
            }
        }
        System.out.println("Counting array after sum of previous counts: " + Arrays.toString(countingArr));
        for (int i = 0; i < arr.length; i++) {
            sortedArr[countingArr[arr[i]]--] = arr[i];
        }
        System.out.println("Array after counting sort: " + Arrays.toString(sortedArr));
        //return sortedArr;
    }
}
