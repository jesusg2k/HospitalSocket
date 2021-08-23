package archivos.interfaz.TableCell;

import javafx.scene.control.TableCell;

public class HabilitadoTableCell<T> extends TableCell<T, Boolean> {

    @Override
    protected void updateItem(Boolean item, boolean empty){
        if(item!=null){
            super.setText((item)?"Si":"No");
        }else{
            super.setText("");
        }
        super.updateItem(item, empty);
    }

}
