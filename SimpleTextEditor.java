import java.util.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;

public class SimpleTextEditor extends Group implements Editor {
  public ReadonlyBooleanProperty isModifiedProperty() {
    return isModified;
  }
  
  public ObservableList<MenuItem> getMenus() {
    return menus;
  }
  
  
  final BooleanProperty isModified = new BooleanProperty(false);
  final ObservableList<MenuItem> menus = FXCollections.observableList(new ArrayList<>());
}