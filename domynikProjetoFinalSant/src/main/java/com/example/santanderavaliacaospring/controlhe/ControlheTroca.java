package com.example.santanderavaliacaospring.controlhe;

import com.example.santanderavaliacaospring.dados.RequisicaoTroca;
import com.example.santanderavaliacaospring.serviso.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/trade")
public class ControlheTroca {

    @Autowired
    private TraderService traderService;

    @PostMapping
    public ResponseEntity signNewRebel(
            @RequestBody @Valid RequisicaoTroca requisicaoTroca
    ) throws Exception {
        traderService.trade(requisicaoTroca);
        return ResponseEntity.ok().build();
    }
}
