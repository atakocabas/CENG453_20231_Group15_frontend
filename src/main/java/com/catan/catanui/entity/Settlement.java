package com.catan.catanui.entity;

import com.catan.catanui.enums.SettlementType;
import javafx.scene.paint.Color;
import lombok.Data;


@Data
public class Settlement {
    private Player owner;
    private SettlementType type;
    private Color color = Color.TRANSPARENT;
    private boolean isCity;

    public Settlement(Player owner) {
        this.owner = owner;
        this.type = SettlementType.SETTLEMENT;
        this.isCity = false;
    }

}
