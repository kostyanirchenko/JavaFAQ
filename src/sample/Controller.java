package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import models.Functions;
import models.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {

    public TextField searchField;
    public TextArea functionInfo;
    private Main main;
    
    public Button exitButton;
    public Label functionPackage;
    public Label functionName;
    public TableView<Functions> infoTable;
    public TableColumn<Functions, String> functionNameColumn;
    public TableColumn<Functions, String> functionPackageColumn;
    public TableColumn<Functions, String> functionShortInfoColumn;
    private ObservableList<Functions> functionsList = FXCollections.observableArrayList();

    public Controller() {
        try {
            Database.setConnection();
            Database.setStatement();
            Database.setResultSet(Database.select("functions", "", ""));
            ResultSet main = Database.getResultSet();
            String fullInfo = "";
            while(main.next()) {
                Database.setStatement();
                Database.setResultSet(Database.select("info", "functionId", main.getString("id")));
                ResultSet info = Database.getResultSet();
                while(info.next()) {
                    fullInfo = info.getString("info");
                }
                info.close();
                functionsList.add(new Functions(main.getString("functionName"),
                        main.getString("package"),
                        main.getString("shortInfo"),
                        fullInfo));
            }
            main.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        functionNameColumn.setCellValueFactory(cellData -> cellData.getValue().functionNameProperty());
        functionPackageColumn.setCellValueFactory(cellData -> cellData.getValue().functionPackageProperty());
        functionShortInfoColumn.setCellValueFactory(cellData -> cellData.getValue().functionShortInfoProperty());
        FilteredList<Functions> filteredData = new FilteredList<>(functionsList, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(functions -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseData = newValue.toLowerCase();
                if (functions.getFunctionName().toLowerCase().contains(lowerCaseData)) {
                    return true;
                } else if (functions.getFunctionShortInfo().toLowerCase().contains(lowerCaseData)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Functions> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(infoTable.comparatorProperty());
        infoTable.setItems(sortedList);
        showFunctionDetails(null);
        infoTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showFunctionDetails(newValue)));
    }

    public ObservableList<Functions> getFunctions() {
        return functionsList;
    }

    private void showFunctionDetails(Functions functions) {
        if(functions != null) {
            functionName.setText(functions.getFunctionName());
            functionPackage.setText(functions.getFunctionPackage());
            functionInfo.setText(functions.getFunctionFullInfo());
        } else {
            functionName.setText("");
            functionPackage.setText("");
            functionInfo.setText("");
        }
    }

    public void exitAction(ActionEvent actionEvent) {
        Database.close();
        Database.closeConnection();
        main.close();
    }

    public void setMain(Main main) {
        this.main = main;

    }
}
