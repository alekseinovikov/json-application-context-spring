package me.alekseinovikov.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class ClasspathJsonApplicationContext extends AbstractRefreshableConfigApplicationContext {

    private final Resource configResource;

    public ClasspathJsonApplicationContext(final String jsonFilePath) {
        configResource = new ClassPathResource(jsonFilePath);
        refresh();
        start();
    }

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
        final BeanDefinitionReader jsonBeanDefinitionReader = new JsonBeanDefinitionReader(beanFactory);
        jsonBeanDefinitionReader.loadBeanDefinitions(configResource);
    }

}
