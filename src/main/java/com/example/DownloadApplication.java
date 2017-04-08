package com.example;

import com.example.util.Crawler;
import com.example.util.DownLoadPic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DownloadApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(DownloadApplication.class, args);
		DownLoadPic downLoadPic = run.getBean(DownLoadPic.class);
		downLoadPic.downloadPic();
	}
}
