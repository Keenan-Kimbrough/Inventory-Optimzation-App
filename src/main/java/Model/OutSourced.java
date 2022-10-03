package Model;

/**
 * This is the Outsourced Class.
 * This class Extends from part class.
 * @author keenankimbrough
 */

public class OutSourced extends Part {

private String cName;

    /**
     * This is the OutSource method that is a constructor
     * @param id - id Param.
     * @param stock - Stock param.
     * @param min -min param.
     * @param max max param.
     * @param name name param.
     * @param price price param.
     * @param cName company name param.
     */
    public OutSourced(int id, int stock, int min, int max, String name, double price, String cName) {
        super(id,name, price, stock, min, max);


        this.cName = cName;
    }


    /**]
     * This is the Get Company Name method.
     * This method returns the Company Name
     * @return - Returns the Company Name that is a string.
     */
   public String getCompanyName() {
        return cName;
   }

    /**
     * This is the set Name Method.
     * @param name the name to set
     */

   public void setName (String name) {
        this.cName = cName;
   }

}
