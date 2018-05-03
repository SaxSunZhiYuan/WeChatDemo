package cn.tmh.wechat.single;

/**
 * @description:
 * @author:SunZhiYuan
 * @Created:2018/5/3 0003 16:33
 */
public class MailSender implements Sender {
    @Override
    public void Send() {
        System.out.println("this is mailsender!");
    }
}