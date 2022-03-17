package com.example.santanderavaliacaospring.modelos;

import com.example.santanderavaliacaospring.dados.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Resistencia {
    private static List<Rebelde> rebeldes = new ArrayList<>();

    public void addRebel(Rebelde rebelde){
        Resistencia.rebeldes.add(rebelde);
    }

    public static List<Rebelde> listMembers() {
        List<Rebelde> result = new ArrayList<>();

        rebeldes.forEach(rebelde -> {
            if (rebelde.getReports().size() <= 2){
                result.add(rebelde);
            }
        });

        return result;
    }

    public static List<Rebelde> listTraitors() {
        List<Rebelde> result = new ArrayList<>();

        rebeldes.forEach(rebelde -> {
            if (rebelde.getReports().size() > 2){
                result.add(rebelde);
            }
        });

        return result;
    }

    public RespostaPontosPerdidos getLostPoints(){
        return new RespostaPontosPerdidos(listTraitors().stream().mapToInt(Rebelde::getTotalValue).sum());
    }

    public static PorcentagenResposta percentageOfTraitors() throws Exception {
        if(listTraitors().size() == 0){
            throw new Exception("Não há traidores");
        }

        return new PorcentagenResposta(
                (listTraitors().size() * 100)/(double) Resistencia.rebeldes.size());
    }

    public static PorcentagenResposta percentageOfNonTraitors() {
        return new PorcentagenResposta((listMembers().size() * 100)/(double) Resistencia.rebeldes.size());
    }

    public RespostaRelatorioDeItens itemsReport(){
        List<Boolean> guns = new ArrayList<>();
        List<Boolean> ammo = new ArrayList<>();
        List<Boolean> water = new ArrayList<>();
        List<Boolean> food = new ArrayList<>();

        listMembers().forEach(member ->{
            member.getInventory().forEach( item -> {
                switch (item.getItem().getValue()){
                    case 4:
                        guns.add(true);
                        break;
                    case 3:
                        ammo.add(true);
                        break;
                    case 2:
                        water.add(true);
                        break;
                    default:
                        food.add(true);
                        break;
                }
            });
        });

        return new RespostaRelatorioDeItens(
                (double)guns.size()*100/listMembers().size(),
                (double)ammo.size()*100/listMembers().size(),
                (double)water.size()*100/listMembers().size(),
                (double)food.size()*100/listMembers().size()
        );
    }

    public static Rebelde findAnyById(UUID id) throws Exception {
        Optional<Rebelde> result = Resistencia.rebeldes.stream().filter(rebelde -> Objects.equals(rebelde.getId(),id)).findAny();

        if (result.isPresent()){
            return result.get();
        } else {
            throw new Exception("Rebel not found");
        }
    }

    public Rebelde rebelDetails(UUID id) throws Exception {
        Optional<Rebelde> resultRebel =
                Resistencia.listMembers().stream().filter(rebelde -> Objects.equals(rebelde.getId(),id)).findAny();
        if(resultRebel.isPresent() && resultRebel.get().getReports().size() <= 2){
            return resultRebel.get();
        } else {
            throw new Exception("Rebel not found");
        }
    }

    public Rebelde updateRebelData (UUID id, RequerirRebelde requestRebel) throws Exception {
        Resistencia.rebeldes.stream().filter(rebelde -> Objects.equals(rebelde.getId(),id))
                .forEach(rebelde -> {
                    rebelde.setName(requestRebel.getName());
                    rebelde.setAge(requestRebel.getAge());
                    rebelde.setGender(requestRebel.getGender());
                    rebelde.setInventory(requestRebel.getInventory().toOwnedItemList());
                    rebelde.setLocation(requestRebel.getLocation());
                });
        return rebelDetails(id);
    }

    public Rebelde updateRebelLocation (UUID id, LocalizacaoRebelde location) throws Exception {
        Resistencia.rebeldes.stream().filter(rebelde -> Objects.equals(rebelde.getId(),id))
                .forEach(rebelde -> {
                    rebelde.setLocation(new LocalizacaoRebelde(
                            location.getLatitude(),
                            location.getLongitude(),
                            location.getLocationName()));
                });
        return rebelDetails(id);
    }

    public void takeFromRebelInventory (UUID id, RequerirInventario inventory) throws Exception {
        Optional<Rebelde> owner = Resistencia.rebeldes.stream().filter(rebelde -> Objects.equals(rebelde.getId(),id)).findAny();

        if (!hasAllItems(owner.get(), inventory)){
            throw new Exception("One of the participants does not have all the Itens");
        }

        List<ItensPossuidos> newInventory = new ArrayList<>();

        inventory.getItemList().forEach(item ->{
            if (Item.contains(item.getItemName())){
                newInventory.add(new ItensPossuidos(Item.findByMame(item.getItemName()), item.getAmount()));
            }
        });

        Resistencia.rebeldes.stream().filter(rebelde -> Objects.equals(rebelde.getId(),id))
                .forEach(rebelde -> {
                    rebelde.getInventory().forEach(rebelItem -> {
                        newInventory.forEach(newItem ->{
                            if (newItem.getItem() == rebelItem.getItem()){
                                newItem.setAmount(
                                        rebelItem.getAmount() - newItem.getAmount()
                                );
                            }
                        });
                    });
                });

        Resistencia.rebeldes.stream().filter(rebelde -> Objects.equals(rebelde.getId(),id))
                .forEach(rebelde -> {
                    rebelde.setInventory(newInventory);
                });


    }

    public boolean hasAllItems(Rebelde rebelde, RequerirInventario inventory){
        AtomicBoolean result = new AtomicBoolean(true);

        inventory.getItemList().forEach( item ->{
            Optional<ItensPossuidos> checkItem = rebelde.getInventory().stream().filter(
                    ownedItem -> ownedItem.getItem() == Item.findByMame(item.getItemName())
            ).findAny();

            if(checkItem.isEmpty()){
                result.set(false);
            }
        });

        return result.get();
    }

    public void addToRebelInventory (UUID id, RequerirInventario inventory) {
        List<ItensPossuidos> newInventory = new ArrayList<>();

        inventory.getItemList().forEach(item ->{
            if (Item.contains(item.getItemName())){
                newInventory.add(new ItensPossuidos(Item.findByMame(item.getItemName()), item.getAmount()));
            }
        });

        Resistencia.rebeldes.stream().filter(rebelde -> Objects.equals(rebelde.getId(),id))
                .forEach(rebelde -> {
                    rebelde.getInventory().forEach(rebelItem -> {
                        newInventory.forEach(newItem ->{
                            if (newItem.getItem().getValue() == rebelItem.getItem().getValue()){
                                newItem.setAmount(
                                        rebelItem.getAmount() + newItem.getAmount()
                                );
                            }
                        });
                    });
                });

        Resistencia.rebeldes.stream().filter(rebelde -> Objects.equals(rebelde.getId(),id))
                .forEach(rebelde -> {
                    rebelde.setInventory(newInventory);
                });


    }

    public void deleteRebel(UUID id) throws Exception{
        Rebelde rebelde = rebelDetails(id);
        Resistencia.rebeldes.remove(rebelde);
    }

    public Rebelde reportRebel(UUID traitorId, UUID whistleblowerId) throws Exception {
        Optional<Rebelde> resultWhistleblower =
                Resistencia.rebeldes.stream().filter(rebelde -> Objects.equals(rebelde.getId(),whistleblowerId)).findAny();
        if(resultWhistleblower.isEmpty()){
            throw new Exception("Whistleblower not found");
        }

        Optional<Rebelde> resultTraitor =
                Resistencia.rebeldes.stream().filter(rebelde -> Objects.equals(rebelde.getId(),traitorId)).findAny();
        if(resultTraitor.isPresent()){
            List<UUID> newList = resultTraitor.get().getReports();
            newList.add(whistleblowerId);
            Resistencia.rebeldes.set(Resistencia.rebeldes.indexOf(findAnyById(traitorId)),
                    new Rebelde(
                            resultTraitor.get().getId(),
                            resultTraitor.get().getName(),
                            resultTraitor.get().getAge(),
                            resultTraitor.get().getGender(),
                            resultTraitor.get().getLocation(),
                            resultTraitor.get().getInventory(),
                            newList
                    )
            );

            resultTraitor.get().setReports(newList);

            return resultTraitor.get();
        } else {
            throw new Exception("Traitor not found");
        }
    }

    public void trade(RequisicaoTroca trade) throws Exception{
        if(trade.getRebel1().getTotalValue() != trade.getRebel2().getTotalValue()){
            throw new Exception("The trade can only be made with equal total values");
        } else {
            Rebelde participant1 = rebelDetails(trade.getRebel1().getId());
            Rebelde participant2 = rebelDetails(trade.getRebel2().getId());

            takeFromRebelInventory(participant1.getId(), trade.getRebel1().getTransactionItems());
            takeFromRebelInventory(participant2.getId(), trade.getRebel2().getTransactionItems());

            addToRebelInventory(participant1.getId(), trade.getRebel2().getTransactionItems());
            addToRebelInventory(participant2.getId(), trade.getRebel1().getTransactionItems());
        }
    }
}
