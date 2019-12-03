package sample;

import Builder.MyNote;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

import javax.xml.transform.Result;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashMap;

public class FileController {
    private HashMap<Tab,FileRead> map = new HashMap<>();
    private HashMap<Path,Tab> mapC = new HashMap<>();
    private Button next;
    private Button prev;
    private Button copyAll;
    private TabPane tabPane;

    public FileController(TabPane pane,Button prev, Button next, Button copyAll)
    {
        tabPane = pane;
        this.copyAll = copyAll;
        this.prev = prev;
        this.next = next;
        this.next.setOnAction(actionEvent -> next());
        this.prev.setOnAction(actionEvent -> prev());
        this.copyAll.setOnAction(actionEvent -> copyAll());

    }
    public void next()
    {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        String result = map.get(tab).next();
        if(result != null) {
            TextArea txt = new TextArea(result);
            tab.setContent(txt);
        }

    }
    public void prev(){
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        String result = map.get(tab).prev();
        if(result != null) {
            TextArea txt = new TextArea(result);
            tab.setContent(txt);
        }

    }
    public void copyAll()
    {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        map.get(tab).copyAll();
    }
    public void getTab(Path file)
    {
        if(mapC.containsKey(file))
        {
            tabPane.getSelectionModel().select(mapC.get(file));
            return;
        }
        FileRead fileRead = new FileRead(file);
        Tab tabs = new Tab(file.getFileName().toString());
        map.put(tabs,fileRead);
        mapC.put(file,tabs);
        tabs.setOnClosed(event -> {close(tabs);});
        String result = map.get(tabs).next();
        if(result != null) {
            TextArea txt = new TextArea(result.toString());
            tabs.setContent(txt);
        }
        tabPane.getTabs().add(tabs);
        setButton(true);
    }
    public void close(Tab tab)
    {
        FileRead read = map.get(tab);
        map.remove(tab);
        mapC.remove(read.getFile());
        if(tabPane.getTabs().size() == 0)
        {
            setButton(false);
        }
    }
    public void setButton(boolean flag)
    {
        prev.setVisible(flag);
        next.setVisible(flag);
        copyAll.setVisible(flag);
    }




}
