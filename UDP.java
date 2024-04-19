import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.imageio.plugins.bmp.BMPImageWriteParam;

public class UDP
{
    public static boolean receive = false;

    // public UDP()
    // {
    //     receive = false;
    // }

    public static void sendData(String data)
    {
        try {
            DatagramSocket transmitterSocket = new DatagramSocket();
            DatagramPacket transmitterPacket = new DatagramPacket(data.getBytes(), data.length(), InetAddress.getLocalHost(), 7500);
            transmitterSocket.send(transmitterPacket);
            transmitterSocket.close();
        } 
        catch(IOException i) {
            System.out.println("error occured in packet data transmission");
        }
    }

    public static String[] receiveData(byte[] packetData, DatagramSocket receiverSocket) throws IOException
    {
        DatagramPacket receiverPacket = new DatagramPacket(packetData, packetData.length);
        receiverSocket.receive(receiverPacket);

        //reconstruct packet into string, separate player[0] = shooter, player[1] = player who was shot
        String playerIn = data(packetData).toString();
        String[] player = playerIn.split(":");
     
    
        
        return player;
    }

    public static void gameState(int state)
    {
        switch(state)
        {
            case 202: //game start
                System.out.println("transmitting 202");
                sendData("202");
                receive = true;
                break;
            case 221: //game end
                for(int i = 0; i < 3; i++)
                    System.out.println("transmitting 221");
                    sendData("221");
                receive = false;
                break;
            default:
                break;
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
