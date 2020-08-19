package cn.maycope.mail;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class MailServiceImpl  {
    private  final Logger logger = LoggerFactory .getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${mail.form}")
    private  String from;

    /**
     * 测试简单的邮件发送，不携带其他的任何信息。
     *
     * @param to 表示发送的对象。
     * @param subject ： 表示邮件的名称。
     * @param content： 表示邮件的内容。
     */
    public void sendSimpleMail(String to ,String subject,String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        try {
            mailSender.send(mailMessage);
            logger.info("简单邮件已经发送成功");
        } catch (Exception e)
        {
            logger.error(" 简单邮件在发送的时候出现了异常情况");
        }

    }
    /**
     * 发送带Html页面的标签内容。
     * 可以写成具体的Html形式。
     */
    public  void sendHtmlMail(String to ,String subject,String content){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            mailSender.send(message);
            logger.info("发送邮件成功");
        }catch (MessagingException e){
            logger.error("发送Html邮件时候发生了异常！");
        }
    }
    /**
     * 发送带附件的邮件。
     * 邮件有正文部分，同时也有具体的附件。
     */
    public  void sendAttachementMails(String to ,String subject,String content,String filepath){
        MimeMessage message  = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            FileSystemResource file = new FileSystemResource(new File(filepath));
            String fileName = filepath.substring(filepath.lastIndexOf(File.separator));
            helper.addAttachment(fileName,file);
            mailSender.send(message);
            logger.info("带附件的邮件已经发送成功");

        }catch (MessagingException e){
            logger.error("发送邮件的时候出现了错误");

        }
    }
    /**
     * 发送带静态资源的邮件。
     * 可以将具体的静态资源如图片等放在文件中，作为邮件正文的一部分。
     */
    public  void sendInlineResourceMail(String to,String subject,String content,String rscPath,String rscld){
        MimeMessage message  = mailSender.createMimeMessage();
        try {
            MimeMessageHelper  helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscld,res);

            mailSender.send(message);
            logger.info("嵌入式资源文件已经发送完成");
        }catch (MessagingException e){
            logger.error("发送嵌入式资源的邮件时候发送了异常");
        }
    }


    /**
     *  表示发送的是模板系列的，
     *  可以设置具体的模板信息，这里的code 可以任意编写。
     * @param to
     * @param subject
     * @param code
     */

    public void sendTemplateMail(String to, String subject, String code) {
        // 表示对模板的处理。
        Context context = new Context();
        context.setVariable("code", code);
        String emailContent = templateEngine.process("email", context);
        sendHtmlMail(to, subject, emailContent);
    }
}
