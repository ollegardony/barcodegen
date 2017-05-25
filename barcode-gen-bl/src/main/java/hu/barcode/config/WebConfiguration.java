package hu.barcode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Ollé Csaba
 * @project Generate Barcode
 * @Created 18/05/2017
 *
 *          Configure base WEBMVC Set resources Set messages (Base language HU
 *          from properties file) Properties: messages_hu.properties
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "hu.barcode")
public class WebConfiguration extends WebMvcConfigurerAdapter {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("ISO-8859-1");
		return messageSource;
	}
}
