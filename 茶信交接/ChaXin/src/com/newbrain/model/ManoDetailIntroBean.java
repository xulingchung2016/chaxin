package com.newbrain.model;

import java.util.List;


public class ManoDetailIntroBean extends BaseJsonDataBean {
	/**茶庄园详情*/
	private ManoDetailIntro result;

	public ManoDetailIntro getResult() {
		return result;
	}

	public void setResult(ManoDetailIntro result) {
		this.result = result;
	}

	/**茶庄园详情信息*/
	public class ManoDetailIntro {
		private String manor_id;
		private String manor_name;
		private String memo;
		private String notice;
		private String price;
		private String statistics;
	
		private String image;
		private String samllIimage;
		private String manorIntro;
		
		
		public String getManorIntro() {
			return manorIntro;
		}
		public void setManorIntro(String manorIntro) {
			this.manorIntro = manorIntro;
		}
		public String getSamllIimage() {
			return samllIimage;
		}
		public void setSamllIimage(String samllIimage) {
			this.samllIimage = samllIimage;
		}
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
		public String getMemo() {
			return memo;
		}
		public void setMemo(String memo) {
			this.memo = memo;
		}
		public String getNotice() {
			return notice;
		}
		public void setNotice(String notice) {
			this.notice = notice;
		}
		
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getStatistics() {
			return statistics;
		}
		public void setStatistics(String statistics) {
			this.statistics = statistics;
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