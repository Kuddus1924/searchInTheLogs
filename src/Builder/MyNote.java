package Builder;

import java.nio.file.Path;

public class MyNote {
    private Path path;
    private String name;

    public MyNote(Path path,String name)
    {
        this.path = path;
        this.name = name;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return name;
    }
}
