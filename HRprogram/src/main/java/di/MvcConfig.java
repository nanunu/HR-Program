package di;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer{

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/", ".jsp");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {		
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// prefix 요청이 오면 suffix 바로이동.
		registry.addViewController("/go_record.do").setViewName("time/work_record");
		registry.addViewController("/error.do").setViewName("Exception/Exception_print");
		registry.addViewController("/form_ver1.do").setViewName("work/confirm_form_ver1");//휴가/초과근무시 입력폼으로 이동
		registry.addViewController("/form_ver2.do").setViewName("work/confirm_form_ver2");//탄력근무시 입력폼으로 이동
	}
	
	
	
}
