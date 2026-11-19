package tn.esprim.gestionfoyer.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprim.gestionfoyer.entities.Foyer;
import tn.esprim.gestionfoyer.entities.Universite;
import tn.esprim.gestionfoyer.repositories.FoyerRepository;
import tn.esprim.gestionfoyer.repositories.UniversiteRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UniversiteServiceImpl implements IUniversiteService {
    @Autowired
    private UniversiteRepository universiteRepository;
    @Autowired
    private FoyerRepository foyerRepository;

    @Override
    public List<Universite> retrieveAllUniversites() {
        return universiteRepository.findAll();
    }

    @Override
    public Universite retrieveUniversite(Long universiteId) {
        return universiteRepository.findById(universiteId).get();
    }

    @Override
    public Universite addUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public void removeUniversite(Long universiteId) {
        universiteRepository.deleteById(universiteId);
    }

    @Override
    public Universite modifyUniversite(Universite universite) {
        return universiteRepository.save(universite);
    }
    @Transactional
    @Override
    public Universite affecterFoyerAUniversite(Long idFoyer, String nomUniversite) {
        Foyer foyer = foyerRepository.findById(idFoyer)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Foyer introuvable avec l'id : " + idFoyer));
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Université introuvable avec le nom : " + nomUniversite));
        if (foyer.getUniversite()!=null){
            throw new IllegalStateException(
                    "le foyer " + foyer.getNomFoyer()+"est déjà affecté à une Université");
        }
        if (universite.getFoyer() !=null){
            throw new IllegalStateException(
                    "l'universite " +nomUniversite+"a déjà un foyer"
            );
        }
        universite.setFoyer(foyer);
        foyer.setUniversite(universite);
        return universite;

    }

    @Override
    public Universite desaffecterFoyerUniversite(Long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Université introuvable avec l'id : " + idUniversite));
        if (universite.getFoyer()==null){
            throw new IllegalStateException(
                    "Aucun foyer n'est actuellement associé à l'université:"
                            + idUniversite);
        }
        universite.setFoyer(null);
        return universite;    }


}