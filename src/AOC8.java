import java.io.FileNotFoundException;

import java.util.Scanner;

public class AOC8 {

    private static Scanner scanner;


    public static void main(String[] args) throws FileNotFoundException {
        scanner = AocTemplate.getScanner(args[0]);
        int[][] grid = initGrid();


        aoc8a(grid);
        aoc8b(grid);


    }

    private static int[][] initGrid(){
        String first = scanner.nextLine();
        int[][] grid = new int[first.length()][first.length()];
        for (int i = 0; i < first.length(); i++){
            grid[0][i] = cToInt(first.charAt(i));
        }
        for (int i = 1; i < first.length(); i++){
            String next = scanner.nextLine();
            for (int j = 0; j < first.length(); j++){
                grid[i][j] = cToInt(next.charAt(j));
            }
        }
        return grid;
    }

    private static boolean[][] transpose(boolean[][] m){
        boolean[][] out = new boolean[m[0].length][m.length];
        for (int i = 0; i < m.length; i++){
            for (int j = 0; j < m[0].length; j++){
                out[j][i] = m[i][j];
            }
        }
        return out;
    }

    private static int[][] transpose(int[][] grid){
        int[][] out = new int[grid[0].length][grid.length];
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[0].length; j++){
                out[j][i] = grid[i][j];
            }
        }
        return out;
    }

    private static int cToInt(char c){
        return Integer.parseInt(Character.toString(c));
    }

    private static void aoc8a(int[][] grid){
        boolean[][] visible = new boolean[grid.length][grid[0].length];
        visible = visibleByRow(grid, transpose(visibleByRow(transpose(grid), transpose(visible))));
        int c = 0;
        for (boolean[] row : visible){
            for (boolean b : row){
                c = b ? c + 1: c;
            }
        }
        System.out.println("Part 1: " + c);

    }

    private static void aoc8b(int[][] grid){
        int max = 0;
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[0].length; j++){
                int scenicScore = scenicScore(i, j, grid);
                max = Math.max(max, scenicScore);
            }
        }

        System.out.println("Part 2: " + max);
    }

    private static boolean[][] visibleByRow(int[][] grid, boolean[][] visible){
        int max;
        for (int i = 0; i < grid.length; i++){
            max = -1;
            for (int j = 0; j < grid[i].length; j++){
                if (grid[i][j] > max){
                    visible[i][j] = true;
                    max = grid[i][j];
                }
            }
            max = -1;
            for (int j = grid[i].length - 1; j >= 0; j--){
                if (grid[i][j] > max){
                    visible[i][j] = true;
                    max = grid[i][j];
                }
            }
        }
        return visible;
    }

    private static int scenicScore(int y, int x, int[][] grid){
        int s1 = viewingDistance(y, x, grid, new int[]{1, 0});
        int s2 = viewingDistance(y, x, grid, new int[]{-1, 0});
        int s3 = viewingDistance(y, x, grid, new int[]{0, 1});
        int s4 = viewingDistance(y, x, grid, new int[]{0, -1});
        return s1 * s2 * s3 * s4;
    }

    private static int viewingDistance(int y, int x, int[][] grid, int[] delta){
        int dy = delta[0];
        int dx = delta[1];
        int current = grid[y][x];
        int next;
        int viewingDistance = 0;
        x += dx;
        y += dy;
        while (inBounds(y, x, grid)){
            next = grid[y][x];
            if (current > next){
                viewingDistance++;
                y += dy;
                x += dx;
            } else {
                viewingDistance++;
                break;
            }
        }
        return viewingDistance;


    }

    private static boolean inBounds(int y, int x, int[][] grid){
        int leny = grid.length;
        int lenx = grid[0].length;
        return 0 <= y && y < leny && 0 <= x && x < lenx;
    }


}
