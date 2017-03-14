package io.ray.hexis.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *  QuadrantItem object. Represents an entry on a Quadrant.
 */
public class QuadrantItem implements Parcelable {
    private String msg;

    private long UID = -1;
    private int completion;

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
    private QuadrantItem(Parcel in) {
        msg = in.readString();
    }

    /**
     * Parameterized constructor that takes a message
     * @param msg   Message to be displayed
     */
    public QuadrantItem(String msg) {
        this.msg = msg;
        this.UID = -1;
        this.completion = 0;
    }

    /**
     * Parameterized constructor that takes a message and UID
     * @param msg
     * @param UID
     */
    public QuadrantItem(String msg, long UID){
        this.msg = msg;
        this.UID = UID;
        this.completion = 0;
    }

    /**
     * Parameterized constructor that takes a message, UID, and completion status
     * @param msg
     * @param UID
     * @param completion
     */
    public QuadrantItem(String msg, long UID, int completion) {
        this.msg = msg;
        this.UID = UID;
        this.completion = completion;
    }

    /**
     * Get and return QuadrantItem's message
     * @return Message
     */
    public String getMessage() {
        return msg;
    }

    /**
     * Describe the contents of the Parcel
     * @return  Integer representing a Parcel's status
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write a QuadrantItem object to a Parcel
     * @param dest      Parcel being written to
     * @param flags     Flags to determine how to write it
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msg);
    }

    public void setUID(long UID) {
        this.UID = UID;
    }

    public void setMessage(String msg) { this.msg = msg; }

    public long getUID() {
        return UID;
    }

    public int getCompletion() {
        return completion;
    }
}
