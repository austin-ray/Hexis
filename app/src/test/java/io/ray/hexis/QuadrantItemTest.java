package io.ray.hexis;

import android.os.Parcel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class QuadrantItemTest {
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
        assertEquals("TEST", test.getMessage());
    }

}