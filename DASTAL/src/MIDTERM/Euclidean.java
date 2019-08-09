package MIDTERM;

public class Euclidean {
    public static void main(String[] args) {
        System.out.println(euclideaninator(270, 192));
    }
    static int euclideaninator(int A, int B){
        // A = 0 -> B
        // B = 0 -> A
        // (A = B⋅Q + R)
        // euclidean -> GCD (B , R)

//        if (A == 0) return B;
//        else if (B == 0) return A;
//        else{
//            return euclideaninator(B, A%B);
//        }

        return (A==0 || B == 0) ? (A==0) ? B : A : euclideaninator(B, A%B);
    }
}
