package com.dragon.flow.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {

    /**
     * 静态资源配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/flow/**")
                .addResourceLocations("classpath:/templates/page/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/").setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
        registry.addResourceHandler("/idm/**")
                .addResourceLocations("classpath:/idm/").setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
    }

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");

        return corsConfiguration;
    }


    /**
     * 跨域过滤器
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //拦截规则：除了login，其他都拦截判断
//        final String[] excludePathPatterns = new String[]{"/login", "/app/rest/account", "/app/authentication",
//                "/app/logout", "/a/**", "/common/*", "**/processes/**", "/flow/form/**",
//                "/flow/*/rest/**","/form/*/rest/**", "/ui/lib/**"};
//        registry.addInterceptor(flowableInterceptor).addPathPatterns("/**").excludePathPatterns(excludePathPatterns);
//    }

    //返回json数据
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        //调用父类的配置
//        super.configureMessageConverters(converters);
//        //创建fastJson消息转换器
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//
//        //升级最新版本需加=============================================================
//        List<MediaType> supportedMediaTypes = new ArrayList<>();
//        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
//        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
//        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
//        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
//        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_XML);
//        supportedMediaTypes.add(MediaType.IMAGE_GIF);
//        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
//        supportedMediaTypes.add(MediaType.IMAGE_PNG);
//        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
//        supportedMediaTypes.add(MediaType.TEXT_HTML);
//        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
//        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
//        supportedMediaTypes.add(MediaType.TEXT_XML);
//        fastConverter.setSupportedMediaTypes(supportedMediaTypes);
//
//        //创建配置类
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        //修改配置返回内容的过滤
//        //WriteNullListAsEmpty  ：List字段如果为null,输出为[],而非null
//        //WriteNullStringAsEmpty ： 字符类型字段如果为null,输出为"",而非null
//        //DisableCircularReferenceDetect ：消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
//        //WriteNullBooleanAsFalse：Boolean字段如果为null,输出为false,而非null
//        //WriteMapNullValue：是否输出值为null的字段,默认为false
//        fastJsonConfig.setSerializerFeatures(
//                SerializerFeature.DisableCircularReferenceDetect,
//                SerializerFeature.WriteMapNullValue
//        );
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        //将fastjson添加到视图消息转换器列表内
//        converters.add(fastConverter);
//    }

}
