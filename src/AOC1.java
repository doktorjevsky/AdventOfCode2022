import java.io.*;
import java.util.*;

public class AOC1 {

    private static Scanner scanner;


    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        scanner = new Scanner(file);
        aoc1ab();
    }

    /*
    * Contains answers to both parts
    * */
    public static void aoc1ab() {
        int sum = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o2, o1);
            }
        });

        while(scanner.hasNextLine()){
            String x = scanner.nextLine();
            if (x.equals("")){
                pq.add(sum);
                sum = 0;
            } else {
                sum += Integer.parseInt(x);
            }
        }
        int maxSum = pq.poll();
        int s2 = pq.poll();
        int s3 = pq.poll();

        System.out.println("aoc1a: " + maxSum);
        System.out.println("aoc1b: " + (maxSum + s2 + s3));
    }
}
