/**
 * Server class that will receive a message from the Client and
 * send back an echo response.
 *
 * @author Jacob Munroe
 */

import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException

public class RecvUDP {

    /**
     * Main method responsible for decoding the message and sending
     * back an encoded response.
     *
      * @param args
     * @throws Exception
     */
  public static void main(String[] args) throws Exception {

      if (args.length != 1)  // Test for correct # of args
	  throw new IllegalArgumentException("Parameter(s): <Port>");
      
      int port = Integer.parseInt(args[0]);   // Receiving Port
      
      DatagramSocket sock = new DatagramSocket(port);  // UDP socket for receiving      
      DatagramPacket packet = new DatagramPacket(new byte[1024],1024);

      // RUN FOREVER!!!
      for (;;) {

          ///////////////////////////////////
          ////// Receiving Encoded Msg //////
          ///////////////////////////////////
          sock.receive(packet); // receive our message

          // Printing out info about our client
          System.out.println("Handling client at " + packet.getAddress().getHostAddress()
                  + " on port " + packet.getPort());

          String str; // String we will use to encode the response

          if (packet.getLength() != 2) { // checking if we are receiving a number of bytes greater than 2.
              str = "****";
          }
          else {
              // Preparing to decode our number
              Decoder decoder = new DecoderBin();

              short request = decoder.decodeShort(packet); // decoding the short


              System.out.println("Received Binary-Encoded Short:");
              System.out.println(request);


              System.out.println("Converting received short to String...");

              str = String.valueOf(request); // converting and storing short as a string
          }


          ///////////////////////////////////
          ////// Sending encoded Resp ///////
          ///////////////////////////////////
          Encoder encoder = new EncoderBin();

          byte[] encodedStr = encoder.encodeString(str); // encoding the string

          // Packet that is carrying the data of the response string
          DatagramPacket response = new DatagramPacket(encodedStr, encodedStr.length,
                  packet.getAddress(), packet.getPort());

          sock.send(response); // finally send back our response message
      }
  }
}
