package co.edu.javeriana.llaves.favoritos.gateways.dtos;

public record FavoritesDTO(String id, String user, String alias, String keyText, String status) {

    @Override
    public String toString() {
        return "FavoritesDTO{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", alias='" + alias + '\'' +
                //", keyText='" + keyText + '\'' + // Comentado en tu original
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public String alias() {
        return alias;
    }
}