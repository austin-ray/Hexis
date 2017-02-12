package io.ray.hexis;

import android.os.Parcel;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class QuadrantItemTest {
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