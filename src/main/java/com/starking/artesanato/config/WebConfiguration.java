package com.starking.artesanato.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;



@EnableWebMvc
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	@Override
	public void addCorsMappings( CorsRegistry registry ) {
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
	}
}
