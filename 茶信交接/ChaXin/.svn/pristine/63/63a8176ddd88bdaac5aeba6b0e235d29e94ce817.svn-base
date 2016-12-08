package com.newbrain.user;

public class User {

	/*
	 * “phoneNo”:”手机号码”, “password”:”密码”, “image”:”图片地址”, “nickName”:”昵称”,
	 * “signature”:”签名”, “sex”:”性别”, “age”:”年龄”
	 */
	
	private static User user;
	
	public static User getInstance()
	{
		
		if(user == null)
		{			
			user = new User();			
		}
		
		return user;
	}
	
	
	private  User() {
		super();
		// TODO Auto-generated constructor stub
		
		this.phoneNo = "";

		this.id = "";

		this.password = "";
		this.image = "";

		this.nickName = "";
		this.signature = "";
		this.sex = "";
		this.age = "";
		this.isLogin = false;
		this.receive_Namephone = "";
		this.receive_DetailAddress = "";
	}
	
	private boolean isLogin;
	private String phoneNo;

	private String id;

	private String password;
	private String image;

	private String nickName;
	private String signature;
	private String sex;
	private String age;
	private String token;
	
	private String register_phoneNo;
	private String register_verity;
	
	private String receive_Namephone;
	private String receive_DetailAddress;
	
	public String getreceive_Namephone() {
		return receive_Namephone;
	}


	public void setreceive_Namephone(String receive_Namephone) {
		this.receive_Namephone = receive_Namephone;
	}
	
	public String getreceive_DetailAddress() {
		return receive_DetailAddress;
	}


	public void setreceive_DetailAddress(String receive_DetailAddress) {
		this.receive_DetailAddress = receive_DetailAddress;
	}
	

	public String getRegister_phoneNo() {
		return register_phoneNo;
	}


	public void setRegister_phoneNo(String register_phoneNo) {
		this.register_phoneNo = register_phoneNo;
	}


	public String getRegister_verity() {
		return register_verity;
	}


	public void setRegister_verity(String register_verity) {
		this.register_verity = register_verity;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public boolean isLogin() {
		return isLogin;
	}


	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

}
