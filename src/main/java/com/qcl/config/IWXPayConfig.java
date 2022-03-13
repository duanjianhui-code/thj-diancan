package com.qcl.config;

import com.github.wxpay.sdk.WXPayConfig;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 重写微信接口，并添加商户的个人信息
 */

public class IWXPayConfig implements WXPayConfig {

    //todo 记得换成你自己的小程序appid
    private String appid = "wx00670d2c9d512d57";
    //todo 记得换成你自己的小程序支付的商户号
    private String mchid = "1600042478";
    //todo 记得换成你自己的小程序支付的商户密匙
    private String key = "LebianXiaoshitouWeixin2501902696";

    private byte[] certData;

    /**
     * 读取证书内的内容
     *
     * @throws Exception
     */
    public IWXPayConfig() throws Exception {
//        InputStream certStrem = Thread.currentThread()
//                .getContextClassLoader().getResourceAsStream("cert/apiclient_cert.p12");
//        this.certData = IOUtils.toByteArray(certStrem);
//        certStrem.close();
    }

    /**
     * 返回用户的APPID
     *
     * @return
     */
    @Override
    public String getAppID() {
        return appid;
    }

    @Override
    public String getMchID() {
        return mchid;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(this.certData);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 0;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 0;
    }
}
