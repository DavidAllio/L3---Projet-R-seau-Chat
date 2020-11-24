import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class serveur {
    public static void Serveur(Integer Port) throws IOException {
        DatagramSocket Ds =new DatagramSocket(Port);
        byte msg[] = new byte[50];
        DatagramPacket in = new DatagramPacket(msg, 50);
        Ds.receive(in);
        byte[] buffer = in.getData();
        String converted = new String(buffer, "UTF-8");
        System.out.println(">"+converted);
        System.out.println("From port : " + in.getPort());
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

        Serveur(port);
        System.out.println(sc.next());

    }
}
