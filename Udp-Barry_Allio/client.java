import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class client {
    public static void Client(DatagramSocket Ds, String Message, String Server, Integer Port) throws IOException {
        System.out.println(">"+Message);
        byte msg[] = Message.getBytes();
        InetAddress localhost = InetAddress.getByName(Server);
        DatagramPacket out = new DatagramPacket(msg, 0, msg.length, localhost, Port);
        out.setSocketAddress(new InetSocketAddress(Server, Port));
        Ds.send(out);
        System.out.println("To port : " + out.getPort());
    }

    public static void main(String[] args) throws IOException{
        String serveur="";
        Integer port=0;
        Scanner sc = new Scanner(System.in);

        if(args.length==2) {
            serveur = args[0];
            port = Integer.parseInt(args[1]);
        }
        else{
            System.out.println("Non. L'usage. Java, puis CliUdp, serveur, et port.");
        }

        //On crée le socket hors de client, pour ne pas le renvoyer constamment avec notre boucle
        DatagramSocket Ds =new DatagramSocket();
        //On appelle le client en boucle afin d'envoyer des messages en continu
        while(sc.hasNext()){
            Client(Ds,sc.next(),serveur,port);
        }

    }
}
