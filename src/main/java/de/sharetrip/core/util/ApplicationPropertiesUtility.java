//package de.sharetrip.core.util;
//
//import lombok.AllArgsConstructor;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.env.Environment;
//
//import java.util.Objects;
//
//@Configuration
//@AllArgsConstructor
//@Order
//public class ApplicationPropertiesUtility implements ApplicationListener<ApplicationReadyEvent> {
//
//    private final Environment environment;
//
//    private static Environment staticEnvironment;
//
//    @Override
//    public void onApplicationEvent(final ApplicationReadyEvent event) {
//        ApplicationPropertiesUtility.setStaticEnvironment(environment);
//    }
//
//    public static String getProperty(final String key) {
//        return staticEnvironment.getProperty(key);
//    }
//
//    private static void setStaticEnvironment(final Environment environment) {
//        ApplicationPropertiesUtility.staticEnvironment = environment;
//    }
//}
