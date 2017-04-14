package com.qaitamarkhatri.myfirebasesample.nativelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qaitamarkhatri.myfirebasesample.R;

import static com.qaitamarkhatri.myfirebasesample.nativelist.NativeMainActivity.getDataList;

/**
 * Created by QAIT\amarkhatri on 6/4/17.
 */

public class NativeListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recycler_list, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDataInView();
    }

    private void setDataInView() {
        RecyclerView recyclerView= (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        RecyclerListAdapter recyclerListAdapter = new RecyclerListAdapter(getActivity(), getDataList());
        recyclerView.setAdapter(recyclerListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));

        OttoCurrentRecyclerListData ottoCurrentRecyclerListData = new OttoCurrentRecyclerListData(recyclerView, recyclerListAdapter);
        ottoCurrentRecyclerListData.setScrollListener();
    }

}
