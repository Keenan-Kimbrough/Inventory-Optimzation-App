package Model;

/**
 * This is the In house class.
 * @author Keenan Kimbrough
 */
public class InHouse extends Part {
    private int machineID;

    /**
     * This is the In House constructor Method.
     * @param id - This is the ID parameter.
     * @param stock - This is the stock parameter.
     * @param min - This is the min parameter.
     * @param max This is the max parameter.
     * @param name This is the Name parameter.
     * @param price This is the price parameter.
     * @param machineID This is the machine ID Parameter.
     */
    public InHouse(int id, int stock, int min, int max, String name, double price, int machineID) {
        super(id,name, price, stock, min, max);

        this.machineID = machineID;

    }

    /**
     * This is the get machine ID method.
     * This method returns the int, machine ID.
     * @return - the return is an Int machine ID.
     */
    public int getMachineID(){
        return machineID;

    }

    /**
     * This is the set Machine ID method.
     * This method sets this.machine ID to the machine parameter.
     * @param machineID - The machine ID
     */
    public void setMachineID(int machineID){
        this.machineID = machineID;

    }
}
