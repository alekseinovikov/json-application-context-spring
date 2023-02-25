package me.alekseinovikov;

import me.alekseinovikov.context.ClasspathJsonApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JsonApplicationContextSpringApplication {

    public static void main(String[] args) {
        final ClasspathJsonApplicationContext context = new ClasspathJsonApplicationContext("application-context.json");
        MyBean myBean = context.getBean("myBean", MyBean.class);
        myBean.hello();
    }

}
