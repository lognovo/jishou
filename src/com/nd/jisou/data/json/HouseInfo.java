package com.nd.jisou.data.json;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;


public class HouseInfo implements Serializable
{
	
	private int picIndex;
	private String address;
	private String size;
	private String apartment;
	private double price;
	private String title;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4951895509572259835L;
	@SerializedName("name")
	private String privateName;
	
	public final String getName()
	{
		return privateName;
	}
	public final void setName(String value)
	{
		privateName = value;
	}
	
	public int getPicIndex() {
		return picIndex;
	}
	public void setPicIndex(int picIndex) {
		this.picIndex = picIndex;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}


	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getApartment() {
		return apartment;
	}
	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	
}