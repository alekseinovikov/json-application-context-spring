package me.alekseinovikov;

import me.alekseinovikov.context.ClasspathJsonApplicationContext;
import org.springframework.context.ApplicationContext;

public class JsonApplicationContextSpringApplication {

    public static void main(String[] args) {
        final ApplicationContext context = new ClasspathJsonApplicationContext("application-context.json");
        MyBean myBean = context.getBean("myBean", MyBean.class);
        myBean.hello();
    }

}
