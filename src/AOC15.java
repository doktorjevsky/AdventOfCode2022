import java.io.FileNotFoundException;
import java.util.*;

public class AOC15 {

    private static Scanner scanner;

    public static void main(String[] args) throws FileNotFoundException {
        scanner = AocTemplate.getScanner(args[0]);
        List<Sensor> sensors = new ArrayList<>();
        Set<Point> beacons = new HashSet<>();
        int maxX = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int md   = Integer.MIN_VALUE;
        // Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        while (scanner.hasNextLine()){
            String[] row = scanner.nextLine().split("(Sensor at x=)|(, y=)|(: closest beacon is at x=)");
            Point s = new Point(Integer.parseInt(row[1]), Integer.parseInt(row[2]));
            Point b = new Point(Integer.parseInt(row[3]), Integer.parseInt(row[4]));
            maxX = Math.max(maxX, Math.max(s.x, b.x));
            minX = Math.min(minX, Math.min(s.x, b.x));
            md = Math.max(md, dist(s, b));
            sensors.add(new Sensor(s, b));
            beacons.add(b);
        }

        aoc15a(sensors, beacons, maxX + md, minX - md, 2000000);
        aoc15b(sensors);
    }

    private static void aoc15a(List<Sensor> ss, Set<Point> bs, int stop, int start, int row){
        Set<Point> occupied = new HashSet<>();
        for (int x = start; x <= stop; x++){
            Point p = new Point(x, row);
            for (Sensor s : ss){
                if (s.inBounds(p) && !bs.contains(p)){
                    occupied.add(p);
                }
            }
        }
        System.out.println("Part 1: " + occupied.size());
    }

    private static void aoc15b(List<Sensor> ss){
        int bound = 4000000;
        List<Point> ps;
        List<Point> temp = new ArrayList<>();
        Point beacon = new Point(0,0);

        for (Sensor s1 : ss) {
            ps = s1.boundary();
            for (Point p : ps) {
                boolean inB = false;
                for (Sensor s : ss) {
                    if (s.inBounds(p) || (p.x < 0 || bound < p.x) || (p.y < 0 || bound < p.y)) {
                        inB = true;
                    }
                }
                if (!inB) {
                    beacon = p;
                    break;
                }
            }
        }
        long ans = ((long) beacon.x * (long) bound) + (long) beacon.y;
        System.out.println("Part 2: " + ans);


    }

    private static int dist(Point a, Point b){
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
    

    private static class Sensor {
        Point pos;
        Point beacon;
        int d;

        Sensor (Point pos, Point beacon){
            this.pos = pos;
            this.beacon = beacon;
            d = dist(pos, beacon);
        }

        public List<Point> boundary(){
            List<Point> ps = new ArrayList<>();
            Point p = new Point(pos.x - d - 1, pos.y);
            ps.add(p);
            for (int i = 1; i <= d + 1; i++){
                Point p2 = new Point(p.x + i, p.y + i);
                Point p3 = new Point(p.x + i, p.y - i);
                Point p4 = new Point(pos.x + i, pos.y + d + 1 - i);
                Point p5 = new Point(pos.x + i, pos.y - d - 1 + i);
                ps.add(p2);
                ps.add(p3);
                ps.add(p4);
                ps.add(p5);
            }
            return ps;
        }

        public boolean inBounds(Point p){
            return dist(p, pos) <= d;
        }
    }

    private static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString(){ return "(" + x + ", " + y + ")"; }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point)) {
                return false;
            }
            Point b = (Point) o;
            return x == b.x && y == b.y;
        }
        @Override
        public int hashCode(){
            return Objects.hash(x, y);
        }
    }
}
