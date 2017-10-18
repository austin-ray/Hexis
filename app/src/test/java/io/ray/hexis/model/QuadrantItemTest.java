package io.ray.hexis.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.os.Parcel;

import io.ray.hexis.BuildConfig;
import io.ray.hexis.model.QuadrantItem;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;



@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, packageName = BuildConfig.BASE_APP_ID, sdk = 25)
public class QuadrantItemTest {
  @Test
  public void setCompletion() throws Exception {
    QuadrantItem item = new QuadrantItem("TEST");

    assertFalse(item.isComplete());
    item.setCompletion(true);
    assertTrue(item.isComplete());
  }

  @Test
  public void quadrantCreatorArray() throws Exception {
    int size = 10;
    QuadrantItem[] array = QuadrantItem.CREATOR.newArray(size);

    assertEquals(size, array.length);
  }

  @Test
  public void createFromParcel() throws Exception {
    String msg = "TEST";

    // Create a new item with the msg String
    QuadrantItem item = new QuadrantItem(msg);

    // Obtain a Parcel
    Parcel src = Parcel.obtain();

    // Save the item to a Parcel
    item.writeToParcel(src, 0);

    // Reset data position
    src.setDataPosition(0);

    // Create a new item from the saved Parcel
    QuadrantItem test = QuadrantItem.CREATOR.createFromParcel(src);

    // Assert that the messages are equal
    assertEquals(msg, test.getMessage());
  }

  @Test
  public void describeContents() throws Exception {
    QuadrantItem test = new QuadrantItem("TEST");
    assertEquals(0, test.describeContents());
  }

  @Test
  public void writeToParcel() throws Exception {
    QuadrantItem test = new QuadrantItem("TEST");

    Parcel dest = Parcel.obtain();
    test.writeToParcel(dest, 0);
    dest.setDataPosition(0);

    assertEquals("TEST", dest.readString());
  }

  @Test
  public void getMessage() throws Exception {
    QuadrantItem test = new QuadrantItem("TEST");
    QuadrantItem test2 = new QuadrantItem("TEST", -1L, false);
    assertEquals("TEST", test.getMessage());
    assertEquals("TEST", test2.getMessage());
  }

  @Test
  public void getUid() throws Exception {
    QuadrantItem test = new QuadrantItem("TEST", -1L, false);
    assertEquals(-1L, test.getUid());
  }

  @Test
  public void setUid() throws Exception {
    QuadrantItem test = new QuadrantItem("TEST", -1L, false);
    test.setUid(2L);
    assertEquals(2L, test.getUid());
  }

  @Test
  public void getCompletionStatus() throws Exception {
    QuadrantItem test = new QuadrantItem("TEST", -1L, false);
    assertFalse(test.isComplete());
  }
}