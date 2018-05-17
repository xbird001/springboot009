package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class App {
	
	public static void main(String[] args) {
		
		
		/*ConfigurableApplicationContext  app = SpringApplication.run(App.class, args);*/
		
		SpringApplication.run(App.class, args);
		SpringApplication app = new SpringApplication(App.class);
		
		
		ConfigurableApplicationContext context = app.run(args);
		context.close();
	}

}
