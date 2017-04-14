package com.qaitamarkhatri.myfirebasesample.nativelist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.qaitamarkhatri.myfirebasesample.R;

import java.util.ArrayList;

/**
 * Created by QAIT\amarkhatri on 13/3/17.
 */

public class NativeMainActivity extends AppCompatActivity {
    String DFP_Native_InFeed_TOP="/28928716/DB_APP_Native_Top";
    String DFP_Native_InFeed_Middle="/28928716/DB_APP_Native_Mid";
    String DFP_Native_InFeed_BOTTOM= "/28928716/DB_APP_Native_Bottom";
    //    String[] DFP_NATIVE_AD_PLACEMENT_ID_ARR1 = {DFP_DEFAULT_NATIVE_AD_UNIT_ID};
    String[] DFP_NATIVE_AD_PLACEMENT_ID_ARR = {DFP_Native_InFeed_TOP,DFP_Native_InFeed_Middle,DFP_Native_InFeed_BOTTOM};//{DFP_Native_InFeed_TOP,DFP_Native_InFeed_Middle,DFP_Native_InFeed_BOTTOM};
    String DFP_Native_InFeed_Top_FOR_BOLLYWOOD_SECTION= "/28928716/DB_APP_Native_Top_ListingPage";
    String DFP_Native_InFeed_Middle_FOR_BOLLYWOOD_SECTION="/28928716/DB_APP_Native_Mid_ListingPage";
    String DFP_Native_InFeed_Bottom_FOR_BOLLYWOOD_SECTION= "/28928716/DB_APP_Native_Bottom_ListingPage";
    //    String[] DFP_NATIVE_AD_PLACEMENT_ID_ARR_FOR_BOLLYWOOD_SECTION1 = {DFP_DEFAULT_NATIVE_AD_UNIT_ID};//
    String[] DFP_NATIVE_AD_PLACEMENT_ID_ARR_FOR_BOLLYWOOD_SECTION = {DFP_Native_InFeed_Top_FOR_BOLLYWOOD_SECTION,DFP_Native_InFeed_Middle_FOR_BOLLYWOOD_SECTION,DFP_Native_InFeed_Bottom_FOR_BOLLYWOOD_SECTION};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_list);


        NativeListFragment nativeListFragment = new NativeListFragment();

        getSupportFragmentManager().beginTransaction().add(nativeListFragment, "nativefragment").commit();


        ArrayList<Integer> placementPositionList = NativeAdUtil.getPlacementPositionList();
        for (int i=0;i<placementPositionList.size();i++) {
            int placementPosition=placementPositionList.get(i);
            NativeAdUtil.loadAd(this,DFP_NATIVE_AD_PLACEMENT_ID_ARR[i],placementPosition);

        }
//        setDataInView();
    }

/*
    private void setDataInView() {
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerListAdapter recyclerListAdapter = new RecyclerListAdapter(this, getDataList());
        recyclerView.setAdapter(recyclerListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        OttoCurrentRecyclerListData ottoCurrentRecyclerListData = new OttoCurrentRecyclerListData(recyclerView, recyclerListAdapter);
        ottoCurrentRecyclerListData.setScrollListener();
    }
*/

    public static ArrayList<ListData> getDataList() {
        ArrayList<ListData> dataArrayList = new ArrayList<>();
        for (int i=0;i<30;i++) {
            dataArrayList.add(new ListData("Item :"+(i+1)));
        }
        return dataArrayList;
    }
}
