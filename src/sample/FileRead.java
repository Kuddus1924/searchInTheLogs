package sample;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class FileRead {
    private Path file;
    private int size = 30;
    private int start;
    private int end;
    private boolean isEnd = true;
    private boolean isFirst = true;
    public FileRead(Path path)
    {
        file = path;
        start = 0;
    }
    private String getString(List<String> list)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < list.size(); i++)
        {
            stringBuilder.append(list.get(i) + "\n");
        }
        return stringBuilder.toString();
    }
    public String next() {
        if (isEnd) {
            try {
                if(end < 0)
                    end = size;
                start = end;
                List<String> list = Files.lines(file, StandardCharsets.UTF_8).skip(start).limit(size).collect(Collectors.toList());
                if (list.size() != size) {
                    isEnd = false;
                }
                else
                {
                    end += list.size();
                }
                return getString(list);

            } catch (IOException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        return null;
    }
    public String prev() {
        if (start >= 0) {
            try {
                start -= size;
                if (start < 0) {
                    start = 0;
                }
                List<String> list = Files.lines(file, StandardCharsets.UTF_8).skip(start).limit(size).collect(Collectors.toList());
                if (isEnd == false) {
                    isEnd = true;
                }
                end = start + size;

                return getString(list);

            } catch (IOException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        return null;
    }
    public void copyAll()
    {
        try {
            List<String> list = Files.lines(file, StandardCharsets.UTF_8).collect(Collectors.toList());
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(getString(list));
            clipboard.setContent(content);
        }
        catch (IOException e)
        {

        }
    }
    public Path getFile() {
        return file;
    }
}
