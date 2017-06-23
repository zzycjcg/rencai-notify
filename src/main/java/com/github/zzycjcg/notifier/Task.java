package com.github.zzycjcg.notifier;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by zzy on 2017/3/1.
 */
@Component
public class Task implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(Task.class);
    @Autowired
    private EmialSender emialSender;
    @Autowired
    private HtmlParser htmlParser;
    @Autowired
    private HttpSender httpSender;

    private static final ThreadLocal<SimpleDateFormat> local = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public void sendResult() {
        try {
            String html = httpSender.send();
            JSONObject result = htmlParser.parse(html);
            emialSender.sendEmail("户口迁移成功通知", result);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public void sendStatus() {
        JSONObject result = new JSONObject();
        result.put("status", "ok");
        result.put("time", local.get().format(new Date()));
        emialSender.sendEmail("服务状态通知", result);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                sendResult();
            }
        }, 0, 30, TimeUnit.MINUTES);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                sendStatus();
            }
        }, 0, 2, TimeUnit.HOURS);
    }
}
