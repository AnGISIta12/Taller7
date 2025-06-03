package co.edu.javeriana.llaves.favoritos.core.use_cases;

import co.edu.javeriana.llaves.favoritos.core.use_cases.infraestructure.PersistanceAdapter;
import org.springframework.stereotype.Service;

@Service
public class DeleteFavoriteKey { //MIS CAMBIOS ATT: ANGY

    private final PersistanceAdapter persistanceAdapter;

    public DeleteFavoriteKey(PersistanceAdapter persistanceAdapter) {
        this.persistanceAdapter = persistanceAdapter;
    }

    public void execute(String id) {
        persistanceAdapter.deleteById(id);
    }
}

