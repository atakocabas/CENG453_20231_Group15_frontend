package com.catan.catanui.entity;

import lombok.Data;

@Data
public class Road {
    private Player owner;
    private Tile tile1;
    private Tile tile2;
    private int index;
}
