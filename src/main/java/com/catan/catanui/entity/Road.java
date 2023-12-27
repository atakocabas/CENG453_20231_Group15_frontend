package com.catan.catanui.entity;

import lombok.Data;

import java.util.List;

@Data
public class Road {
    private Player owner;
    private List<Tile> adjacentTiles;
    private int index;

    public Road(){
        this.owner = null;
        this.adjacentTiles = null;
        this.index = -1;
    }
}
