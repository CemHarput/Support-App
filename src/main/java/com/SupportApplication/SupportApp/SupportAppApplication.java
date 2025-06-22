package com.SupportApplication.SupportApp;

import com.SupportApplication.SupportApp.Category.model.Category;
import com.SupportApplication.SupportApp.Category.repository.CategoryRepository;
import com.SupportApplication.SupportApp.User.model.Role;
import com.SupportApplication.SupportApp.User.model.User;
import com.SupportApplication.SupportApp.User.repository.UserRepository;
import com.SupportApplication.SupportApp.User.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SupportAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupportAppApplication.class, args);
    }


    @Bean
    public CommandLineRunner loadData(CategoryRepository categoryRepository, UserRepository userRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category("Network Issues"));
                categoryRepository.save(new Category("Hardware Problems"));
                categoryRepository.save(new Category("Software Installation"));
                categoryRepository.save(new Category("Email Access"));
                categoryRepository.save(new Category("Account Recovery"));

            }
            String encodedUserPass = new BCryptPasswordEncoder().encode("user123");
            String encodedAdminPass = new BCryptPasswordEncoder().encode("admin123");
            String encodedUserPass1 = new BCryptPasswordEncoder().encode("user456");

            userRepository.save(new User("user1", encodedUserPass1, Role.USER));

            userRepository.save(new User("user", encodedUserPass, Role.USER));
            userRepository.save(new User("admin", encodedAdminPass, Role.ADMIN));
        };
    }

}
