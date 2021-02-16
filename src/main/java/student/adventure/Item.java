package student.adventure;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("name")
    private String itemName;
    @SerializedName("description")
    private String itemDescription;

    public String getItemName() {
        return itemName.toLowerCase();
    }

    public String getItemDescription() {
        return itemDescription;
    }
}