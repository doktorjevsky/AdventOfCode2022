import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AOC4 {

    private static Scanner scanner;

    public static void main(String[] args) throws FileNotFoundException {
        scanner = AocTemplate.getScanner(args[0]);

        List<ElfPair> intervalPairs = new ArrayList<>();

        while(scanner.hasNextLine()){
            String[] row = scanner.nextLine().split(",");
            Tuple<String, String> t = new Tuple<>(row[0], row[1]);
            intervalPairs.add(new ElfPair(t));
        }

        aoc4a(intervalPairs);

    }

    // both parts
    private static void aoc4a(List<ElfPair> pairs){
        int count = 0;
        int count2 = 0;
        for (ElfPair ep : pairs){
            count = ep.fullyContains() ? count + 1 : count;
            count2 = ep.overlaps() ? count2 + 1 : count2;
        }

        System.out.println("Part 1: " + count);
        System.out.println("Part 2: " + count2);

    }

    private static class Tuple<A, B>{
        A fst;
        B snd;

        Tuple(A fst, B snd){
            this.fst = fst;
            this.snd = snd;
        }

        @Override
        public String toString(){
            return "(" + fst + ", " + snd + ")";
        }
    }

    private static class ElfPair {
        Tuple<Integer, Integer> p1;
        Tuple<Integer, Integer> p2;

        ElfPair(Tuple<String, String> row){
            parse(row);
        }

        private void parse(Tuple<String, String> row){
            String[] low = row.fst.split("-");
            String[] high = row.snd.split("-");
            p1 = new Tuple<>(Integer.parseInt(low[0]), Integer.parseInt(low[1]));
            p2 = new Tuple<>(Integer.parseInt(high[0]), Integer.parseInt(high[1]));
        }

        public boolean fullyContains(){
            boolean x = p1.fst <= p2.fst && p2.snd <= p1.snd;
            boolean y = p2.fst <= p1.fst && p1.snd <= p2.snd;
            return x || y;
        }

        public boolean overlaps(){
            boolean x = p1.fst <= p2.fst && p2.fst <= p1.snd;
            boolean y = p1.fst <= p2.snd && p2.snd <= p1.snd;
            return x || y || fullyContains();
        }

        @Override
        public String toString(){
            return p1 + " " + p2;
        }
    }


}
