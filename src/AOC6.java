import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class AOC6 {


    private static Scanner scanner;

    public static void main(String[] args) throws FileNotFoundException {
        scanner = AocTemplate.getScanner(args[0]);
        String input = scanner.next();
        aoc6a(input);
        aoc6b(input);
    }

    private static void aoc6a(String input){
        Set<Character> set = new HashSet<>();
        int i = 0;
        while (set.size() < 4){
            set = new HashSet<>();
            for (int j = i; j < i + 4; j++){
                set.add(input.charAt(j));
            }
            i++;
        }

        System.out.println("Part 1: " + (i + 3));
    }

    private static void aoc6b(String input){
        Set<Character> set = new HashSet<>();
        int i = 0;
        while (set.size() < 14){
            set = new HashSet<>();
            for (int j = i; j < i + 14; j++){
                set.add(input.charAt(j));
            }
            i++;
        }

        System.out.println("Part 2: " + (i + 13));
    }
}
