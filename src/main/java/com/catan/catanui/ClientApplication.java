package com.catan.catanui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientApplication extends Application {
    private static Logger LOGGER = LoggerFactory.getLogger(ClientApplication.class);
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        applicationContext = new SpringApplicationBuilder(CatanUiApplication.class).run(args);
        LOGGER.info("Application initialized.");
    }

    @Override
    public void start(Stage stage) throws Exception {
        applicationContext.publishEvent(new StageReadyEvent(stage));
        LOGGER.info("Application started.");
    }

    @Override
    public void stop() throws Exception {
        applicationContext.close();
        Platform.exit();
        LOGGER.info("Application stopped.");
    }

    public static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return (Stage) getSource();
        }
    }
}
