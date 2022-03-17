package com.example.santanderavaliacaospring.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class LocalizacaoRebelde {
    private String latitude;
    private String longitude;
    private String locationName;
}
