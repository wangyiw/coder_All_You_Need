package com.yww.coder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"com.yww"})
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.yww.coder.user.mapper")
public class CoderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoderApplication.class, args);
		System.out.println("接口文档路径："+ "http://localhost:8801/api/doc.html");
	}

}
