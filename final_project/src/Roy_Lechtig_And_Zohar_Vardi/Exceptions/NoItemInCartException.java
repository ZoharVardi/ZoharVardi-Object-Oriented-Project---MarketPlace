package Roy_Lechtig_And_Zohar_Vardi.Exceptions;

public class NoItemInCartException extends Exception {
    public NoItemInCartException() {
        super("Cart is empty.");
    }
}