package com.juhui.entity;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.io.Serializable;

/** 电子会员卡list item */
public class ECardItem implements Serializable{

	/**
	 * 
	 */
	@Transient private static final long serialVersionUID = 1L;
	@Id String itemID;
	String itemTitle;
	String itemAddress;
	String itemImageUrl;
	double distance;
	int useCount;
	String eCardType;

    //登陆后的信息
	String awardInfo;//可享折扣
    int jiFen;//用户信息
    int isHisCard;//是否显示为会员使用过的卡


	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getItemAddress() {
		return itemAddress;
	}

	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}

	public String getItemImageUrl() {
		return itemImageUrl;
	}

	public void setItemImageUrl(String itemImageUrl) {
		this.itemImageUrl = itemImageUrl;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getUseCount() {
		return useCount;
	}

	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}

	public String geteCardType() {
		return eCardType;
	}

	public void seteCardType(String eCardType) {
		this.eCardType = eCardType;
	}

	public String getAwardInfo() {
		return awardInfo;
	}

	public void setAwardInfo(String awardInfo) {
		this.awardInfo = awardInfo;
	}

    public int getJiFen() {
        return jiFen;
    }

    public void setJiFen(int jiFen) {
        this.jiFen = jiFen;
    }

    public int getIsHisCard() {
        return isHisCard;
    }

    public void setIsHisCard(int isHisCard) {
        this.isHisCard = isHisCard;
    }
}
