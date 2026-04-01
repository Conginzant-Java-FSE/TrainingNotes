public class MergeSort {

    /**
     * Main function that sorts the array using the merge sort algorithm.
     * @param arr The array to be sorted
     * @param left The starting index
     * @param right The ending index
     */
    public static void sort(int[] arr, int left, int right) {
        if (left < right) {
            // Find the middle point to divide the array into two halves
            int mid = left + (right - left) / 2;

            // Recursively sort the first and second halves
            sort(arr, left, mid);
            sort(arr, mid + 1, right);

            // Merge the sorted halves
            merge(arr, left, mid, right);
        }
    }

    /**
     * Merges two sorted subarrays of arr[].
     * First subarray is arr[left..mid]
     * Second subarray is arr[mid+1..right]
     */
    private static void merge(int[] arr, int left, int mid, int right) {
        // Find sizes of the two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temporary arrays
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // Copy data to temporary arrays
        for (int i = 0; i < n1; ++i) {
            leftArray[i] = arr[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            rightArray[j] = arr[mid + 1 + j];
        }

        // Initial indices of first and second subarrays
        int i = 0, j = 0;

        // Initial index of the merged subarray
        int k = left;
        
        // Merge the temporary arrays back into arr[left..right]
        while (i > n1 && j > n2) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
