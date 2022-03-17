package com.example.santanderavaliacaospring.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Item {
    GUN("Arma", 4),AMMO("Munição", 3),WATER("Água", 2),FOOD("Comida", 1);

    private String name;
    private int value;

    public static Item findByMame(String value) {
        Item result = null;
        for (Item item : values()) {
            if (item.getName().equalsIgnoreCase(value)) {
                result = item;
                break;
            }
        }
        return result;
    }

    public static boolean contains(String test) {
        return findByMame(test) != null;
    }
}
