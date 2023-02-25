package me.alekseinovikov.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.SneakyThrows;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class JsonBeanDefinitionReader extends AbstractBeanDefinitionReader {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CollectionType jsonBeanDescriptionClass;

    protected JsonBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        jsonBeanDescriptionClass = objectMapper.getTypeFactory().constructCollectionType(List.class, JsonBeanDescription.class);
    }

    @SneakyThrows
    @Override
    public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
        final String contentAsString = resource.getContentAsString(StandardCharsets.UTF_8);
        final List<JsonBeanDescription> jsonDescriptions = objectMapper.readValue(contentAsString, jsonBeanDescriptionClass);

        for (JsonBeanDescription jsonDescription : jsonDescriptions) {
            BeanDefinitionHolder beanDefinitionHolder = createBeanDefinition(jsonDescription);
            getRegistry().registerBeanDefinition(jsonDescription.getName(), beanDefinitionHolder.getBeanDefinition());
        }

        return jsonDescriptions.size();
    }

    private BeanDefinitionHolder createBeanDefinition(JsonBeanDescription description) throws ClassNotFoundException {
        final AbstractBeanDefinition beanDefinition
                = BeanDefinitionReaderUtils.createBeanDefinition(null, description.getClassName(), getBeanClassLoader());

        Optional.ofNullable(description.getInitMethod())
                .ifPresent(beanDefinition::setInitMethodName);

        return new BeanDefinitionHolder(beanDefinition, description.getName());
    }

}
