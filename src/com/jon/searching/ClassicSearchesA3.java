package com.jon.searching;

import java.util.Arrays;
import java.util.Random;

public class ClassicSearchesA3 {

	private static int ops = 0;
	private static int dataSet[];

	private static int linearSearchOrdered(int[] arr, int key) {
		int n = arr.length;
		ops = 0;

		for (int i = 0; i < n; i++) {
			ops++;
			if (arr[i] == key) {
				return ops;
			} else if (arr[i] > key) {
				return ops;
			}
		}

		return ops;
	}

	private static int binarySearch(int arr[], int key) {
		int start = 0;
		int end = arr.length - 1;
		ops = 0;

		while (start <= end) {
			ops++;
			int mid = (start + end) / 2;
			if (arr[mid] == key) {
				return ops;
			} else if (arr[mid] < key) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}

		return ops;
	}

	private static int interpolationSearch(int[] arr, int key) {
		int low = 0;
		int high = arr.length - 1;
		ops = 0;

		while (low <= high && key >= arr[low] && key <= arr[high]) {
			ops++;
			int index = low + (((key - arr[low]) * (high - low)) / (arr[high] - arr[low]));

			if (key == arr[index]) {
				return ops;
			}

			if (key < arr[index]) {
				high = index - 1;
			} else {
				low = index + 1;
			}
		}

		return ops;
	}

	private static void searchResult(String type, int key, int index) {
		if (index != -1) {
			System.out.println(type + ": Found " + key + " at index " + index + " in " + ops + " operations");
		} else {
			System.out.println(type + ": Did not find " + key + " in " + ops + " operations");
		}
	}

	private static void printArray(int arr[]) {
		int n = arr.length;

		for (int i = 0; i < n; i++) {
			System.out.print(arr[i] + " ");
		}

		System.out.println();
	}

	private static Integer generateRandom(boolean check) {
		int value = new Random().nextInt(10001 - 1) + 1;
		if (check) {
			if (Arrays.stream(dataSet).anyMatch(d -> d == value)) {
				generateRandom(true);
			}
		}
		return value;
	}

	private static int[] collectRandoms(boolean check) {
		int[] data = new int[1000];

		for (int i = 0; i < data.length; i++) {
			data[i] = (generateRandom(check));
		}

		return data;
	}

	public static void main(String[] args) {
		// Tests cases
		int index;
		int key;
		int[] nums = { 15, 98, 7, 22, 9, 61, 57 };

		Arrays.sort(nums);
		printArray(nums);
		key = 61;
		searchResult("Linear", key, linearSearchOrdered(nums, key));
		searchResult("Binary", key, binarySearch(nums, key));
		searchResult("Interpolation", key, interpolationSearch(nums, key));
		System.out.println();

		int[] linearOperations = new int[1000];
		double[] linearAverages = new double[100];

		int[] binaryOperations = new int[1000];
		double[] binaryAverages = new double[100];

		int[] interpolationOperations = new int[1000];
		double[] interpolationAverages = new double[100];

		for (int i = 0; i < 100; i++) { // Contained averages
			dataSet = collectRandoms(false);
			Arrays.sort(dataSet);

			for (int n = 0; n < dataSet.length; n++) {
				linearOperations[n] = linearSearchOrdered(dataSet, dataSet[n]);
				binaryOperations[n] = binarySearch(dataSet, dataSet[n]);
				interpolationOperations[n] = interpolationSearch(dataSet, dataSet[n]);
			}

			linearAverages[i] = Arrays.stream(linearOperations).average().getAsDouble();
			binaryAverages[i] = Arrays.stream(binaryOperations).average().getAsDouble();
			interpolationAverages[i] = Arrays.stream(interpolationOperations).average().getAsDouble();
		}

		System.out.println("The following averages are for operations where the generated number was in the dataset");
		System.out.println("LINEAR AVERAGE " + Arrays.stream(linearAverages).average().getAsDouble());
		System.out.println("BINARY AVERAGE " + Arrays.stream(binaryAverages).average().getAsDouble());
		System.out.println("INTERPOLATION AVERAGE " + Arrays.stream(interpolationAverages).average().getAsDouble() + "\n");

		for (int i = 0; i < 100; i++) { // Non contained averages
			dataSet = collectRandoms(true);
			Arrays.sort(dataSet);

			for (int n = 0; n < dataSet.length; n++) {
				linearOperations[n] = linearSearchOrdered(dataSet, dataSet[n]);
				binaryOperations[n] = binarySearch(dataSet, dataSet[n]);
				interpolationOperations[n] = interpolationSearch(dataSet, dataSet[n]);
			}

			linearAverages[i] = Arrays.stream(linearOperations).average().getAsDouble();
			binaryAverages[i] = Arrays.stream(binaryOperations).average().getAsDouble();
			interpolationAverages[i] = Arrays.stream(interpolationOperations).average().getAsDouble();
		}

		System.out.println("The following averages are for operations where the generated number was not in the dataset");
		System.out.println("LINEAR AVERAGE " + Arrays.stream(linearAverages).average().getAsDouble());
		System.out.println("BINARY AVERAGE " + Arrays.stream(binaryAverages).average().getAsDouble());
		System.out.println("INTERPOLATION AVERAGE " + Arrays.stream(interpolationAverages).average().getAsDouble());

	}

}
