/**
 * Class that is responsible for encoding the given user messages, does so with 2 methods
 * that utilize UTF-16.
 *
 * @author Jacob Munroe
 */
import java.io.*;
import java.nio.ByteBuffer;

public class EncoderBin implements Encoder {

    /**
     * Encoding protocol
     */
    private final String encoding;

    /**
     * Constructor for our EncoderBin, sets the encoding protocol.
     */
    public EncoderBin () { encoding = "UTF-16"; }


    /**
     * Method that encodes a given short.
     *
     * @param number Number to be encoded.
     * @return Byte Array with the encoded message in UTF-16.
     * @throws Exception Checks for exceptions
     */
    public byte[] encodeShort(short number) throws Exception {

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buf);

        byte[] encodedNumber = ByteBuffer.allocate(2).putShort(number).array();

        out.write(encodedNumber);
        out.flush();

        for (byte b : encodedNumber) {
            System.out.printf("%02X%n", b);
        }

        return buf.toByteArray();
    }


    /**
     * Method that encodes a given string.
     *
     * @param number String to be encoded.
     * @return Byte Array with the encoded message in UTF-16.
     * @throws Exception Checks for exceptions
     */
    public byte[] encodeString(String number) throws Exception {

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buf);

        byte[] encodedStr = number.getBytes(encoding);

        out.writeByte(encodedStr.length);
        out.write(encodedStr);
        out.flush();

        System.out.println("Hexadecimal UTF-16 Encoding of Response String:");

        for (byte b : encodedStr) {
            System.out.printf("%02X%n", b);
        }

        return buf.toByteArray();
    }
}
