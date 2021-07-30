package com.halo.health.config;

import com.halo.health.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：     User过滤器的配置
 */
@Configuration
public class UserFilterConfig {

    @Bean
    public UserFilter userFilter() {
        return new UserFilter();
    }

    @Bean(name = "userFilterConf")
    public FilterRegistrationBean adminFilterConfig() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(userFilter());
        filterRegistrationBean.addUrlPatterns("/health/*");
        filterRegistrationBean.addUrlPatterns("/user/update");
        filterRegistrationBean.addUrlPatterns("/user/getInfo");
        filterRegistrationBean.addUrlPatterns("/user/changePassword");
        filterRegistrationBean.setName("userFilterConf");
        return filterRegistrationBean;
    }
}
