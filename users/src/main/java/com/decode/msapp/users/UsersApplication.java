package com.decode.msapp.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
		log.info("Myapp has started");
		}
}

/* Get Beans from context */
// Get info from the context
// ConfigurableApplicationContext context = SpringApplication.run(UsersApplication.class, args);
// MyTestBeanNew bn = (MyTestBeanNew)context.getBean("mytestbean");
