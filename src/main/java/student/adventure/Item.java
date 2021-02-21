package student.adventure;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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

    public String getItemName() {
        return itemName.toLowerCase();
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
            return itemName.equals(toCompare.itemName) && itemType.equals(toCompare.itemType);
        }

        return false;
    }

    /**
     * Checks to see if all of the items in the subset are also in the larger set
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
}
