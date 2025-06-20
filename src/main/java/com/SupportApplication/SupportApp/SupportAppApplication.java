package com.SupportApplication.SupportApp;

import com.SupportApplication.SupportApp.Category.model.Category;
import com.SupportApplication.SupportApp.Category.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SupportAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupportAppApplication.class, args);
	}


	@Bean
	public CommandLineRunner loadData(CategoryRepository categoryRepository) {
		return args -> {
			if (categoryRepository.count() == 0) {
				categoryRepository.save(new Category("Network Issues"));
				categoryRepository.save(new Category("Hardware Problems"));
				categoryRepository.save(new Category("Software Installation"));
				categoryRepository.save(new Category("Email Access"));
				categoryRepository.save(new Category("Account Recovery"));

				System.out.println("Sample categories inserted.");
			} else {
				System.out.println("Categories already exist.");
			}
		};
	}
}
