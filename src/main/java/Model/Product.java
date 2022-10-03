package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *This is the Product Class.
 * @author Keenan Kimbrough
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    private FXCollections FXCollection;
    private ObservableList<Part> associatedParts = FXCollection.observableArrayList();

    /**
     * This is the Product Constructor
     * @param id - id param.
     * @param name - name param.
     * @param price price param.
     * @param stock stock param.
     * @param min min param.
     * @param max max param.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public Product(){
        this(0,null,0.00,0,0,0);
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This is the get All Associated part Method.
     * This method returns all associated parts.
     * @return associated Parts which is an observable list.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;

    }

    /**
     * This method is add associated part.
     * This methods add Part to assocaited parts.
     * @param part -Returns Part Object.
     */
    public void addAssociatedPart(ObservableList<Part> part){
        this.associatedParts.addAll(part);
    }

    /**
     * This Method sets products price.
     * This method sets the product price.
     * @param productPrice - double that is the product price.
     */
    public void setProductPrice(double productPrice){
        this.price = productPrice;
    }

    /**
     * This Method is get products price.
     * This method gets the product price.
     * @return - returns the price of the product.
     */
    public double getProductPrice() {
        return price;
    }


}
