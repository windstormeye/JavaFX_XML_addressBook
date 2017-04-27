package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import javafx.stage.Modality;
import javafx.scene.layout.VBox;


import java.util.Iterator;
import java.util.List;


public class Main extends Application {
    private final TableView<xmlMsg> table = new TableView<>();
    private final ObservableList<xmlMsg> data =
            FXCollections.observableArrayList(
                    new xmlMsg("", "", "", "")
            );
    private Document document;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // 表格布局
        GridPane gridOfHome = new GridPane();
        // 设置各个控件间的水平距离
        gridOfHome.setHgap(10);
        // 设置各个控件间的垂直距离
        gridOfHome.setVgap(10);
        gridOfHome.setPadding(new Insets(10, 10, 0, 10));

        stage.setTitle("班级同学通讯录");
        stage.setScene(new Scene(gridOfHome, 460, 450));

        TextField searchTex = new TextField();
        searchTex.setPromptText("输入需要查找的内容");
        gridOfHome.add(searchTex, 0, 0);

        Button searchBtn = new Button("搜索");
        gridOfHome.add(searchBtn, 1, 0);
        searchBtn.setOnAction(event -> {
            Label msgLabel = new Label("该同学的信息如下");
            msgLabel.setFont(new Font("Arial", 20));
            Label nameLabel = new Label();
            Label genderLabel = new Label();
            Label ageLabel = new Label();
            Label phoneLabel = new Label();

            XMLHandler xmlManager = new XMLHandler();
            Element targetElement = xmlManager.searchNodeMsg(document, searchTex.getText());
            for (Iterator it = targetElement.elementIterator(); it.hasNext();) {
                Element node = (Element) it.next();
                switch (node.getName()) {
                    case "name":
                        nameLabel.setText("姓名: " + node.getText()); break;
                    case "gender":
                        genderLabel.setText("性别: " + node.getText()); break;
                    case "age":
                        ageLabel.setText("年龄: " +  node.getText()); break;
                    case "phone":
                        phoneLabel.setText("电话: " + node.getText()); break;
                }
            }

            Stage window = new Stage();
            window.setTitle("提示");
            //modality要使用Modality.APPLICATION_MODEL
            window.initModality(Modality.APPLICATION_MODAL);
            window.setMinWidth(200);
            window.setMinHeight(200);

            Button btn = new Button("确定");
            btn.setOnAction(aaa -> window.close());

            VBox vBox = new VBox();
            vBox.getChildren().addAll(nameLabel, genderLabel, ageLabel, phoneLabel);
            vBox.setSpacing(5);

            GridPane gridOfDelete = new GridPane();
            gridOfDelete.setPadding(new Insets(25, 10, 25, 25));
            gridOfDelete.add(msgLabel, 0, 0);
            gridOfDelete.add(vBox, 0, 1);
            gridOfDelete.add(btn, 1 , 2);

            Scene scene = new Scene(gridOfDelete);
            window.setScene(scene);
            //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
            window.showAndWait();
        });

        table.setEditable(true);
        table.setPrefSize(200, 350);
        gridOfHome.add(table, 0, 1);

        Callback<TableColumn<xmlMsg, String>,
                TableCell<xmlMsg, String>> cellFactory
                = (TableColumn<xmlMsg, String> p) -> new EditingCell();

