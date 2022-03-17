package com.example.santanderavaliacaospring.dados;

import com.example.santanderavaliacaospring.modelos.LocalizacaoRebelde;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
public class RequerirRebelde {

    private String name;

    private int age;
    private String gender;
    private LocalizacaoRebelde location;
    private RequerirInventario inventory;
}
