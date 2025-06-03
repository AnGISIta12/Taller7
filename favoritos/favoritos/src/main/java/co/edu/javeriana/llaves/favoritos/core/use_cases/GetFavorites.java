package co.edu.javeriana.llaves.favoritos.core.use_cases; // <<-- ¡Este es el paquete correcto!

import co.edu.javeriana.llaves.favoritos.core.domain.entities.FavoritesEntity;
import co.edu.javeriana.llaves.favoritos.core.domain.util.IdsGenerator; // Si la usas
import co.edu.javeriana.llaves.favoritos.core.domain.util.UUIDGenerator; // Si la usas
import co.edu.javeriana.llaves.favoritos.core.use_cases.infraestructure.PersistanceAdapter; // <<-- ¡IMPORTACIÓN CLAVE AQUÍ!
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetFavorites {

    private final PersistanceAdapter<FavoritesEntity> persistanceAdapter;

    // Constructor para inyección. Ajusta si idsGenerator es un bean de Spring.
    public GetFavorites(PersistanceAdapter<FavoritesEntity> persistanceAdapter) {
        this.persistanceAdapter = persistanceAdapter;
        // this.idsGenerator = new UUIDGenerator(); // Solo si lo necesitas aquí y no es un bean de Spring
    }

    public List<FavoritesEntity> getFavorites(String user) {
        List<FavoritesEntity> allFavorites = persistanceAdapter.findAll();
        return allFavorites.stream()
                .filter(favorite -> favorite.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public void registerNewFavorite(FavoritesEntity favorite) {
        persistanceAdapter.save(favorite);
    }
}