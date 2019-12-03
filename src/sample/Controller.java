package sample;

import Builder.MyNote;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import thread.SearchThread;

import javax.swing.*;
import java.io.File;

public class Controller {
    private File dir;
    private FileController fileController;
    @FXML
    private AnchorPane mainWindows;

    @FXML
    private SplitPane pane;

    @FXML
    private TreeView<MyNote> tree;

    @FXML
    private SplitPane tab;

    @FXML
    private TabPane tabs;

    @FXML
    private Button copyAll;

    @FXML
    private Button prev;

    @FXML
    private Button next;

    @FXML
    private Button search;

    @FXML
    private TextField expansion;

    @FXML
    private TextField stringSearch;

    @FXML
    private Button directory;
    @FXML
    void  initialize()
    {
        prev.setVisible(false);
        next.setVisible(false);
        copyAll.setVisible(false);
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        fileController = new FileController(tabs,prev,next,copyAll);
        SelectionModel<TreeItem<MyNote>> selectionModel = tree.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((changed,oldValue,newValue) -> {
            if(newValue != null) {
                if (newValue.getValue().getPath() != null) {
                    fileController.getTab(newValue.getValue().getPath());
                }
            }
        });
        final DirectoryChooser directoryChooser = new DirectoryChooser();
         directory.setOnAction(actionEvent -> {
             dir = directoryChooser.showDialog(tab.getScene().getWindow());
         });
         search.setOnAction(actionEvent ->
         {
             int lengthX = stringSearch.getText().length();
             int lengthY = expansion.getText().length();
             if (lengthY < 1)
             {
                 expansion.setText(".log");
             }
            if(lengthX < 1) {
                JOptionPane.showMessageDialog(null, "\"Некоторые поля не были заполнены\"");
            }
            else if(dir == null)
            {
                JOptionPane.showMessageDialog(null, "\"Не выбрана директория \"");
            }
            else
            {
                tree.setRoot(new TreeItem<MyNote>(new MyNote(null,"Идет поиск...")));
                SearchThread searchThread = new SearchThread(dir.getAbsolutePath(),stringSearch.getText(),expansion.getText(),tree);
                searchThread.start();
            }
         });


    }
}
