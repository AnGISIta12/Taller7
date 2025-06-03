package co.edu.javeriana.llaves.favoritos.core.use_cases; // <<-- ¡Este es el paquete correcto!

import co.edu.javeriana.llaves.favoritos.core.domain.entities.FavoritesEntity;
import co.edu.javeriana.llaves.favoritos.core.use_cases.infraestructure.PersistanceAdapter; // <<-- ¡IMPORTACIÓN CLAVE AQUÍ!
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
    public class EditFavoriteKeyAlias {

    private final PersistanceAdapter<FavoritesEntity> persistenceAdapter;

    public EditFavoriteKeyAlias(PersistanceAdapter<FavoritesEntity> persistenceAdapter) {
        this.persistenceAdapter = persistenceAdapter;
    }

    public boolean execute(String favoriteKeyId, String newAlias, String userId) {
        Optional<FavoritesEntity> optionalFavoriteKey = persistenceAdapter.findById(favoriteKeyId);

        if (optionalFavoriteKey.isEmpty()) {
            System.out.println("Error: Llave favorita con ID " + favoriteKeyId + " no encontrada.");
            return false;
        }
        FavoritesEntity existingFavoriteKey = optionalFavoriteKey.get();

        if (!existingFavoriteKey.getUser().equals(userId)) {
            System.out.println("Error: La llave favorita no pertenece al usuario " + userId + " o no tiene permisos.");
            return false;
        }

        existingFavoriteKey.setAlias(newAlias);
        persistenceAdapter.update(existingFavoriteKey);

        System.out.println("Alias de llave favorita " + favoriteKeyId + " actualizado a: " + newAlias + " por usuario " + userId);
        return true;
    }
}