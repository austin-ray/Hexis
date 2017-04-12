package io.ray.hexis.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.ray.hexis.R;

/**
 * Placeholder ViewHolder for Quadrant items.
 */
public class QuadrantItemViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.quadrant_item_text)
  CheckedTextView textView;

  /**
   * Default constructor.
   *
   * @param itemView View
   */
  public QuadrantItemViewHolder(View itemView) {
    super(itemView);

    // Bind views
    ButterKnife.bind(this, itemView);
  }

  /**
   * Set the text view message.
   *
   * @param message Message
   */
  public void setTextView(String message) {
    textView.setText(message);
  }

  /**
   * Set the value of the checkbox.
   * @param isChecked   Is the box checked?
   */
  public void setCheck(boolean isChecked) {
    textView.setChecked(isChecked);
  }

  /**
   * Click the check box.
   */
  public void clickCheck() {
    // Negate the current the value of the check box
    textView.setChecked(!textView.isChecked());
  }

  /**
   * Return if the TextView is checked.
   * @return    Is the text view checked?
   */
  public boolean isChecked() {
    return textView.isChecked();
  }

  public TextView getTextView() {
    return textView;
  }
}
