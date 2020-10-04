/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.ui.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.watr.app.R;
import com.watr.app.constants.ActivityPeriod;
import com.watr.app.datastore.room.hydration.HydrationEntity;
import com.watr.app.datastore.sharedpreferences.settings.SettingsManager;
import com.watr.app.datastore.sharedpreferences.userprofile.UserProfileManager;
import com.watr.app.timemgmt.ActivityPeriodManager;
import com.watr.app.timemgmt.UnknownTimeIntervalException;
import com.watr.app.ui.activities.MainActivity;
import com.watr.app.utils.NumberUtils;
import com.watr.app.utils.TimeUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;

/**
 * Hydration record recycler view adapter.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class HydrationRecordListAdapter
    extends RecyclerView.Adapter<HydrationRecordListAdapter.HydrationRecordViewHolder> {

  private final LayoutInflater layoutInflater;
  private final SettingsManager settingsManager;
  private final UserProfileManager userProfileManager;

  private List<HydrationEntity> hydrationRecords;

  public HydrationRecordListAdapter(Context context) {
    layoutInflater = LayoutInflater.from(context);
    settingsManager = MainActivity.getSettingsManager();
    userProfileManager = MainActivity.getUserProfileManager();
  }

  @Override
  public HydrationRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = layoutInflater.inflate(R.layout.recyclerview_item, parent, false);
    return new HydrationRecordViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull HydrationRecordViewHolder holder, int position) {
    // Set page title based on current activity period
    try {
      val activityPeriod = ActivityPeriodManager.getCurrentActivityPeriod();

      if (activityPeriod == ActivityPeriod.AWAKE) {
        holder.hydrationRecordItemView.setText(R.string.history_page_title_awake);
      } else {
        holder.hydrationRecordItemView.setText(R.string.history_page_title_asleep);
      }
    } catch (UnknownTimeIntervalException e) {
      Log.e("history-view-adapter", "Could not determine current activity period: ", e);
    }

    // Generate list item
    generateItem(holder, position);
  }

  /**
   * Updates hydration record cache.
   *
   * @param hydrationRecords {@link List}<{@link HydrationEntity}>
   */
  @SneakyThrows
  public void setHydrationRecords(List<HydrationEntity> hydrationRecords) {
    val activityPeriod = ActivityPeriodManager.getCurrentActivityPeriodWithOffsets();
    val userWakeTime = userProfileManager.getWakeTime();

    // Only show items for current/most recent waking activity period
    this.hydrationRecords =
        hydrationRecords.stream()
            .filter(
                record ->
                    record.getTimestamp().getTime()
                        > TimeUtils.localTimeToUnixTimestamp(
                            userWakeTime, activityPeriod.wakeTimeOffset))
            .collect(Collectors.toList());
    notifyDataSetChanged();
  }

  // This function is potentially called before DB initialisation, hence why a null check is
  // required here
  @Override
  public int getItemCount() {
    return hydrationRecords != null ? hydrationRecords.size() : 0;
  }

  /**
   * Generates a RecyclerView item from a hydration record.
   *
   * @param holder {@link HydrationRecordViewHolder} Parent view holder
   * @param position {@link Integer} Position of item in array (Corresponds to position in hydration
   *     record array)
   */
  private void generateItem(@NonNull HydrationRecordViewHolder holder, int position) {
    // Populate list item
    if (hydrationRecords != null) {
      HydrationEntity currentRecord = hydrationRecords.get(position);

      // Determine if we need to use metric or imperial units
      val useMetricUnits = settingsManager.getCtx().getBoolean("useMetricUnits", true);

      // Determine visual representation of unit
      val unit = useMetricUnits ? "ml" : "fl.oz";

      // Determine absolute and relative hydration with the correct unit

      val absoluteHydration =
          useMetricUnits
              ? currentRecord.getAbsoluteHydration()
              : NumberUtils.convertMillilitresToFluidOunces(currentRecord.getAbsoluteHydration());

      val relativeHydration =
          useMetricUnits
              ? currentRecord.getRelativeHydration()
              : NumberUtils.convertMillilitresToFluidOunces(currentRecord.getRelativeHydration());

      // Determine drink type and add explicit plus to net-positive hydration
      val drinkType = currentRecord.getDrinkType();
      val positive = currentRecord.getRelativeHydration() > 0 ? "+" : "";

      // Deduct proper time display based on whether we need to use a 24-hour or 12-hour clock
      val use24HrClock = settingsManager.getCtx().getBoolean("use24HrClock", true);

      // Construct time information
      val time = String.format(use24HrClock ? "%tR" : "%tr", currentRecord.getTimestamp());

      // Construct drink information
      @SuppressLint("DefaultLocale")
      val drink =
          String.format(
              "%s, %d %s (%s%d %4$s)",
              drinkType.getLabel(),
              (int) absoluteHydration,
              unit,
              positive,
              (int) relativeHydration);

      // Set final text
      holder.hydrationRecordItemView.setText(String.format("%s: %s", time, drink));
    } else {
      // Show placeholder string if data is not ready yet
      holder.hydrationRecordItemView.setText(R.string.hydration_record_list_item_default_value);
    }
  }

  /**
   * Hydration record recycler view view holder.
   *
   * @author linuswillner
   * @version 1.0.0
   */
  public static class HydrationRecordViewHolder extends RecyclerView.ViewHolder {
    private final TextView hydrationRecordItemView;

    private HydrationRecordViewHolder(View itemView) {
      super(itemView);
      hydrationRecordItemView = itemView.findViewById(R.id.itemDescription);
    }
  }
}
