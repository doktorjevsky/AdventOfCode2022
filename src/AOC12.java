import java.io.FileNotFoundException;
import java.util.*;

public class AOC12 {

    private static Scanner scanner;

    public static void main(String[] args) throws FileNotFoundException {
        scanner = AocTemplate.getScanner(args[0]);

        List<String> grid = new ArrayList<>();
        while (scanner.hasNextLine()){
            grid.add(scanner.nextLine());
        }

        aoc12a(grid);
    }

    private static void aoc12a(List<String> grid){
        WeightFunction f = (from, to) -> {
            char x = from == 'S' ? 'a' : from;
            char y = to == 'E' ? 'z' : to;
            return y - x;
        };
        Point start = getPosition('S', grid);
        Point end   = getPosition('E', grid);

        Map<Point, Point> posPaths = bfSearch(start, grid, f);
        List<Edge> path = computePath(start, end, posPaths);

        System.out.println("Part 1: " + path.size());

        // Part 2
        List<Point> startingPoints = getAllStartingPoints(grid);
        int shortest = Integer.MAX_VALUE;
        for (Point p : startingPoints){
            posPaths = bfSearch(p, grid, f);
            path = computePath(p, end, posPaths);
            shortest = path.size() < 2 ? shortest : Math.min(shortest, path.size());
        }

        System.out.println("Part 2: " + shortest);
    }


    private static List<Point> getAllStartingPoints(List<String> grid){
        List<Point> startingPoints = new ArrayList<>();
        for (int i = 0; i < grid.size(); i++){
            for (int j = 0; j < grid.get(0).length(); j++){
                char c = grid.get(i).charAt(j);
                if (c == 'S' || c == 'a'){
                    startingPoints.add(new Point(i, j));
                }
            }
        }
        return startingPoints;
    }

    private static Map<Point, Point> bfSearch(Point from, List<String> grid, WeightFunction f){
        Set<Point> visited = new HashSet<>();
        Map<Point, Point> posPath = new HashMap<>();
        visited.add(from);
        Queue<Edge> q = new LinkedList<>(adjacent(from, grid, f));

        while (!q.isEmpty()){
            Edge nextEdge = q.poll();
            Point toP = nextEdge.to;
            if (!visited.contains(toP)){
                posPath.put(toP, nextEdge.from);
                visited.add(toP);
                q.addAll(adjacent(toP, grid, f));

            }
        }
        return posPath;

    }

    private static Set<Edge> adjacent(Point p, List<String> grid, WeightFunction f){
        int[] dp = new int[]{1, 0, -1};
        Set<Edge> adj = new HashSet<>();
        char at = grid.get(p.i).charAt(p.j);
        for (Integer di : dp){
            for (Integer dj : dp){
                if (inBounds(p.i + di, p.j + dj, grid) && ((Math.abs(di) ^ Math.abs(dj)) != 0)){
                    if (f.apply(at, grid.get(p.i + di).charAt(p.j + dj)) < 2){
                        Point p2 = new Point(p.i + di, p.j + dj);
                        adj.add(new Edge(p, p2));
                    }
                }
            }
        }
        return adj;
    }

    private static boolean inBounds(int i , int j, List<String> grid){
        return 0 <= i && i < grid.size() && 0 <= j && j < grid.get(0).length();
    }

    // linear search for first occurrence of a node "x"
    private static Point getPosition(char x, List<String> grid){
        for (int i = 0; i < grid.size(); i++){
            for (int j = 0; j < grid.get(0).length(); j++){
                if (grid.get(i).charAt(j) == x){
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    // to, from
    private static List<Edge> computePath(Point from, Point to, Map<Point, Point> possiblePaths){
        List<Edge> path = new ArrayList<>();
        if (!possiblePaths.containsKey(to)){
            return path;
        }
        // current = from
        Point current = possiblePaths.get(to);
        path.add(new Edge(current, to));
        while (!current.equals(from)) {
            path.add(new Edge(possiblePaths.get(current), current));
            current = possiblePaths.get(current);
        }
        return path;
    }

    private static class Point {
        int i;
        int j;

        Point(int i, int j){
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString(){
            return "(" + i + ", " + j + ")";
        }

        @Override
        public boolean equals(Object o){
            if (!(o instanceof Point)){
                return false;
            }
            Point x = (Point) o;
            return x.i == i && x.j == j;
        }

        @Override
        public int hashCode(){
            return Objects.hash(i, j);
        }
    }

    private static class Edge {
        Point from, to;
        Edge(Point from, Point to){
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o){
            if (!(o instanceof Edge)){
                return false;
            }
            Edge x = (Edge) o;
            return x.from.equals(from) && x.to.equals(to);
        }

        @Override
        public int hashCode(){
            return Objects.hash(from, to);
        }
    }


    interface WeightFunction {
        int apply(char from, char to);
    }

}
