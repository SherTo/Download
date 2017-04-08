package com.example.util;

import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by xh on 2017/4/8.
 */
public class LinkQueue {
    //已访问URL
    private static Set<String> visitedUrl = Collections.synchronizedSet(new HashSet<>());
    //未访问URL
    private static List<String> unvisitedUrl = Collections.synchronizedList(new ArrayList<>());

    //去掉未访问的URL
    public static String delUnvisitedUrl() {
        if (unvisitedUrl.size() > 0) {
            String remove = unvisitedUrl.remove(0);
            visitedUrl.add(remove);
            return remove;
        }
        return null;
    }

    //验证新加入URL，确保只添加一次
    public static void addUnvisitedUrl(String url) {
        if (!StringUtils.isEmpty(url) && !visitedUrl.contains(url) && unvisitedUrl.contains(url)) {
            unvisitedUrl.add(url);
        }
    }
    // 判断未访问的URL队列中是否为空
    public static boolean unVisitedUrlsEmpty() {
        return unvisitedUrl.isEmpty();
    }
}
