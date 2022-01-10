package myframework.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PageAppContextUtil {
    private static ApplicationContext appContext = null;

    private PageAppContextUtil() {
        super();
    }

    public static ApplicationContext getContext() {

        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();

        if (appContext == null) {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
            context.addBeanFactoryPostProcessor(configurer);
            context.setConfigLocation("page-spring-context.xml");
            context.refresh();
            appContext = context;
        }

        return appContext;
    }
}
