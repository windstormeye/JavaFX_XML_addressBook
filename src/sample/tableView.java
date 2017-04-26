package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.dom4j.Node;

/**
 * Created by incloud on 2017/4/4.
 */
public class tableView extends TableView {
    public void TablePane() {

        //列表的列
        TableColumn nameColumn = new TableColumn();
        //列名
        nameColumn.setText("姓名");
        //列宽
        nameColumn.setMinWidth(100);
        //该列绑定的数据

        TableColumn genderColumn = new TableColumn();
        genderColumn.setText("性别");
        genderColumn.setMinWidth(100);

        TableColumn ageColumn = new TableColumn();
        ageColumn.setText("年龄");
        ageColumn.setMinWidth(100);

        TableColumn phoneColumn = new TableColumn();
        phoneColumn.setText("电话");
        phoneColumn.setMinWidth(100);

        this.getColumns().addAll(nameColumn, genderColumn, ageColumn, phoneColumn);
    }
}
