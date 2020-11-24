import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class EchoServerDPool {

    public static void main(String[] args) throws IOException{
        Integer port=0;
        Integer nbr_thread=0;
        if(args.length==2) {
            port = Integer.parseInt(args[0]);
            nbr_thread = Integer.parseInt(args[1]);
        }
        else{
            System.out.println("Non. L'usage. Java, puis serveur, port, et le nombre de threads voulus.");
        }

        ServerSocket ss = new ServerSocket(port);

        ExecutorService executor = Executors.newCachedThreadPool();
        //ThreadPoolExecutor pool = (ThreadPoolExecutor) executor;

        for(int i=0; i<nbr_thread;i++) {
            try {
                Socket s = ss.accept();

                DataInputStream inFromClient = new DataInputStream(s.getInputStream());
                executor.submit(new ClientHandlerDPool(s, inFromClient));

                //Thread t = new ClientHandlerDPool(s, inFromClient);

                // Invoking the start() method
                //t.start();

            } catch (Exception ex) {
				System.exit(0);
            }
        }

    }
}

class ClientHandlerDPool extends Thread
{

    final DataInputStream dis;
    final Socket s;


    // Constructor
    public ClientHandlerDPool(Socket s, DataInputStream dis)
    {
        this.s = s;
        this.dis = dis;
    }




    @Override
    public void run()
    {
        String received;
        String toreturn;

        while (true)
        {
            long timeTest = System.nanoTime();
            try {


                byte[] msg;
                BufferedReader reader = new BufferedReader(new InputStreamReader(dis));
				String converted=null;
                if((converted = reader.readLine())!=null){
					System.out.println(">"+converted);
					System.out.println("From port : " + s.getPort());
                    System.out.println("temps d'execution : " + (System.nanoTime()-timeTest) +" Nanosecondes, soit "+ (System.nanoTime()-timeTest)/1000000 +" millisecondes");
				}
                } catch (IOException e1) {
                e1.printStackTrace();
            }catch (Exception ex) {
				System.exit(0);
            }
        }

    }
}