import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class AOC2 {


    private static Scanner scanner;
    private static Map<String, String> decode = Map.ofEntries(
            Map.entry("A", "R"),
            Map.entry("B", "P"),
            Map.entry("C", "S"),
            Map.entry("X", "R"),
            Map.entry("Y", "P"),
            Map.entry("Z", "S")
    );
    private static Map<String, Integer> shape = Map.ofEntries(
            Map.entry("R", 1),
            Map.entry("P", 2),
            Map.entry("S", 3)
    );

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        scanner = new Scanner(file);
        aoc2a();

    }

    private static void aoc2a(){
        int totalScore = 0;
        int totalScore2 = 0;
        while (scanner.hasNextLine()){
            String oppDecoded = decode.get(scanner.next());
            String you = scanner.next();
            String youDecoded = decode.get(you);
            String neededMove = neededMove(oppDecoded, you);
            totalScore += shape.get(youDecoded) + outcome(oppDecoded, youDecoded);
            totalScore2 += shape.get(neededMove) + outcome(oppDecoded, neededMove);

        }

        System.out.println("aoc2a: " + totalScore);
        System.out.println("aoc2b: " + totalScore2);
    }

    private static int outcome(String decodedOpp, String decodedYou){
        if (decodedOpp.equals(decodedYou)){
            return 3;
        }
        else if (decodedYou.equals("R")){
            return decodedOpp.equals("S") ? 6 : 0;
        }
        else if (decodedYou.equals("P")){
            return decodedOpp.equals("R") ? 6 : 0;
        } else {
            return decodedOpp.equals("P") ? 6 : 0;
        }
    }

    private static String neededMove(String decodedOpp, String outCome){
        if (decodedOpp.equals("R")){
            return outCome.equals("X") ? "S" : (outCome.equals("Y") ? "R" : "P");
        }
        else if (decodedOpp.equals("P")){
            return outCome.equals("X") ? "R" : (outCome.equals("Y") ? "P" : "S");
        } else {
            return outCome.equals("X") ? "P" : (outCome.equals("Y") ? "S" : "R");
        }
    }

}
