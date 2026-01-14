package ClassWork;

public class BubbleSortRecursion {

    static void bubbleSort(int[] arr, int n) {

        if (n == 1)
            return;

        for (int i = 0; i < n - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                int temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }

        bubbleSort(arr, n - 1);
    }

    static void display(int[] arr) {
        System.out.print("{ ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.print("}");
    }

    public static void main(String[] args) {
        int[] arr = {34, 56, 85, 99, 45};
        bubbleSort(arr, arr.length);
        display(arr);
    }
}