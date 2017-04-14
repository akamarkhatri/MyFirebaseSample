package com.qaitamarkhatri.myfirebasesample.nativelist;

/**
 * Created by QAIT\amarkhatri on 22/7/16.
 */
public class QANativAdBeans {
    private Object adObject;
    private int adPartner;
    private int adPlacementPosition;
    private int listItemType;
    private String adId;
    private String adPartnerName;

    public QANativAdBeans(Object adObject, int adPartner, int adPlacementPosition, int listItemType) {
        this.adObject = adObject;
        this.adPartner = adPartner;
        this.adPlacementPosition = adPlacementPosition;
        this.listItemType = listItemType;

    }

    public Object getAdObject() {
        return adObject;
    }


    public int getAdPartner() {
        return adPartner;
    }


    public int getAdPlacementPosition() {
        return adPlacementPosition;
    }


    public int getListItemType() {
        return listItemType;
    }


    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getAdPartnerName() {
        /*switch (adPartner) {
            case IAdConstants.AD_PARTNER_DFP:
                return IAdConstants.DFP_NAME;
            default:
            case IAdConstants.AD_PARTNER_FB:
                return IAdConstants.FB_NAME;

        }*/
        return "";
    }

    @Override
    public String toString() {
//        String adPartnerName=getAdPartnerName();

        return getAdPartnerName()+"\nposition :"+getAdPlacementPosition();
    }
}
