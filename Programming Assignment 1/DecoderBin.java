/**
 * Class that is responsible for decoding messages that are sent on the "wire".
 * Does so by utilizing methods that decode the UTF-16 messages.
 *
 * @author Jacob Munroe
 */
import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class DecoderBin implements Decoder {

    /**
     * Encoding protocol
     */
    private final String encoding;

    /**
     * Constructor for our DecoderBin, sets the decoding protocol.
     */
    public DecoderBin() { encoding = "UTF-16"; }

    /**
     * Decoding method to decode a short that receives information that is read
     * from a DataInputStream.
     *
     * @param source DataInputStream of which the data is being sent on.
     * @return The short that was decoded.
     * @throws IOException Checks for exception.
     */
    public short decodeShort(InputStream source) throws IOException {

        DataInputStream src = new DataInputStream(source);

        byte[] byteArray = new byte[2];

        src.readFully(byteArray);

        short decodedNumber = (short) ((byteArray[0] << 8) | (byteArray[1] & 0xFF));


        System.out.println("The decoded hexadecimal short received is:");

        for (byte b : byteArray) {
            System.out.printf("%02X%n", b);
        }

        return decodedNumber;
    }


    /**
     * Decoding method that reads in the information from a DatagramPacket.
     *
     * @param p DatagramPacket on which on data is being sent on in the UDP protocol.
     * @return The short that was decoded.
     * @throws IOException Checks for exception
     */
    public short decodeShort(DatagramPacket p) throws IOException {
        ByteArrayInputStream payload =
                new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
        return decodeShort(payload);
    }


    /**
     * Decoding method to decode a string that receives information that is read
     * from a DataInputStream.
     *
     * @param source DataInputStream of which the data is being sent on.
     * @return The string that was decoded.
     * @throws IOException Checks for exception.
     */
    public String decodeString(InputStream source) throws IOException {

        DataInputStream src = new DataInputStream(source);

        int stringLength = src.read();
        if (stringLength == -1)
            throw new EOFException();

        byte[] stringBuf = new byte[stringLength];
        src.readFully(stringBuf);

        System.out.println("The received echo from the server in hexadecimal:");

        for (byte b: stringBuf) {
            System.out.printf("%02X%n", b);
        }

        return new String(stringBuf, encoding);
    }


    /**
     * Decoding method that reads in the information from a DatagramPacket.
     *
     * @param p DatagramPacket on which on data is being sent on in the UDP protocol.
     * @return The string that was decoded.
     * @throws IOException Checks for exception
     */
    public String decodeString(DatagramPacket p) throws IOException {
        ByteArrayInputStream payload =
                new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
        return decodeString(payload);
    }
}
