package search;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class SearchInFile {
    static boolean seach(String stirng,Path file)
    {
        boolean flag = false;
        try {
            flag = Files.lines(file, StandardCharsets.US_ASCII).allMatch((s) -> -1 == s.lastIndexOf(stirng));
        }
        catch (Exception e)
        {
            return flag;
        }
        return !flag;
    }

}
