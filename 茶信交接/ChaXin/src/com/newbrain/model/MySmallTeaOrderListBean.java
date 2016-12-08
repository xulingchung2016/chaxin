package com.newbrain.model;

import java.util.List;

public class MySmallTeaOrderListBean extends BaseJsonDataBean {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private List<MySmallTeaOrder> result;
	public List<MySmallTeaOrder> getResult() {
		return result;
	}
	private String count;
	private String pageNo;
	private String pageSize;
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public void setResult(List<MySmallTeaOrder> result) {
		this.result = result;
	}
	public class MySmallTeaOrder{
		
		private String price;
		private String order_count;
		private String desc;
		private String id;
		private String variety_name;
		private String manor_name;
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getOrder_count() {
			return order_count;
		}
		public void setOrder_count(String order_count) {
			this.order_count = order_count;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getVariety_name() {
			return variety_name;
		}
		public void setVariety_name(String variety_name) {
			this.variety_name = variety_name;
		}
		public String getManor_name() {
			return manor_name;
		}
		public void setManor_name(String manor_name) {
			this.manor_name = manor_name;
		}
		
	}
}
