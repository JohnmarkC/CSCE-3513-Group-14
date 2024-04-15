import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDP
{
    public static boolean receive = false;

    // public UDP()
    // {
    //     receive = false;
    // }

    public static void main(String[] args) throws IOException, UnknownHostException
    {   
        DatagramSocket receiverSocket = new DatagramSocket(7501, InetAddress.getLocalHost());
        byte[] packetData = new byte[256];

        while(true)
        {
            // while(receive)
            // {
                String[] players = receiveData(packetData, receiverSocket);
                sendData(players[1]);
            // }
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

    public static String[] receiveData(byte[] packetData, DatagramSocket receiverSocket) throws IOException
    {
        DatagramPacket receiverPacket = new DatagramPacket(packetData, packetData.length);
        receiverSocket.receive(receiverPacket);

        //reconstruct packet into string, separate player[0] = shooter, player[1] = player who was shot
        String playerIn = data(packetData).toString();
        String[] player = playerIn.split(":");
        
        return player;
        /*
        switch(player[1])
        {
            case 53:
                //green player shot red base
                break;
            case 43:
                //red player shot green base
                break;
            default:
                //red/green player has shot green/red player
                sendData(player[1]);
                break;
        } 
        */

        // sendData(player[1]);
    }

    public static void gameState(int state)
    {
        switch(state)
        {
            case 202: //game start
                sendData("202");
                receive = true;
                break;
            case 221: //game end
                for(int i = 0; i < 3; i++)
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