        TableColumn<xmlMsg, String> nameCol =
                new TableColumn<>("姓名");
        TableColumn<xmlMsg, String> genderCol =
                new TableColumn<>("性别");
        TableColumn<xmlMsg, String> ageCol =
                new TableColumn<>("年龄");
        TableColumn<xmlMsg, String> phoneCol =
                new TableColumn<>("电话");
        // 姓名
        nameCol.setMinWidth(70);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(cellFactory);
        nameCol.setOnEditCommit(
                (CellEditEvent<xmlMsg, String> t) -> {
                    ((xmlMsg) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                });
        // 性别
        genderCol.setMinWidth(50);
        genderCol.setCellValueFactory(
                new PropertyValueFactory<>("gender"));
        genderCol.setCellFactory(cellFactory);
        genderCol.setOnEditCommit(
                (CellEditEvent<xmlMsg, String> t) -> {
                    ((xmlMsg) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setGender(t.getNewValue());
                });
        // 年龄
        ageCol.setMinWidth(50);
        ageCol.setCellValueFactory(
                new PropertyValueFactory<>("age"));
        ageCol.setCellFactory(cellFactory);
        ageCol.setOnEditCommit(
                (CellEditEvent<xmlMsg, String> t) -> {
                    ((xmlMsg) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setAge(t.getNewValue());
                });
        // 电话
        phoneCol.setMinWidth(153);
        phoneCol.setCellValueFactory(
                new PropertyValueFactory<>("phone"));
        phoneCol.setCellFactory(cellFactory);
        phoneCol.setOnEditCommit(
                (CellEditEvent<xmlMsg, String> t) -> {
                    ((xmlMsg) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setPhone(t.getNewValue());
                });

        table.setItems(data);
        table.getColumns().addAll(nameCol, genderCol, ageCol, phoneCol);

        final TextField addName = new TextField();
        addName.setPromptText("姓名");
        addName.setPrefWidth(70);

        final TextField addGender = new TextField();
        addGender.setPrefWidth(50);
        addGender.setPromptText("性别");

        final TextField addAge = new TextField();
        addAge.setPrefWidth(50);
        addAge.setPromptText("年龄");

        final TextField addPhone = new TextField();
        addPhone.setPrefWidth(100);
        addPhone.setPromptText("电话");

        final Button addButton = new Button("添加");
        addButton.setOnAction((ActionEvent e) -> {
            data.add(new xmlMsg(addName.getText(), addGender.getText(), addAge.getText(),
                    addPhone.getText()));
            XMLHandler xmlManager = new XMLHandler();
            xmlManager.addNode(document, addName.getText(), addGender.getText(), addAge.getText(),
                    addPhone.getText());
            addName.clear();
            addGender.clear();
            addAge.clear();
            addPhone.clear();
        });

        HBox hb = new HBox();
        hb.getChildren().addAll(addName, addGender, addAge, addPhone,addButton);
        hb.setSpacing(3);

        Button intoBtn = new Button("导入XML文件");
        intoBtn.setOnAction((ActionEvent e) -> {
            GridPane gridOfFile = new GridPane();
            gridOfFile.setAlignment(Pos.CENTER);
            // 设置各个控件间的水平距离
            gridOfFile.setHgap(10);
            // 设置各个控件间的垂直距离
            gridOfFile.setVgap(10);
            // 顶，gridOfResult，底部，左侧周围的内容填充
            gridOfFile.setPadding(new Insets(10, 10, 10, 10));

            Stage fileStage = new Stage();
            fileStage.setScene(new Scene(gridOfFile, 300, 275));
            fileStage.show();

            TextField text = new TextField();
            text.setPromptText("请输入需要导入的XML文件路径");
            text.setPrefWidth(300);
            gridOfFile.add(text, 0, 0);

            Button sureBtn = new Button("确定");
            gridOfFile.add(sureBtn, 0, 1);
            sureBtn.setOnAction((ActionEvent aaa) -> {
                data.clear();
                XMLHandler xmlManeger = new XMLHandler();
                document = xmlManeger.readFile(text.getText());

                List<org.dom4j.Node> nodes = xmlManeger.getNode(document, "/addressbook/person");
                for (Node node : nodes) {
                    data.add(new xmlMsg(node.selectSingleNode("name").getText(),
                            node.selectSingleNode("gender").getText(),
                            node.selectSingleNode("age").getText(),
                            node.selectSingleNode("phone").getText()));
                }
                stage.show();
                fileStage.close();
            });
        });

        // 给intoBtn和outputBtn设置垂直布局
        VBox vBox = new VBox();
        vBox.setSpacing(10);

        gridOfHome.add(vBox, 1, 1);

        Button outputBtn = new Button("导出XML文件");
        gridOfHome.add(outputBtn, 1, 1);
        outputBtn.setOnAction((ActionEvent e) -> {
            Stage window = new Stage();
            window.setTitle("提示");
            //modality要使用Modality.APPLICATION_MODEL
            window.initModality(Modality.APPLICATION_MODAL);
            window.setMinWidth(300);
            window.setMinHeight(150);

            Button button = new Button("好的");
            button.setOnAction(aaa -> window.close());
            XMLHandler xmlManager = new XMLHandler();
            Label msgLabel = new Label("保存的路径为:" + xmlManager.outputMethon(document));

            VBox layout = new VBox(10);
            layout.getChildren().addAll(msgLabel , button);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout);
            window.setScene(scene);
            //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
            window.showAndWait();
        });

        vBox.getChildren().addAll(intoBtn, outputBtn);

        table.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.BACK_SPACE){
                Stage window = new Stage();
                window.setTitle("提示");
                //modality要使用Modality.APPLICATION_MODEL
                window.initModality(Modality.APPLICATION_MODAL);
                window.setMinWidth(150);
                window.setMinHeight(150);

                Button cancelButton = new Button("取消");
                cancelButton.setOnAction(aaa -> window.close());
                Button sureButton = new Button("确定");
                sureButton.setOnAction(aaa -> {
                    data.remove(table.getSelectionModel().getSelectedIndex());
                    window.close();
                });
                Label msgLabel = new Label("确定要删除该列吗?");

                HBox hBox = new HBox();
                hBox.getChildren().addAll(cancelButton, sureButton);
                hBox.setSpacing(10);

                GridPane gridOfDelete = new GridPane();
                gridOfDelete.setAlignment(Pos.CENTER);
                gridOfDelete.add(msgLabel, 0, 0);
                gridOfDelete.add(hBox, 0, 1);

                Scene scene = new Scene(gridOfDelete);
                window.setScene(scene);
                //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
                window.showAndWait();
            }
        });

        gridOfHome.add(hb, 0, 2);
        stage.show();
    }
}
