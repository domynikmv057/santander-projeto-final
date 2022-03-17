package com.example.santanderavaliacaospring.dados;

import com.example.santanderavaliacaospring.modelos.Item;
import com.example.santanderavaliacaospring.modelos.ItensPossuidos;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequerirInventario {
    private List<RequerirItem> itemList;

    public List<ItensPossuidos> toOwnedItemList(){
        List<ItensPossuidos> result = new ArrayList<>();

        itemList.forEach(item ->{
            if(Item.contains(item.getItemName())){
                result.add(new ItensPossuidos(
                        Item.findByMame(item.getItemName()),
                        item.getAmount()));
            }
        });

        return result;
    }
}
