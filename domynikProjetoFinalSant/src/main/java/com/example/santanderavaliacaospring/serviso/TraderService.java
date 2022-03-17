package com.example.santanderavaliacaospring.serviso;

import com.example.santanderavaliacaospring.dados.RequisicaoTroca;
import com.example.santanderavaliacaospring.SantanderAvaliacaoSpringApplication;
import org.springframework.stereotype.Service;

@Service
public class TraderService {
    public void trade(RequisicaoTroca requisicaoTroca) throws Exception {
        SantanderAvaliacaoSpringApplication.resistencia.trade(requisicaoTroca);
    }
}
