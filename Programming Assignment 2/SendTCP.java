import java.io.*;   // for Input/OutputStream
import java.net.*;  // for Socket
import java.util.Scanner;
import java.util.Objects;

public class SendTCP {

  public static void main(String args[]) throws Exception {

    if (args.length != 2)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Destination> <Port>");


    InetAddress destAddr = InetAddress.getByName(args[0]);  // Destination address
    int destPort = Integer.parseInt(args[1]);// Destination port


    Short number;


    Scanner scan = new Scanner(System.in);


    String choice = "y";

    // loop to run prompting user for shorts
    do {

      // Prompt user for a short
      System.out.print("\nPlease enter an integer to encode between -32768 and 32767: ");
      number = scan.nextShort();


      // Check if we are given a valid value
      if (number > 32767 || number < -32768) {
          System.out.println("Please enter a valid number in the range of -32768 and 32767.");
      }
      else { // continuing if given valid short

          ///////////////////////////////////
          /////// Sending encoded msg ///////
          ///////////////////////////////////
          Socket sock = new Socket(destAddr, destPort); // TCP socket for sending


          System.out.println("Display number before encoding: "); 
          System.out.println(number); // Display friend just to check what we send

          Encoder encoder = new EncoderBin();

          System.out.println("Encoded number to send: ");
          byte[] codedNumber = encoder.encodeShort(number);

          System.out.println("Sending number...\n");
          OutputStream out = sock.getOutputStream(); // Get a handle onto Output Stream
          
          long startTime = System.nanoTime(); // start tracking time
          
          out.write(codedNumber); // sending message



          ///////////////////////////////////
          ////// Receiving Server Echo //////
          ///////////////////////////////////
          System.out.println("Receiving Response...");

          InputStream in = sock.getInputStream();

          Decoder decoder = new DecoderBin();

          String receivedString = decoder.decodeString(sock.getInputStream()); // decoding the response from server

          long endTime = System.nanoTime(); // stop tracking time

          long elapsedTime = endTime - startTime; // calculate time

          System.out.println("To send and receive a message it took: "
                + (elapsedTime/1e6) +"ms.\n");


          // Prompt user if they want to enter another number
          System.out.print("Would you like to enter another number? [y/n]: ");
          choice = scan.next();
      }
    } while (!Objects.equals(choice, "n")); // runs until user enters 'n'
  }
}
