import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;

public interface Editor {
  ObservableList<MenuItem> getMenus();
  ReadonlyBooleanProperty isModifiedProperty();
}