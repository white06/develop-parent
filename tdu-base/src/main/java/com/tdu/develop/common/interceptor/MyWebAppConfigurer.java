package com.tdu.develop.common.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 拦截器
 *
 * @author fuce
 * @ClassName: MyWebAppConfigurer
 * @date 2018年6月3日
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurationSupport {

    /**
     * 解决跨域问题
     **/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//		super.addCorsMappings(registry);
    }

    /**
     * 添加拦截器
     **/
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor());
        super.addInterceptors(registry);
    }

    /**
     * 这里配置视图解析器
     **/
    @Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
    }

    /**
     * 配置内容裁决的一些选项
     **/
    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        super.configureContentNegotiation(configurer);
    }

    /**
     * 视图跳转控制器
     **/
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {

        super.addViewControllers(registry);
    }


    /**
     * 静态资源处理
     **/
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations("file:d:/webstorm/")
                .addResourceLocations("file:/www/wwwroot/")
                .addResourceLocations("classpath:/www/wwwroot/");
        super.addResourceHandlers(registry);
    }

    /**
     * 默认静态资源处理器
     **/

    protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//		super.configureDefaultServletHandling(configurer);
//		configurer.enable("stati");
        configurer.enable();
    }


}
