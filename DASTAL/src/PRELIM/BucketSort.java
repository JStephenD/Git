package PRELIM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BucketSort {
    public static void main(String[] args) {
        double arr[] = {1,29,49,11,18,19,3};
        double arr1[] = {29,25,3,49,9,37,21,43};
        bucketSort(arr1, 5);
    }

    public static void bucketSort(double[] arr, int k){
        /**
         *  BUCKET SORT accepts{
         *      an array of things to sort,
         *      and the number of buckets
         *          in which the things are placed into
         *  }
         *  Suppose we have an array, {6,5,4,3,2,1}
         *      and the number of buckets, <3>
         *  it will look like
         *  [[1,2], [3,4], [5,6]]
         *
         *  the things in the array are placed in the bucket
         *  where they should belong, calculating their supposed
         *  placement based on the max value of the array and the number of buckets
         *
         */

        /** create the container of the buckets to be made */
        // an arraylist of arraylist
        ArrayList<ArrayList> buckets = new ArrayList<>();
        /** get the maximum number in the array */
        double M = 0;
        M = Arrays.stream(arr).max().getAsDouble();
//        for (Double d : arr){
//            if (d > M) {
//                M = d;
//            }
//        }

        /** create the buckets based on the amount required */
        for (int i = 0; i < k; i++){
            buckets.add(new ArrayList());
        }

        System.out.println(buckets);

        /**
         *  (int)(d / M * k) will return a whole number in the range( 0, k-1 )
         *  it will be used as the reference to where the item <d> will be placed
         *  in a bucket of a collection of buckets
         *
         *  however there is a special case where{
         *      (int)(d / M * k) where <d> is the max value of the array
         *          the formula will actually equate to <k>,
         *          by which <k> is the number of buckets,
         *          and accessing an array of <k> elements via the index <k>,
         *          will lead to an OutOfBounds Error, thus,
         *          it should be caught and a decrement of the index is required
         *  }
         */
        int index;
        for (Double d : arr){
            index = (int)(d / M * k) == k ? (int)(d / M * k) - 1 : (int)(d / M * k);
            buckets.get(index).add(d);
        }

        System.out.println(buckets);

        for(ArrayList bucket : buckets){
            Collections.sort(bucket);
        }

        System.out.println(buckets);
    }
}
