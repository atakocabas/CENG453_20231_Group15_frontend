package com.catan.catanui;

import com.catan.catanui.ClientApplication.StageReadyEvent;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;

public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.getStage();
    }
}
