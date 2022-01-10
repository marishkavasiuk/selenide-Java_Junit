package myframework.config;

import org.aeonbits.owner.Config;

@Config.Sources({"file:src/main/resources/myframework.properties"})
public interface TestConfig extends Config {

        @Key("run.on.env")
        String getEnv();

        @Key("base.url")
        String getBaseUrl();

        @Key("trello.admin")
        String getTrelloAdminName();

        @Key("trello.admin.pass")
        String getTrelloAdminPass();

}
