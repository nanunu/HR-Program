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
		
		registry.addViewController("/work_record.do").setViewName("time/work_record");
		// registry.addViewController("/free_work.do").setViewName("time/free_work");
		registry.addViewController("/over_work.do").setViewName("time/over_work");
		registry.addViewController("/day_off.do").setViewName("time/day_off");
		
		registry.addViewController("/pay_stub.do").setViewName("paystub/pay_stub");
		registry.addViewController("/staff_pay_stub.do").setViewName("paystub/staff_pay_stub");
		
		registry.addViewController("/all_staff.do").setViewName("management/all_staff");
		//registry.addViewController("/register_staff.do").setViewName("management/"); input 안만듦
		
		registry.addViewController(".desc_card_ver1.do").setViewName("work/desc_card_ver1");
		//registry.addViewController("/desc_card_ver2.do").setViewName("work/desc_card_ver2");
		
	}
	
	
	
}
