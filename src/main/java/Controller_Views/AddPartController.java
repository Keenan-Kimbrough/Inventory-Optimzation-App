package Controller_Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.ResourceBundle;
import java.net.URL;

import Model.InHouse;
import Model.OutSourced;

import static Model.Inventory.getAllParts;

/***
 * This Class creates the Add Part Form, this class it adds a part to the Inventory list All Parts
 * @author Keenan Kimbrough
 *
 */
public class AddPartController implements Initializable {

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
    public RadioButton inHouse;
    public RadioButton outSourced;
    public TextField inputMachineIDOrCompanyName;
    @FXML
    private Label machineIdOrCompanyName;

    /**
    This method Initializes the Add Part Form.
    @param location - This is the location
    @param resources - This is the resources used
    */
    @Override
    public void initialize ( URL location, ResourceBundle resources){

    }

    /**
     * This is the Save Part Method for the Add Part Form.
     * This Method Saves the input of a new Part and adds it to All Parts Inventory, and opens the Main Form page after closing this one.
     *
     * @param actionEvent - This is the action Event of the Save Button being clicked on.
     * @throws IOException - This is the exception that may appear while the method runs.
     */
    public void onSave(ActionEvent actionEvent) throws IOException {

        try {
            int newPartInventory = Integer.parseInt(inputInv.getText());
            int newPartMax = Integer.parseInt(inputMax.getText());
            int newPartMin = Integer.parseInt(inputMin.getText());
            if(MainController.confirmationDialogue("Save", "Do you want to savec this part?"))
            if(newPartMax < newPartMin) {
                MainController.informationDialog(" There is an Input Error", " The Error is in min and max field", " please check those inputs and try again");
            }
            else if (newPartInventory< newPartMin || newPartInventory> newPartMax){
                MainController.informationDialog(" There is an Input Error"," The Error is in the inventory field ", " Check these inputs and try again.");
            }
            else {
                int newPartID = getNewPartID();
                String newName = inputName.getText();
                int newStock = newPartInventory;
                double newPrice = Double.parseDouble(inputPrice.getText());

                if(outSourced.isSelected()) {
                    String newCompanyName =  inputMachineIDOrCompanyName.getText();
                    OutSourced temp = new OutSourced(newPartID, newStock, newPartMin, newPartMax, newName, newPrice, newCompanyName);
                    Model.Inventory.addPart(temp);
                }
                else {
                    int newMachineID = Integer.parseInt(inputMachineIDOrCompanyName.getText());
                    InHouse temporary = new InHouse(newPartID, newStock, newPartMin, newPartMax, newName, newPrice, newMachineID);
                    Model.Inventory.addPart(temporary);
                }
                FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1750, 1000);
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

                stage.setScene(scene);
                stage.show();
            }
        }
            catch (Exception e){
            System.out.println(e);
                MainController.informationDialog("Input Error,"," Error While trying to add Part", "check inputs");
            }

        }

    /**
     *  This it the Cancel method for the Add Part Form.
     *  This method cancels the Add Part Form and renders the main page form again.
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
     *  This it the Change Label method for the Add Part Form.
     *  This method changes the label of the Add Part Controller based off of which radio button is selected.
     *
     * @param actionEvent - This is action event Parameter, the action event that is used is clicking on the radio buttons "Outsourced"/"Inhouse" button being clicked.
     */
    public void changeLabel(ActionEvent actionEvent) {
      if(outSourced.isSelected()){
          this.machineIdOrCompanyName.setText("Company Name");
      }
      else {
          this.machineIdOrCompanyName.setText("Machine ID");
      }
    }

    /***
     * This is the get New Part ID method.
     * This method loops through all the parts and then gives the new ID that is unique.
     *
     * @return This method returns an integer that is the used as the new part ID when using the Add Part Controller.
     */
    public static int getNewPartID(){
        int newPartID = 1;
        for( int i = 0; i <  getAllParts().size() ; i++){
            newPartID++;}
        return newPartID;
        }

    }






