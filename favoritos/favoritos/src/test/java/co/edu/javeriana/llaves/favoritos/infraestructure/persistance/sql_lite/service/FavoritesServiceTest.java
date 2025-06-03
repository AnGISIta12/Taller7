package co.edu.javeriana.llaves.favoritos.infraestructure.persistance.sql_lite.service;

import co.edu.javeriana.llaves.favoritos.infraestructure.persistance.sql_lite.entities.Favorites;
import co.edu.javeriana.llaves.favoritos.infraestructure.persistance.sql_lite.repositories.FavoritesDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FavoritesServiceTest {

    @Autowired
    private FavoritesDataAccess favoritesService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addFavorite() {
        //arrange
        Favorites favoritesEntity = new Favorites();
        favoritesEntity.setId(UUID.randomUUID().toString());
        favoritesEntity.setUser("otros");
        favoritesEntity.setAlias("princesa");
        favoritesEntity.setKeyText("@dAVI301");
        favoritesEntity.setStatus("active");
        //act
        favoritesService.saveFavorite(favoritesEntity);
        //assert
        assertNotNull(favoritesEntity);
    }

    @Test
    void getAllFavorites() {
        //arrange
        List<Favorites> favoritesEntityList;
        //act
        favoritesEntityList = favoritesService.getAllFavorites();
        //assert
        System.out.println(favoritesEntityList);
        assertNotNull(favoritesEntityList);
    }
}