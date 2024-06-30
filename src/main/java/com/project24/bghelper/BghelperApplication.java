package com.project24.bghelper;

import com.project24.bghelper.model.Companion;
import com.project24.bghelper.service.CompanionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BghelperApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(BghelperApplication.class, args);

		// Testing
		CompanionService companionService = applicationContext.getBean(CompanionService.class);

	}

}
