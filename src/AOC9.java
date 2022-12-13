import java.io.FileNotFoundException;
import java.util.*;

public class AOC9 {

    private static Scanner scanner;

    public static void main(String[] args) throws FileNotFoundException {
        scanner = AocTemplate.getScanner(args[0]);
        List<String[]> instructions = new ArrayList<>();
        while (scanner.hasNextLine()){
            String[] inst = scanner.nextLine().split(" ");
            instructions.add(inst);
        }

        aoc9a(instructions);
        aoc9b(instructions);
    }

    private static void aoc9a(List<String[]> instructions){
        Set<Position> tailPositions = new HashSet<>();
        Snake head = new Snake();
        Snake tail = new Snake();
        head.setChild(tail);
        tail.setParent(head);
        tailPositions.add(tail.getPos());
        Position delta;

        for (String[] instr : instructions){
            String ins = instr[0];
            int times = Integer.parseInt(instr[1]);
            if (ins.equals("R")){
                delta = new Position(1, 0);
            }
            else if (ins.equals("L")){
                delta = new Position(-1, 0);
            }
            else if (ins.equals("D")){
                delta = new Position(0, 1);
            } else { // U
                delta = new Position(0, -1);
            }
            simulateSnake(head, delta, times, tailPositions);
        }

        System.out.println("Part 1: " + tailPositions.size());

    }

    private static void aoc9b(List<String[]> instructions){
        Set<Position> tailPositions = new HashSet<>();
        Snake head = new Snake();
        Snake current = head;
        for (int i = 0; i < 9; i++){
            Snake newSnake = new Snake();
            current.setChild(newSnake);
            current = newSnake;
        }

        tailPositions.add(current.getPos());

        Position delta;

        for (String[] instr : instructions){
            String ins = instr[0];
            int times = Integer.parseInt(instr[1]);
            if (ins.equals("R")){
                delta = new Position(1, 0);
            }
            else if (ins.equals("L")){
                delta = new Position(-1, 0);
            }
            else if (ins.equals("D")){
                delta = new Position(0, 1);
            } else { // U
                delta = new Position(0, -1);
            }
            simulateSnake(head, delta, times, tailPositions);
        }

        System.out.println("Part 2: " + tailPositions.size());

    }


    private static void simulateSnake(Snake head, Position delta, int times, Set<Position> tailPostitions){
        Snake tail = head.child;
        while(tail.child != null){
            tail = tail.child;
        }
        for (int i = 0; i < times; i++){
            head.move(delta);
            tailPostitions.add(tail.getPos());
        }
    }

    private static class Snake {
        private Snake parent;
        private Snake child;
        private Position pos;
        private Position prevPos;

        public Snake(){
            pos = new Position(0, 0);
            prevPos = pos;
        }

        public void setParent(Snake parent){
            this.parent = parent;
            parent.child = this;
        }

        public void setChild(Snake child){
            this.child = child;
            child.parent = this;

        }

        public Position getPos() {
            return pos.copy();
        }

        public void move(Position delta){
            if (parent == null){
                prevPos = pos.copy();
                pos.add(delta);
                if (child != null){
                    child.move(delta);
                }
            } else {
                if (pos.dist(parent.pos) > 1){
                    prevPos = pos.copy();
                    Position nDelta = delta.or(parent.prevPos.sub(pos));
                    pos.add(nDelta);
                    if (child != null){
                        child.move(nDelta);
                    }
                }
            }
        }
    }

    private static class Position {
        private int x;
        private int y;

        Position(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void add(Position p){
            x += p.getX();
            y += p.getY();
        }

        public Position copy(){
            return new Position(x, y);
        }

        public int dist(Position p){
            int dy = Math.abs(y - p.getY());
            int dx = Math.abs(x - p.getX());
            return Math.max(dy, dx);
        }

        public Position sub(Position p){
            return new Position(x - p.x, y - p.y);
        }

        public Position or(Position p){
            int x1 = x == p.x ? x : x + p.x;
            int y1 = y == p.y ? y : y + p.y;
            return new Position(x1, y1);
        }

        @Override
        public String toString(){
            return "(" + x + ", " + y + ")";
        }

        @Override
        public int hashCode(){
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object o){
            if (!(o instanceof Position)){
                return false;
            } else {
                Position other = (Position) o;
                return x == other.getX() && y == other.getY();
            }
        }
    }
}
