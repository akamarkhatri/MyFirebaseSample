package com.qaitamarkhatri.myfirebasesample.nativelist;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.qaitamarkhatri.myfirebasesample.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by QAIT\amarkhatri on 13/3/17.
 */
public class NativeAdUtil {
    public static final String TAG_QA = "NATIVE";
    private static ArrayList<Integer> placementPositionList;
    private static HashMap<Integer,QANativAdBeans> qaNativAdBeansHashMap=new HashMap<>();

    public static void removePreviousAdObjectFromList(ArrayList<ListData> dataList) {
        ArrayList<ListData> tempArrList = new ArrayList<ListData>();
        for (ListData listData:dataList) {
            if (listData.isAd()) {
                tempArrList.add(listData);
            }
        }
        for (ListData listData:tempArrList) {
                dataList.remove(listData);
        }
    }

    public static void notifyUser(String tag, String msg) {
        Log.d(tag,msg);
    }

    public static ArrayList<Integer> getPlacementPositionList() {
        placementPositionList=new ArrayList<>();
        int[] placementArr={3,5,7};
        for (int i = 0; i<placementArr.length; i++) {
            placementPositionList.add(placementArr[i]);

        }
        return placementPositionList;
    }

    public static ListData getAdObject(Integer placementPosition) {
        QANativAdBeans qaNativAdBeans = qaNativAdBeansHashMap.get(placementPosition);
        if (qaNativAdBeans==null) {
            return null;
        }
        ListData listData = new ListData("Ad :" + placementPosition);
        listData.setQANativeAdBean(qaNativAdBeans);
        return listData;
    }

    public static void loadAd(Context context, String adId, final int placementPosition) {
//        adId="ca-app-pub-3940256099942544/2247696110";
        AdLoader.Builder builder = new AdLoader.Builder(context, adId);
        builder.forContentAd(new NativeContentAd.OnContentAdLoadedListener() {
            @Override
            public void onContentAdLoaded(NativeContentAd nativeContentAd) {
                QANativAdBeans qaNativAdBeans = new QANativAdBeans(nativeContentAd, 0, placementPosition, 0);
                qaNativAdBeansHashMap.put(placementPosition,qaNativAdBeans);
            }
        });
        builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });
        NativeAdOptions.Builder nativeAdOptionBuilder = new NativeAdOptions.Builder();
//        nativeAdOptionBuilder.setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT);
        builder.withNativeAdOptions(nativeAdOptionBuilder.build());
        AdLoader adLoader = builder.build();

        adLoader.loadAd(getAdRequest());
//        LogUtility.notifyUser(IAdConstants.TAG_QA, "Ad Load call for " + toString());
    }
    private static AdRequest getAdRequest() {
        AdRequest.Builder builder = new AdRequest.Builder();

       /* builder.addTestDevice(("DE3B54E79DBA329AA949CB1A8C8FCF32"));
        builder.addTestDevice(("DA3DA711A1C21DEFA372C2A860C231E9"));*/
//        builder.addTestDevice(("C82828BDD9E275B8E254F2055D9FF6E4"));
//        builder.addCustomTargeting(APP_VERSION_KEY, VERSION_NAME);
        AdRequest adRequest = builder.build();

        return  adRequest;
    }
    public static void setDataInDfpSmallAdView(View adLayout, NativeContentAd nativeContentAd, Context context) {



        ImageView imageViewAdIcon = (ImageView) adLayout.findViewById(R.id.native_ad_icon);
        TextView buttonCallToAction = (TextView) adLayout.findViewById(R.id.native_ad_call_to_action);
        TextView textViewTitle = (TextView) adLayout.findViewById(R.id.native_ad_title);
        TextView textViewSocialContext = (TextView) adLayout.findViewById(R.id.native_ad_social_context);
        TextView textViewBody = (TextView) adLayout.findViewById(R.id.native_ad_body);



        List<NativeAd.Image> imagesList = nativeContentAd.getImages();
        if (imagesList!=null && imagesList.size()>0) {
            com.google.android.gms.ads.formats.NativeAd.Image image = imagesList.get(0);
            if (image!=null) {
                Uri uri = image.getUri();
                setImage(image,imageViewAdIcon);
            }
        }
        buttonCallToAction.setText(nativeContentAd.getCallToAction());
        textViewTitle.setText(nativeContentAd.getHeadline());
        textViewSocialContext.setText(nativeContentAd.getAdvertiser());
        textViewBody.setText(nativeContentAd.getBody());

//        nativeContentAdView.setAd



        try {
            NativeContentAdView nativeContentAdView= (NativeContentAdView) adLayout.findViewById(R.id.nativeContentAdView);
            /*nativeContentAdView.setHeadlineView(textViewTitle);
            nativeContentAdView.setBodyView(textViewBody);
            nativeContentAdView.setImageView(imageViewAdIcon);
            nativeContentAdView.setAdvertiserView(textViewSocialContext);
            */
            nativeContentAdView.setDuplicateParentStateEnabled(true);
            nativeContentAdView.setSaveFromParentEnabled(true);
            nativeContentAdView.setCallToActionView(buttonCallToAction);
            nativeContentAdView.setNativeAd(nativeContentAd);
        } catch (Exception e) {
//            DBUtility.notifyUser(IAdConstants.TAG_QA," error :"+e.getMessage());

        }
    }
    private static void setImage(NativeAd.Image image, ImageView imageView) {
//        DBNewsListFragment.getDBNewsListFragment().displayImageUsingNewImageLoader(url,imageView, false);
//        MyImageLoader.getInstance().displayImageUsingNewImageLoaderInList(url,imageView,false);
        imageView.setImageDrawable(image.getDrawable());
    }

}

