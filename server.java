import java.net.*;
import java.io.*;
public class server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    public server(){
        try {
            server = new ServerSocket(5555);
            System.out.println("We Are Ready");
            System.out.println("Waiting for connection....");
            socket= server.accept();
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
                        System.out.println("CLIENT TERMINATED THE CHAT");
                        socket.close();
                        break;
                    }

                    System.out.println("CLIENT: " + msg);

                }
            }
                catch (IOException e) {
                    e.printStackTrace();
                }

        };
        new Thread(r1).start();


    }

    public void writing(){
        Runnable r2=()->{
            System.out.println("writing started");
            try {
                while (true) {


                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    String content = in.readLine();
                    out.println(content);
                    out.flush();
                    if (content.equals("exit")) {
                        System.out.println("SERVER STOP");
                        break;
                    }
                }
            }
                catch (Exception e){
                    //e.printStackTrace();
                    System.out.println("CONEECTION CLOSED");
                }
        };
        new Thread(r2).start();



    }

    public static void main(String[] args) {
        System.out.println("THIS IS SERVER... GOING TO START ");
        new server();

    }


}