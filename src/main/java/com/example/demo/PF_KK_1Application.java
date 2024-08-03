package com.example.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PF_KK_1Application {

	public static void main(String[] args) {
//		SpringApplication.run(PF_KK_1Application.class, args); バナーあり起動
		SpringApplication app = new SpringApplication(PF_KK_1Application.class);
        app.setBannerMode(Banner.Mode.OFF); // バナーをオフに設定
        app.run(args);
		
	}

}
