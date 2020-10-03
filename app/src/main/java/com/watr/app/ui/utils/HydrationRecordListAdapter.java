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
import com.watr.app.datastore.sharedpreferences.userprofile.UserProfileManager;
import com.watr.app.timemgmt.ActivityPeriodManager;
import com.watr.app.timemgmt.TimeUtils;
import com.watr.app.timemgmt.UnknownTimeIntervalException;
import com.watr.app.ui.activities.MainActivity;
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
  private final UserProfileManager userProfileManager;

  private List<HydrationEntity> hydrationRecords;

  public HydrationRecordListAdapter(Context context) {
    layoutInflater = LayoutInflater.from(context);
    userProfileManager = MainActivity.getUserProfileManager();
  }

  @Override
  public HydrationRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = layoutInflater.inflate(R.layout.recyclerview_item, parent, false);
    return new HydrationRecordViewHolder(itemView);
  }

  @SuppressLint("DefaultLocale")
  @Override
  public void onBindViewHolder(@NonNull HydrationRecordViewHolder holder, int position) {
    // Set page title based on current activity period
    try {
      val activityPeriod = ActivityPeriodManager.getCurrentActivityPeriod();

      if (activityPeriod == ActivityPeriod.AWAKE) {
        holder.hydrationRecordItemView.setText(R.string.history_view_title_awake);
      } else {
        holder.hydrationRecordItemView.setText(R.string.history_view_title_asleep);
      }
    } catch (UnknownTimeIntervalException e) {
      Log.e("history-view-adapter", "Could not determine current activity period: ", e);
    }

    // Populate list item
    if (hydrationRecords != null) {
      HydrationEntity current = hydrationRecords.get(position);
      holder.hydrationRecordItemView.setText(
          String.format(
              "%tR: %s, %d ml",
              current.getTimestamp(), current.getDrinkType().getLabel(), current.getAmount()));
    } else {
      // Show placeholder string if data is not ready yet
      holder.hydrationRecordItemView.setText(R.string.hydration_record_list_item_default_value);
    }
  }

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
