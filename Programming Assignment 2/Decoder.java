/**
 * Interface that defines the methods that child classes will use
 *
 * @author Jacob Munroe
 */
import java.io.*;
import java.net.*;

public interface Decoder {

    /**
     * Decoding method to decode a short that receives information that is read
     * from a DataInputStream.
     *
     * @param source DataInputStream of which the data is being sent on.
     * @return The short that was decoded.
     * @throws IOException Checks for exception.
     */
    short decodeShort(InputStream source) throws IOException;

    /**
     * Decoding method that reads in the information from a DatagramPacket.
     *
     * @param packet DatagramPacket on which on data is being sent on in the UDP protocol.
     * @return The short that was decoded.
     * @throws IOException Checks for exception
     */
    short decodeShort(DatagramPacket packet) throws IOException;

    /**
     * Decoding method to decode a string that receives information that is read
     * from a DataInputStream.
     *
     * @param source DataInputStream of which the data is being sent on.
     * @return The string that was decoded.
     * @throws IOException Checks for exception.
     */
    String decodeString(InputStream source) throws IOException;

    /**
     * Decoding method that reads in the information from a DatagramPacket.
     *
     * @param packet DatagramPacket on which on data is being sent on in the UDP protocol.
     * @return The string that was decoded.
     * @throws IOException Checks for exception
     */
    String decodeString(DatagramPacket packet) throws IOException;
}
