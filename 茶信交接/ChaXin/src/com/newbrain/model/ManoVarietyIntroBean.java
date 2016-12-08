package com.newbrain.model;

import java.util.List;


public class ManoVarietyIntroBean extends BaseJsonDataBean {
	/**茶庄园品种列表*/
	private List<ManoVarietyIntro> result;

	public List<ManoVarietyIntro> getResult() {
		return result;
	}

	public void setResult(List<ManoVarietyIntro> result) {
		this.result = result;
	}

	/**茶庄园品种信息*/
	public class ManoVarietyIntro {
		private String variety_id;
		private String variety_name;
		public String getVariety_id() {
			return variety_id;
		}
		public void setVariety_id(String variety_id) {
			this.variety_id = variety_id;
		}
		public String getVariety_name() {
			return variety_name;
		}
		public void setVariety_name(String variety_name) {
			this.variety_name = variety_name;
		}
	}
}
