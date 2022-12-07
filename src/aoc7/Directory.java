package aoc7;

import java.util.ArrayList;
import java.util.List;

public class Directory {

    private String name;
    private Directory parent;
    private List<Directory> children;
    private List<DirectoryFile> files;
    private long fileSize;

    public Directory(String name, Directory parent){
        this.name = name;
        this.parent = parent;
        children = new ArrayList<>();
        files = new ArrayList<>();
        fileSize = 0;
    }

    public List<Directory> getChildren(){ return children; }

    public String getName(){ return name; }

    public long getSize(){
        return fileSize + getChildrenSize();
    }

    private long getChildrenSize(){
        long size = 0;
        for (Directory child : children){
            size += child.getSize();
        }
        return size;
    }

    public Directory getParent(){ return parent; }

    // TODO implement better
    public Directory getChild(String name){
        for (Directory child : children){
            if (child.getName().equals(name)){
                return child;
            }
        }
        return null;
    }

    public void addFile(DirectoryFile file){
        files.add(file);
        fileSize += file.getSize();
    }

    public void addChild(Directory directory){
        children.add(directory);
    }

    public long sumOfDirSizesMaxN(long n){
        long size = getSize() <= n ? getSize() : 0;
        for (Directory child : children){
            size += child.sumOfDirSizesMaxN(n);
        }
        return size;
    }


}
