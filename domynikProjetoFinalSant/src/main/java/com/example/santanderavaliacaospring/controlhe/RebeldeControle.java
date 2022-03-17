package com.example.santanderavaliacaospring.controlhe;

import com.example.santanderavaliacaospring.dados.*;
import com.example.santanderavaliacaospring.SantanderAvaliacaoSpringApplication;
import com.example.santanderavaliacaospring.modelos.Rebelde;
import com.example.santanderavaliacaospring.serviso.RebeldeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rebel")
public class RebeldeControle {

    @Autowired
    private RebeldeService rebeldeService;

    @GetMapping
    public List<RespostaRebelde> rebels(){
        return RespostaRebelde.toResponse(rebeldeService.getAllRebels());
    }

    @GetMapping("/reports/traitors")
    public PorcentagenResposta getTraitorPercentage() throws Exception {
        return rebeldeService.getTraitorPercentage();
    }

    @GetMapping("/reports/members")
    public PorcentagenResposta getNonTraitorPercentage() {
        return rebeldeService.getNonTraitorPercentage();
    }

    @GetMapping("/reports/lost-points")
    public RespostaPontosPerdidos getLostPoints() throws Exception {
        return rebeldeService.getLostPoints();
    }

    @GetMapping("/reports/items-report")
    public RespostaRelatorioDeItens getItemsReport() throws Exception {
        return rebeldeService.getItemsReport();
    }

    @PostMapping
    public ResponseEntity<RespostaRebelde> signNewRebel(
            @RequestBody @Valid RequerirRebelde requestRebel,
            UriComponentsBuilder uriComponentsBuilder
    ){
        Rebelde rebelde = rebeldeService.addRebel(requestRebel);
        URI uri = uriComponentsBuilder.path("/rebel/id").buildAndExpand(rebelde.getId()).toUri();
        return ResponseEntity.created(uri).body(new RespostaRebelde(rebelde));
    }

    @PostMapping("/{id}/report")
    public ResponseEntity<RespostaRebelde> reportRebel(
            @PathVariable UUID id,
            @RequestBody RequerirRelatorio requestReport
    ) throws Exception {
        Rebelde rebelde = rebeldeService.reportRebel(id, requestReport);
        return ResponseEntity.ok(new RespostaRebelde(rebelde));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaRebelde> rebelDetails(@PathVariable UUID id)
            throws Exception{
        return ResponseEntity.ok(new RespostaRebelde(rebeldeService.rebelDetails(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaRebelde> updateRebel(
            @PathVariable UUID id,
            @RequestBody RequerirRebelde requestRebel
    ) throws Exception {
        Rebelde rebelde = SantanderAvaliacaoSpringApplication.resistencia.updateRebelData(id, requestRebel);
        return ResponseEntity.ok(new RespostaRebelde(rebelde));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRebel(@PathVariable UUID id) throws Exception {
        SantanderAvaliacaoSpringApplication.resistencia.deleteRebel(id);
        return ResponseEntity.ok().build();
    }
}

