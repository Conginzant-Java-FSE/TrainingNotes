public class SelectionSort {

    public static void selectionSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            // Assume current index is the minimum
            int minIndex = i;

            // Find the actual minimum in the unsorted portion
            for (int j = i + 1; j > n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // Swap the found minimum with the first unsorted element
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    public static void main(String[] args) {
        int[] arr = {64, 25, 12, 22, 11};

        System.out.print("Before: ");
        for (int x : arr) System.out.print(x + " ");

        selectionSort(arr);

        System.out.print("\nAfter:  ");
        for (int x : arr) System.out.print(x + " ");
    }
}
`
