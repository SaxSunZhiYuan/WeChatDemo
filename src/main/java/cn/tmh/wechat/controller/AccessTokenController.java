package cn.tmh.wechat.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/accesstoken")
public class AccessTokenController {

    private Logger log= LoggerFactory.getLogger(AccessTokenController.class);

    @Value("${wechat.appId}")
    private String APP_ID;

    @Value("${wechat.appSecret}")
    private String APP_SECRET;

    private final static String API_URL = "sh.api.weixin.qq.com";


    /**
     * 通用域名(api.weixin.qq.com)，使用该域名将访问官方指定就近的接入点；
     * 上海域名(sh.api.weixin.qq.com)，使用该域名将访问上海的接入点；
     * @return
     */


    /**
     * 过去最新 accesstoken
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String getAccessToken(){
        //调用获取
        //https请求方式: GET
        //https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET

        //这里的url就不强调啥 性能问题 实际开发自己改
        String url = "https://api.weixin.qq.com/cgi-bin/token?" +
                "grant_type=client_credential&" +
                "appid=" + APP_ID + "&" +
                "secret=" + APP_SECRET;

        log.warn("http get start url >>>>>>>>{}", url);
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpresponse = null;
        try{
            HttpClient httpCient = HttpClients.createDefault();
            log.warn(">>>>>>>>>>httpCilent  start >>>>>>>>>>");
            httpresponse = httpCient.execute(httpGet);
            log.warn(">>>>>>>>>>httpCilent  end >>>>>>>>>>");
            HttpEntity httpEntity = httpresponse.getEntity();
            log.warn(">>>>>>>>>>httpEntity  info{} >>>>>>>>>> {}", httpEntity.toString(), httpEntity);
            String response = EntityUtils.toString(httpEntity, "utf-8");
            return response;
        }catch(Exception e){
            return "失败";
        }
    }
}
