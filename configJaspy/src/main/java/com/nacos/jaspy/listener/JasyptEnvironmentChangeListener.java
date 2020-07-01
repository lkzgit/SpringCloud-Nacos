package com.nacos.jaspy.listener;

import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.environment.EnvironmentManager;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 针对 Jasypt 并未对 Nacos 自动配置刷新获取到的最新配置进行解密
 * ，我们可以通过自定义 JasyptEnvironmentChangeListener 监听器
 */

@Component
public class JasyptEnvironmentChangeListener implements ApplicationListener<EnvironmentChangeEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // Environment 管理器，可以实现配置项的获取和修改
    @Autowired
    private EnvironmentManager environmentManager;

    // Jasypt 加密器，可以对配置项进行加密和加密
    @Autowired
    private StringEncryptor encryptor;

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        for (String key : event.getKeys()) {
            // 获得 value
            Object valueObj = environmentManager.getProperty(key);
            if (!(valueObj instanceof String)) {
                continue;
            }
            String value = (String) valueObj;
            // 判断 value 是否为加密。如果是，则进行解密
            if (value.startsWith("ENC(") && value.endsWith(")")) {
                value = encryptor.decrypt(StringUtils.substringBetween(value, "ENC(", ")"));
                logger.info("[onApplicationEvent][key({}) 解密后为 {}]", key, value);
                // 设置到 Environment 中
                environmentManager.setProperty(key, value);
            } else {
                System.out.println("没有加密:" + value);
            }
        }
    }

}
