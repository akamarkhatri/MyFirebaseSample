package com.qaitamarkhatri.myfirebasesample.nativelist;

/**
 * Created by QAIT\amarkhatri on 12/3/17.
 */

public class ListData {
    private boolean isAd;
    private String name;
    private QANativAdBeans QANativeAdBean;

    public ListData(String name) {
        this.name = name;
    }

    public boolean isAd() {
        return isAd;
    }

    public void setAd(boolean ad) {
        isAd = ad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQANativeAdBean(QANativAdBeans QANativeAdBean) {
        setAd(true);
        this.QANativeAdBean = QANativeAdBean;
    }

    public QANativAdBeans getQANativeAdBean() {
        return QANativeAdBean;
    }
}
