package myframework.config;

import org.aeonbits.owner.ConfigFactory;

public abstract class ConfigStrategy {
    public static final TestConfig testConfiguration = ConfigFactory.create(TestConfig.class);
}
