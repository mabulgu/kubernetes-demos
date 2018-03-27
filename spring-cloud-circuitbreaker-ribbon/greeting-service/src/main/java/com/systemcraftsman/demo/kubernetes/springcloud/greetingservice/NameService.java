package com.systemcraftsman.demo.kubernetes.springcloud.greetingservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service invoking name-service via REST and guarded by Hystrix.
 *
 */
@Service
public class NameService
{

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackName", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000") })
    public String getName(int delay)
    {
        return this.restTemplate.getForObject(String.format("http://name-service/name?delay=%d", delay), String.class);
    }

    private String getFallbackName(int delay)
    {
        return "Fallback";
    }

}
