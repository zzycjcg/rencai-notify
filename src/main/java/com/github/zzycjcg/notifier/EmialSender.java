package com.github.zzycjcg.notifier;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by zzy on 2017/3/1.
 */
@Service
public class EmialSender {

    public void sendEmail(String subject, JSONObject msg) {
        Iterator<Object> values = msg.values().iterator();
        if (!values.hasNext()) {
            return;
        }
        String value = (String) values.next();
        if (value == null || value.isEmpty() || value.trim().isEmpty()) {
            return;
        }

        SimpleEmail email = new SimpleEmail();
        email.setAuthentication("test", "test");
        email.setCharset("utf-8");
        email.setHostName("smtp.qq.com");
        email.setSmtpPort(465);
        email.setSSLOnConnect(true);
        try {
            email.setMsg(msg.toJSONString());
            email.setFrom("zzycjcg@qq.com");
            email.setTo(Arrays.asList(new InternetAddress("zzycjcg@qq.com")));
            email.setSubject(subject);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        }
    }

}
