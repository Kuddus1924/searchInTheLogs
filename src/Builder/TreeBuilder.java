package Builder;
import javafx.scene.control.TreeItem;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class TreeBuilder {
    public  static TreeItem<MyNote> build(List<Path> pathList,Path root)
    {
           TreeItem<MyNote> rootTreeNode = new TreeItem<>(new MyNote(null,root.toString()));
           HashMap<String,TreeItem<MyNote> > map = new HashMap<>();
           for(int i = 0; i < pathList.size(); i++)
           {
               String str = root.relativize(pathList.get(i)).toString();
               String[] mas = str.split("\\\\");
               for(int j = 0; j < mas.length; j++)
               {
                   if((j == 0) &&  mas.length != 1)
                   {
                       if(!map.containsKey(mas[j])) {
                           TreeItem<MyNote> tmp = new TreeItem<>(new MyNote(null,mas[j]));
                           map.put(mas[j], tmp);
                           rootTreeNode.getChildren().add(tmp);
                       }
                   }
                   else if(mas.length - 1 == j)
                   {
                       if(j == 0)
                       {
                           TreeItem<MyNote> tmp = new TreeItem<>(new MyNote(pathList.get(i),mas[j]));
                           rootTreeNode.getChildren().add(tmp);
                       }
                       else
                       {
                           TreeItem<MyNote> tmp = new TreeItem<>(new MyNote(pathList.get(i),mas[j]));
                           map.get(sum(mas,j - 1)).getChildren().add(tmp);
                       }
                   }
                   else
                   {
                       if(!map.containsKey(sum(mas,j))) {
                           TreeItem<MyNote> tmp = new TreeItem<>(new MyNote(null,mas[j]));
                           map.put(sum(mas,j), tmp);
                           map.get(sum(mas,j - 1)).getChildren().add(tmp);
                       }
                   }
               }
           }
           return rootTreeNode;
    }
    private static  String sum(String[] mas,int index)
    {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < index; i++)
        {
            str.append(mas[i]);
            str.append(".");
        }
        str.append(mas[index]);
        return str.toString();
    }

}
