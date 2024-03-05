import java.util.Scanner;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class test
{  
    public static void main(String[] args) throws IOException, SocketException
    {
        // UDP udp = new UDP();
        InetAddress address = InetAddress.getLocalHost();
        DatagramSocket transmitterSocket = new DatagramSocket();
        DatagramSocket receiverSocket = new DatagramSocket(7500, address);
        DatagramPacket receiverPacket = null;
        Scanner input = new Scanner(System.in);

        DatagramSocket random;

        byte[] packetData = null;

        // udp.sendData("22", InetAddress.getLocalHost());

        while(true)
        {
            // udp.sendData("22", transmitterSocket);

            //type string inp, convert into byte array and transmit to UDP
            String inp = input.nextLine(); 
            packetData = inp.getBytes();
            DatagramPacket transmitterPacket = new DatagramPacket(packetData, packetData.length, address, 7501);  
            transmitterSocket.send(transmitterPacket);
            if(inp.equals("quit"))
                break;

            //clear byte array for next entry
            packetData = new byte[256];
            
            //scan for potential packets on port 7500, receive packet, convert from byte array to string and display
            receiverPacket = new DatagramPacket(packetData, packetData.length);
            receiverSocket.receive(receiverPacket);
            System.out.println(data(packetData).toString());
        }
    }

    public static StringBuilder data(byte[] a)
    {
        if (a == null) 
            return null; 
        StringBuilder ret = new StringBuilder(); 
        int i = 0; 
        while (a[i] != 0) 
        { 
            ret.append((char) a[i]); 
            i++; 
        } 
        return ret; 
    }
}