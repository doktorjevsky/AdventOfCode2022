package aoc7;

public class DirectoryFile {

    private String name;
    private long size;

    public DirectoryFile(String name, long size){
        this.name = name;
        this.size = size;
    }

    public long getSize() {
        return size;
    }

}
