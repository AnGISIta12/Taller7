package co.edu.javeriana.llaves.favoritos.core.use_cases;

import co.edu.javeriana.llaves.favoritos.core.domain.entities.FavoritesEntity;
import co.edu.javeriana.llaves.favoritos.core.domain.enums.FavoriteState;
import co.edu.javeriana.llaves.favoritos.core.use_cases.infraestructure.PersistanceAdapter; // <<-- ¡IMPORTACIÓN CLAVE AQUÍ!

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ValidateFavoriteKeyUseCase {

    // Inyecta la interfaz del adaptador, que está en otro subpaquete
    private final PersistanceAdapter<FavoritesEntity> persistanceAdapter;

    public ValidateFavoriteKeyUseCase(PersistanceAdapter<FavoritesEntity> persistanceAdapter) {
        this.persistanceAdapter = persistanceAdapter;
    }

    public boolean execute(String keyText, String user) {

        return persistanceAdapter.findByKeyTextAndUser(keyText, user)
                .map(favoritesEntity -> favoritesEntity.getStatus() == FavoriteState.ACTIVE)
                .orElse(false);
    }
}