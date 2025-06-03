package co.edu.javeriana.llaves.favoritos.core.domain.util;

public class UUIDGenerator extends IdsGenerator {
    @Override
    public String generateId() {
        return java.util.UUID.randomUUID().toString();
    }
}
