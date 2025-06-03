package co.edu.javeriana.llaves.favoritos.infraestructure.persistance.sql_lite.repositories;

import co.edu.javeriana.llaves.favoritos.infraestructure.persistance.sql_lite.entities.Favorites; // Entidad de persistencia
import co.edu.javeriana.llaves.favoritos.core.domain.entities.FavoritesEntity; // Entidad de dominio
import co.edu.javeriana.llaves.favoritos.infraestructure.persistance.sql_lite.mapper.FavoriteMapper; // Tu mapper de persistencia a dominio

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritesDataAccess {

    private final FavoritesRepository favoritesRepository;
    private final FavoriteMapper favoriteMapper;


    @Autowired
    public FavoritesDataAccess(FavoritesRepository favoritesRepository, FavoriteMapper favoriteMapper) {
        this.favoritesRepository = favoritesRepository;
        this.favoriteMapper = favoriteMapper;
    }

    public void saveFavorite(Favorites favorite) {
        favoritesRepository.save(favorite);
    }

    public Optional<Favorites> findById(String id) {
        return favoritesRepository.findById(id);
    }

    public List<Favorites> getAllFavorites() {
        return favoritesRepository.findAll();
    }

    public void deleteFavorite(Favorites favorite) {
        favoritesRepository.delete(favorite);
    }

    public void deleteFavoriteById(String id) {
        favoritesRepository.deleteById(id);
    }

    // MÉTODO PARA CU003!!!
    // Este método toma los parámetros de dominio y devuelve la entidad de dominio
    public Optional<FavoritesEntity> findFavoriteEntityByKeyTextAndUser(String keyText, String user) {
        // 1. Usa el JpaRepository para encontrar la entidad de persistencia
        Optional<Favorites> persistenceEntityOptional = favoritesRepository.findByKeyTextAndUser(keyText, user);

        // 2. Mapea la entidad de persistencia a la entidad de dominio
        return persistenceEntityOptional.map(favoriteMapper::toDomainEntity); // Asumiendo un método toDomainEntity en tu mapper
    }
}