package tn.esprim.gestionfoyer.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprim.gestionfoyer.entities.Bloc;
import tn.esprim.gestionfoyer.entities.Chambre;
import tn.esprim.gestionfoyer.repositories.BlocRepository;
import tn.esprim.gestionfoyer.repositories.ChambreRepository;


import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class BlocServiceImpl implements IBlocService {
    private final ChambreRepository chambreRepository;
    private BlocRepository blocRepository;

    @Override
    public List<Bloc> retrieveAllBlocs() {
        return blocRepository.findAll();
    }

    @Override
    public Bloc retrieveBloc(Long blocId) {
        return blocRepository.findById(blocId).get();
    }

    @Override
    public Bloc addBloc(Bloc b) {
        return blocRepository.save(b);
    }

    @Override
    public void removeBloc(Long blocId) {
        blocRepository.deleteById(blocId);
    }

    @Override
    public Bloc modifyBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    @Transactional
    @Override
    public Bloc affecterChambresABloc(List<Long> numChambres, Long idBloc) {
        Bloc bloc = blocRepository.findById(idBloc).orElseThrow(() -> new EntityNotFoundException(
                "Bloc introuvable: " +idBloc));

        List<Chambre> chambres = chambreRepository.findAllByNumeroChambreIn(numChambres);

        if (chambres.size()!=new HashSet<>(numChambres).size()){
            throw new IllegalStateException(
                    "Une ou plusieurs chambres sont introuvables");

        }
        for (Chambre chambre : chambres){
            if (chambre.getBloc()!=null && !chambre.getBloc().getIdBloc().equals(idBloc)){
                throw new IllegalStateException(
                        "la chambre "+chambre.getNumeroChambre()+"est déjà affecté à un autre bloc.");
            }
        }
        return bloc;
    }


}