package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Kostya Nirchenko on 18.07.2015.
 */
public class Functions {

    private StringProperty functionName;
    private StringProperty functionPackage;
    private StringProperty functionShortInfo;
    private StringProperty functionFullInfo;

    public Functions(String functionName, String functionPackage, String functionShortInfo, String functionFullInfo) {
        this.functionName = new SimpleStringProperty(functionName);
        this.functionPackage = new SimpleStringProperty(functionPackage);
        this.functionShortInfo = new SimpleStringProperty(functionShortInfo);
        this.functionFullInfo = new SimpleStringProperty(functionFullInfo);
    }

    public String getFunctionName() {
        return functionName.get();
    }

    public StringProperty functionNameProperty() {
        return functionName;
    }

    public String getFunctionPackage() {
        return functionPackage.get();
    }

    public StringProperty functionPackageProperty() {
        return functionPackage;
    }

    public String getFunctionShortInfo() {
        return functionShortInfo.get();
    }

    public StringProperty functionShortInfoProperty() {
        return functionShortInfo;
    }

    public String getFunctionFullInfo() {
        return functionFullInfo.get();
    }
}
