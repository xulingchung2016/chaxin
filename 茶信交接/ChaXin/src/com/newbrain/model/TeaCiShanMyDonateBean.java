package com.newbrain.model;

import java.util.List;
/**
 * 我的捐献
 * @author Administrator
 *
 */
public class TeaCiShanMyDonateBean extends BaseJsonDataBean {
	/** 捐献总数 */
	private String gold_total;
	/** 捐献次数 */
	private String donate_count;
	private String count;
	private String pageNo = "1";
	private String pageSize = "10";

	/** 慈善项目列表 */
	private List<CiShanMyDonateIntro> result;

	public String getGold_total() {
		return gold_total;
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

	public void setGold_total(String gold_total) {
		this.gold_total = gold_total;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getDonate_count() {
		return donate_count;
	}

	public void setDonate_count(String donate_count) {
		this.donate_count = donate_count;
	}

	public List<CiShanMyDonateIntro> getResult() {
		return result;
	}

	public void setResult(List<CiShanMyDonateIntro> result) {
		this.result = result;
	}

	/** 我的捐献信息简介 */
	public class CiShanMyDonateIntro {
		/** 用户编号 */
		private String user_id;
		/** 项目编号 */
		private String project_id;
		/** 标题 */
		private String title;
		/** 摘要 */
		private String digest;
		/** 图片 */
		private String img;
		/** 捐献次数 */
		private String love_count;
		/** 捐献额 */
		private String gold;

		private String status;
		
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getProject_id() {
			return project_id;
		}

		public void setProject_id(String project_id) {
			this.project_id = project_id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDigest() {
			return digest;
		}

		public void setDigest(String digest) {
			this.digest = digest;
		}

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public String getLove_count() {
			return love_count;
		}

		public void setLove_count(String love_count) {
			this.love_count = love_count;
		}

		public String getGold() {
			return gold;
		}

		public void setGold(String gold) {
			this.gold = gold;
		}

	}

}
