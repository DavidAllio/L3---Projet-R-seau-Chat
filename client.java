import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class client {
    public static void Client(Socket Ds, String Message) throws IOException {
        System.out.println(">"+Message);
        System.out.println("To port : " + Ds.getPort());

        DataOutputStream outToServer = new DataOutputStream(Ds.getOutputStream());
        DataInputStream inFromServer = new DataInputStream(Ds.getInputStream());

        outToServer.writeBytes(Message + "\n");
        outToServer.flush();

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
            System.out.println("Non. L'usage. Java, puis client, serveur, et port.");
        }

        //On crée le socket hors de client, pour ne pas le renvoyer constamment avec notre boucle
        try {
            Socket clientSocket = new Socket(serveur,port);

        //On appelle le client en boucle afin d'envoyer des messages en continu
             while(sc.hasNext()){
                Client(clientSocket,sc.next());
             }
        }
        catch(Exception ex) { }
    }
}
