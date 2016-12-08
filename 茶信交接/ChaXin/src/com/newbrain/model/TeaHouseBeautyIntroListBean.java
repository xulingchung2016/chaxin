package com.newbrain.model;

import java.util.List;

public class TeaHouseBeautyIntroListBean extends BaseJsonDataBean {
	/**茶馆丽人信息列表*/
	private List<TeaBeautyIntro> result;
	private String nextPage;

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public List<TeaBeautyIntro> getResult() {
		return result;
	}

	public void setResult(List<TeaBeautyIntro> result) {
		this.result = result;
	}
	public class TeaBeautyIntro{
		private String id;//主键
		private String sex;//性别，0：男，1：女，2：未知
		private String price;//价格
		private String levels;//等级,0:翡翠，1：钻石，2：黄金，3：白银
		private String sales;//成交量
		private String no;//编号
		private String age;//年龄
		private String userId;//用户id
		private String name;//姓名
		private String image;//图片，加上http://xxx/TeaMall可加载
		private String storeId;//商品id
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getLevels() {
			return levels;
		}
		public void setLevels(String levels) {
			this.levels = levels;
		}
		public String getSales() {
			return sales;
		}
		public void setSales(String sales) {
			this.sales = sales;
		}
		public String getNo() {
			return no;
		}
		public void setNo(String no) {
			this.no = no;
		}
		public String getAge() {
			return age;
		}
		public void setAge(String age) {
			this.age = age;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		public String getStoreId() {
			return storeId;
		}
		public void setStoreId(String storeId) {
			this.storeId = storeId;
		}

	}
}
