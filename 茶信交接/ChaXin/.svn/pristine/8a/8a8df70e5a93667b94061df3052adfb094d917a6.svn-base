package com.newbrain.chaxin.teasearchtea;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.CancelableCallback;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.FindTeaHouseIntroBean;
import com.newbrain.model.FindTeaHouseIntroBean.TeaHouseIntro;
import com.newbrain.model.HttpReturnData;
import com.newbrain.utils.ToastUtil;

public class TeaSearchTeaMainFrament extends Fragment implements
		OnClickListener, LocationSource, AMapLocationListener, OnMarkerClickListener, OnInfoWindowClickListener, InfoWindowAdapter {

	private TeaSearchTeaActivity mParentsActivity;
	private TextView tv_startPoint;
	private TextView tv_endPoint;
	private Button btn_GPS;
	private JsonThread mThread;
	private MapView mapView;
	private AMap aMap;
	private double myLat = -1;
	private double myLon = -1;

	private boolean isFirstLoc = true;

	private float zoomLevel = 16;
	private Dialog mapSelectDialog;

	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private Marker marker;// 定位雷达小图标

	private List<Marker> markers=new ArrayList<Marker>();
	private SharedPreferences sp;
	/** 搜索到的茶馆简介列表 */
	private List<TeaHouseIntro> mTeaHouseIntros;
	@SuppressLint("HandlerLeak")
	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.FLAG_SEARCH_TEAHOUSE_NAME:
			case Constant.FLAG_FIND_TEAHOUSE_LOACTION:
				CustomProgressDialog.stopProgressDialog();
				HttpReturnData reData = (HttpReturnData) msg.obj;
				if (reData.isSuccess()) {
					FindTeaHouseIntroBean findTeaHouseIntroBean = (FindTeaHouseIntroBean) reData
							.getObg();
					if (findTeaHouseIntroBean.getCode().equals("1")) {
						mTeaHouseIntros = findTeaHouseIntroBean.getResult();
						clearMarker();
						for (int i = 0; i < mTeaHouseIntros.size(); i++) {
							TeaHouseIntro teaHouseIntro = mTeaHouseIntros.get(i);
							try {
								double lat=Double.parseDouble(teaHouseIntro.getLatitude());
								double lon=Double.parseDouble(teaHouseIntro.getLongitude());
								LatLng latlng=new LatLng(lat, lon);
								Marker marker = aMap.addMarker(new MarkerOptions()
								.position(latlng)
								.title(teaHouseIntro.getName())
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.chaguanicon))
								);
								markers.add(marker);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					} else {
						System.out.println("result:"
								+ findTeaHouseIntroBean.getMessage());
					}
				}
				break;
			case Constant.SEARCH_BACK:
				mTeaHouseIntros = (List<TeaHouseIntro>) msg.obj;
				TeaHouseIntro teaHouseIntro = mTeaHouseIntros.get(msg.arg1);
				LatLng latLng = new LatLng(Double.parseDouble(teaHouseIntro
						.getLatitude()), Double.parseDouble(teaHouseIntro
						.getLongitude()));
				Log.e("test", "lat:"+latLng);
				sp.edit().putString("lat", teaHouseIntro.getLatitude()).commit();
				sp.edit().putString("lon", teaHouseIntro.getLongitude()).commit();
				break;
			case 2147:

				break;
			}
		}
	};

	/**
	 * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
	 */
	private void changeCamera(CameraUpdate update, CancelableCallback callback) {
			aMap.moveCamera(update);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mParentsActivity = (TeaSearchTeaActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tea_searchtea_main_frament, null);
		mParentsActivity.setTopBar(mParentsActivity.SEARCHTEA_MAIN_PAGE);
		mapView = (MapView) view.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		initView(view);
		System.out.println("mapView onCreateView");
		initData();
		
		sp=mParentsActivity.getSharedPreferences("config",mParentsActivity.MODE_PRIVATE);
		return view;
	}

	private void initView(View view) {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
		tv_startPoint = (TextView) view.findViewById(R.id.tv_startPoint);
		tv_startPoint.setText("我的位置");
		tv_endPoint = (TextView) view.findViewById(R.id.tv_endPoint);
		btn_GPS = (Button) view.findViewById(R.id.btn_GPS);
		btn_GPS.setOnClickListener(this);
		aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
		aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
		aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
	}

	/** 设置高德地图 */
	private void setUpMap() {
		ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point1));
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point2));
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point3));
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point4));
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point5));
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point6));
		marker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
				.icons(giflist).period(50));
		// 自定义系统定位小蓝点
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.location_marker));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
		// myLocationStyle.anchor(int,int)//设置小蓝点的锚点
		myLocationStyle.strokeWidth(0.1f);// 设置圆形的边框粗细
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setMyLocationRotateAngle(180);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
	}

	private void initData() {

	}

	public LatLng mPosition = null;// 当前位置
	// 终点
	public String dlat = null;
	public String dlon = null;

	/** 根据位置查找茶馆 */
	private void startFindTeaHouseLocation() {
		List<Bean> list = new ArrayList<Bean>();
		startSearchThread(list, Constant.FLAG_FIND_TEAHOUSE_LOACTION);
	}

	/** 根据名称查找茶馆 */
	public void startSearchTeaHouseName(String key_word) {
		List<Bean> list = new ArrayList<Bean>();
		list.add(new Bean("name", key_word));
		startSearchThread(list, Constant.FLAG_SEARCH_TEAHOUSE_NAME);
	}
	private void startSearchThread(List<Bean> params, int flag) {
		if (mPosition != null) {
			CustomProgressDialog.startProgressDialog(mParentsActivity);
			params.add(new Bean("lng", String.valueOf(mPosition.longitude)));
			params.add(new Bean("lat", String.valueOf(mPosition.latitude)));
			for (int i = 0; i < params.size(); i++) {
				Bean bean = params.get(i);
			}
			if (mThread != null) {
				mThread.cancleReturnData();
			}
			mThread = new JsonThread(mParentsActivity, params, mHandler, flag);
			mThread.start();
		}
	}

	/**
	 * 点击跳到手机地图客户端导航
	 * */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_GPS:
			AlertDialog.Builder builder = new Builder(mParentsActivity);
			builder.setTitle("选择地图");
			builder.setItems(new String[] {  "高德地图" },
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							switch (which) {
							/*
							 * case 0: LatLng pt1 = mPosition; LatLng pt2 = new
							 * LatLng(Double .parseDouble(dlat), Double
							 * .parseDouble(dlon));
							 * 
							 * // 构建 导航参数 RouteParaOption para = new
							 * RouteParaOption() .startPoint(pt1) .endPoint(pt2)
							 * .startName("我的位置") .endName(
							 * tv_endPoint.getText() .toString()); try {
							 * BaiduMapRoutePlan.openBaiduMapDrivingRoute( para,
							 * mParentsActivity); } catch (Exception e) {
							 * e.printStackTrace(); } break;
							 */
							case 0:
								String latitude = String
										.valueOf(mPosition.latitude);
								String longitude = String
										.valueOf(mPosition.longitude);
								String uri = "androidamap://route?sourceApplication="
										+ getString(R.string.app_name)
										+ "&slat="
										+ latitude
										+ "&slon="
										+ longitude
										+ "&sname="
										+ tv_startPoint.getText().toString()
										+ "&dlat="
										+ dlat
										+ "&dlon="
										+ dlon
										+ "&dname="
										+ tv_endPoint.getText().toString()
										+ "&dev=0&m=0&t=0";
								System.out.println("uri:" + uri);
								try {
									Intent intent = new Intent(
											"android.intent.action.VIEW",
											android.net.Uri
													.parse("androidamap://navi?sourceApplication=chaxin&poiname=fangheng&lat="
															+ dlat
															+ "&lon="
															+ dlon
															+ "&dev=0&style=2"));
									android.net.Uri.parse(uri);
									intent.setPackage("com.autonavi.minimap");
									startActivity(intent);
								} catch (Exception e) {
									ToastUtil.Toast_Center(mParentsActivity,
											"没有安装高德地图客户端");
								}
								break;
							}

						}
					});

			if (mPosition != null && dlat != null && dlon != null) {
				mapSelectDialog = builder.create();
				mapSelectDialog.show();
			}
			// try {
			// Intent intent =
			// Intent.getIntent("intent://map/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving®ion=西安&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
			// if(isInstallByread("com.baidu.BaiduMap")){
			// startActivity(intent); //启动调用
			// Log.e("GasStation", "百度地图客户端已经安装") ;
			// }else{
			// Log.e("GasStation", "没有安装百度地图客户端") ;
			// }
			// } catch (URISyntaxException e) {
			// e.printStackTrace();
			// }
			break;
		}
	}

	/** 定位完回调函数 */
	@Override
	public void onLocationChanged(Location aLocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}
	/**定位后的回调函数*/
	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		// TODO Auto-generated method stub
		if (mListener != null && aLocation != null) {
			mListener.onLocationChanged(aLocation);// 显示系统小蓝点
			marker.setPosition(new LatLng(aLocation.getLatitude(), aLocation
					.getLongitude()));// 定位雷达小图标
			float bearing = aMap.getCameraPosition().bearing;
			aMap.setMyLocationRotateAngle(bearing);// 设置小蓝点旋转角度
			aMap.moveCamera(CameraUpdateFactory.zoomTo(zoomLevel));
			
			myLat = aLocation.getLatitude();
			myLon=aLocation.getLongitude();
			mPosition=new LatLng(myLat, myLon);
			startFindTeaHouseLocation();
			
			String back_lat = sp.getString("lat", null);
			String back_lon = sp.getString("lon", null);
			Log.e("test","back_lat"+ back_lat);
			if(back_lat!=null&&!back_lat.equals("")){
				
				LatLng latLng=new LatLng(Double.parseDouble(back_lat), Double.parseDouble(back_lon));
				/*changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
						latLng, 4, 0, 30)), null);*/
				aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
				aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
				sp.edit().putString("lat","").commit();
				sp.edit().putString("lon","").commit();
			}
			
			
		}
	}

	private void clearMarker(){
		if(markers!=null){
			for (int i = 0; i < markers.size(); i++) {
				markers.get(i).remove();
			}
		}
	}
	
	/** 激活定位 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		// TODO Auto-generated method stub
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy
					.getInstance(mParentsActivity);
			/*
			 * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
			mAMapLocationManager.requestLocationUpdates(
					LocationProviderProxy.AMapNetwork,60*1000, 10, this);
		}
	}
	/** 停止定位 */
	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destory();
		}
		if(null != aMap)aMap  = null;
		mAMapLocationManager = null;
	}
	/**
	 * 方法必须重写
	 */
	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
