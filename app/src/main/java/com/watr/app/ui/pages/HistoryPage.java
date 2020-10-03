/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.ui.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.watr.app.R;
import com.watr.app.ui.activities.MainActivity;
import com.watr.app.ui.utils.HydrationRecordListAdapter;
import com.watr.app.ui.viewmodels.MainViewModel;
import lombok.NoArgsConstructor;
import lombok.val;

/**
 * History page. All functionality specific to the history page goes here.
 *
 * @author linuswillner
 * @version 1.0.0
 */
@NoArgsConstructor
public class HistoryPage extends Fragment {
  private View view;
  private RecyclerView recyclerView;
  private MainViewModel mainViewModel;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_history, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    this.view = view;
    mainViewModel = MainActivity.getMainViewModel();
    recyclerView = view.findViewById(R.id.recyclerView);

    // Initialise view model
    val context = getContext();
    val adapter = new HydrationRecordListAdapter(context);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(context));

    // Start observer for the data being streamed in
    mainViewModel
        .getAllHydrationRecords()
        .observe(getViewLifecycleOwner(), adapter::setHydrationRecords);
  }
}
