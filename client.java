import java.io.*;
import java.net.*;

public class client {

    Socket socket;
    BufferedReader br;
    PrintWriter out;
    public client(){
        try {

            System.out.println("Request sending...");
            socket = new Socket("192.168.242.1",5555);
            System.out.println("Request sent");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            reading();
            writing();

        }
        catch (Exception e){
            e.printStackTrace();
        }



    }
    public void reading(){
        Runnable r1=()->{

            System.out.println("Reading started...");
            try {
                while (true) {


                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("SERVER TERMINATED THE CHAT");
                        socket.close();
                        break;
                    }

                    System.out.println("SERVER: " + msg);

                }
            }
                catch (Exception e) {
                    //e.printStackTrace();
                    System.out.println("CONEECTION CLOSED");
                }

        };
        new Thread(r1).start();


    }

    public void writing() {
        Runnable r2 = () -> {
            System.out.println("writing started");
            try {
                while (true) {


                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    String content = in.readLine();
                    out.println(content);
                    out.flush();
                    if (content.equals("exit")) {
                        System.out.println("CLIENT STOP");
                        break;
                    }
                }
            }

                 catch (Exception e) {
                    //e.printStackTrace();
                     System.out.println("CONEECTION CLOSED");
                }



        };
        new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("THIS IS CLIENT....");
        new client();
    }

}
