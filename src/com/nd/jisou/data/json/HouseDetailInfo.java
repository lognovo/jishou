package com.nd.jisou.data.json;

import java.io.Serializable;



public class HouseDetailInfo extends HouseInfo
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4019786203885216290L;
	
	private int unit_price;
	private String toward;
	private String type;
	private String decoration;
	private String floors;
	private int years;
	private int favorite ;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public int getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(int unit_price) {
		this.unit_price = unit_price;
	}
	public String getToward() {
		return toward;
	}
	public void setToward(String toward) {
		this.toward = toward;
	}
	
	public String getDecoration() {
		return decoration;
	}
	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}
	public String getFloors() {
		return floors;
	}
	public void setFloors(String floors) {
		this.floors = floors;
	}
	public int getYears() {
		return years;
	}
	public void setYears(int years) {
		this.years = years;
	}
	public int getFavorite() {
		return favorite;
	}
	public void setFavorite(int favorite) {
		this.favorite = favorite;
	} 
	
}