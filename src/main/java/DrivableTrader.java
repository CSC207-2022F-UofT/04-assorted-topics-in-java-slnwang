/* TODO: Create a subclass of Trader named DrivableTrader
 * This class should be identical to Trader, except that it takes
 * only Drivable objects in its inventory, wishlist, etc.
 *
 * The sellingPrice returned should also be overridden. The selling price
 * should be equal to the:
 *     Object's price + Object's max speed
 * If the object is Tradable (and Tradable.MISSING_PRICE otherwise.)
 *
 * Look at DomesticatableTrader.java for an example.
 */

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.List;

public class DrivableTrader extends Trader<Drivable>{
    private int money;
    /**
     * Construct a Trader, giving them the given inventory,
     * wishlist, and money.
     *
     * @param inventory Objects in this Trader's inventory
     * @param wishlist  Objects in this Trader's wishlist
     * @param money     The Trader's money
     */
    public DrivableTrader(List inventory, List wishlist, int money) {
        super(inventory, wishlist, money);
        this.money = money;
    }

    public DrivableTrader(int money){
         super(money);
    }

    public void addToWishList(Drivable item){
         this.getWishlist().add(item);
    }

    public int getSellingPrice(Drivable item){
         if(item instanceof Tradable){
             return super.getSellingPrice(item) + item.getMaxSpeed();
         } else {
             return Tradable.MISSING_PRICE;
         }
    }

    public boolean exchangeMoney(DrivableTrader other, Drivable item){
         int price = this.getSellingPrice(item);
         if(price == Tradable.MISSING_PRICE){
             return false;
         } else if (price <= other.money){
             other.money -= price;
             this.money += price;
             return true;
        }
         return false;
    }

    public boolean sellTo(DrivableTrader other){
        boolean sold_once = false;
        ArrayList<Drivable> items_sold = new ArrayList<>();

        for(Drivable item : this.getInventory()){
            if (other.getWishlist().contains(item) && exchangeMoney(other, item)) {
                items_sold.add(item);
                sold_once = true;
            }
        }
        other.getInventory().addAll(items_sold);
        other.getWishlist().removeAll(items_sold);
        this.getInventory().removeAll(items_sold);
        return sold_once;
    }

    public boolean buyFrom(DrivableTrader other){
        return other.sellTo(this);
    }

    public String toString() {
        StringBuilder details = new StringBuilder("-- Inventory --\n");

        for (Drivable item : this.getInventory()) {
            details.append(item).append("\n");
        }

        details.append("-- Wishlist --\n");
        for (Drivable item : this.getWishlist()) {
            details.append(item).append("\n");
        }

        return details.toString();
    }



}
