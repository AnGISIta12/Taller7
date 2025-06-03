package co.edu.javeriana.llaves.favoritos.core.domain.enums;

public enum FavoriteState {
    ACTIVE("active"),
    INACTIVE("inactive");

    private final String description;

    FavoriteState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
