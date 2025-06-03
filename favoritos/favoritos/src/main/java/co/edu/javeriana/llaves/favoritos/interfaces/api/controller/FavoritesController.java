package co.edu.javeriana.llaves.favoritos.interfaces.api.controller;

import co.edu.javeriana.llaves.favoritos.gateways.dtos.FavoritesDTO;
import co.edu.javeriana.llaves.favoritos.gateways.service.FavoritesFacade;
// Importa los casos de uso desde su nueva (correcta) ubicación
import co.edu.javeriana.llaves.favoritos.core.use_cases.ValidateFavoriteKeyUseCase;
import co.edu.javeriana.llaves.favoritos.core.use_cases.EditFavoriteKeyAlias; // Si lo inyectas
import co.edu.javeriana.llaves.favoritos.core.use_cases.GetFavorites; // Si lo inyectas

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/favorites")
public class FavoritesController {

    private final static Logger log = Logger.getLogger(FavoritesController.class.getName());

    private final FavoritesFacade favoritesFacade;
    private final ValidateFavoriteKeyUseCase validateFavoriteKeyUseCase;
    // Si también inyectas EditFavoriteKeyAlias y GetFavorites aquí:
    // private final EditFavoriteKeyAlias editFavoriteKeyAlias;
    // private final GetFavorites getFavorites;

    @Autowired
    public FavoritesController(FavoritesFacade favoritesFacade,
                               ValidateFavoriteKeyUseCase validateFavoriteKeyUseCase
            /*, EditFavoriteKeyAlias editFavoriteKeyAlias, GetFavorites getFavorites */) {
        this.favoritesFacade = favoritesFacade;
        this.validateFavoriteKeyUseCase = validateFavoriteKeyUseCase;
        // this.editFavoriteKeyAlias = editFavoriteKeyAlias;
        // this.getFavorites = getFavorites;
    }

    // ... tus endpoints existentes ...

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateFavoriteKey(
            @RequestParam String keyText,
            @RequestParam String user) {

        log.log(Level.INFO, "validateFavoriteKey called for keyText: " + keyText + " and user: " + user);

        boolean isValid = validateFavoriteKeyUseCase.execute(keyText, user);
        return ResponseEntity.ok(isValid);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable String id) {
        log.log(Level.INFO, "deleteFavorite called for id: " + id);
        return ResponseEntity.noContent().build(); //MIS CAMBIOS ATT: ANGY
    }
}