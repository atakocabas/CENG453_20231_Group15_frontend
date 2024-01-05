package com.catan.catanui.entity;

import com.catan.catanui.enums.SettlementType;
import javafx.scene.paint.Color;
import lombok.Data;


@Data
public class Settlement {
    private Player owner;
    private SettlementType type;
    private Color color = Color.TRANSPARENT;
    private int index;

    public Settlement(Player owner, int index) {
        this.owner = owner;
        this.type = SettlementType.SETTLEMENT;
        this.index = index;
    }

}
