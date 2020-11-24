

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class EchoServerWSPool {

    public static void main(String[] args) throws IOException{
        Integer port=0;
        Integer nbr_thread=0;
        String actual="",before="";
        if(args.length==2) {
            port = Integer.parseInt(args[0]);
            nbr_thread = Integer.parseInt(args[1]);
        }
        else{
            System.out.println("Non. L'usage. Java, puis serveur, port, et le nombre de threads voulus.");
        }

        Selector selector = Selector.open();
        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.configureBlocking(false);
        ss.socket().bind(new InetSocketAddress("localhost",port));
        ss.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer msg= ByteBuffer.allocate(1000);

        ExecutorService executor = Executors.newWorkStealingPool(nbr_thread);

        while(selector.isOpen()) {
            try {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while(keys.hasNext()){
                    SelectionKey key = keys.next();
                    if(!key.isValid()) {
                        continue;
                    }

                    if(key.isAcceptable()){
                        SocketChannel client = ss.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);

                        System.out.println("Client Connecté ");
                    }
                    if (key.isReadable()) {

                        msg.clear();
                        SocketChannel client = (SocketChannel) key.channel();
                        client.read(msg);
                        String dis = new String(msg.array());
                        actual = dis;

                        ss.configureBlocking(false);
                        Socket sock = ss.socket().accept();

                        executor.submit(new ClientHandlerWSPool(sock, dis));

                        if(actual.equals(before)) {
                            client.close();
                        }
                        before=actual;

                    }
                    //keys.remove();

                }
                selector.selectedKeys().clear();


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}


class ClientHandlerWSPool extends Thread
{

    final String dis;
    final Socket s;


    // Constructor
    public ClientHandlerWSPool(Socket s, String dis)
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

                if(dis!=null){
                    System.out.println(">"+dis);
                    System.out.println("From port : " + s.getPort());
                    System.out.println("temps d'execution : " + (System.nanoTime()-timeTest) +" Nanosecondes, soit "+ (System.nanoTime()-timeTest)/1000000 +" millisecondes");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(0);
            }
        }

    }
}
