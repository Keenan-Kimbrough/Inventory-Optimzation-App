package Controller_Views;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/***
 * This Class creates the Add Product Form, this class it adds a product to the Inventory list All Products
 * @author Keenan Kimbrough
 *
 */
public class AddProductController  implements Initializable {
    public TextField textMax;
    public TextField textPrice;
    public TextField textInv;
    public TextField textName;
    public TextField textId;
    public TextField textMin;
    public TextField partsSearchField;
    @FXML
    private TableView<Part> PartTableView;
    @FXML
    private TableColumn<Part, Integer> PartsIDColumn;
    @FXML
    private TableColumn<Part, String> PartsNameColumn;
    @FXML
    private TableColumn<Part, Integer> PartsInventoryColumn;
    @FXML
    private TableColumn<Part, Double> PartsCostColumn;
    @FXML
    private TableView<Part> AssociatedPartsTableview;
    @FXML
    private TableColumn<Product, Integer> AssociatedPartsIDColumn;
    @FXML
    private TableColumn<Product, String> AssociatedPartsNameColumn;
    @FXML
    private TableColumn<Product, Integer> AssociatedPartsInventoryColumn;
    @FXML
    private TableColumn<Product, Double> AssociatedPartsCostColumn;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private ObservableList<Part> originalParts = FXCollections.observableArrayList();

    /**
     This method Initializes the Add Product Form.
     @param location - This is the location
     @param resources - This is the resources used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        originalParts = Model.Inventory.getAllParts();

        //for no associated parts.
        PartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartTableView.setItems(originalParts);

        //for associated parts
        AssociatedPartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        AssociatedPartsTableview.setItems(associatedParts);

        updatePartTable();
        updatedAssociatedPartTable();
    }

    /**
     * This is the get new ID method.
     * This method gets a new ID once it is called.
     * @return -This returns  and int of New ID.
     */
    private int getNewId() {
        int newId = 1;
        for (int i = 0; i < Model.Inventory.getAllProducts().size(); i++) {
            if (Model.Inventory.getAllProducts().get(i).getId() == newId) {
                newId++;
            }
        }
        return newId;
    }

    /**
     * This is the Add method.
     * This button adds the selected part from the Parts Table view to the associated Part Table.
     *
     * @param actionEvent - The action Event is the Add Button being clicked.
     */
    public void onAdd(ActionEvent actionEvent) {
        Part selectedPart = PartTableView.getSelectionModel().getSelectedItem();
        if(selectedPart != null){
            associatedParts.add(selectedPart);
            updatePartTable();
            updatedAssociatedPartTable();
        }
        else {
            MainController.informationDialog("select a part", "Part", " add a part to the product");
        }
    }

    /**
     * This is the Delete method.
     * This Button deletes the selected Part from the associated Parts Table.
     *
     * @param actionEvent - The action Event is the Delete Button being clicked.
     */
    public void onDelete(ActionEvent actionEvent) {
        Part selectedPart = AssociatedPartsTableview.getSelectionModel().getSelectedItem();
        if(selectedPart != null){
            MainController.confirmationDialogue("Delete Parts", " Do you wna to delete this part" + selectedPart.getName() + "from the list?");
            associatedParts.remove(selectedPart);
            updatePartTable();
            updatedAssociatedPartTable();
        }
        else {
            MainController.informationDialog("There is no selection"," Selection N/A", " Please choose a item to remove");
        }
    }

    /**
     *  This it the Cancel method for the Add Product Form.
     *  This method cancels the Add Product Form and renders the main page form again.
     *
     * @param actionEvent - This is action event Parameter, the action event that is used is the cancel button being clicked.
     * @throws IOException - his is the exception that may appear while the method runs.
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        if(MainController.confirmationDialogue("Cancel","You Sure?")){
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1750, 1000);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
/**
 * -- Future Feature ---
 * that would be cool would be to have a seperate form to see which associated parts would
 * make the best product for sale.
 */

    /** -- RUN TIME ERROR---
     * Inside the OnSave method on the second if statement.
     * I had a run time error when I didn't make a gettext for text min. So when I tried to submit the Product it caused an error.
     * I ended up adding all the different possible input to correct this issue.
     */
    /**
     * This is the Save Product Method for the Add Part Form.
     * This Method Saves the input of a new Product and adds it to Inventory, and opens the Main Form page after closing this one.
     *
     * @param actionEvent - This is the action Event of the Save Button being clicked on.
     * @throws IOException - This is the exception that may appear while the method runs.
     */
    public void onSave(ActionEvent actionEvent) throws IOException {
        Integer inv = Integer.parseInt(this.textInv.getText());
        Integer min = Integer.parseInt(this.textMin.getText());
        Integer max = Integer.parseInt(this.textMax.getText());

        if(associatedParts.isEmpty()){
            MainController.informationDialog("input Error", " Please add another part", " A product must have a part associated with it");
            return;
        }
        if(textName.getText().isEmpty() || textMin.getText().isEmpty()  || textMax.getText().isEmpty() || textPrice.getText().isEmpty() || textInv.getText().isEmpty()){
            MainController.informationDialog("Input Error", " need to have text in fields, can not be blank", " check all fields and try again.");
            return;
        }
        if( inv < min || inv > max){
            MainController.informationDialog(" input error", " Error in inventory field", " Inventory must be  different");
            return;
        }
        if( max < min) {
            MainController.informationDialog(" input error", " Error in inventory field", " check inventory again");
            return;
        }
        if(MainController.confirmationDialogue(" Would you like to save?", " Would you like to save this?")){
            Product product = new Product();
            product.setId(getNewId());
            product.setName(this.textName.getText());
            product.setStock(Integer.parseInt(this.textInv.getText()));
            product.setMin(Integer.parseInt(this.textMin.getText()));
            product.setMax(Integer.parseInt(this.textMax.getText()));
            product.setProductPrice(Double.parseDouble(this.textPrice.getText()));
            product.addAssociatedPart(associatedParts);
            Model.Inventory.addProduct(product);

            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1750, 1000);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

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
        ObservableList <Part> foundParts = Model.Inventory.getPartResultHandler(partValue);
        if(foundParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("NO MATCH");
            alert.setHeaderText("Unable to locate a Part name with: " + partValue);
            alert.showAndWait();
        } else {
            PartTableView.setItems(foundParts);
            AssociatedPartsTableview.setItems(foundParts);

        }
    }


    /**
     * This is the Update Associated Parts Table Method.
     * This Method updates the current Associated Parts Table View wih the current associated Parts.
     */
    public void updatedAssociatedPartTable () {
        AssociatedPartsTableview.setItems(associatedParts);
}

    /**
     * This is the Update Parts Table Method.
     * This Method updates The current Part Table View with the Current Inventory.
     */
    public void updatePartTable() {
        PartTableView.setItems(Model.Inventory.getAllParts());


    }

}
