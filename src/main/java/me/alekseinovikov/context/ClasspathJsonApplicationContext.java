package me.alekseinovikov.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ClasspathJsonApplicationContext extends AbstractRefreshableConfigApplicationContext {

    private final Resource configResource;

    public ClasspathJsonApplicationContext(final String jsonFilePath) {
        setConfigLocation(jsonFilePath);

        configResource = new ClassPathResource(jsonFilePath);
        refresh();
        start();
    }

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
        final JsonBeanDefinitionReader jsonBeanDefinitionReader = new JsonBeanDefinitionReader(beanFactory);

        jsonBeanDefinitionReader.setEnvironment(this.getEnvironment());
        jsonBeanDefinitionReader.setResourceLoader(this);

        jsonBeanDefinitionReader.loadBeanDefinitions(configResource);
    }

}
