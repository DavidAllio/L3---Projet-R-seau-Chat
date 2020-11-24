import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Stress2 {
    public static void Client(Socket Ds, String Message) throws IOException {
        System.out.println(">"+Message);
        System.out.println("To port : " + Ds.getPort());

        DataOutputStream outToServer = new DataOutputStream(Ds.getOutputStream());
        //DataInputStream inFromServer = new DataInputStream(Ds.getInputStream());

        outToServer.writeBytes(Message + "\n");
        outToServer.flush();
		outToServer.close();

    }

    public static void main(String[] args) throws IOException{
        String serveur="localhost";
        Integer port=1234, nbr_client=0, i;

        if(args.length==1) {
            nbr_client = Integer.parseInt(args[0]);
			System.out.println(args[0]);
        }
        else{
            System.out.println("Non. L'usage : Stress1 nbr_client.");
        }

        //On cree le socket hors de client, pour ne pas le renvoyer constamment avec notre boucle
        try {

        //On appelle le client en boucle afin d'envoyer des messages en continu
             for(i=0;i<nbr_client;i++){
                 Socket clientSocket = new Socket(serveur,port);
                 Client(clientSocket,"client stress1 n "+i);
             }
        }
        catch(Exception ex) { 
			System.out.println("ouille");
		}
    }
}
