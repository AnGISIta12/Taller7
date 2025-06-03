package co.edu.javeriana.llaves.favoritos.gateways.service;

import co.edu.javeriana.llaves.favoritos.core.use_cases.EditFavoriteKeyAlias;
import co.edu.javeriana.llaves.favoritos.core.use_cases.GetFavorites;
import co.edu.javeriana.llaves.favoritos.gateways.dtos.FavoritesDTO;
import co.edu.javeriana.llaves.favoritos.gateways.mappers.FavoritesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class FavoritesFacade {
    private final static Logger log = Logger.getLogger(FavoritesFacade.class.getName());

    @Autowired
    private GetFavorites getFavoritesUseCase;
    private final EditFavoriteKeyAlias editFavoriteKeyAlias;

    // Inyección de dependencias para los casos de uso y el mapper
    public FavoritesFacade(GetFavorites getFavorites, EditFavoriteKeyAlias editFavoriteKeyAlias) {
        this.getFavoritesUseCase = getFavorites;
        this.editFavoriteKeyAlias = editFavoriteKeyAlias;
    }

    public List<FavoritesDTO> getFavorites(String userName) {
        return getFavoritesUseCase(userName);
    }

    public void addFavorite(FavoritesDTO favorite) {
        log.info("Adding favorite: " + favorite);
        getFavoritesUseCase.registerNewFavorite(FavoritesMapper.toDomainEntity(favorite));
    }

    private List<FavoritesDTO> getFavoritesUseCase(String userName) {
        List<FavoritesDTO> favoritesDTO = new ArrayList<>();
        getFavoritesUseCase.getFavorites(userName).stream().map(FavoritesMapper::toDTO).forEach(favoritesDTO::add);
        return favoritesDTO;
    }
    /**
     * Método para la edición de alias de una llave favorita.
     * Llama al caso de uso EditFavoriteKeyAlias.
     * @param favoriteKeyId ID de la llave favorita.
     * @param newAlias El nuevo alias.
     * @param userId El ID del usuario.
     * @return true si la operación fue exitosa.
     */
    public boolean editAlias(String favoriteKeyId, String newAlias, String userId) {
        return editFavoriteKeyAlias.execute(favoriteKeyId, newAlias, userId);
    }
}
