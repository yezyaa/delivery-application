package com.example.deliveryapplication;

import com.example.deliveryapplication.menuItem.MenuItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public MenuItemRepository menuItemRepository() {
        return new MenuItemRepository();
    }
}
