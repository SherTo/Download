package com.example.util;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xh on 2017/4/8.
 * 过滤http的url，获取可以符合规则的url
 */
public class ParserHttpUrl {
    // 获取一个网站上的链接,filter 用来过滤链接
    public static Set<String> getLinks(String url/*, LinkFilter filter*/) {
        Set<String> links = new HashSet<>();
        try {
            Parser parser = new Parser(url);
            // 过滤 <frame >标签的 filter，用来提取 frame 标签里的 src 属性所表示的链接
            NodeFilter nodeFilter = new NodeFilter() {
                @Override
                public boolean accept(Node node) {
                    if (node.getText().startsWith("frame src=")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            };
            // OrFilter 来设置过滤 <a> 标签，和 <frame> 标签
            OrFilter orFilter = new OrFilter(new NodeClassFilter(LinkTag.class), nodeFilter);
            // 得到所有经过过滤的标签
            NodeList nodeList = parser.extractAllNodesThatMatch(orFilter);
            for (int i = 0; i < nodeList.size(); i++) {
                Node node = nodeList.elementAt(i);
                if (node instanceof LinkTag) {
                    LinkTag linkTag = (LinkTag) node;
                    String link = linkTag.getLink();
//                    if (filter.accept(link)) {
                    links.add(link);
//                    }
                } else {
                    // 提取 frame 里 src 属性的链接如 <frame src="test.html"/>
                    String text = node.getText();
                    int start = text.indexOf("src=");
                    text = text.substring(start);
                    int end = text.indexOf("");
                    if (end == -1) {
                        end = text.indexOf(">");
                        String frameurl = text.substring(5, end - 1);
//                        if (filter.accept(frameurl)) {
                        links.add(frameurl);
//                        }
                    }
                }
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return links;
    }
}
