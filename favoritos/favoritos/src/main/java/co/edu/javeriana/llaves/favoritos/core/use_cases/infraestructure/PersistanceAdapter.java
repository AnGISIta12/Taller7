package co.edu.javeriana.llaves.favoritos.core.use_cases.infraestructure;

import co.edu.javeriana.llaves.favoritos.core.domain.entities.FavoritesEntity; // <<-- ¡ASEGÚRATE DE ESTA IMPORTACIÓN!
import java.util.List;
import java.util.Optional;

// @Service // <<-- QUITA ESTO SI TODAVÍA LO TIENE. Las interfaces no son @Service.
public interface PersistanceAdapter<T> {

    void save(T entity);

    Optional<T> findById(String id);

    void delete(T entity);

    void deleteById(String id);

    List<T> findAll();

    void update(T entity);

    // Método específico para tu CU003 de validación
    Optional<FavoritesEntity> findByKeyTextAndUser(String keyText, String user); // Asegúrate de esta firma
}