//		activate(mListener);
	}
	/**
	 * 方法必须重写
	 */
	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		mapView.onDestroy();
	}
	/**marker点击事件*/
	@Override
	public boolean onMarkerClick(final Marker marker) {
		// TODO Auto-generated method stub
		dlat=marker.getPosition().latitude+"";
		dlon=marker.getPosition().longitude+"";
		tv_endPoint.setText(marker.getTitle());
		return false;
	}
	@Override
	public void onInfoWindowClick(Marker arg0) {
		ToastUtil.Toast(mParentsActivity, "点击了infowindow");
		if(mTeaHouseIntros!=null){
			for (int i = 0; i <mTeaHouseIntros.size(); i++) {
				TeaHouseIntro teaHouseIntro = mTeaHouseIntros.get(i);
				try {
					double lat=Double.parseDouble(teaHouseIntro.getLatitude());
					double lon=Double.parseDouble(teaHouseIntro.getLongitude());
					if(arg0.getPosition().latitude==lat&&arg0.getPosition().longitude==lon){
						mParentsActivity.replaceFragment(
								new TeaSearchTeaDetailMainFragment(teaHouseIntro.getId()), true, false, true);
						break;
					}
				} catch (Exception e) {
					
				}
			}
		}
	}
	/**
	 * 自定义infowinfow窗口
	 */
	public void render(Marker marker, View view) {
		TextView name=(TextView) view.findViewById(R.id.name);
		TextView distance=(TextView) view.findViewById(R.id.distance);
		
		if(mTeaHouseIntros!=null){
			for (int i = 0; i <mTeaHouseIntros.size(); i++) {
				TeaHouseIntro teaHouseIntro = mTeaHouseIntros.get(i);
				try {
					double lat=Double.parseDouble(teaHouseIntro.getLatitude());
					double lon=Double.parseDouble(teaHouseIntro.getLongitude());
					if(marker.getPosition().latitude==lat&&marker.getPosition().longitude==lon){
						DecimalFormat df = new DecimalFormat("######0.00");
						double d = Double.parseDouble(teaHouseIntro
								.getDistance());
						distance.setText(df.format(d / 1000) + "KM");
						break;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		name.setText(marker.getTitle());
		
	}

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		View infoWindow = View.inflate(mParentsActivity,
				R.layout.tea_search_infowindow, null);

		render(arg0, infoWindow);
		return infoWindow;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		View infoWindow = View.inflate(mParentsActivity,
				R.layout.tea_search_infowindow, null);

		render(arg0, infoWindow);
		return infoWindow;
	}

}
