package com.newbrain.model;

import java.util.List;

/** 我的收益*/
public class MyIncomeBean extends BaseJsonDataBean {
	private String current;
	private String total;
	private String count;
	private String pageNo;
	private String pageSize;
	private List<MyIncomeInfo> result;

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

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

	public List<MyIncomeInfo> getResult() {
		return result;
	}

	public void setResult(List<MyIncomeInfo> result) {
		this.result = result;
	}

	public class MyIncomeInfo {
		private String user_id;
		private String gold;
		private String createDate;

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getGold() {
			return gold;
		}

		public void setGold(String gold) {
			this.gold = gold;
		}

		public String getCreateDate() {
			return createDate;
		}

		public void setCreateDate(String createDate) {
			this.createDate = createDate;
		}

	}
}
