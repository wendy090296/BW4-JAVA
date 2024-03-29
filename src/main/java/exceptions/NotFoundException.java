package exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("Element with the id: " + id + "is invalid ༼ ༎ຶ ᆺ ༎ຶ༽");
    }

}
