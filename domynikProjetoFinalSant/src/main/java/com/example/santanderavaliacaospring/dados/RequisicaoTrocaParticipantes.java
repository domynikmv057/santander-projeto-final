package com.example.santanderavaliacaospring.dados;

import com.example.santanderavaliacaospring.modelos.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RequisicaoTrocaParticipantes {
    private UUID id;
    private RequerirInventario transactionItems;

    public int getTotalValue(){
        return transactionItems.getItemList().stream().mapToInt(item -> Item.findByMame(item.getItemName()).getValue() * item.getAmount()).sum();
    }
}
