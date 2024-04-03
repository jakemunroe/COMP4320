import java.io.*;   // for Input/OutputStream
import java.net.*;  // for Socket and ServerSocket

public class RecvTCP {

  public static void main(String args[]) throws Exception {

    if (args.length != 1)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int port = Integer.parseInt(args[0]);   // Receiving Port
	
    ServerSocket servSock = new ServerSocket(port);


    for (;;) {

      ///////////////////////////////////
      ////// Receiving Encoded Msg //////
      ///////////////////////////////////
      Socket clntSock = servSock.accept();

      // reporting info on our client
      System.out.println("\nHandling client at "
          + clntSock.getInetAddress().getHostAddress()
          + " on port " + clntSock.getPort());

      

      String str;

      Decoder decoder = new DecoderBin();

      // buffer we will utilize for receiving
      byte[] buffer = new byte[2];
      int bytesRead = clntSock.getInputStream().read(buffer); // read in our message


      // checking for message larger than 2 bytes
      if (bytesRead != 2) {
        str = "****";
      }
      else {

        // decoding our message and prints to console
        short request = decoder.decodeShort(new ByteArrayInputStream(buffer));

        System.out.println("Received Binary-Encoded Short: ");
        System.out.println(request);

        str = String.valueOf(request); // converting from short to string
      }



      ///////////////////////////////////
      ////// Sending encoded Resp ///////
      ///////////////////////////////////
      Encoder encoder = new EncoderBin();

      byte[] encodedStr = encoder.encodeString(str); // encoding string for response


      System.out.println("Sending back response...");

      OutputStream out = clntSock.getOutputStream();
      out.write(encodedStr); // sending encoded response
      System.out.println("Sent!");

      clntSock.close();
    }
  }
}
