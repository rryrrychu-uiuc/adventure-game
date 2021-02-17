package student.adventure;

import com.google.gson.annotations.SerializedName;

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
}
