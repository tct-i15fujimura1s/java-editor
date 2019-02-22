import java.nio.files.*;
import javafx.beans.binding.*;
import javafx.collections.*;
import javafx.scene.control.*;


public interface Editor {
  ObservableList<MenuItem> getMenus();
  BooleanExpression isModifiedProperty();
  BooleanExpression isOpenProperty();
  
  boolean open(Path path);
  boolean close();
  boolean save();
}