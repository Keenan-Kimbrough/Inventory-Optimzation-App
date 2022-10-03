package Model;
import Controller_Views.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * This is the Inventory Class.
 * @author keenankimbrough
 */
public class Inventory {
    public TextField productsTextField;
    public TextField partsTextField;
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    // add part

    /**
     * This is the add part Method.
     * This adds a part to all parts Observable List.
     * @param part - part that is added.
     */
    public static void addPart(Part part){
        allParts.add(part);
    }

    /**
     * This is the Get all parts method.
     * This method returns all the parts.
     * @return - returns all parts List.
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    // deletes part

    /**
     * This is delete part method.
     * This deletes the selected part.
     * @param selectedPart - the part that is wanted ot be deleted.
     * @return returns the all parts list with the removed part.
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /**
     * This is the Search By part name Method.
     * @param partialName - This  Method is meant to search a list and return the part based off name.
     * @return returns the named part.
     */
    public static ObservableList<Part> searchByPartName(String partialName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        for (Part pt : allParts) {
            if (pt.getName().contains(partialName)) {
                namedParts.add(pt);
            }
        }
        return namedParts;
    }

    /**
     * This is the get results handler.
     * This method is mean to search and return the specifc part.
     * @param q - This is the searched letters of input from the search box.
     * @return - This. returns the selected part.
     */
    public  static ObservableList<Part> getPartResultHandler(String q){

        ObservableList<Part> parts = searchByPartName(q);
        if(parts.size()== 0) {
            try {
                int id = Integer.parseInt(q);
                Part pt = getPartByID(id);
                if (pt != null)
                    parts.add(pt);

            }
            catch(NumberFormatException e){
                //ignore
            }
        }
        // sets the correct part in the parts Table
       return parts;
    }

    /**
     * This is get Part By ID Method.
     * This method searched the All parts List for the selected Part.
     * @param ID - This is the ID that is searched for.
     * @return - This method returns the part name by iD
     */
    public static Part getPartByID(int ID){

        for (int i = 0; i < allParts.size(); i++){
            Part pt = allParts.get(i);
            if(pt.getId()== ID){
                return pt;
            }
        }
        return null;
    }

    /**
     * This Is the Get All products method.
     * This Method returns all the products.
     * @return - returns all the products.
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /**
     * This the add product method.
     * This method adds the products to all products lists.
     * @param product - The product.
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }

    /**
     * This is the update Part list.
     * This method updates the all parts list, with the selected part.
     * @param index
     * @param selectedPart
     */
    public static void updatePart ( int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     * This is the update Product Method.
     * This method updates the Product.
     *
     * @param index - Index of selected Product.
     * @param selectedProduct - selected Productt.
     */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index,selectedProduct);
    }

    /**
     * This is the delete Product Method.
     * This method deletes the selected Product.
     * @param selectedProduct - selected product.
     * @return - Returns the all products list with the removed product.
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /**
     * This is the search product name method.
     * This Method searched for product name.
     * @param partialName - partial name parameter that is being searched.
     * @return - returns the searched Product.
     */

    private  static ObservableList<Product> searchByProductName(String partialName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        for (Product pt : allProducts) {
            if (pt.getName().contains(partialName)) {
                namedProducts.add(pt);
            }
        }
        return namedProducts;
    }

    /**
     * This Method us the get products ID.
     * This Method gets the product of the id.
     * @param ID -  this is the ID parameter.
     * @return - This method returns the part, or null.
     */
    private  static Product getProductByID(int ID){
        for (int i = 0; i < allProducts.size(); i++){
            Product pt = allProducts.get(i);
            if(pt.getId()== ID){
                return pt;
            }
        }
        return null;
    }

    /**
     * This is the get producuts Results handler Method.
     * This method gets the results of the searched products.
     * @param q - searched letters of product name.
     * @return returns the product.
     */
    public static ObservableList<Product> getProductResultHandler(String q){

        // want to get input search

        //uses the search by Product Name method
        ObservableList<Product> product = searchByProductName(q);
        if(product.size()== 0) {
            try {
                int id = Integer.parseInt(q);
                // uses the getProductById Method
                Product pt = getProductByID(id);
                if (pt != null)
                    product.add(pt);
            }
            catch(NumberFormatException e){
                //ignore
            }
        }
        // sets the correct part in the parts Table
        return product;
    }

}
