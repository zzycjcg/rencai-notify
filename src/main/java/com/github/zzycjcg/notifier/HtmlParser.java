package com.github.zzycjcg.notifier;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by zzy on 2017/3/1.
 */
@Service
public class HtmlParser {
    private static final Logger log = LoggerFactory.getLogger(HtmlParser.class);

    public JSONObject parse(String html) {
        try {
            Document doc = Jsoup.parse(html);
            Elements elements = doc.getElementsByClass("tab01");
            String paichusuoTitle = elements.get(0).getElementsByTag("th").get(9).text();
            String key = paichusuoTitle.replace("<br/>", "");
            String paichusuoValue = elements.get(0).getElementsByClass("bgColorA").get(0).child(9).text();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(key.trim(), paichusuoValue.trim());
            return jsonObject;
        } catch (Exception e) {
            log.error("parse error, html={}", html, e);
        }
        return null;
    }
}
