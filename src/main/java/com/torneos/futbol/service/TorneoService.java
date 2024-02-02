package com.torneos.futbol.service;

import com.torneos.futbol.model.entity.Torneo;

import java.util.List;

public interface TorneoService {
    Torneo save(Torneo torneo);
    void delete(Torneo torneo);
    Torneo findById(Long id);
    List<Torneo> findAll();
    void update(Torneo torneo);



}
