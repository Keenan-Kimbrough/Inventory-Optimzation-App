package Controller_Views;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import  javafx.scene.control.Button ;


/***
 * This Main Class.
 * This class is the main form.
 * @author Keenan Kimbrough
 *
 */
public class MainController implements Initializable {

    //connects the controller fx Id to the public class
    public TableView<Part> partsTable;
    public TableColumn<Part, Integer> partIDCol;
    public TableColumn<Part, String> partNameCol;
    public TableColumn<Part, Integer> partInventoryCol;
    public TableColumn<Part, Double> partPriceCol;
    public TableView<Product> productsTable;
    public TableColumn<Product, Integer> productIDCol;
    public TableColumn<Product, String>productNameCol;
    public TableColumn<Product, Integer> productInventoryCol;
    public TableColumn<Product, Double> productPriceCol;
    public TextField queryTF;
    public Button closeButton;
    public TextField productSearchField;
    public TextField partsSearchField;

    /**
     This method Initializes the Modify Part Form.
     @param resourceBundle - This is the resourced used
     @param url - This is the URl used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Init the columns by setting the value factory, basically attaching it
        partsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("Id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        // Init the columns by setting the value factory, basically attaching it
        productsTable.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    /***
     *This is the Add Part Method.
     * This Method Opens the Add Part Form.
     * @param actionEvent - action event is the Add button being clicked.
     * @throws IOException- Handles the exception.
     */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddPartController.class.getResource("addPart-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1750, 1000);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    /**
     * This is the Update Parts Table Method.
     * This Method updates The current Part Table View with the Current Inventory.
     */
    public void updatePartTable() {
        partsTable.setItems(Model.Inventory.getAllParts());
    }
    /**
     * This is the Modify part Method.
     * This Method opens the Modify Part Form.
     * @param actionEvent - This action is based off The modify Part button being clicked.
     * @throws IOException - This handles the exceptions for the method.
     */
    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        try {
            Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(ModifyPartController.class.getResource("modifyPart-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1750, 1000);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            ModifyPartController controller = fxmlLoader.getController();
            controller.setPart(selectedPart);
        } catch(IOException e) {
            System.Logger logger = System.getLogger(getClass().getName());
            logger.log(System.Logger.Level.WARNING, "Failed to Create New Window");
        }
    }
    /**
     * This is the Delete Part Method.
     * This Method Deletes the selected Part from the Inventory.
     * @param actionEvent - The Action event is the Delete Button being clicked.
     * @throws IOException - This handles the exception for the method.
     */
    public void onDeletePart(ActionEvent actionEvent) throws IOException {

        if (partsTable.getSelectionModel().isEmpty()) {
            informationDialog("Warning!!!!", "No Part Selected", "Please choose the correct Part from the  list");
            return;
        }
        if (confirmationDialogue("Warning!!!", "Would you like to delete this Part?")) {
            int selectedPart = partsTable.getSelectionModel().getSelectedIndex();
            Model.Inventory.getAllParts().remove(selectedPart);
           updatePartTable();

        }

    }

    /**
     * This the add product Method.
     * This method opens up the Add Product Form.
     * @param actionEvent - This Action event is the Add Product being clicked.
     * @throws IOException - This handles the exception for the method.
     */
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddProductController.class.getResource("addProduct-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1750, 1000);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    /**
     * This is the modifyProduct Method.
     * This Method opens the Modify Product Form.
     * @param actionEvent - This action is based off The modify product button being clicked.
     * @throws IOException - This handles the exceptions for the method.
     */
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {

        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

            if (selectedProduct == null) {
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(ModifyPartController.class.getResource("modifyProduct-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1750, 1000);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            ModifyProductController  controller = fxmlLoader.getController();
            controller.setProduct(selectedProduct);

        }

    /**
     * This is the Delete Product Method.
     * This Method Deletes the selected Product from the Inventory.
     * @param actionEvent - The Action event is the Delete Button being clicked.
     * @throws IOException - This handles the exception for the method.
     */
    public void onDeleteProduct(ActionEvent actionEvent) throws IOException {
        int selectedProductIndex = productsTable.getSelectionModel().getSelectedIndex();

        if (productsTable.getSelectionModel().isEmpty()) {
            informationDialog("Warning!!!!", "No Product Selected", "Please choose the correct Product from the  list");
            return;
        }

        System.out.println(productsTable.getItems().get(selectedProductIndex).getAllAssociatedParts().isEmpty());
        System.out.println(selectedProductIndex);

        if(productsTable.getItems().get(selectedProductIndex).getAllAssociatedParts().isEmpty()) {

            if (confirmationDialogue("Warning!!!", "Would you like to delete this Product?")) {

                productsTable.getItems().remove(selectedProductIndex);



            }
        }
            else {
            informationDialog("Warning!!!!", "Can not Delete, Product has Associated Parts", "This product has associated parts. Can not delete");

        }
        }

    /**
     * This is the confirmation Dialogue Method.
     * This method Opens up a new alert to which the alert type of confirmation.
     * @param title - title of confirmation Dialogue.
     * @param content - Content of Confirmation Dialogue.
     * @return This method returns a Boolean of either true or false.
     */
    static Boolean confirmationDialogue(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This is the information Dialog Method.
     * This Method opens up an Alert Window.
     *
     * @param title - Title of Alert.
     * @param header - Header of Alert.
     * @param content - Content of Alert.
     */
    static void informationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * This is the exit Method
     * This method exits the Application
     */
    public void closeButton() {
        // get a handle to the stage
        confirmationDialogue("Exit", " Do you wan to exit this application?");
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    /**
     * This is the search Product Method.
     * This method searches the Products, based on what is typed in the input
     *
     * @param keyEvent - The parameter is key typed which initiates this method to work on each key, dynamically.
     */
    public void searchProduct(KeyEvent keyEvent){
        String temporary = productSearchField.getText();

        ObservableList <Product> foundProducts = Inventory.getProductResultHandler(temporary);
        if(foundProducts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("NO MATCH");
            alert.setHeaderText("Unable to locate a Product name with: " + temporary);
            alert.showAndWait();
        } else {
            productsTable.setItems(foundProducts);
        }
    }
    /**
     * This is the search Part Method.
     * This method searches the Parts Table and the Associated parts Table for the desired search, based on what is typed in the input
     *
     * @param keyEvent - The parameter is key typed which initiates this method to work on each key, dynamically.
     */
    public void searchPart(KeyEvent keyEvent){
        String partValue = partsSearchField.getText();

        ObservableList <Part> foundParts = Inventory.getPartResultHandler(partValue);
        if(foundParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("NO MATCH");
            alert.setHeaderText("Unable to locate a Part name with: " + partValue);
            alert.showAndWait();
        } else {
            partsTable.setItems(foundParts);
        }
        }

    }





