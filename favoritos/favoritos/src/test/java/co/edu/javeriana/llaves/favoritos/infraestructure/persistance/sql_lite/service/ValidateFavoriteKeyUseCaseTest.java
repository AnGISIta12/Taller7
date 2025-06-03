package co.edu.javeriana.llaves.favoritos.infraestructure.persistance.sql_lite.service; // <<-- ¡Este es el paquete declarado para esta prueba!

import co.edu.javeriana.llaves.favoritos.core.domain.entities.FavoritesEntity;
import co.edu.javeriana.llaves.favoritos.core.domain.enums.FavoriteState;
import co.edu.javeriana.llaves.favoritos.core.use_cases.infraestructure.PersistanceAdapter; // <<-- ¡Importación clave! Asegúrate de que sea con "infraestructure"
import co.edu.javeriana.llaves.favoritos.core.use_cases.ValidateFavoriteKeyUseCase; // <<-- Importa el caso de uso desde su ubicación correcta

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class ValidateFavoriteKeyUseCaseTest {

    @Mock // Esta anotación de Mockito creará un mock de PersistanceAdapter
    private PersistanceAdapter<FavoritesEntity> persistanceAdapter;

    private ValidateFavoriteKeyUseCase validateFavoriteKeyUseCase;

    @BeforeEach // Este método se ejecuta antes de cada prueba
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks anotados
        // Pasa el mock de persistanceAdapter al constructor del caso de uso
        validateFavoriteKeyUseCase = new ValidateFavoriteKeyUseCase(persistanceAdapter);
    }

    @Test
    void testExecute_WhenFavoriteKeyExistsAndIsActive_ReturnsTrue() {
        // 1. Preparar el dato de prueba
        FavoritesEntity activeFavorite = new FavoritesEntity("id1", "user1", "alias1", "key123");
        activeFavorite.setStatus(FavoriteState.ACTIVE); // El estado es ACTIVO

        // 2. Configurar el mock: cuando se llame a findByKeyTextAndUser, que devuelva la entidad activa
        when(persistanceAdapter.findByKeyTextAndUser("key123", "user1"))
                .thenReturn(Optional.of(activeFavorite));

        // 3. Ejecutar el caso de uso
        boolean isValid = validateFavoriteKeyUseCase.execute("key123", "user1");

        // 4. Verificar el resultado
        assertTrue(isValid, "La llave favorita activa debería ser válida");
    }

    @Test
    void testExecute_WhenFavoriteKeyExistsAndIsInactive_ReturnsFalse() {
        // 1. Preparar el dato de prueba
        FavoritesEntity inactiveFavorite = new FavoritesEntity("id2", "user2", "alias2", "key456");
        inactiveFavorite.setStatus(FavoriteState.INACTIVE); // El estado es INACTIVO

        // 2. Configurar el mock: que devuelva la entidad inactiva
        when(persistanceAdapter.findByKeyTextAndUser("key456", "user2"))
                .thenReturn(Optional.of(inactiveFavorite));

        // 3. Ejecutar el caso de uso
        boolean isValid = validateFavoriteKeyUseCase.execute("key456", "user2");

        // 4. Verificar el resultado
        assertFalse(isValid, "La llave favorita inactiva debería ser inválida");
    }

    @Test
    void testExecute_WhenFavoriteKeyDoesNotExist_ReturnsFalse() {
        // 1. Configurar el mock: que devuelva Optional.empty() (llave no encontrada)
        when(persistanceAdapter.findByKeyTextAndUser("nonExistentKey", "user3"))
                .thenReturn(Optional.empty());

        // 2. Ejecutar el caso de uso
        boolean isValid = validateFavoriteKeyUseCase.execute("nonExistentKey", "user3");

        // 3. Verificar el resultado
        assertFalse(isValid, "Una llave favorita no existente debería ser inválida");
    }
}