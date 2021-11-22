package app.javachat.Excepciones;

public class ExceptionConnexion extends Exception {

    public ExceptionConnexion() {
        super();
    }

    public ExceptionConnexion(String message) {
        super("Error al conectarse con el server" +message);
    }
}
