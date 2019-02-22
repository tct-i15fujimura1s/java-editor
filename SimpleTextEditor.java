import java.util.*;
import java.io.*;
import java.nio.files.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;


public class SimpleTextEditor extends Group implements Editor {
  @Override
  public ReadonlyBooleanProperty isModifiedProperty() {
    return isModified;
  }
  
  @Override
  public ReadonlyBooleanProperty isOpenProperty() {
    return isOpen;
  }
  
  @Override
  public ObservableList<MenuItem> getMenus() {
    return menus;
  }
  
  @Override
  public boolean open(Path path) {
    if(!close()) return false;
    FileChooser chooser = new FileChooser();
    File file = chooser.showOpenDialog(getScene().getWindow());
    if(file == null) return false;
    path.set(file.toPath());
    return true;
  }
  
  @Override
  public boolean close() {
    if(isModified.getValue() && !save()) return false;
    //TODO: clear textarea
  }
  
  @Override
  public boolean save() {
    if(!isOpen.getValue()) {
      FileChooser chooser = new FileChooser();
      
    }
  }
  
  final ObjectProperty<Path> path = new SimpleObjectProperty(null);
  final BooleanProperty isModified = new BooleanProperty(false);
  final BooleanProperty isOpen = path.isNotNull();
  final ObservableList<MenuItem> menus = FXCollections.observableList(new ArrayList<>());
  
}