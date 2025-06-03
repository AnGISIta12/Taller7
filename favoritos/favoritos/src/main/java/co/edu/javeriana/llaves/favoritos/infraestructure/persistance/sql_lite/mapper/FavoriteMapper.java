package co.edu.javeriana.llaves.favoritos.infraestructure.persistance.sql_lite.mapper;

import co.edu.javeriana.llaves.favoritos.core.domain.entities.FavoritesEntity;
import co.edu.javeriana.llaves.favoritos.core.domain.enums.FavoriteState;
import co.edu.javeriana.llaves.favoritos.gateways.dtos.FavoritesDTO; // Puede que no necesites esto aquí si solo mapeas entre persistencia y dominio
import co.edu.javeriana.llaves.favoritos.infraestructure.persistance.sql_lite.entities.Favorites;
import org.springframework.stereotype.Component;

@Component
public class FavoriteMapper { // CLASE DE INSTANCIA

    // REMUEVE 'static'
    public Favorites toDatabaseEntity(FavoritesEntity favoritesEntity) {
        Favorites favorites = new Favorites();
        favorites.setId(favoritesEntity.getId());
        favorites.setUser(favoritesEntity.getUser());
        favorites.setAlias(favoritesEntity.getAlias());
        favorites.setKeyText(favoritesEntity.getKeyText());
        favorites.setStatus(favoritesEntity.getStatus().name());
        return favorites;
    }

    // REMUEVE 'static'
    public FavoritesEntity toDomainEntity(Favorites favorites) {
        FavoritesEntity favoritesEntity = new FavoritesEntity(
                favorites.getId(),
                favorites.getUser(),
                favorites.getAlias(),
                favorites.getKeyText()
        );
        // ¡Manejo del estado!
        try {
            // Asumiendo que favorites.getStatus() devuelve "ACTIVE" o "INACTIVE"
            // Si la DB guarda en minúsculas, usa .toUpperCase()
            favoritesEntity.initStatus(FavoriteState.valueOf(favorites.getStatus().toUpperCase()));
        } catch (IllegalArgumentException e) {
            // Manejo de error: loggea o asigna un estado por defecto
            System.err.println("Advertencia: Estado inválido de DB para FavoriteState: " + favorites.getStatus());
            favoritesEntity.initStatus(FavoriteState.INACTIVE); // Valor por defecto
        }
        return favoritesEntity;
    }

    // Este método toDto probablemente no pertenece a este mapper,
    // ya que este mapper es para persistencia. Podría ir en el mapper de DTOs.
    // Si lo necesitas, también quita 'static'.
    public FavoritesDTO toDto(FavoritesEntity favoritesEntity) {
        return new FavoritesDTO(
                favoritesEntity.getId(),
                favoritesEntity.getUser(),
                favoritesEntity.getAlias(),
                favoritesEntity.getKeyText(),
                favoritesEntity.getStatus().name()
        );
    }
}