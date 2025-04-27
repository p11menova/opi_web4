package com.example.web4_2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;
@Configuration
public class ClientLocaleConfig {
    // класс для настройки локализации клиента - чтоб понять, на каком языке отвечать
    // в зависимости от заголовка Accept-Language из http-запроса

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH); // язык по умолчанию
        return resolver;
    }
}
