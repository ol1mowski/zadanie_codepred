package com.example.task_codepred.config;

import com.bucket4j.Bucket;
import com.example.task_codepred.interceptor.RateLimitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final Bucket rateLimitBucket;

    public WebConfig(Bucket rateLimitBucket) {
        this.rateLimitBucket = rateLimitBucket;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimitInterceptor(rateLimitBucket))
                .addPathPatterns("/ads/**")
                .excludePathPatterns("/h2-console/**");
    }
}
