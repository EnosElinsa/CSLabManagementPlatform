package top.galaxyrockets.cslabmanagementplatform.config;

import org.springframework.lang.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import top.galaxyrockets.cslabmanagementplatform.interceptor.LoginInterceptor;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    
    @Override
    protected void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login");
 
        super.addInterceptors(registry);
    }

    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

}
