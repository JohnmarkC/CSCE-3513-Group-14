import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDP
{
    public static void main(String[] args) throws IOException, UnknownHostException
    {   
        DatagramSocket receiverSocket = new DatagramSocket(7501, InetAddress.getLocalHost());
        byte[] packetData = new byte[256];

        while(true)
        {
            //check for incoming packets
            DatagramPacket receiverPacket = new DatagramPacket(packetData, packetData.length);
            receiverSocket.receive(receiverPacket);

            // terminate test file
            // if(data(packetData).toString() == "quit")
            //     break;

            //reconstruct packet into string, separate player[0] = shooter, player[1] = player who was shot
            String playerIn = data(packetData).toString();
            String[] player = playerIn.split(":");
            
            sendData(player[1]);
        }
    }

    public static void sendData(String data)
    {
        try {
            DatagramSocket transmitterSocket = new DatagramSocket();
            DatagramPacket transmitterPacket = new DatagramPacket(data.getBytes(), data.length(), InetAddress.getLocalHost(), 7500);
            transmitterSocket.send(transmitterPacket);
        } 
        catch(IOException i) {
            System.out.println("error occured in packet data transmission");
        }
    }

    public static StringBuilder data(byte[] packetData)
    {
        StringBuilder ret = new StringBuilder();
        int i = 0; 
        if (packetData == null) 
            return null; 
        while (packetData[i] != 0) 
        { 
            ret.append((char) packetData[i]); 
            i++; 
        } 
        return ret; 
    }

}
