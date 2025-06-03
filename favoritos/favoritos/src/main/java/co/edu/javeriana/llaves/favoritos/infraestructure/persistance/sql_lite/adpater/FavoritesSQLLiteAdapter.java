package co.edu.javeriana.llaves.favoritos.infraestructure.persistance.sql_lite.adpater;

import co.edu.javeriana.llaves.favoritos.core.domain.entities.FavoritesEntity;
import co.edu.javeriana.llaves.favoritos.core.use_cases.infraestructure.PersistanceAdapter; // <<-- ¡IMPORTACIÓN CLAVE AQUÍ!
import co.edu.javeriana.llaves.favoritos.infraestructure.persistance.sql_lite.entities.Favorites;
import co.edu.javeriana.llaves.favoritos.infraestructure.persistance.sql_lite.mapper.FavoriteMapper;
import co.edu.javeriana.llaves.favoritos.infraestructure.persistance.sql_lite.repositories.FavoritesDataAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoritesSQLLiteAdapter implements PersistanceAdapter<FavoritesEntity> { // <<-- Implementa la interfaz

    private final FavoritesDataAccess favoritesDataAccess;
    private final FavoriteMapper favoriteMapper;

    @Autowired
    public FavoritesSQLLiteAdapter(FavoritesDataAccess favoritesDataAccess, FavoriteMapper favoriteMapper) {
        this.favoritesDataAccess = favoritesDataAccess;
        this.favoriteMapper = favoriteMapper;
    }

    @Override
    public void save(FavoritesEntity entity) {
        favoritesDataAccess.saveFavorite(favoriteMapper.toDatabaseEntity(entity));
    }

    @Override
    public Optional<FavoritesEntity> findById(String id) {
        Optional<Favorites> optionalFavorite = favoritesDataAccess.findById(id);
        return optionalFavorite.map(favoriteMapper::toDomainEntity);
    }

    @Override
    public void delete(FavoritesEntity entity) {
        if (entity == null || entity.getId() == null) {
            throw new IllegalArgumentException("La entidad o su ID no pueden ser nulos.");
        }

        Favorites favorite = favoritesDataAccess.findById(entity.getId()).orElseThrow(() ->
                new RuntimeException("Llave favorita no encontrada con ID: " + entity.getId()));
        favoritesDataAccess.deleteFavorite(favorite);
        System.out.println("Entidad eliminada de la base de datos: " + entity.getId());
    }

    @Override
    public void deleteById(String id) {
        Favorites favorite = favoritesDataAccess.findById(id).orElseThrow(() ->
                new RuntimeException("Llave favorita no encontrada con ID: " + id));
        favoritesDataAccess.deleteFavorite(favorite);
        System.out.println("Entidad eliminada de la base de datos: " + id);
    }


    @Override
    public List<FavoritesEntity> findAll() {
        return favoritesDataAccess.getAllFavorites().stream()
                .map(favoriteMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void update(FavoritesEntity entity) {
        favoritesDataAccess.saveFavorite(favoriteMapper.toDatabaseEntity(entity));
    }

    @Override
    public Optional<FavoritesEntity> findByKeyTextAndUser(String keyText, String user) {
        return favoritesDataAccess.findFavoriteEntityByKeyTextAndUser(keyText, user);
    }
}