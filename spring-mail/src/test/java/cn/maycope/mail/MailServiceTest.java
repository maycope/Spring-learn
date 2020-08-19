package cn.maycope.mail;


import org.junit.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.Context;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailServiceTest {

    @Autowired
    private  MailServiceImpl mailService;


    /**
     * 邮件发送简单测试。
     */
    @Test
    public  void testSimpleMail() throws  Exception{

        mailService.sendSimpleMail("1912918309@qq.com","测试邮件发送成功","内容？");
    }

    /**
     * 发送带页面的邮件的测试
     */
    @Test
    public void testHtmlMail()throws  Exception{
        String content ="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail("19129***@qq.com","测试发送Html页面",content);

    }
    /**
     * 测试发送带附件的邮件
     */
    @Test
    public void sendAttacheMentsMail(){
        String  path = "D:\\plmm.jpg";
        mailService.sendAttachementMails("19129***9@qq.com","测试发送plmm","注意查收",path);
    }
    /**
     * 测试在正文中添加图片
     */
    @Test
    public void sendLnlineResourceMail(){
        String rscld = "neo";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscld+ "\' ></body></html>";
        String imgPath = "D:\\plmm.jpg";
        mailService.sendInlineResourceMail("19129***@qq.com","有图片的邮件",content,imgPath,rscld);
    }
    /**
     * 测试模板解析系列
     */
    @Test
    public void sendTemplateMail() {
        int code = (int) (Math.random() * 9 + 1) * 100000;
        mailService.sendTemplateMail("19129***@qq.com", "这是模板邮件", String.valueOf(code));
    }
}
