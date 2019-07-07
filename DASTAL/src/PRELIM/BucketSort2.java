package PRELIM;

import java.util.ArrayList;

public class BucketSort2 {
    public static void main(String[] args) {
        ArrayList arr = new ArrayList();
        arr.add(1);
        arr.add(100);
        arr.add(8);
        arr.add(23);
        arr.add(34);
        arr.add(456);
        System.out.println("final result " + bucketSort(arr));
    }

    public static ArrayList bucketSort(ArrayList arr){
        return bucketSort(arr, 0);
    }

    private static ArrayList bucketSort(ArrayList arr, int index){
        ArrayList<ArrayList<String>> buckets = new ArrayList<>();
        ArrayList result = new ArrayList();

        Object obj;
        for (int i = 0; i < arr.size(); i++){
            obj = arr.get(i);
            arr.remove(i);
            try {
                arr.add(i, String.format("%03d", obj));
            }
            catch (Exception e){
                arr.add(obj);
            }
        }

        String str, str2;
        int num, peek, bucketindex;
        for (int k = 0; k < arr.size(); k++){
            str = (String)arr.get(k);
            num = Integer.parseInt(str.charAt(index) + "");

            if (buckets.size() == 0) {
                ArrayList al = new ArrayList();
                al.add(str);
                buckets.add(al);
            }
            else {
                bucketindex = 1;
                for (int j = 0; j < buckets.size(); j++){
                    str2 = buckets.get(j).get(0);
                    peek = Integer.parseInt(str2.charAt(index) + "");

                    if (num < peek) {
                        bucketindex = 0;
                        break;
                    }
                    else if (num > peek) {
                        bucketindex++;
                        if (bucketindex == buckets.size())
                            break;
                    }
                }
                if (bucketindex > buckets.size()){
                    ArrayList al2 = new ArrayList();
                    al2.add(str);
                    buckets.add(al2);
                }
                else{
                    try {
                        buckets.get(bucketindex).add(str);
                    }
                    catch (Exception e){
                        ArrayList al3 = new ArrayList();
                        al3.add(str);
                        buckets.add(al3);
                    }
                }
            }
        }

        System.out.println("buckets: "+buckets);

        for (int i = 0; i < buckets.size(); i++){
            ArrayList bucket = buckets.get(i);
            if (bucket.size() == 1){
                result = merge(result, bucket);
            }
            else{
//                System.out.println(bucket + " " + index);
                result = merge(result, bucketSort(bucket, index + 1));
            }
        }

        System.out.println(result);

        return result;
    }

    public static ArrayList merge(ArrayList arr1, ArrayList arr2){
        for (int i = 0; i < arr2.size(); i++){
            arr1.add(arr2.get(i));
        }
        return arr1;
    }
}