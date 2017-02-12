package io.ray.hexis;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Placeholder QuadrantItem class
 */
public class QuadrantItem implements Parcelable {
    private String msg;

    /**
     * Parcelable Creator for a QuadrantItem
     * Allows a QuadrantItem to be created from a Parcelable
     */
    public static final Parcelable.Creator<QuadrantItem> CREATOR
            = new Parcelable.Creator<QuadrantItem>() {
        public QuadrantItem createFromParcel(Parcel in) {
            return new QuadrantItem(in);
        }

        public QuadrantItem[] newArray(int size) {
            return new QuadrantItem[size];
        }
    };

    /**
     * Required constructor to load a Parcelable item
     * @param in    Parcel to unpack and create QuadrantItem from
     */
    public QuadrantItem(Parcel in) {
        msg = in.readString();
    }

    /**
     * Parameterized constructor that takes a message
     * @param msg   Message to be displayed
     */
    public QuadrantItem(String msg) {
        this.msg = msg;
    }

    /**
     * Get and return QuadrantItem's message
     * @return Message
     */
    public String getMessage() {
        return msg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msg);
    }
}
