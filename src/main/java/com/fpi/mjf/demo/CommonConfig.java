package com.fpi.mjf.demo;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * 
 *
 */
public class CommonConfig {
    private static ConfigurableApplicationContext  appContext;
    
    public static String getProperty(String key) {
        return appContext.getEnvironment().getProperty(key);
    }
    
    public static void setContext(ConfigurableApplicationContext context) {
        appContext = context;
    }
}
