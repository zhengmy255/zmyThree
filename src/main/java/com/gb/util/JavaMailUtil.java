package com.gb.util;


import com.gb.pojo.tbMailHistory;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Administrator on 2017/6/7.
 */
public class JavaMailUtil {


    public static final Logger log = Logger.getLogger(JavaMailUtil.class);


    /**
     * 创建 javaMailsession会话
     */
    public static Session createJavaMailSession(final String username , final String password ){
        Properties prop = new Properties();
        Authenticator auth = null;

        Session session = null;
        try{
            //邮件参数 1. 邮件服务器 2 . smtp发送邮件协议 3.是否auth安全认证
            prop.setProperty("mail.host", ConfigUtil.get("mail.host"));
            prop.setProperty("mail.transport.protocol",  ConfigUtil.get("mail.transport.protocol"));
            prop.setProperty("mail.smtp.auth",ConfigUtil.get("mail.smtp.auth"));
            //使用JavaMail发送邮件的5个步骤
            //1、创建session
            // 创建验证器
            auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };
            session = Session.getInstance(prop,auth);
            log.info("邮件会话session成功");
        }catch (Exception e){
            e.printStackTrace();
            log.error("邮件session创建失败"+e.getMessage());
        }
        return session;
    }



    public static int sendJavaMail(HttpServletRequest request, tbMailHistory mail){
        int x = 0;
        try {
            //1.创建 邮件会话
            Session session = createJavaMailSession(mail.getAddresser(),mail.getSendPwd());
            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            session.setDebug(true);

            //通过session得到transport对象

            //创建邮件对象
            MimeMessage message = new MimeMessage(session);

            // 设置发件人
            message.setFrom(new InternetAddress(mail.getAddresser()));

            // 设置收件人
            message.addRecipients(Message.RecipientType.TO, mail.getRecipients());
            // 设置抄送人
            if (null != mail.getToRecipients() && !"".equals(mail.getToRecipients())){
                message.addRecipients(Message.RecipientType.CC,mail.getToRecipients());
            }
            // 设置密送人
            if (null != mail.getsecretRecipients() && !"".equals(mail.getsecretRecipients())){
                message.addRecipients(Message.RecipientType.BCC,mail.getsecretRecipients());
            }

            //设置邮件主题
            message.setSubject(mail.getMailStyle());


            // 创建部件集对象----完整邮件的部件集对象
            MimeMultipart multipart = new MimeMultipart();
            //获取附件文件夹得真实路径
            String realPath = request.getServletContext().getRealPath(ConfigUtil.get("mailAttachPath"));

            //==================设置文本============================
            //设置邮件 文本内容
            MimeBodyPart text = new MimeBodyPart();//创建一个部件
            // 设置邮件文本内容
            text.setContent("商品信息excel<img src='cid:aaa.jpg'/>","text/html;charset=utf-8");

            ////添加到部件集对象中
            multipart.addBodyPart(text);

            //==========================设置图片附件===========================
            MimeBodyPart imgbody = new MimeBodyPart();
            DataHandler hd = new DataHandler(new FileDataSource(realPath+"/"+"1.jpg"));
            imgbody.setDataHandler(hd);
            //设置附件名称
            imgbody.setFileName(hd.getName());
            imgbody.setContentID("aaa.jpg");
            multipart.addBodyPart(imgbody);

            //======================设置附件========================================

            //循环 创建 邮件附件对象
            if (null != mail.getAccessoryName() && !"".equals(mail.getAccessoryName())){
                String[] attachArray = mail.getAccessoryName().split(",");
                for (int i = 0; i < attachArray.length; i++) {
                    MimeBodyPart body = new MimeBodyPart();
                    DataHandler handler = new DataHandler(new FileDataSource(realPath+"/"+attachArray[i]));
                    body.setDataHandler(handler);
                    //设置附件名称
                    body.setFileName(handler.getName());
                    //添加到部件集对象中
                    multipart.addBodyPart(body);
                }
            }

            //将部件集对象 赋值给 message 邮件对象
            message.setContent(multipart);
            //显示发送时间
            message.setSentDate(new Date());
            //5发送邮件
            Transport.send(message,message.getAllRecipients());

            log.info("发送邮件对象信息="+mail.toString());
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            log.info("失败....发送邮件对象信息="+mail.toString());
            x = 1;//发送失败
        } catch (MessagingException e) {
            e.printStackTrace();
            x = 1;//发送失败
            log.info("失败....发送邮件对象信息="+mail.toString());
        }
        return x;
    }
}
