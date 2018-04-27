package cn.tmh.wechat.controller;

import cn.tmh.wechat.bean.WeChatCheck;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequestMapping("/base")
@RestController
public class BaseController {

    private Logger log= LoggerFactory.getLogger(BaseController.class);

    /**
     * 服务测试
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "HelloWorld";
    }


    /**
     *
     */

    /**
     * 1.4 开发者基本配置
     * 模拟参数  signature=63603f83cb6f12d376a5460779c8a3f59d150990
     *          echostr=12631531042608380285
     *          timestamp=1517668717
     *          nonce=3442480512
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(@RequestParam String signature, @RequestParam String timestamp,
                     @RequestParam String nonce, @RequestParam String echostr, HttpServletResponse response) throws IOException {

        WeChatCheck weChatCheck = new WeChatCheck(signature, timestamp, nonce, echostr);
        log.warn(">>>>> test start <<<<< paramInfo{}:", weChatCheck.toString());
        String relEchostr = CheckSignature(weChatCheck);
        //返回echostr给微信服务器

        log.warn(">>>>> test end --------  <<<<<");
        OutputStream os=response.getOutputStream();
        os.write(URLEncoder.encode(relEchostr,"UTF-8").getBytes()); os.flush(); os.close();
    }

    public String CheckSignature(WeChatCheck weChatCheck){
        List<String> list = new ArrayList();
        String token="mgzt";
        list.add(weChatCheck.getNonce());
        list.add(weChatCheck.getTimestamp());
        list.add(token);
        //字典序排序
        Collections.sort(list);
        //SHA1加密
        String checksignature=DigestUtils.sha1Hex(list.get(0)+list.get(1)+list.get(2));
        log.warn(">>>>> Signature request  param <<<<< :{}", weChatCheck.getSignature());
        log.warn(">>>>> Signature sha1  <<<<< :{}", checksignature);
        if(checksignature.equals(weChatCheck.getSignature())){
            return weChatCheck.getEchostr();
        }
        return null;
    }

    @RequestMapping(value = "/sha", method = RequestMethod.GET)
    public String getSHA1(){
        List<String> list = new ArrayList();
        list.add("3442480512");
        list.add("1517668717");
        list.add("mgzt");
        Collections.sort(list);
        String signature = DigestUtils.sha1Hex(list.get(0)+list.get(1)+list.get(2));
        return signature;
    }


}

