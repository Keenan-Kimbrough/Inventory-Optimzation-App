package Controller_Views;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;


/***
 * This Class modifies the selected Product,then updates the current Product.
 * @author Keenan Kimbrough
 *
 */
public class ModifyProductController implements Initializable {

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
    public int productId;
    private Product selectedProduct;
    private Product modifyProduct;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    @Override
    /**
     This method Initializes the Modify Product Form.
     @param location - This is the location
     @param resources - This is the resources used
     */
    public void initialize (URL location, ResourceBundle resources){
        //for no associated parts.
        PartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartTableView.setItems(Model.Inventory.getAllParts());

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
     * This is the Save Product Method for the Modify Part Form.
     * This Method Saves the input of a Modified Product and adds it to Inventory, and opens the Main Form page after closing this one.
     *
     * @param actionEvent - This is the action Event of the Save Button being clicked on.
     * @throws IOException - This is the exception that may appear while the method runs.
     */
    @FXML void modifyProductSave(ActionEvent actionEvent) throws IOException {
        int productInv = Integer.parseInt(textInv.getText());
        int productMin = Integer.parseInt(textMin.getText());
        int productMax = Integer.parseInt(textMax.getText());
        if(MainController.confirmationDialogue("Save?", "Would you like to save this part?"))
            if(productMax < productMin) {
                MainController.informationDialog("Input Error", "Error in min and max field", "Check Min and Max value." );
            }
            else if(productInv < productMin || productInv > productMax) {
                MainController.informationDialog("Input Error", "Error in inventory field", "Inventory must be between Minimum and Maximum" );
            }
            else {
                this.modifyProduct = selectedProduct;
                selectedProduct.setId(Integer.parseInt(textId.getText()));
                selectedProduct.setName(textName.getText());
                selectedProduct.setStock(Integer.parseInt(textInv.getText()));
                selectedProduct.setProductPrice(Double.parseDouble(textPrice.getText()));
                selectedProduct.setMax(Integer.parseInt(textMax.getText()));
                selectedProduct.setMin(Integer.parseInt(textMax.getText()));
                selectedProduct.getAllAssociatedParts().clear();
                selectedProduct.addAssociatedPart(associatedParts);
                Model.Inventory.updateProduct(productId, selectedProduct);
                FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1750, 1000);
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
    }

    /**
     * This is the set Product Method.
     * This Method is used to set the attributes of the current project to this Modify product form
     * @param selectedProduct- This is the Selected product that is chosen.
     */
    public void setProduct(Product selectedProduct){
        this.selectedProduct = selectedProduct;
        productId = Model.Inventory.getAllProducts().indexOf(selectedProduct);
        textId.setText(Integer.toString(selectedProduct.getId()));
        textName.setText(selectedProduct.getName());
        textInv.setText(Integer.toString(selectedProduct.getStock()));
        textPrice.setText(Double.toString(selectedProduct.getProductPrice()));
        textMax.setText(Integer.toString(selectedProduct.getMax()));
        textMin.setText(Integer.toString(selectedProduct.getMin()));
        associatedParts.addAll(selectedProduct.getAllAssociatedParts());
    }
    /**
     *  This it the Cancel method for the Modify Product Form.
     *  This method cancels the Modify Product Form and renders the main page form again.
     *
     * @param actionEvent - This is action event Parameter, the action event that is used is the cancel button being clicked.
     * @throws IOException - his is the exception that may appear while the method runs.
     */
    public void modifyProductsCancel(ActionEvent actionEvent) throws IOException {
        if (MainController.confirmationDialogue("You sure you want to cancel?", " You sure?")) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1750, 1000);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This is the Add method.
     * This button adds the selected part from the Parts Table view to the associated Part Table.
     *
     * @param actionEvent - The action Event is the Add Button being clicked.
     */
    public void onAdd(ActionEvent actionEvent) {
        Part selectedPart = PartTableView.getSelectionModel().getSelectedItem();
        if(selectedPart != null) {
            associatedParts.add(selectedPart);
            updatedAssociatedPartTable();
        }
        else {
            MainController.informationDialog("Select a part","Select a part", "Select a part to add to the Product");
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
            PartTableView.setItems(foundParts);
            AssociatedPartsTableview.setItems(foundParts);
        }
    }
}


