package me.alekseinovikov;

import me.alekseinovikov.context.ClasspathJsonApplicationContext;

public class JsonApplicationContextSpringApplication {

    public static void main(String[] args) {
        final ClasspathJsonApplicationContext context = new ClasspathJsonApplicationContext("application-context.json");
        MyBean myBean = context.getBean("myBean", MyBean.class);
        myBean.hello();
    }

}
