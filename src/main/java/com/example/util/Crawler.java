package com.example.util;


import org.springframework.stereotype.Component;

import java.util.Set;
@Component
public class Crawler {
    static final String url = "http://www.tooopen.com/view/1439719.html";

    /**
     * 抓取过程
     *
     * @param
     * @return
     */
    public void crawling() { // 定义过滤器
//        LinkFilter filter = url1 -> {
//            //这里过滤规则随需要爬的网站的规则进行改变，推荐使用正则实现，本人是爬豆瓣网站
//            if (url1.indexOf("douban.com/group/topic") != -1 || url1.indexOf("douban.com/group/haixiuzu/discussion?start") != -1)
//                return true;
//            else
//                return false;
//        };
        // 初始化 URL 队列  
        LinkQueue.addUnvisitedUrl(url);
        // 循环条件，待抓取的链接不空
        while (!LinkQueue.unVisitedUrlsEmpty()) {
            // 队头URL出队列  
            String visitUrl = (String) LinkQueue.delUnvisitedUrl();
            if (visitUrl == null)
                continue;
//            DownLoadPic.downloadPic(visitUrl);

            // 提取出下载网页中的 URL  
            Set<String> links = ParserHttpUrl.getLinks(visitUrl/*, filter*/);
            // 新的未访问的 URL 入队  
            for (String link : links) {
                LinkQueue.addUnvisitedUrl(link);
            }
        }
    }

}