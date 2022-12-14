import java.io.FileNotFoundException;
import java.util.*;

public class AOC11 {

    private static Scanner scanner;

    public static void main(String[] args) throws FileNotFoundException {
        scanner = AocTemplate.getScanner(args[0]);
        List<Monkey> ms = initMonkeys();
        scanner = AocTemplate.getScanner(args[0]);
        List<Monkey> ms2 = initMonkeys();
        aoc11a(ms);
        aoc11b(ms2);

    }

    private static void aoc11a(List<Monkey> ms){
        for (int i = 0; i < 20; i++){
            for (Monkey m : ms){
                m.throwTo(ms);
            }
        }

        Collections.sort(ms, (a, b) -> Long.compare(a.getItemsInspected(), b.getItemsInspected()));
        System.out.println("Part 1: " + (ms.get(ms.size() - 1).getItemsInspected() * ms.get(ms.size() - 2).getItemsInspected()));
    }

    private static void aoc11b(List<Monkey> ms){
        for (Monkey m : ms){
            m.setDivideBy(1);
        }

        for (int i = 0; i < 10000; i++){
            for (Monkey m : ms){
                m.throwTo(ms);
            }
        }


        Collections.sort(ms, (b, a) -> Long.compare(a.getItemsInspected(), b.getItemsInspected()));
        System.out.println("Part 2: " + (ms.get(0).getItemsInspected() * ms.get(1).getItemsInspected()));
    }

    private static List<Monkey> initMonkeys(){
        List<Monkey> ms = new ArrayList<>();
        long mod = 1;
        while (scanner.hasNextLine()){
            scanner.nextLine();
            String[] items = scanner.nextLine().split(": ")[1].split(", ");
            List<Long> is = new ArrayList<>();
            for (String s : items){
                is.add(Long.parseLong(s));
            }
            String operationString = scanner.nextLine().split(" = ")[1];
            long divisible = Long.parseLong(scanner.nextLine().split("by ")[1]);
            mod *= divisible;
            int monkeyTrue = Integer.parseInt(scanner.nextLine().split("monkey ")[1]);
            int monkeyFalse = Integer.parseInt(scanner.nextLine().split("monkey ")[1]);
            Monkey m = new Monkey(is, operationString, divisible, monkeyTrue, monkeyFalse);
            ms.add(m);
            try {
                scanner.nextLine();
            } catch (Exception e){
                break;
            }


        }

        for (Monkey m : ms){
            m.setMonkeyMod(mod);
        }
        return ms;
    }


    private static class Monkey {
        private final Queue<Long> items = new LinkedList<>();
        private MonkeyParse op;
        private MonkeyTest test;
        private final int monkeyTrue;
        private final int monkeyFalse;
        private long itemsInspected = 0;
        private long divideBy = 3;
        private long mod;

        Monkey(List<Long> items, String operationString, long testCondition, int monkeyTrue, int monkeyFalse){
            this.items.addAll(items);
            test = (x) -> x % testCondition == 0;
            initOp(operationString);
            this.monkeyTrue = monkeyTrue;
            this.monkeyFalse = monkeyFalse;
        }

        public void setMonkeyMod(long mod){ this.mod = mod; }

        public void setDivideBy(long d){ divideBy = d;}

        public long getItemsInspected(){ return itemsInspected; }

        private void initOp(String operationString){
            String[] parts = operationString.split(" ");
            MonkeyOperation math = parts[1].equals("*") ? (a,b) -> (a * b) % mod : (a, b) -> (a+b) % mod;
            op = (items) -> {
                long a;
                long b;
                if (parts[0].equals("old")){
                    a = items.peek();
                } else {
                    a = Integer.parseInt(parts[0]);
                }
                if (parts[2].equals("old")){
                    b = items.peek();
                } else {
                    b = Integer.parseInt(parts[2]);
                }
                return math.op(a, b);
            };

        }

        public void throwTo(List<Monkey> ms){
            while (!items.isEmpty()){
                long item = op.op(items);
                items.poll();
                itemsInspected++;
                item /= divideBy;

                if (test.op(item)){
                    ms.get(monkeyTrue).addItem(item);
                } else {
                    ms.get(monkeyFalse).addItem(item);
                }
            }
        }

        public void addItem(long item){
            items.add(item);
        }

    }

    interface MonkeyParse {
        long op(Queue<Long> items);
    }

    interface MonkeyOperation {
        long op(long a, long b);
    }

    interface MonkeyTest {
        boolean op(long x);
    }
}
