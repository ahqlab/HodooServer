package net.octacomm.sample.config;

import net.octacomm.logger.LoggerBeanPostProcessor;
import net.octacomm.sample.interceptor.LogInterceptor;
import net.octacomm.sample.interceptor.LoginInterceptor;
import net.octacomm.sample.view.ConnectStatsExcelView;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.tiles2.TilesViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "net.octacomm.sample.controller" })
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean
	public BeanPostProcessor loggerBeanPostProcessor() {
		return new LoggerBeanPostProcessor();
	}

	@Bean
	public AbstractExcelView connectStatsExcelView() {
		return new ConnectStatsExcelView();
	}

	@Bean
	public HandlerInterceptor logInterceptor() {
		return new LogInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String patterns[] = new String[] { "/**" };
		registry.addInterceptor(logInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns(patterns).excludePathPatterns("/login");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
		registry.addResourceHandler("/img/**").addResourceLocations("/resources/images/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1000000);
		return multipartResolver;
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions(new String[] { "classpath:tilesdef-admin.xml" });
		return configurer;
	}

	@Bean
	public TilesViewResolver tilesViewResolver() {
		TilesViewResolver resolver = new TilesViewResolver();
		resolver.setOrder(1);
		return resolver;
	}

	@Bean
	public BeanNameViewResolver beanNameViewResolver() {
		BeanNameViewResolver resolver = new BeanNameViewResolver();
		resolver.setOrder(0);
		return resolver;
	}

	@Bean
	public static JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("hellomyhodoo@gmail.com");
		mailSender.setPassword("hodoo114");
		mailSender.getJavaMailProperties().setProperty("mail.transport.protocol", "smtp");
		mailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
		mailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
		mailSender.getJavaMailProperties().setProperty("mail.debug", "true");
		return mailSender;
	}

}
