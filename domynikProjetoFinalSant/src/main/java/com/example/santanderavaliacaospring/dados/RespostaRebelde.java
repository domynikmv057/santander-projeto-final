package com.example.santanderavaliacaospring.dados;

import com.example.santanderavaliacaospring.modelos.ItensPossuidos;
import com.example.santanderavaliacaospring.modelos.Rebelde;
import com.example.santanderavaliacaospring.modelos.LocalizacaoRebelde;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class RespostaRebelde {
    private UUID id;
    private String name;
    private int age;
    private String gender;
    private LocalizacaoRebelde location;
    private List<ItensPossuidos> inventory;
    private List<UUID> reports;

    public RespostaRebelde(Rebelde rebelde){
        this.id = rebelde.getId();
        this.name = rebelde.getName();
        this.age = rebelde.getAge();
        this.gender = rebelde.getGender();
        this.location = rebelde.getLocation();
        this.inventory = rebelde.getInventory();
        this.reports = rebelde.getReports();
    }

    public static List<RespostaRebelde> toResponse(List<Rebelde> rebeldes){
        return  rebeldes.stream().map(RespostaRebelde::new).collect(Collectors.toList());
    }
}
