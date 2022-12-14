import java.io.FileNotFoundException;
import java.util.Scanner;

public class AOC10 {

    private static Scanner scanner;

    public static void main(String[] args) throws FileNotFoundException {
        scanner = AocTemplate.getScanner(args[0]);
        int[] register = getRegister();
        aoc10a(register);
        aoc10b(register);

    }

    private static void aoc10a(int[] register){
        int sum = 0;
        int[] reg = register;

        for (int next = 20; next <= 220; next += 40){
            sum += next * reg[next-1];
        }
        System.out.println("Part 1: " + sum);
    }

    private static void aoc10b(int[] register){
        int[] reg = register;
        StringBuilder sb = new StringBuilder();
        StringBuilder line;
        for (int row = 0; row < 6; row++){
            line = new StringBuilder();
            for (int p = 0; p < 40; p++){
                char pxl = getPixel(p, reg[40 * row + p]);
                line.append(pxl);
            }
            line.append('\n');
            sb.append(line);
        }
        System.out.println(sb);
    }

    private static char getPixel(int pointer, int register){
        if (register - 1 <= pointer && pointer <= register + 1){
            return '#';
        } else {
            return '.';
        }
    }

    private static int[] getRegister(){
        int x = 1;
        int[] reg = new int[240];
        reg[0] = x;
        int clock = 0;

        while (scanner.hasNextLine()){
            String[] line = scanner.nextLine().split(" ");
            if (line.length < 2){
                clock++;
                reg[clock] = reg[clock - 1];

            }  else {
                int v = Integer.parseInt(line[1]);
                for (int i = 0; i < 2; i++){
                    clock++;
                    reg[clock] = reg[clock - 1];
                }
                reg[clock] = reg[clock] + v;
            }
            if (clock >= 239){
                break;
            }
        }
        return reg;
    }



}
