package student.adventure;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Item is a storage object that store specific stats about an item
 *
 * @author Harry Chu
 */
public class Item {

    @SerializedName("name")
    private String itemName;
    @SerializedName("description")
    private String itemDescription;
    private String itemType;

    /**
     * Finds an item within a target item array
     *
     * @param itemName name of the target item
     * @param itemLocation location to search for the item
     * @return an Item with itemName if it exists or null if the item doesn't exist or location doesn't exist
     */
    public static Item findItem(String itemName, ArrayList<Item> itemLocation) {

        if(itemLocation == null) {
            return null;
        }

        for (Item targetItem : itemLocation) {
            if (targetItem.getItemName().equalsIgnoreCase(itemName)) {
                return targetItem;
            }
        }

        return null;
    }

    /**
     * Checks to see if all of the items in the subset are also in the larger set
     *
     * @param baseItemSet the set of items to compare with
     * @param subItemSet the subset of items to be examined
     * @return false if the base set is null, true if the subset is null, true if all of the items in the subset
     *         are in the base set
     */
    public static boolean containsAllItems(ArrayList<Item> baseItemSet, ArrayList<Item> subItemSet) {

        if(baseItemSet == null) {
            return false;
        } else if(subItemSet == null) {
            return true;
        }

        return baseItemSet.containsAll(subItemSet);
    }

    /**
     * Finds all of the itemTypes and the number of each itemType in an arraylist
     *
     * @param itemLocation the arraylist to tally the items from
     * @return a map that associates an item type to the number of items with that type in the location
     */
    public static HashMap<String, Integer> tallyItemTypes(ArrayList<Item> itemLocation){

        HashMap<String, Integer> numOfItemType = new HashMap<>();

        for(Item targetItem: itemLocation) {

            String itemType = targetItem.getItemType();
            if(numOfItemType.containsKey(itemType)) {
                numOfItemType.put(itemType, numOfItemType.get(itemType) + 1);
            } else {
                numOfItemType.put(itemType, 1);
            }
        }

        return numOfItemType;
    }

    public String getItemName() {

        return itemName;
    }

    public String getItemDescription() {

        return itemDescription;
    }

    public String getItemType() {

        return itemType;
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof Item toCompare) {
            return itemName.equalsIgnoreCase(toCompare.itemName) && itemType.equalsIgnoreCase(toCompare.itemType);
        }

        return false;
    }
}
