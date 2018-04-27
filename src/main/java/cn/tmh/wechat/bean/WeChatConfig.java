package cn.tmh.wechat.bean;

public class WeChatConfig {

    private static String ACCESS_TOKEN;

    public static String getAccessToken() {
        return ACCESS_TOKEN;
    }

    public static void setAccessToken(String accessToken) {
        ACCESS_TOKEN = accessToken;
    }
}
