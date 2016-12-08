package com.newbrain.model;

import java.util.List;

import com.newbrain.model.TeaHouseDetailInfoBean.TeaHouseDetailInfo;

public class SearchTeaHouseIntroBean extends BaseJsonDataBean {
	/**茶馆信息列表*/
	private List<TeaHouseDetailInfo> result;

	public List<TeaHouseDetailInfo> getResult() {
		return result;
	}

	public void setResult(List<TeaHouseDetailInfo> result) {
		this.result = result;
	}

}
