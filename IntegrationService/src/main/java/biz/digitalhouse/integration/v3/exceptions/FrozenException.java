package biz.digitalhouse.integration.v3.exceptions;

/**
 * @author Vitalii Babenko
 *         on 15.04.2016.
 */
public class FrozenException extends RuntimeException {

    public FrozenException(String message) {
        super(message);
    }
}
