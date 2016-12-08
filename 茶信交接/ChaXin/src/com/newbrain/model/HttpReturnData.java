package com.newbrain.model;

public class HttpReturnData {
	/**获取数据是否成功*/
	private boolean isSuccess;
	/**返回数据*/
	private Object obj;
	
	public HttpReturnData(boolean isSuccess,Object obj){
		this.isSuccess = isSuccess;
		this.obj = obj;
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public Object getObg() {
		return obj;
	}
	public void setObg(Object obg) {
		this.obj = obg;
	}
	
}
