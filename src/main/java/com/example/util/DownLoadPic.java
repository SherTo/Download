package com.example.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xh on 2017/4/8.
 * 下载图片
 */
@Component
public class DownLoadPic {


    //编码
    private static final String ENCODE = "UTF-8";
    //匹配图片img
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    //匹配src
    private static final String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";
    private static final String savePath = "H:\\downloadPhoto\\";

    /**
     * 获取html
     *
     * @param url
     * @return
     */
    public static String getHtml(String url) {
        try {
            URL uri = new URL(url);
            URLConnection connection = uri.openConnection();
            InputStream inputStream = connection.getInputStream();
            byte[] bytes = new byte[1024];
            StringBuffer buffer = new StringBuffer();
            while (inputStream.read(bytes, 0, bytes.length) > 0) {
                buffer.append(new String(bytes, ENCODE));
            }
            inputStream.close();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取img url
     *
     * @param html
     */
    private static List<String> getImgUrl(String html) {
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(html);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    /**
     * 获取src
     *
     * @return
     */

    public static List<String> getImgSrc(List<String> imgurlList) {
        List<String> srcList = new ArrayList<>();
        imgurlList.forEach(img -> {
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(img);
            while (matcher.find()) {
                srcList.add(matcher.group().substring(0, matcher.group().length() - 1));
            }
        });
        return srcList;
    }

    /**
     * 下载图片
     */
    private static void downLoad(List<String> listImgSrc) {
        try {
            for (String url : listImgSrc) {
                System.out.println(url);
                String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
                URL uri = new URL(url);
                InputStream in = uri.openStream();
                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                FileOutputStream fo = new FileOutputStream(file.getPath() + "\\" + imageName);
                byte[] buf = new byte[1024];
                int length = 0;
                while ((length = in.read(buf, 0, buf.length)) != -1) {
                    fo.write(buf, 0, length);
                }
                in.close();
                fo.close();
            }
        } catch (Exception e) {
            System.out.println("下载失败");
        }
        System.out.println("下载完成");
    }
//    public static void downLoad(List<String> srcUrlList) {
//        srcUrlList.forEach(src -> {
//            String imgname = src.substring(src.indexOf("/") + 1, src.length());
//            try {
//                InputStream in = new URL(src).openStream();
//                System.out.println(imgname);
//                File file = new File(savePath);
//                if (!file.exists()) {
//                    file.mkdirs();
//                }
//                FileOutputStream out = new FileOutputStream(file.getPath() + imgname);
//                byte[] bytes = new byte[1024];
//                int length = 0;
//                while ((length = in.read(bytes, 0, bytes.length)) != -1) {
//                    out.write(bytes, 0, length);
//                }
//                in.close();
//                out.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }

    /**
     * 下载
     */
    public void downloadPic() {
//        String url = "http://www.tooopen.com/view/1439719.html";
        String url="http://www.zbjuran.com/dongtai/";
        String html = getHtml(url);
        if (!StringUtils.isEmpty(html)) {
            // 获取图片标签
            List<String> imgUrlList = getImgUrl(html);
            // 获取图片src地址
            List<String> imgSrcList = getImgSrc(imgUrlList);
            // 下载图片
            downLoad(imgSrcList);
        }

    }
}
