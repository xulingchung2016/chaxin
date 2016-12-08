package com.newbrain.model;

import java.util.List;


public class CommentsBean extends BaseJsonDataBean {
private List <Comments> result;
	
	public List<Comments> getResult() 
	{
		return result;
	}

	public CommentsBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setResult(List<Comments> result) 
	{
		this.result = result;
	}
	
	public static class Comments{
		private String goodsId;
		private String beautyId;
		private String highOpinion;
		private String serviceScore;
		private String expressScore;
		private String goodsScore;
		private String userId;
		private String phoneNo;
		private String content;
		private String nickName;
		
		/*public  Comments(String goodsId, String highOpinion,
				String serviceScore, String expressScore, String goodsScore,
				String userId, String content) {
			super();
			this.goodsId = goodsId;
			this.highOpinion = highOpinion;
			this.serviceScore = serviceScore;
			this.expressScore = expressScore;
			this.goodsScore = goodsScore;
			this.userId = userId;
			this.content = content;
		}*/
		
		public String getBeautyId() {
			return beautyId;
		}

		public void setBeautyId(String beautyId) {
			this.beautyId = beautyId;
		}

		public String getPhoneNo() {
			return phoneNo;
		}

		public void setPhoneNo(String phoneNo) {
			this.phoneNo = phoneNo;
		}

		public String getGoodsId() {
			return goodsId;
		}
		
		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		public void setGoodsId(String goodsId) {
			this.goodsId = goodsId;
		}
		public String getHighOpinion() {
			return highOpinion;
		}
		public void setHighOpinion(String highOpinion) {
			this.highOpinion = highOpinion;
		}
		public String getServiceScore() {
			return serviceScore;
		}
		public void setServiceScore(String serviceScore) {
			this.serviceScore = serviceScore;
		}
		public String getExpressScore() {
			return expressScore;
		}
		public void setExpressScore(String expressScore) {
			this.expressScore = expressScore;
		}
		public String getGoodsScore() {
			return goodsScore;
		}
		public void setGoodsScore(String goodsScore) {
			this.goodsScore = goodsScore;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		
		
		
	}
}
