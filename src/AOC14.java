import java.io.FileNotFoundException;
import java.util.*;

public class AOC14 {

    private static Scanner scanner;

    public static void main(String[] args) throws FileNotFoundException {
        scanner = AocTemplate.getScanner(args[0]);
        Set<Point> occupied = new HashSet<>();
        Set<Point> occupied2 = new HashSet<>();
        int stopValue = 0;
        while (scanner.hasNextLine()){
            String[] points = scanner.nextLine().split("( -> )|(,)");
            Point p1 = new Point(Integer.parseInt(points[0]), Integer.parseInt(points[1]));
            stopValue = Math.max(stopValue, p1.y);
            for (int i = 2; i < points.length; i += 2){
                Point p2 = new Point(Integer.parseInt(points[i]), Integer.parseInt(points[i+1]));
                stopValue = Math.max(stopValue, p2.y);
                LineSegment l = new LineSegment(p1, p2);
                occupied.addAll(l.getPoints());
                occupied2.addAll(l.getPoints());
                p1 = p2;
            }
        }
        aoc14a(occupied, stopValue);
        aoc14b(occupied2, stopValue);
    }

    private static void aoc14a(Set<Point> occupied, int stop){
        Point origin = new Point(500, 0);

        int nBallsBefore = occupied.size();
        Point rock = origin.copy();
        while (rock.y <= stop) {
            Point rock1 = rock.copy();
            rock1.moveDown();
            if (occupied.contains(rock1)){
                rock1 = rock.copy();
                rock1.moveDownLeft();
                if (occupied.contains(rock1)) {
                    rock1 = rock.copy();
                    rock1.moveDownRight();
                    if (occupied.contains(rock1)) {
                        occupied.add(rock);
                        rock = origin.copy();
                    } else {
                        rock = rock1;
                    }
                } else {
                    rock = rock1;
                }
            } else {
                rock = rock1;
            }
        }

        int result = occupied.size() - nBallsBefore;
        System.out.println("Part 1: " + result);

    }

    private static void aoc14b(Set<Point> occupied, int stop){
        Point origin = new Point(500, 0);
        addFloor(stop, occupied);
        int nBallsBefore = occupied.size();
        Point rock = origin.copy();
        boolean canMove = true;

        while (canMove) {
            Point rock1 = rock.copy();
            rock1.moveDown();
            if (occupied.contains(rock1)){
                rock1 = rock.copy();
                rock1.moveDownLeft();
                if (occupied.contains(rock1)) {
                    rock1 = rock.copy();
                    rock1.moveDownRight();
                    if (occupied.contains(rock1)) {
                        if (rock.equals(origin)){
                            canMove = false;
                            occupied.add(rock);
                        } else {
                            occupied.add(rock);
                            rock = origin.copy();
                        }
                    } else {
                        rock = rock1;
                    }
                } else {
                    rock = rock1;
                }
            } else {
                rock = rock1;
            }
        }

        int result = occupied.size() - nBallsBefore;
        System.out.println("Part 2: " + result);
    }

    private static void addFloor(int y, Set<Point> set){
        int floorY = y + 2;
        int leftX = Collections.min(set).x - 10000;
        int rightX = Collections.max(set).x + 10000;
        LineSegment l = new LineSegment(new Point(leftX, floorY), new Point(rightX, floorY));
        set.addAll(l.getPoints());
    }

    private static Set<Point> initSet(List<List<LineSegment>> ls){
        Set<Point> ps = new HashSet<>();
        for (List<LineSegment> line : ls){
            for (LineSegment l : line){
                for (Point p : l.getPoints()){
                    System.out.println(p);
                }
                ps.addAll(l.getPoints());
            }
        }
        return ps;
    }


    private static class LineSegment {
        Point p1;
        Point p2;
        boolean vertical;

        LineSegment(Point p1, Point p2){
            this.p1 = p1;
            this.p2 = p2;
            vertical = (p1.x - p2.x) == 0;
        }


        public Set<Point> getPoints(){
            Set<Point> ps = new HashSet<>();
            if (vertical){
                for (int i = Math.min(p1.y, p2.y); i <= Math.max(p1.y, p2.y); i++){
                    ps.add(new Point(p1.x, i));
                }
            } else {
                for (int i = Math.min(p1.x, p2.x); i <= Math.max(p2.x, p1.x); i++){
                    ps.add(new Point(i, p1.y));
                }
            } return ps;
        }

        public boolean onLine(Point p){
            if (vertical){
                return p.x == p1.x && p1.y <= p.y && p.y <= p2.y;
            } else {
                return p.y == p1.y && p1.x <= p.x && p.x <= p2.x;
            }
        }
    }


    private static class Point implements Comparable<Point>{
        int x;
        int y;

        Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        public Point copy(){ return new Point(x, y); }

        public int getX(){ return x; }

        public void moveDown(){
            y++;
        }

        public void moveDownLeft(){
            y++;
            x--;
        }

        public void moveDownRight(){
            y++;
            x++;
        }

        @Override
        public String toString(){
            return "(" + x + ", " + y + ")";
        }

        @Override
        public boolean equals(Object o){
            if (!(o instanceof Point)){
                return false;
            } else {
                Point oo = (Point) o;
                return x == oo.x && y == oo.y;
            }

        }

        @Override
        public int hashCode(){
            return Objects.hash(x, y);
        }

        @Override
        public int compareTo(Point o) {
            return Integer.compare(x, o.x);
        }
    }
}
