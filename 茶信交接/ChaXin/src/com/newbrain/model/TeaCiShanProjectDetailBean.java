package com.newbrain.model;

import java.util.List;

/**
 * 慈善项目详情
 * 
 * @author Administrator
 * 
 */
public class TeaCiShanProjectDetailBean extends BaseJsonDataBean {

	private CiShanProjectDetail result;

	public CiShanProjectDetail getResult() {
		return result;
	}

	public void setResult(CiShanProjectDetail result) {
		this.result = result;
	}

	/** 慈善项目详情信息 */
	public class CiShanProjectDetail {

		/** 项目编号 */
		private String project_id;
		private String total_gold;
		private String total_count;
		private String detail_img;
		/** 标题 */
		private String title;
		/** 摘要 */
		private String digest;
		/** 项目说明 */
		private String description;
		/** 善款接收机构 */
		private String receiver;
		/** 执行机构 */
		private String actuator;
		/** 图片列表 */
		private String images;

		private String status;
		
		public String getProject_id() {
			return project_id;
		}

		public void setProject_id(String project_id) {
			this.project_id = project_id;
		}

		public String getTotal_gold() {
			return total_gold;
		}

		public void setTotal_gold(String total_gold) {
			this.total_gold = total_gold;
		}

		public String getTotal_count() {
			return total_count;
		}

		public void setTotal_count(String total_count) {
			this.total_count = total_count;
		}

		public String getDetail_img() {
			return detail_img;
		}

		public void setDetail_img(String detail_img) {
			this.detail_img = detail_img;
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

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getReceiver() {
			return receiver;
		}

		public void setReceiver(String receiver) {
			this.receiver = receiver;
		}

		public String getActuator() {
			return actuator;
		}

		public void setActuator(String actuator) {
			this.actuator = actuator;
		}


		public String getImages() {
			return images;
		}

		public void setImages(String images) {
			this.images = images;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}


		public class Img{
			private String img;
			
			public String getImg() {
				return img;
			}
			
			public void setImg(String img) {
				this.img = img;
			}
		}
	}

}
