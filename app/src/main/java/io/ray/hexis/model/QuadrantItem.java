package io.ray.hexis.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *  QuadrantItem object. Represents an entry on a Quadrant.
 */
public class QuadrantItem implements Parcelable {
  private String msg;

  private long uid = -1;
  private int completion;

  /**
   * Parcelable Creator for a QuadrantItem.
   * Allows a QuadrantItem to be created from a Parcelable
   */
  public static final Parcelable.Creator<QuadrantItem> CREATOR =
      new Parcelable.Creator<QuadrantItem>() {
    public QuadrantItem createFromParcel(Parcel in) {
      return new QuadrantItem(in);
    }

    public QuadrantItem[] newArray(int size) {
      return new QuadrantItem[size];
    }
  };

  /**
   * Required constructor to load a Parcelable item.
   * @param in    Parcel to unpack and create QuadrantItem from
   */
  private QuadrantItem(Parcel in) {
    msg = in.readString();
  }

  /**
   * Parameterized constructor that takes a message.
   * @param msg   Message to be displayed
   */
  public QuadrantItem(String msg) {
    // Pass the message parameter, but set uid to -1 (i.e. not assigned yet)
    // and completion to 0 (i.e. not complete).
    this(msg, -1, 0);
  }

  /**
   * Parameterized constructor that takes a message and uid.
   * @param msg     Message to be display
   * @param uid     Uid from the database
   */
  public QuadrantItem(String msg, long uid) {
    // Pass message and uid, but set completion to 0 (i.e. not complete)
    this(msg, uid, 0);
  }

  /**
   * Parameterized constructor that takes a message, uid, and completion status.
   * @param msg           Message to be displayed
   * @param uid           Uid from the database
   * @param completion    Completion status of the item, 0 for not complete, 1 for complete
   */
  public QuadrantItem(String msg, long uid, int completion) {
    this.msg = msg;
    this.uid = uid;
    this.completion = completion;
  }

  /**
   * Get and return QuadrantItem's message.
   * @return Message
   */
  public String getMessage() {
    return msg;
  }

  /**
   * Describe the contents of the Parcel.
   * @return  Integer representing a Parcel's status
   */
  @Override
  public int describeContents() {
    return 0;
  }

  /**
   * Write a QuadrantItem object to a Parcel.
   * @param dest      Parcel being written to
   * @param flags     Flags to determine how to write it
   */
  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(msg);
  }

  public void setUid(long uid) {
    this.uid = uid;
  }

  public void setMessage(String msg) {
    this.msg = msg;
  }

  public void setCompletion(int completion) { this.completion = completion; }

  public long getUid() {
    return uid;
  }

  public int getCompletion() {
    return completion;
  }
}
