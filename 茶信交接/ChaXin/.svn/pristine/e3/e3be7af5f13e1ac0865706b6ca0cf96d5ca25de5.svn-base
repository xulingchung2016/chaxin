package com.pay.yl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;

import com.unionpay.UPPayAssistEx;
/**
 * 银联支付功能
 * @author zyh
 *
 */
public class YLPayUtils {
	   public static final String LOG_TAG = "PayDemo";
	   public static final int PLUGIN_VALID = 0;
	    public static final int PLUGIN_NOT_INSTALLED = -1;
	    public static final int PLUGIN_NEED_UPGRADE = 2;
	    /*****************************************************************
	     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
	     *****************************************************************/
	    private static final String mMode = "01";
	 public static void doStartUnionPayPlugin(final Activity context, String tn) {
		 if(!TextUtils.isEmpty(tn)){
	        // mMode参数解释：
	        // 0 - 启动银联正式环境
	        // 1 - 连接银联测试环境
	        int ret = UPPayAssistEx.startPay(context, null, null, tn, mMode);
	        if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
	            // 需要重新安装控件
	            Log.e(LOG_TAG, " plugin not found or need upgrade!!!");

	            AlertDialog.Builder builder = new AlertDialog.Builder(context);
	            builder.setTitle("提示");
	            builder.setMessage("完成购买需要安装银联支付控件，是否安装？");

	            builder.setNegativeButton("确定",
	                    new DialogInterface.OnClickListener() {
	                        @Override
	                        public void onClick(DialogInterface dialog, int which) {
	                            UPPayAssistEx.installUPPayPlugin(context);
	                            dialog.dismiss();
	                        }
	                    });

	            builder.setPositiveButton("取消",
	                    new DialogInterface.OnClickListener() {

	                        @Override
	                        public void onClick(DialogInterface dialog, int which) {
	                            dialog.dismiss();
	                        }
	                    });
	            builder.create().show();

	        }
	        Log.e(LOG_TAG, "" + ret);
	    }
	 }

}
