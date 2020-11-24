

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

public class serveurSelect {

    public static void main(String[] args) throws IOException{
        Integer port=1234;
        String actual="",before="";

        if(args.length==1) {
            port = Integer.parseInt(args[0]);
        }
        else{
            System.out.println("Non. L'usage. Java, puis serveur, et port.");
        }

        Selector selector = Selector.open();
        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.configureBlocking(false);
        ss.socket().bind(new InetSocketAddress("localhost",port));
        ss.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer msg= ByteBuffer.allocate(1000);

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
                        String s = new String(msg.array());
                        actual = s;

                        if(actual.equals(before)) {
                            client.close();
                        }else{
                            System.out.println("msg: " + s);
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
