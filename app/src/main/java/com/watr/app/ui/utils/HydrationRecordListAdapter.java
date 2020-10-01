/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.ui.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.watr.app.R;
import com.watr.app.datastore.room.hydration.HydrationEntity;
import java.util.List;
import lombok.NonNull;

public class HydrationRecordListAdapter
    extends RecyclerView.Adapter<HydrationRecordListAdapter.HydrationRecordViewHolder> {

  private final LayoutInflater layoutInflater;

  private List<HydrationEntity> hydrationRecords;

  public HydrationRecordListAdapter(Context context) {
    layoutInflater = LayoutInflater.from(context);
  }

  @Override
  public HydrationRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = layoutInflater.inflate(R.layout.recyclerview_item, parent, false);
    return new HydrationRecordViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull HydrationRecordViewHolder holder, int position) {
    if (hydrationRecords != null) {
      HydrationEntity current = hydrationRecords.get(position);
      holder.hydrationRecordItemView.setText(current.toString());
    } else {
      // Show placeholder string if data is not ready yet
      holder.hydrationRecordItemView.setText(R.string.hydration_record_list_item_default_value);
    }
  }

  public void setHydrationRecords(List<HydrationEntity> hydrationRecords) {
    this.hydrationRecords = hydrationRecords;
    notifyDataSetChanged();
  }

  // This function is potentially called before DB initialisation, hence why a null check is
  // required here
  @Override
  public int getItemCount() {
    return hydrationRecords != null ? hydrationRecords.size() : 0;
  }

  public static class HydrationRecordViewHolder extends RecyclerView.ViewHolder {
    private final TextView hydrationRecordItemView;

    private HydrationRecordViewHolder(View itemView) {
      super(itemView);
      hydrationRecordItemView = itemView.findViewById(R.id.textView);
    }
  }
}
