import java.io.FileNotFoundException;
import java.util.*;

public class AOC5 {

    private static Scanner scanner;

    public static void main(String[] args) throws FileNotFoundException {
        scanner = AocTemplate.getScanner(args[0]);
        List<Stack<String>> cols;
        List<Stack<String>> cols2;
        List<List<String>> parsedRows = new ArrayList<>();
        List<Instruction> instructions = new ArrayList<>();


        // TODO: Fix bug: Won't work on inputs where last column has height < 8. I was lucky :)))
        // input stacks
        for (int i = 0; i < 8; i++){
            String row = scanner.nextLine();
            List<String> r = parseRow(row);
            parsedRows.add(r);

        }
        scanner.nextLine();


        // set stacks
        cols = initCrates(parsedRows);
        cols2 = initCrates(parsedRows);



        while (scanner.hasNext()){
            scanner.next();
            int move = scanner.nextInt();
            scanner.next();
            int from = scanner.nextInt();
            scanner.next();
            int to = scanner.nextInt();
            Instruction instruction = new Instruction(move, from - 1, to - 1);
            instructions.add(instruction);
        }

        aoc5a(instructions, cols);
        aoc5b(instructions, cols2);

    }

    private static List<Stack<String>> initCrates(List<List<String>> parsedRows){
        List<Stack<String>> cols = initStacks(parsedRows.get(0).size());

        for (int i = parsedRows.size() - 1; i >= 0; i--){
            for (int j = 0; j < parsedRows.get(0).size(); j++){
                String element = parsedRows.get(i).get(j);
                if (!element.equals("   ")) {
                    cols.get(j).push(element);
                }
            }
        }
        return cols;
    }

    private static void aoc5a(List<Instruction> instructions, List<Stack<String>> crates){
        for (Instruction i : instructions){
            i.apply(crates);
        }

        System.out.println("Part 1: ");
        for (Stack<String> stack : crates){
            System.out.print(stack.peek());
        }
        System.out.println();
    }

    private static void aoc5b(List<Instruction> instructions, List<Stack<String>> crates){
        for (Instruction i : instructions){
            i.apply2(crates);
        }
        System.out.println("Part 2: ");
        for (Stack<String> c : crates){
            System.out.print(c.peek());
        }
    }

    private static List<Stack<String>> initStacks(int nCols){
        List<Stack<String>> stacks = new ArrayList<>();
        for (int i = 0; i < nCols; i++){
            stacks.add(new Stack<>());
        }
        return stacks;
    }

    private static List<String> parseRow(String row){
        List<String> r = new ArrayList<>();
        int i = 0;
        while (i < row.length()){
            StringBuilder sb = new StringBuilder();
            sb.append(row.charAt(i));
            sb.append(row.charAt(i+1));
            sb.append(row.charAt(i+2));
            r.add(sb.toString());
            i += 4;

        }
        return r;
    }

    private static class Instruction {
        private int move;
        private int from;
        private int to;

        Instruction(int move, int from, int to){
            this.move = move;
            this.from = from;
            this.to   = to;
        }

        public void apply(List<Stack<String>> crates){
            for (int i = 0; i < move; i++){
                String crate = crates.get(from).pop();
                crates.get(to).push(crate);
            }
        }

        public void apply2(List<Stack<String>> crates){
            Stack<String> dummy = new Stack<>();
            for (int i = 0; i < move; i++){
                String crate = crates.get(from).pop();
                dummy.push(crate);
            }
            for (int i = 0; i < move; i++){
                String crate = dummy.pop();
                crates.get(to).push(crate);
            }
        }
    }


}
