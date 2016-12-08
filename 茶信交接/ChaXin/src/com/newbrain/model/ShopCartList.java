package com.newbrain.model;

import java.util.ArrayList;
import java.util.List;

public class ShopCartList 
{	
	public String getShopName() 
	{
		return ShopName;
	}
	
	public void setShopName(String shopName) 
	{
		ShopName = shopName;
	}
	
	public String getShopID() 
	{
		return ShopID;
	}
	
	public void setShopID(String shopID)
	{
		ShopID = shopID;
	}
	
	public String getID() 
	{
		return ID;
	}
	
	public void setID(String iD) {
		ID = iD;
	}
	public String getGoodsId()
	{
		return goodsId;
	}
	
	public void setGoodsId(String goodsId) 
	{
		this.goodsId = goodsId;
	}
	
	public String getGoodsName() 
	{
		return goodsName;
	}
	
	public void setGoodsName(String goodsName)
	{
		this.goodsName = goodsName;
	}
	
	public String getGoodsImage() 
	{
		return goodsImage;
	}
	public void setGoodsImage(String goodsImage) 
	{
		this.goodsImage = goodsImage;
	}
	
	public String getPrimeCost() 
	{
		return primeCost;
	}
	
	public void setPrimeCost(String primeCost) 
	{
		this.primeCost = primeCost;
	}
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) 
	{
		this.price = price;
	}
	
	public String getNum() 
	{
		return num;
	}
	public void setNum(String num) 
	{
		this.num = num;
	}
	
	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	private String ShopName;
	private String ShopID;
	private String ID;
	private String goodsId;
	private String goodsName;
	private String goodsImage;
	private String primeCost;
	private String price;
	private String num;
	private boolean isCheck;
}
