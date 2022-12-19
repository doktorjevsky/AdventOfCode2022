import java.io.FileNotFoundException;
import java.util.*;

public class AOC18 {

    private static Scanner scanner;

    public static void main(String[] args) throws FileNotFoundException {
        scanner = AocTemplate.getScanner(args[0]);
        Set<Cube> cubes = new HashSet<>();
        int maxX = Integer.MIN_VALUE;
        int maxY = maxX;
        int maxZ = maxX;


        while (scanner.hasNextLine()){
            String[] coord = scanner.nextLine().split(",");
            int x = Integer.parseInt(coord[0]);
            int y = Integer.parseInt(coord[1]);
            int z = Integer.parseInt(coord[2]);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            maxZ = Math.max(maxZ, z);


            Cube c = new Cube(x, y, z);
            cubes.add(c);
        }

        int maxD = Math.max(maxX, Math.max(maxY, maxZ));
        Cube bound = new Cube(maxD + 4, maxD + 4, maxD + 4);

        aoc18a(cubes);
        aoc18b(cubes, bound);


    }


    private static void aoc18a(Set<Cube> cubes){
        int sides = cubes.size() * 6;
        for (Cube c : cubes){
            for (Cube c1 : cubes){
                if (c != c1 && c.adjacentSides(c1)){
                    sides--;
                }
            }
        }
        System.out.println("Part 1: " + sides);


    }

    private static void aoc18b(Set<Cube> cubes, Cube bound){
        Set<Cube> reachable = bfSearch(cubes, bound);
        int sides = 0;
        for (Cube c : reachable){
            for (Cube c1 : cubes){
                if (!c.equals(c1) && c.adjacentSides(c1)){
                    sides++;
                }
            }
        }

        System.out.println("Part 2: " + sides);

    }

    // breadth first search on the 3D grid from (0,0,0) to max (x, y, z)
    private static Set<Cube> bfSearch(Set<Cube> cubes, Cube bound){
        Set<Cube> visited = new HashSet<>();
        Queue<Cube> q = new LinkedList<>();
        Cube current = new Cube(-1,-1,-1);
        q.add(current);

        while (!q.isEmpty()){
            current = q.poll();
            if (!visited.contains(current)){
                addAdjacent(current, q, cubes, bound);
                visited.add(current);
            }
        }
        return visited;
    }

    private static void addAdjacent(Cube c, Queue<Cube> queue, Set<Cube> cubes, Cube bound){

        for (int dx = -1; dx < 2; dx++){
            for (int dy = -1; dy < 2; dy++){
                for (int dz = -1; dz < 2; dz++){
                    int s = Math.abs(dx) + Math.abs(dy) + Math.abs(dz);
                    if (s == 1){
                        Cube nc = new Cube(c.x + dx, c.y + dy, c.z + dz);
                        if (-1 <= nc.x && nc.x <= bound.x
                                && -1 <= nc.y && nc.y <= bound.y
                                && -1 <= nc.z && nc.z <= bound.z
                                && !cubes.contains(nc)){
                            queue.add(nc);
                        }
                    }
                }
            }
        }
    }

    private static class Cube {
        int x;
        int y;
        int z;

        Cube(int x, int y, int z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public boolean adjacentSides(Cube c){
            return ((x == c.x && y == c.y) || (x == c.x && z == c.z) || (y == c.y && z == c.z)) && dist(c) < 2;
        }

        private int dist(Cube c){
            return Math.max(Math.abs(x - c.x), Math.max(Math.abs(y - c.y), Math.abs(z - c.z)));
        }

        @Override
        public boolean equals(Object o){
            if (!(o instanceof Cube)){
                return false;
            }
            Cube c = (Cube) o;
            return x == c.x && y == c.y && z == c.z;
        }

        @Override
        public int hashCode(){
            return Objects.hash(x, y, z);
        }
    }
}
