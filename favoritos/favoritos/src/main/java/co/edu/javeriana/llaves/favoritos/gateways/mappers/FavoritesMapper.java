package co.edu.javeriana.llaves.favoritos.gateways.mappers;

import co.edu.javeriana.llaves.favoritos.core.domain.entities.FavoritesEntity;
import co.edu.javeriana.llaves.favoritos.core.domain.util.IdsGenerator;
import co.edu.javeriana.llaves.favoritos.core.domain.util.UUIDGenerator;
import co.edu.javeriana.llaves.favoritos.gateways.dtos.FavoritesDTO;


public class FavoritesMapper {

    public static FavoritesDTO toDTO(FavoritesEntity favorites) {
        FavoritesDTO dto = new FavoritesDTO(
                favorites.getId(),
                favorites.getUser(),
                favorites.getAlias(),
                favorites.getKeyText(),
                favorites.getStatus().name()
        );
        return dto;
    }

    public static FavoritesEntity toDomainEntity(FavoritesDTO favoritesDTO) {

        IdsGenerator idsGenerator = new UUIDGenerator();




        FavoritesEntity entity = new FavoritesEntity(
                idsGenerator.generateId(),
                favoritesDTO.user(),
                favoritesDTO.alias(),
                favoritesDTO.keyText()
        );

        return entity;
    }
}
