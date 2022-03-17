package com.example.santanderavaliacaospring.serviso;

import com.example.santanderavaliacaospring.dados.*;
import com.example.santanderavaliacaospring.SantanderAvaliacaoSpringApplication;
import com.example.santanderavaliacaospring.modelos.Item;
import com.example.santanderavaliacaospring.modelos.ItensPossuidos;
import com.example.santanderavaliacaospring.modelos.Rebelde;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RebeldeService {
    public Rebelde addRebel(RequerirRebelde requestRebel){
        List<ItensPossuidos> newRebelInventory = new ArrayList<>();

        requestRebel.getInventory().getItemList().forEach( item -> {
            Item i = Item.findByMame(item.getItemName());
            newRebelInventory.add(new ItensPossuidos(i, item.getAmount()));
        });

        Rebelde newRebelde = new Rebelde(
                UUID.randomUUID(),
                requestRebel.getName(),
                requestRebel.getAge(),
                requestRebel.getGender(),
                requestRebel.getLocation(),
                newRebelInventory,
                new ArrayList<>()
        );

            SantanderAvaliacaoSpringApplication.resistencia.addRebel(newRebelde);

        return newRebelde;
    }

    public PorcentagenResposta getTraitorPercentage() throws Exception {
        return SantanderAvaliacaoSpringApplication.resistencia.percentageOfTraitors();
    }

    public PorcentagenResposta getNonTraitorPercentage(){
        return SantanderAvaliacaoSpringApplication.resistencia.percentageOfNonTraitors();
    }

    public RespostaPontosPerdidos getLostPoints(){
        return SantanderAvaliacaoSpringApplication.resistencia.getLostPoints();
    }

    public RespostaRelatorioDeItens getItemsReport(){
        return SantanderAvaliacaoSpringApplication.resistencia.itemsReport();
    }

    public List<Rebelde> getAllRebels(){
        return SantanderAvaliacaoSpringApplication.resistencia.listMembers();
    }

    public Rebelde rebelDetails(UUID id) throws Exception {
        return SantanderAvaliacaoSpringApplication.resistencia.rebelDetails(id);
    }

    public Rebelde updateRebel(UUID id, RequerirRebelde requestRebel) throws Exception{
        return SantanderAvaliacaoSpringApplication.resistencia.updateRebelData(id, requestRebel);
    }

    public Rebelde reportRebel(UUID traitorId, RequerirRelatorio requestReport) throws Exception{
        return SantanderAvaliacaoSpringApplication.resistencia.reportRebel(traitorId, requestReport.getWhistleblowerId());
    }
}
