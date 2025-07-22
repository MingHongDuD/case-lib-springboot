package com.damon.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Map;

/**
 * 邮件工具类
 *
 * @author damon du/minghongdud
 */
public class MailUtil {

    private final JavaMailSender mailSender;

    public MailUtil(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 发送邮件
     */
    public void sendSimpleMailToOne(String[] to, String subject, String content) {
        // 创建一个邮件消息
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 设置收件人
        mailMessage.setTo(to);
        // 设置邮件标题
        mailMessage.setSubject(subject);
        // 设置邮件内容
        mailMessage.setText(content);

        // 发送
        mailSender.send(mailMessage);
    }

    /**
     * 发送携带附件的邮件
     */
    public void sendOneAttachmentMailToOne(String[] to, String subject, String content, Map<String, InputStreamSource> attachments) throws MessagingException {
        // 创建一个邮件消息
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // 创建 MimeMessageHelper，指定 boolean multipart 参数为 true
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        // 设置收件人
        mimeMessageHelper.setTo(to);
        // 设置邮件标题
        mimeMessageHelper.setSubject(subject);
        // 设置邮件内容，第二个参数表示是否是HTML正文
        mimeMessageHelper.setText(content, true);

        // 添加附件，指定附件名称，文件的 Input stream 流以及 Content-Type
        for (Map.Entry<String, InputStreamSource> attachementEntry : attachments.entrySet()) {
            mimeMessageHelper.addAttachment(attachementEntry.getKey(), attachementEntry.getValue());
        }

        // 发送
        mailSender.send(mimeMessage);
    }
}
