package search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchInDirectory {
    public ArrayList<ArrayList<String>> tree;
    public String directory;
    public SearchInDirectory(String directory)
    {
        this.directory = directory;
        tree = new ArrayList<>();
    }
    public  List<Path> search(String string,String extension)
    {
        List<Path> streamFromFiles = null;
        try {
             streamFromFiles = Files.walk(Paths.get(directory))
                    .filter(Files::isRegularFile)
                    .filter(s -> s.getFileName().toString().endsWith(extension))
                    .filter(s -> SearchInFile.seach(string,s))
                    .collect(Collectors.toList());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return streamFromFiles;
    }



}
