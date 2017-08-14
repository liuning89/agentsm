package com.lifang.agentsm.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class PushUtils {

    private static final Logger logger = LoggerFactory.getLogger(PushUtils.class);

    @Autowired
    private ThreadPoolTaskExecutor executor;

//	public void sendVerifyCodeMSg(final String mobile, final String verifyCode, final String content) {
//		executor.submit(new Runnable() {
//			@Override
//			public void run() {
//				String titile = "利房宝验证码" + verifyCode + "！点击查看...";
//				String messge = content;
//				sendPushMsg(mobile, titile, messge,"2");
//			}
//		});
//	}
    
    /**
     * 
     * @param mobile
     * @param msg
     * @param result
     */
    public void sendUserLookHouseMsg(final String mobile ,final String msg,final String result){
    	executor.submit(new Runnable() {
            @Override
            public void run() {
                    String titile = "你领取的"+msg+"拍照任务已审核"+result+"！点击查看...";
                    String messge = "您好！您领取的"+msg+"拍照任务已审核"+result+"！";
                    sendPushMsg(mobile, titile, messge,"1");
            }
        });
    }
    
    public void sendPayedPushMsg(final List<PushPayInfo> pushPayInfos) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                for (PushPayInfo tmp : pushPayInfos) {
                    String titile = "利房宝奖金已到账！点击查看...";
                    String msg = "您好！" + tmp.getPaySum() + "元奖金已转账到您的支付宝，感谢您为利房宝平台作出的贡献！";
                    sendPushMsg(tmp.getMobile(), titile, msg,"1");
                }
            }
        });
    }

    public void sendPushMsg(String mobiles, String title, String msg,String msgType) {
       /* DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(Global.PUSH_SERVER_URL);
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();  
        params.add(new BasicNameValuePair("broadcast", "N"));  
        params.add(new BasicNameValuePair("username", mobiles)); 
        params.add(new BasicNameValuePair("title", title));  
        params.add(new BasicNameValuePair("message", msg)); 
        params.add(new BasicNameValuePair("msgType", msgType));  
        params.add(new BasicNameValuePair("uri", "")); 
        
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse response = client.execute(httpPost);
            System.out.println(response.getStatusLine().getStatusCode());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 302) {
               logger.info("手机号{}，消息{}，推送成功！", mobiles, msg);
            } else {
               logger.info("手机号{}，消息{}，推送失败！", mobiles, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }*/
    }
    

    public static void main(String[] args) {
        PushUtils pushUtils = new PushUtils();
        pushUtils.sendPushMsg("12312314234", "title", "message","1");
    }

    public static class PushPayInfo {
        private String mobile;
        private String paySum;

        public PushPayInfo() {
        }

        public PushPayInfo(String mobile, String paySum) {
            super();
            this.mobile = mobile;
            this.paySum = paySum;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPaySum() {
            return paySum;
        }

        public void setPaySum(String paySum) {
            this.paySum = paySum;
        }
    }
}
