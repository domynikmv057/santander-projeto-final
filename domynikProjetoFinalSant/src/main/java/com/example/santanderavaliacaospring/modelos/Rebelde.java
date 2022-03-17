package com.example.santanderavaliacaospring.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Getter @Setter
@AllArgsConstructor
public class Rebelde {
    private UUID id;
    private String name;
    private int age;
    private String gender;
    private LocalizacaoRebelde location;
    private List<ItensPossuidos> inventory;
    private List<UUID> reports;

    public int getTotalValue(){
        return inventory.stream().mapToInt(item -> item.getItem().getValue() * item.getAmount()).sum();
    }

}