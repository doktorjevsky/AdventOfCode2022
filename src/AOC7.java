import aoc7.Directory;
import aoc7.DirectoryFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AOC7 {

    private static Scanner scanner;

    public static void main(String[] args) throws FileNotFoundException {
        scanner = AocTemplate.getScanner(args[0]);
        Directory directory = new Directory("HEAD", null);
        directory.addChild(new Directory("/", directory));
        List<String> instructions = new ArrayList<>();

        while(scanner.hasNextLine()){
            instructions.add(scanner.nextLine());
        }

        Directory loaded = initDirectory(directory, instructions);
        aoc7a(loaded);
        aoc7b(loaded);
    }


    // SOLUTIONS

    private static void aoc7a(Directory d){
        long sum = d.sumOfDirSizesMaxN(100000);
        System.out.println("Part 1: " + sum);
    }

    private static void aoc7b(Directory d){
        List<Long> sizes = new ArrayList<>();
        setAllSizes(d, sizes);
        long unused = 70000000 - d.getSize();
        long required = 30000000 - unused;
        long smallest = Long.MAX_VALUE;
        for (Long size : sizes){
            if (size >= required){
                smallest = Math.min(smallest, size);
            }
        }
        System.out.println("Part 2: " + smallest);

    }

    private static void setAllSizes(Directory d, List<Long> sizes){
        sizes.add(d.getSize());
        if (d.getChildren().size() != 0){
            for (Directory child : d.getChildren()){
                setAllSizes(child, sizes);
            }
        }
    }


    // PARSING

    private static Directory initDirectory(Directory head, List<String> instructions){
        Directory current = head;
        int pointer = 0;
        int endOfFile = instructions.size();

        while (pointer < endOfFile){
            String instruction = instructions.get(pointer);
            if (isCmd(instruction)){
                String[] cmd = instruction.split(" ");
                if (cmd[1].equals("ls")){
                    pointer++;
                    while (pointer < endOfFile && !isCmd(instructions.get(pointer))){

                        String[] content = instructions.get(pointer).split(" ");
                        if (content[0].equals("dir")){
                            current.addChild(new Directory(content[1], current));
                        } else {
                            current.addFile(new DirectoryFile(content[1], Integer.parseInt(content[0])));
                        }
                        pointer++;
                    }
                } else {

                    if (cmd[2].equals("..")){
                        current = current.getParent();
                    } else {
                        Directory child = current.getChild(cmd[2]);
                        if (child == null){
                            current.addChild(new Directory(cmd[2], current));
                        }
                        current = current.getChild(cmd[2]);
                    }
                    pointer++;
                }
            }
        }
        return head;
    }

    private static boolean isCmd(String instruction){
        return instruction.charAt(0) == '$';
    }

}
