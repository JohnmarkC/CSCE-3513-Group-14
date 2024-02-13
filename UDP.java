import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDP
{
    DatagramSocket receiver;
    DatagramSocket transmitter;
    InetAddress ip;
    static boolean gameOn;

    public UDP() {   //On startup, establish socket and IP
        try{
            receiver = new DatagramSocket(7501);
            transmitter = new DatagramSocket(7500);
            ip = InetAddress.getLocalHost();
            gameOn = false;
        }
        catch(SocketException s){   //checking if ports are set, error if they aren't =7500/7501
            if(receiver.getLocalPort() != 7501)
                System.out.println("Receiver Port failed to initialize");
            else if(transmitter.getLocalPort() != 7500)
                System.out.println("Transmitter Port failed to initialize");
        }
        catch(UnknownHostException u){  //checking if local ip has been set
            if(ip == null)
                System.out.println("No IP found");
        }
    }

    public static void main(String[] args) throws IOException
    {
        UDP udp = new UDP();
        int code = 0;
        byte[] buf = new byte[8];

        while(gameOn)
        {
            DatagramPacket packetReceive = new DatagramPacket(buf, 5);  //all inbound packets limited to 5 char
            DatagramPacket packetSend = new DatagramPacket(buf, 3);     //all outbound packets limited to 3 char
            udp.receiver.receive(packetReceive);
            udp.transmitter.send(packetSend);

            switch(code)
            {
                case 43:
                    //green player has shot red player
                    break;
                case 53:
                    //red player has shot green player
                    break;
                case 202:
                    //GAME BEGIN
                    break;
                case 221:
                    // for(int i = 0; i < 3; i++)
                    //GAME OVER
                    break;
                default:
                    System.out.println("Invalid code");
                    break;
            }
        }
    }
}