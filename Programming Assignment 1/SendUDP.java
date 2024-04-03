/**
 * Client Class that is responsible for sending a message through the UDP protocol
 * and receiving an echo from the Server.
 *
 * @author Jacob p Munroe
 */

import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException
import java.util.Objects;
import java.util.Scanner;

public class SendUDP {

    /**
     * Main method that will be sending our message and receiving the server
     * echo.
     *
     * @param args Command line arguments - specify port number and host destination IP
     * @throws Exception Check for Exception
     */
  public static void main(String args[]) throws Exception {

      if (args.length != 2)  // Test for correct # of args
	  throw new IllegalArgumentException("Parameter(s): <Destination>" +
					     " <Port>");
      
      
      InetAddress destAddr = InetAddress.getByName(args[0]);  // Destination address
      int destPort = Integer.parseInt(args[1]);               // Destination port

      short number; // number from user

      Scanner scan = new Scanner(System.in);

      // String for do-while loop
      String choice = "y";

      do {

          // Prompt user for a short
          System.out.print("Please enter an integer to encode between -32768 and 32767: ");
          number = scan.nextShort();


          // Check if we are given a valid value
          if (number > 32767 || number < -32768) {
              System.out.println("Please enter a valid number in the range of -32768 and 32767.");
          }
          else { // continuing if given valid short

              ///////////////////////////////////
              /////// Sending encoded msg ///////
              ///////////////////////////////////
              DatagramSocket sock = new DatagramSocket(); // UDP socket for sending


              // Use the encoding scheme given on the command line (args[2])
              Encoder encoder = new EncoderBin();

              byte[] codedNumber = encoder.encodeShort(number); // Encode short

              // Packet that is sending our message
              DatagramPacket message = new DatagramPacket(codedNumber, codedNumber.length,
                      destAddr, destPort);

              long startTime = System.nanoTime(); // start tracking time

              sock.send(message); // sending message



              ///////////////////////////////////
              ////// Receiving Server Echo //////
              ///////////////////////////////////
              DatagramPacket packet = new DatagramPacket(new byte[1024],1024);
              sock.receive(packet);

              long endTime = System.nanoTime(); // stop tracking time

              Decoder decoder = new DecoderBin();

              String receivedString = decoder.decodeString(packet); // decode the received echo

              System.out.println("The received echo string from the server is:");
              System.out.println(receivedString);

              long elapsedTime = endTime - startTime; // calculate time

              System.out.println("To send and receive a message, it took: " + (elapsedTime/1e6) +"ms.");


              // Prompt user if they want to enter another number
              System.out.print("Would you like to enter another number? [y/n]: ");
              choice = scan.next();
          }
      } while (!Objects.equals(choice, "n")); // if we receive an answer of n, then stop loop
  }
}
