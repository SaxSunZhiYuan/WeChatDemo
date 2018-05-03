package cn.tmh.wechat.single;

/**
 * @description:
 * @author:SunZhiYuan
 * @Created:2018/5/3 0003 16:34
 */
public class SmsSender implements Sender {

    @Override
    public void Send() {
        System.out.println("this is sms sender!");
    }
}