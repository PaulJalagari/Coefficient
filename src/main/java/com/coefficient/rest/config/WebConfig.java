package com.coefficient.rest.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



@ComponentScan({"com.coefficient.rest"})
@Configuration
@EnableWebMvc
@Import({DataAccessConfig.class
})

@PropertySources({ 
	@PropertySource(value = "file:///${app.conf.dir}/log_configuration.properties"),
	@PropertySource(value = "file:///${app.conf.dir}/failed_login.properties"),
	@PropertySource(value = "file:///${app.conf.dir}/basic_configuration.properties")
})
@ImportResource("classpath:security-context.xml")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configuration) {

        configuration.enable();
    }

	@Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() throws IOException {

        PropertySourcesPlaceholderConfigurer config = new PropertySourcesPlaceholderConfigurer();
        config.setLocations(new PathMatchingResourcePatternResolver().getResources("file:///" + System.getProperty("app.conf.dir") + "/log_configuration.properties"));   
        
        return config;
    }
	
	@Bean(name = "multipartResolver")
	public MultipartResolver getMultipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(20971520);
		commonsMultipartResolver.setMaxInMemorySize(1048576);
		return commonsMultipartResolver;

	}

}