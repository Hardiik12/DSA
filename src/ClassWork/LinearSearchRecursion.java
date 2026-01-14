package ClassWork;

import java.util.Scanner;

public class LinearSearchRecursion {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] a = new int[5];

        System.out.println("Enter key:");
        int key = sc.nextInt();

        System.out.println("Enter 5 elements:");
        for (int i = 0; i < a.length; i++) {
            a[i] = sc.nextInt();
        }
        int pos = linearSearch(a, key, 0); // recursive call

        if (pos != -1) {
            System.out.println("Element found at index: " + pos);
        } else {
            System.out.println("Element not found");
        }
        sc.close();
    }
    static int linearSearch(int[] arr, int key, int index) {

        if (index == arr.length) {
            return -1;
        }
        if (arr[index] == key) {
            return index;
        }
        return linearSearch(arr, key, index + 1);
    }
}
