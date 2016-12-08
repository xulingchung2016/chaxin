package com.newbrain.model;

import java.util.List;

public class GoodsListBean   extends BaseJsonDataBean
{
	private List<goodlist> result;
	
	public List<goodlist> getResult()
	{
		return result;
	}


	public void setResult(List<goodlist> result) 
	{
		this.result = result;
	}

	public class goodlist
	{
		
		public String getId() 
		{
			return id;
		}
		
		
		public void setId(String id) 
		{
			this.id = id;
		}
		
		public String getGoodsName()
		{
			return goodsName;
		}
		
		public void setGoodsName(String goodsName) 
		{
			this.goodsName = goodsName;
		}
		
		public String getTeaTypeId() 
		{
			return teaTypeId;
		}
		
		public void setTeaTypeId(String teaTypeId) 
		{
			this.teaTypeId = teaTypeId;
		}
		
		public String getTeaTypeName()
		{
			return teaTypeName;
		}
		
		public void setTeaTypeName(String teaTypeName)
		{
			this.teaTypeName = teaTypeName;
		}
		
		public String getHomerbred() 
		{
			return homerbred;
		}
		
		public void setHomerbred(String homerbred) 
		{
			this.homerbred = homerbred;
		}
		
		public String getPackaging() 
		{
			return packaging;
		}
		
		public void setPackaging(String packaging) 
		{
			this.packaging = packaging;
		}
		
		public String getProcucingArea()
		{
			return procucingArea;
		}
		
		public void setProcucingArea(String procucingArea) 
		{
			this.procucingArea = procucingArea;
		}
		
		public String getPrice() 
		{
			return price;
		}
		
		public void setPrice(String price) 
		{
			this.price = price;
		}
		
		public String getTotal() 
		{
			return total;
		}
		
		public void setTotal(String total) 
		{
			this.total = total;
		}
		
		public String getStorageMethod()
		{
			return storageMethod;
		}
		
		public void setStorageMethod(String storageMethod) 
		{
			this.storageMethod = storageMethod;
		}
		
		public String getProductionCertificate()
		{
			return productionCertificate;
		}
		
		public void setProductionCertificate(String productionCertificate) 
		{
			this.productionCertificate = productionCertificate;
		}
		
		public String getMemo() 
		{
			return memo;
		}
		
		public void setMemo(String memo)
		{
			this.memo = memo;
		}
		
		public String getImage()
		{
			return image;
		}
		
		public void setImage(String image) 
		{
			this.image = image;
		}
		
		public String getEvalToal()
		{
			return evalToal;
		}
		
		public void setEvalToal(String evalToal) 
		{
			this.evalToal = evalToal;
		}
		
		public String getExpressNo() 
		{
			return expressNo;
		}
		
		public void setExpressNo(String expressNo) 
		{
			this.expressNo = expressNo;
		}
		
		public String getSalesTotal()
		{
			return salesTotal;
		}
		
		public void setSalesTotal(String salesTotal) 
		{
			this.salesTotal = salesTotal;
		}
		
		public String getPrimeCost() 
		{
			return primeCost;
		}
		
		public void setPrimeCost(String primeCost) 
		{
			this.primeCost = primeCost;
		}
		
		public String getStoreName()
		{
			return storeName;
		}
		
		public void setStoreName(String storeName) 
		{
			this.storeName = storeName;
		}
		
		private String id;
		private String goodsName;
		private String teaTypeId;
		private String teaTypeName;
		private String homerbred;
		private String packaging;
		private String procucingArea;
		private String price;
		private String total;
		private String storageMethod;
		private String productionCertificate;
		private String memo;
		private String image;
		private String evalToal;
		private String expressNo;
		private String salesTotal;
		private String primeCost;
		private String storeName;
	}
}
