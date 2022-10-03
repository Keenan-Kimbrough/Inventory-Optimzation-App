package Controller_Views;


import Model.InHouse;
import Model.OutSourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Model.Part;
import Model.Inventory;

import java.io.IOException;
import java.net.URL;
import java.text.BreakIterator;
import java.util.ResourceBundle;


/***
 * This Class modifies the selected Part,then updates the current Product.
 * @author Keenan Kimbrough
 *
 */
public class ModifyPartController implements Initializable {
    @FXML
    private TextField inputId;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputInv;
    @FXML
    private TextField inputPrice;
    @FXML
    private TextField inputMax;
    @FXML
    private TextField inputMin;
    @FXML
    private TextField inputMachineIDOrCompanyName;
    public TextField machineIdOrCompanyName;
    public RadioButton outSourced;
    public Part thisPart;
    public int partID;
    @FXML
    private RadioButton inHouse;
    @FXML
    private Label companyOrMachineID;
    /**
     This method Initializes the Modify Part Form.
     @param location - This is the location
     @param resources - This is the resources used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    /**
     * This is the set part Method.
     * This Method is used to set the attributes of the current project to this Modify product form
     * @param thisPart- This is the Selected part that is chosen to be modified.
     */
    public void setPart(Part thisPart) {
        this.thisPart = thisPart;
        partID= Inventory.getAllParts().indexOf(thisPart);
        inputId.setText(Integer.toString(thisPart.getId()));
        inputName.setText(thisPart.getName());
        inputInv.setText(Integer.toString(thisPart.getStock()));
        inputPrice.setText(Double.toString(thisPart.getPrice()));
        inputMax.setText(Integer.toString(thisPart.getMax()));
        inputMin.setText(Integer.toString(thisPart.getMin()));

        if(thisPart instanceof OutSourced){
            OutSourced newOutSourced = (OutSourced) thisPart;
            outSourced.setSelected(true);
            companyOrMachineID.setText("Company Name");
            inputMachineIDOrCompanyName.setText(newOutSourced.getCompanyName());
        }
        else if (thisPart instanceof InHouse){
            InHouse newInHouse = (InHouse) thisPart;
            inHouse.setSelected(true);
            companyOrMachineID.setText("Machine ID");
            inputMachineIDOrCompanyName.setText(Integer.toString(newInHouse.getMachineID()));
        }
    }

    /**
     * This is the Save Part Method for the Modify Part Form.
     * This Method Saves the input of a Modified Product and adds it to Inventory, and opens the Main Form page after closing this one.
     *
     * @param actionEvent - This is the action Event of the Save Button being clicked on.
     * @throws IOException - This is the exception that may appear while the method runs.
     */
    public void onSave(ActionEvent actionEvent) throws IOException {
            int newPartInventory = Integer.parseInt(inputInv.getText());
            int newPartMax = Integer.parseInt(inputMax.getText());
            int newPartMin = Integer.parseInt(inputMin.getText());
            if(MainController.confirmationDialogue("Save", "Do you want to savec this part?"))
               if(newPartMax < newPartMin) {
                MainController.informationDialog(" There is an Input Error", " The Error is in min and max field", " please check those inputs and try again");
                }
                else if (newPartInventory< newPartMin || newPartInventory > newPartMax){
                MainController.informationDialog(" There is an Input Error"," The Error is in the inventory field ", " Check these inputs and try again.");
             } else {
                    int newPartID = Integer.parseInt(inputId.getText());
                    String newName = inputName.getText();
                    int newStock = newPartInventory;
                    double newPrice = Double.parseDouble(inputPrice.getText());
                    int newMin = newPartMin;
                    int newMax = newPartMax;
                    if(outSourced.isSelected()){
                        try {
                            int machineId = Integer.parseInt(inputMachineIDOrCompanyName.getText());
                            InHouse temporary = new InHouse(newPartID, newStock, newMin, newMax, newName, newPrice, machineId);
                            Model.Inventory.updatePart(partID, temporary);
                            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
                            Scene scene = new Scene(fxmlLoader.load(), 1750, 1000);
                            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                            stage.setScene(scene);
                            stage.show();
                    }
                        catch(NumberFormatException e){
                            MainController.informationDialog("Input Error", " Check Machine ID", "Machine ID can only contain numbers 0-9");
                        }
                    }
                else {
                    String newCompanyName = machineIdOrCompanyName.getText();
                    OutSourced temporary = new OutSourced(newPartID, newStock, newMin, newMax, newName, newPrice, newCompanyName);
                    Model.Inventory.updatePart(partID, temporary);
                    FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1750, 1000);
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                    }
            }
        }
    /**
     *  This it the Cancel method for the Modify Part Form.
     *  This method cancels the Modify Part Form and renders the main page form again.
     *
     * @param actionEvent - This is action event Parameter, the action event that is used is the cancel button being clicked.
     * @throws IOException - his is the exception that may appear while the method runs.
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        if(MainController.confirmationDialogue("Cancel??","Are you sure you want to Cancel?")) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1750, 1000);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     *  This it the Change Label method for the Modify Part Form.
     *  This method changes the label of the Modify part Controller based off of which radio button is selected.
     *
     * @param actionEvent - This is action event Parameter, the action event that is used is clicking on the radio buttons "Outsourced"/"Inhouse" button being clicked.
     */
    public void changeLabel(ActionEvent actionEvent) {
        if(outSourced.isSelected()){
            this.companyOrMachineID.setText("Company Name");
        }
        else {
            this.companyOrMachineID.setText("Machine ID");
        }
    }

}
