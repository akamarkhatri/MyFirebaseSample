package com.qaitamarkhatri.myfirebasesample.nativelist;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by QAIT\amarkhatri on 7/3/17.
 */

public class OttoCurrentRecyclerListData extends RecyclerView.OnScrollListener {
    private RecyclerView recyclerView;
    private RecyclerListAdapter dbNewsListAdapter;
    private int lastVisibleItemPosition;
    private int firstVisibleItemPosition;
    private Integer incrementIndex;
    HashMap<Integer,Boolean> cacheMap;
    ArrayList<String > adInsertedPositionList;
    private boolean processStart;

    public OttoCurrentRecyclerListData(RecyclerView recyclerView, RecyclerListAdapter dbNewsListAdapter) {
        this.recyclerView = recyclerView;

        this.dbNewsListAdapter = dbNewsListAdapter;

    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }


    public void removeScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        if (dbNewsListAdapter.headerAdded) {
            removeHeader();
        }
        if (dbNewsListAdapter.footerAdded) {
            removeFooter();
        }
    }
    public void setScrollListener() {
        incrementIndex=0;
        cacheMap =new HashMap<>();
        adInsertedPositionList=new ArrayList<>();
//        cacheMap.clear();
        recyclerView.addOnScrollListener(this);
        NativeAdUtil.removePreviousAdObjectFromList(dbNewsListAdapter.dataList);
       /* LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

        firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        ArrayList arrayList = (ArrayList) NativeAdUtil.addNativeAdObjectInList(null, dbNewsListAdapter.getListObjectType(), dbNewsListAdapter.getDataList());

        dbNewsListAdapter.resetDataList(arrayList);*/
//        addFooter(null);
//        addHeader(null);
//        checkAndInsertNativeAds();
//        checkAndCallCaching();

    }
    public void addHeader(View customAdatfAdView) {
        /*if (dbNewsListAdapter.getDataList().size()==0) {
            return;
        }
        if (dbNewsListAdapter.headerAdded) {
//            dbNewsListAdapter.notifyItemChanged(0);
            dbNewsListAdapter.notifyDataSetChanged();
            return;
        }

        dbNewsListAdapter.headerAdded=true;
        dbNewsListAdapter.setHeaderView(customAdatfAdView);
        Object headerObject=getObjectOfTypeList(true,false);
        dbNewsListAdapter.getDataList().add(0,headerObject);
//        dbNewsListAdapter.notifyItemInserted(0);
        dbNewsListAdapter.notifyDataSetChanged();*/
    }


    public void removeHeader() {
        if (!dbNewsListAdapter.headerAdded) {
            return;
        }
        dbNewsListAdapter.dataList.remove(0);
        dbNewsListAdapter.headerAdded=false;
        dbNewsListAdapter.notifyDataSetChanged();
        removeParent(dbNewsListAdapter.headerView);
    }

    private void removeParent(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent == null) {
            return;
        }
        parent.removeAllViews();
    }

    public void addFooter(View customAdafterListAdView) {
       /* if (dbNewsListAdapter.getDataList().size()==0) {
            return;
        }
        if (dbNewsListAdapter.footerAdded) {
            dbNewsListAdapter.notifyDataSetChanged();
//            dbNewsListAdapter.notifyItemChanged(dbNewsListAdapter.getDataList().size()-1);
            return;
        }

        dbNewsListAdapter.setFooterView(customAdafterListAdView);
        Object footerObject=getObjectOfTypeList(false,true);
        dbNewsListAdapter.getDataList().add(footerObject);
        dbNewsListAdapter.footerAdded=true;
//        dbNewsListAdapter.notifyItemInserted(dbNewsListAdapter.getDataList().size()-1);
        dbNewsListAdapter.notifyDataSetChanged();*/
    }
    public void removeFooter() {
        if (!dbNewsListAdapter.footerAdded) {
            return;
        }
        dbNewsListAdapter.footerAdded=false;
//        dbNewsListAdapter.getDataList().remove(dbNewsListAdapter.getDataList().size()-1);
        dbNewsListAdapter.notifyDataSetChanged();
    }




    private void checkAndCallCaching() {
       /* int listItemType = NativeAdUtil.getListItemType(dbNewsListAdapter.getDataList(), dbNewsListAdapter.getListObjectType());
        NativeAdController nativeAdController = NativeAdController.getNativeAdController();
        ArrayList<Integer> placementPositionList = nativeAdController.getPlacementPositionList(listItemType);
        for (int i=firstVisibleItemPosition;i<=lastVisibleItemPosition;i++) {
            QANativAdBeans qaNativeAdBean = dbNewsListAdapter.getQANativeAdBean(i);
            if (qaNativeAdBean!=null && !isAlreadyCached(qaNativeAdBean.getAdPlacementPosition())) {
                nativeAdController.startCaching(listItemType,qaNativeAdBean.getAdPlacementPosition());
            }
        }*/
    }

    private boolean isAlreadyCached(int adPlacementPosition) {
        if (cacheMap.isEmpty() || !cacheMap.containsKey(adPlacementPosition)) {
            cacheMap.put(adPlacementPosition,true);
            return false;
        }
        return cacheMap.get(adPlacementPosition);
    }
    private boolean isAlreadyInserted(int adPlacementPosition) {
        if (cacheMap.isEmpty()) {
            return false;
        }

        /*if (cacheMap.get(adPlacementPosition)==null ){
            return false;
        }

        return true;*/
        Set<Map.Entry<Integer, Boolean>> entries = cacheMap.entrySet();
        Iterator<Map.Entry<Integer, Boolean>> entryIterator = entries.iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<Integer, Boolean> entry = entryIterator.next();
            if (entry.getKey()==adPlacementPosition) {
                return true;
            }
        }
        return false;

    }

    private synchronized void checkAndInsertNativeAds() {
        if (processStart) {
            return;
        }
        processStart=true;
        NativeAdUtil.notifyUser(NativeAdUtil.TAG_QA,"checkAndInsertNativeAds");
        ArrayList<Integer> placementPositionList = NativeAdUtil.getPlacementPositionList();
        if (cacheMap.size()==placementPositionList.size()) {
            NativeAdUtil.notifyUser(NativeAdUtil.TAG_QA,"Return__checkAndInsertNativeAds");
            processStart=false;
            return;
        }
//        printCacheMap();
        for (Integer placementPosition : placementPositionList) {
            if (!isAlreadyInserted(placementPosition)) {
                int listIndex=placementPosition+cacheMap.size();
                NativeAdUtil.notifyUser(NativeAdUtil.TAG_QA,"plac"+placementPosition+" list index "+listIndex+" cachemapSize :"+cacheMap.size());


                if (dbNewsListAdapter.headerAdded) {
                    listIndex+=1;
                }
                if (listIndex >= firstVisibleItemPosition && listIndex <=lastVisibleItemPosition) {


                    ListData objectOfTypeAD=NativeAdUtil.getAdObject(placementPosition);
                    if (objectOfTypeAD != null) {
//                        adInsertedPositionList.add(""+placementPosition);
                        int lastPosition=dbNewsListAdapter.dataList.size();
                        if (dbNewsListAdapter.footerAdded) {
                            lastPosition-=1;
                        }
                        if (listIndex<lastPosition) {
                            cacheMap.put(placementPosition,true);
                            dbNewsListAdapter.dataList.add(listIndex, objectOfTypeAD);
//                        dbNewsListAdapter.notifyDataSetChanged();
//                            nativeAdController.startCaching(listItemType, placementPosition);
                            dbNewsListAdapter.notifyItemInserted(listIndex);
                        }
                    }
//                    nativeAdController.startCaching(listItemType, placementPosition);
                }
            }
        }
        processStart=false;
        printCacheMap();
    }

    private void printCacheMap() {
        String TAG="CACHEMAP";
        NativeAdUtil.notifyUser(TAG,"cacheMAp");
        Set<Map.Entry<Integer, Boolean>> entries = cacheMap.entrySet();
        Iterator<Map.Entry<Integer, Boolean>> entryIterator = entries.iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<Integer, Boolean> entry = entryIterator.next();
            NativeAdUtil.notifyUser(TAG,"key "+entry.getKey()+" value "+entry.getValue());
        }
    }


    /*private boolean alreadyAdInserted(Integer placementPosition) {
        ArrayList dataList = dbNewsListAdapter.getDataList();
        switch (dbNewsListAdapter.getListObjectType()) {
            case NativeAdUtil.OBJECT_TYPE_NEWA_GALLERY_LIST_BEAN:
                NewsGalleryListBean newsGalleryListBean = (NewsGalleryListBean) dataList.get(placementPosition);
                return newsGalleryListBean.isAd();
            case NativeAdUtil.OBJECT_TYPE_NEWS_LIST_MOVIE_REVIEW_BEAN:
                NewsListMovieReviews newsMoviewReviewBean = (NewsListMovieReviews) dataList.get(placementPosition);
                return newsMoviewReviewBean.isAd();
            default:
            case NativeAdUtil.OBJECT_TYPE_NEWS_LIST_BEAN:
                NewsListBean newsListBean = (NewsListBean) dataList.get(placementPosition);
                return newsListBean.isAd();
        }
    }*/
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        NativeAdUtil.notifyUser("onScrollStateChanged_amar", "onScrollStateChanged");
        if (newState== RecyclerView.SCROLL_STATE_IDLE )//|| newState==RecyclerView.SCROLL_STATE_SETTLING)
        {
            if (processStart) {
                return;
            }
            checkAndInsertNativeAds();
            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (processStart) {
                        return;
                    }
                    checkAndInsertNativeAds();
                }
            },500);*/

//            return;
        }
//        checkAndInsertNativeAds();
//        checkAndCallCaching();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
//        NativeAdUtil.notifyUser("onScrollStateChanged_amar", "onScrolled");

//        NativeAdUtil.notifyUser("onScrolled amar", "onScroll amar");

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

        firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        int visibleItemCount = lastVisibleItemPosition-firstVisibleItemPosition;
        int totalItemCount   = linearLayoutManager.getItemCount();

//                        int visibleItemCount = lastVisibleItemPosition - firstVisibleItemPosition;

        if (visibleItemCount == 0)
            return;


       /* boolean loadMore = firstVisibleItemPosition + visibleItemCount >= totalItemCount;
        Log.d("TAG", "onScroll " + loadMore + " " + totalItemCount + " ");
*/


    }


}


