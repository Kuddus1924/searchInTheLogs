package thread;

import Builder.MyNote;
import Builder.TreeBuilder;
import javafx.application.Platform;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import search.SearchInDirectory;

import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SearchThread extends Thread{
    private String path;
    private String search;
    private String expansion;
    private TreeView<MyNote> tree;
    public  SearchThread(String path,String search,String expansion,TreeView<MyNote> tree)
    {
        this.path = path;
        this.search = search;
        this.expansion = expansion;
        this.tree = tree;
    }
    @Override
    public void run()
    {
        SearchInDirectory searchInDirectory = new SearchInDirectory(path);
        List<Path> list = searchInDirectory.search(search,expansion);
        if(list == null || list.size() == 0) {
            JOptionPane.showMessageDialog(null, "\"Ничего не найдено\"");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    tree.setRoot(new TreeItem<MyNote>(new MyNote(null,"Ничего не найдено")));
                }
            });
            return;
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tree.setRoot(TreeBuilder.build(list,Paths.get(path)));
            }
        });
        JOptionPane.showMessageDialog(null, "Найдено " + list.size() + " файлов");
    }

}
