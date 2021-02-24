package student.adventure;

import com.google.gson.annotations.SerializedName;

/**
 * Achievement is a storage object that store specific stats of an achievement
 *
 * @author Harry Chu
 */
public class Achievement {

  private String itemType;
  private int minRequiredItems;

  @SerializedName("message")
  private String achievementMessage;

  public int getMinRequiredItems() {

    return minRequiredItems;
  }

  public String getItemType() {

    return itemType;
  }

  public String getAchievementMessage() {

    return achievementMessage;
  }
}
