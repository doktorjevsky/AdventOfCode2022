import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
*
* [1, 2, 3, 4]
* [2, 3, 5]
* [3, 4, 5]
*
* max = 3
* [2, 3, 4]
* [2, 3, 5]
* [3, 4, 5]
*
* max = 3
* [3, 4]
* [3, 5]
* [3, 4, 5]
* */

public class AOC3{

    private static Scanner scanner;

    public static void main(String[] args) throws FileNotFoundException {
        scanner = AocTemplate.getScanner(args[0]);
        List<String> rucksack = new ArrayList<>();

        while(scanner.hasNext()){
            String row = scanner.nextLine();
            rucksack.add(row);
        }

        aoc3a(rucksack);
        aoc3b(rucksack);
    }

    private static void aoc3a(List<String> ruckSack){
        int result = 0;

        for (String row : ruckSack){
            String s1 = row.substring(0, row.length() / 2);
            String s2 = row.substring(row.length() / 2);

            PriorityQueue<Integer> pq1 = stringToPq(s1);
            PriorityQueue<Integer> pq2 = stringToPq(s2);

            int p1 = pq1.peek();
            int p2 = pq2.peek();

            while (p1 != p2){
                if (p1 > p2){
                    p2 = pq2.poll();
                } else {
                    p1 = pq1.poll();
                }
            }

            result += p1;
        }

        System.out.println("Part 1: " + result);

    }

    private static void aoc3b(List<String> ruckSack){
        int result = 0;
        for (int i = 0; i < ruckSack.size() - 2; i += 3){
            PriorityQueue<Integer> pq1 = stringToPq(ruckSack.get(i));
            PriorityQueue<Integer> pq2 = stringToPq(ruckSack.get(i+1));
            PriorityQueue<Integer> pq3 = stringToPq(ruckSack.get(i+2));

            int p1 = pq1.peek();
            int p2 = pq2.peek();
            int p3 = pq3.peek();

            while (!(p1 == p2 && p2 == p3)){
                int min = minOfThree(p1, p2, p3);
                if (p1 == min){
                    p1 = pq1.poll();
                }
                else if (p2 == min){
                    p2 = pq2.poll();
                } else {
                    p3 = pq3.poll();
                }
            }

            result += (p1);
        }
        System.out.println("Part 2: " + result);
    }

    private static int maxOfThree(int a, int b, int c){
        return Math.max(a, Math.max(b, c));
    }

    private static int minOfThree(int a, int b, int c){
        return Math.min(a, Math.min(b, c));
    }

    private static PriorityQueue<Integer> stringToPq(String s){
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < s.length(); i++){
            pq.add(priority(s.charAt(i)));
        }
        return pq;
    }

    private static int priority(char c){
        return (int) c < 97 ? (int) c - (int) 'A' + 27 : (int) c - (int) 'a' + 1;
    }
}
