package com.qcl.controller;


import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.qcl.config.IWXPayConfig;
import com.qcl.config.WxpayParam;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/wc")
public class WxPayController {

    /**
     * 调用微信统一下单接口并实现签名
     * @return
     * @throws Exception
     */
    @PostMapping("/pay/{openid}/{iprice}")
    public Map<String,String> wxPayFunction(@PathVariable("openid") String openid, @PathVariable("iprice") Double iprice) throws Exception {



        IWXPayConfig config = new IWXPayConfig();
        WxpayParam wxpayParam = new WxpayParam();
        //需要换成处理业务的回调接口
        String notifyUrl = "/hello";

        WXPay wxpay = new WXPay(config);

        Map<String,String> data = new HashMap<>();
        data.put("appid",config.getAppID());
        data.put("mch_id",config.getMchID());
        data.put("body",wxpayParam.getBody());
        data.put("out_trade_no",wxpayParam.getOutTradeNo());
        data.put("device_info","");
        data.put("fee_type","CNY");
        //  订单金额:单位元
        if(iprice != null){
            wxpayParam.setTotalFee(iprice);
        }
        data.put("total_fee",wxpayParam.getTotalFee());
        data.put("spbill_create_ip","127.0.0.1");
        data.put("notify_url",notifyUrl);
        data.put("trade_type","JSAPI");
        data.put("openid",openid);
        data.put("sign",WXPayUtil.generateSignature(data,config.getKey()));

        Map<String,String> resp = wxpay.unifiedOrder(data);


        if("SUCCESS".equals(resp.get("return_code"))){
            Map<String,String> reData = new HashMap<>();
            reData.put("appId",config.getAppID());
            reData.put("nonceStr",resp.get("nonce_str"));
            String newPackage = "prepay_id=" + resp.get("prepay_id");
            reData.put("package", newPackage);
            reData.put("signType","MD5");
            reData.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
            String newSign = WXPayUtil.generateSignature(reData, config.getKey());
            resp.put("paySign",newSign);
            resp.put("timeStamp", reData.get("timeStamp"));
            resp.put("package",newPackage);
            System.out.println(resp);
            return resp;
        } else {
            Map<String,String> errMap = new HashMap<>();
            errMap.put("err_code","参数请求失败");
            return errMap;
        }

    }

    /**
     *  获取OpenID
     */
//    @GetMapping("/getOpenid")
//    public String getOpenid(@RequestParam("code") String code) throws Exception {
//        IWXPayConfig config = new IWXPayConfig();
//        String appID = config.getAppID();
//        String appSecret = "cae735d666c8bfb06368dd880084dc3f";
//        String result = "";
//        try{//请求微信服务器，用code换取openid。HttpUtil是工具类，后面会给出实现，Configure类是小程序配置信息，后面会给出代码
//            result = HttpUtil.doGet(
//                    "https://api.weixin.qq.com/sns/jscode2session?appid="
//                            + appID + "&secret="
//                            + appSecret + "&js_code="
//                            + code
//                            + "&grant_type=authorization_code", null);
//            System.out.println(result);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        OpenIdJson openIdJson = mapper.readValue(result,OpenIdJson.class);
//        System.out.println(result.toString());
//        System.out.println(openIdJson.getOpenid());
//        return result;
//    }


    @GetMapping("/pay/success")
    public Integer paySuccess(@RequestParam("code") Integer code){
        return code;
    }

    @GetMapping("/pay/fail")
    public Integer payFail(@RequestParam("code") Integer code){
        return code;
    }

    @GetMapping("/pay/getPrice")
    public String getPrice(@RequestParam("id") Integer id){
        return "查询成功";
    }



}
