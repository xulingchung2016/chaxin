package com.newbrain.model;

import java.util.List;

public class ManoSearchIntroBean extends BaseJsonDataBean {
	/**茶庄园列表*/
	private String count;
	private String pageNo = "1";
	private String pageSize = "10";
	
	private List<ManoSearchIntro> result;

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

	public List<ManoSearchIntro> getResult() {
		return result;
	}

	public void setResult(List<ManoSearchIntro> result) {
		this.result = result;
	}

	/**茶庄园信息*/
	public class ManoSearchIntro {
		private String manor_id;
		private String manor_name;
		private String smallImage;
		public String getManor_id() {
			return manor_id;
		}
		public void setManor_id(String manor_id) {
			this.manor_id = manor_id;
		}
		public String getManor_name() {
			return manor_name;
		}
		public void setManor_name(String manor_name) {
			this.manor_name = manor_name;
		}
		public String getSmallImage() {
			return smallImage;
		}
		public void setSmallImage(String smallImage) {
			this.smallImage = smallImage;
		}
		
	}
}
