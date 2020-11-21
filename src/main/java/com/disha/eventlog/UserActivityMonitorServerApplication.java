package com.disha.quickride;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages={"com.disha.*","com.quickride.*"})
@SpringBootApplication
public class UserActivityMonitorServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserActivityMonitorServerApplication.class, args);
	}
}
