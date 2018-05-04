package com.ifenqu.app.model;

import java.io.Serializable;
import java.util.List;

public class GoodModel implements Serializable {
    private String productImg;
    private String productName;
    private String termsPrice;
    private double highPrice;
    private double lowPrice;
    private int terms;
    private String webView_content;
    private boolean onlyOnePrice;

    public boolean isOnlyOnePrice() {
        return onlyOnePrice;
    }

    public void setOnlyOnePrice(boolean onlyOnePrice) {
        this.onlyOnePrice = onlyOnePrice;
    }

    private String productId;
    private String colorTitle;
    private String styleTitle;
    private List<GoodDetailModel> colorList;
    private List<GoodDetailModel> styleList;
    private List<ProductDetailGoodsModel> goodsList;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getWebView_content() {
        return webView_content;
    }

    public void setWebView_content(String webView_content) {
        this.webView_content = webView_content;
    }

    public List<ProductDetailGoodsModel> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<ProductDetailGoodsModel> goodsList) {
        this.goodsList = goodsList;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getTermsPrice() {
        return termsPrice;
    }

    public void setTermsPrice(String termsPrice) {
        this.termsPrice = termsPrice;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    public String getColorTitle() {
        return colorTitle;
    }

    public void setColorTitle(String colorTitle) {
        this.colorTitle = colorTitle;
    }

    public String getStyleTitle() {
        return styleTitle;
    }

    public void setStyleTitle(String styleTitle) {
        this.styleTitle = styleTitle;
    }

    public List<GoodDetailModel> getColorList() {
        return colorList;
    }

    public void setColorList(List<GoodDetailModel> colorList) {
        this.colorList = colorList;
    }

    public List<GoodDetailModel> getStyleList() {
        return styleList;
    }

    public void setStyleList(List<GoodDetailModel> styleList) {
        this.styleList = styleList;
    }
}
