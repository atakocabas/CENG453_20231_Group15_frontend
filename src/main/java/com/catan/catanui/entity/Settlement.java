package com.catan.catanui.entity;

import com.catan.catanui.enums.SettlementType;
import lombok.Data;

import java.util.List;

@Data
public class Settlement {
    private Player owner;
    private List<Tile> adjacentTiles;
    private SettlementType type;

    public Settlement(Player owner, List<Tile> adjacentTiles) {
        this.owner = owner;
        this.adjacentTiles = adjacentTiles;
        this.type = SettlementType.SETTLEMENT;
    }

}
