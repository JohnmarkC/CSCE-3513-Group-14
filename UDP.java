import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP
{
    public static void sendData(String data)
    {
        try {
            DatagramSocket transmitterSocket = new DatagramSocket();
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            DatagramPacket transmitterPacket = new DatagramPacket(data.getBytes(), data.length(), addr, 7500);
            transmitterSocket.send(transmitterPacket);
            transmitterSocket.close();
        } 
        catch(Exception e) {
            System.out.println("Error has occured in packet data transmission");
            e.printStackTrace();
        }
    }

    public static String[] receiveData(byte[] packetData, DatagramSocket receiverSocket)
    {
        try {
            DatagramPacket receiverPacket = new DatagramPacket(packetData, packetData.length);
            receiverSocket.receive(receiverPacket);
        }
        catch(Exception e)
        {
            System.out.println("Error has occurred in packet reception");
            e.printStackTrace();
        }

        //reconstruct packet into string, separate player[0] = shooter, player[1] = player who was shot
        String playerIn = data(packetData).toString();
        String[] player = playerIn.split(":");
        for(int i = 0; i < player.length; i++) {
            if(!player[i].matches("\\d+")) //checks if the entries in player[] contain only integers, if not it will clear both entries
                player[i] = " ";
        }
        return player;
    }

    public static void gameState(int state)
    {
        switch(state)
        {
            case 202: //game start
                System.out.println("transmitting 202");
                sendData("202");
                break;
            case 221: //game end
                for(int i = 0; i < 3; i++) {
                    System.out.println("transmitting 221");
                    sendData("221");
                }
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
