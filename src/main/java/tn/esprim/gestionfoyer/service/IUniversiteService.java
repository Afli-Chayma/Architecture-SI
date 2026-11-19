package tn.esprim.gestionfoyer.service;


import tn.esprim.gestionfoyer.entities.Universite;

import java.util.List;

public interface IUniversiteService {
    List<Universite> retrieveAllUniversites();

    Universite retrieveUniversite(Long universiteId);

    Universite addUniversite(Universite u);

    void removeUniversite(Long universiteId);

    Universite modifyUniversite(Universite universite);

    Universite affecterFoyerAUniversite(Long idFoyer,String nomUniversite);

    Universite desaffecterFoyerUniversite(Long idUniversite);
}
