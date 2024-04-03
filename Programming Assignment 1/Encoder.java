/**
 * Interface that defines the methods that child classes will use
 *
 * @author Jacob Munroe
 */
public interface Encoder {

    /**
     * Method that encodes a given short.
     *
     * @param number Number to be encoded.
     * @return Byte Array with the encoded message in UTF-16.
     * @throws Exception Checks for exceptions
     */
    byte[] encodeShort(short number) throws Exception;


    /**
     * Method that encodes a given string.
     *
     * @param number String to be encoded.
     * @return Byte Array with the encoded message in UTF-16.
     * @throws Exception Checks for exceptions.
     */
    byte[] encodeString(String number) throws Exception;
}
