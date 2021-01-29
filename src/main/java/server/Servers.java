package server;

// Java implementation of Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.InputMismatchException;
import java.util.Scanner;

// Server class
public class Servers extends Thread{

    ServerSocket ss;


    private static boolean isLocalPortInUse(int port) {
        try {
            // ServerSocket try to open a LOCAL port
            new ServerSocket(port).close();
            // local port can be opened, it's available
            return false;
        } catch(IOException e) {
            // local port cannot be opened, it's in use
            return true;
        }
    }


    public static int readInt(String msg) {
        final Scanner in = new Scanner(System.in);
        System.out.print("Port : ");
        int num = 0;
        //System.out.println(in.nextLine());
        //if(in.nextLine().isEmpty()) System.out.println("vide");
        /*if(!in.hasNext()) {
            break;
        }*/
        try {
            num = in.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Valeur invalide! ");
            System.out.println();
            num = readInt(msg);
        }
        return num;
    }

    public void run(){
        try{
            while (!isInterrupted()) {
                // Enter the port to listen
                final Scanner in = new Scanner(System.in);
                System.out.print("Port: ");
                if(!in.hasNext()) {
                    break;
                }
                int port =in.nextInt();
                while(isLocalPortInUse(port) ){
                    //String message = "Le port %d est déja utilisé" + port);
                    System.out.format("Le port %d est déja utilisé \n", port);
                    System.out.println("Entrer un autre port: ");
                    port =in.nextInt();
                }

                // server is listening on port 5056
                ss = new ServerSocket(port);

                System.out.println("[Serveur] Créé!");
                System.out.format("[Serveur] Commencez à écouter sur le port %d...\n", port);
                System.out.println("En attente d'un client  ...");
                // running infinite loop for getting
                // client request
                while (true)
                {
                    Socket s = null;

                    try {
                        // socket object to receive incoming client requests
                        //while (! isInterrupted()) {
                        s = ss.accept();

                        System.out.println("A new client is connected : " + s);
                        System.out.println("address: " + s.getLocalPort());
                        System.out.println("Client accepted");

                        BufferedReader cin = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        PrintStream cout = new PrintStream(s.getOutputStream());
                        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

                        System.out.println("Assigning new thread for this client");
                        // create a new thread object
                        Thread t = new ClientHandler(s, cin, cout, stdin);

                        // Invoking the start() method
                        t.start();

                    }
                    catch (SocketTimeoutException exception) {
                        // Output expected SocketTimeoutExceptions.
                        //System.out.println(exception);
                    }
                }

            }
        }
        catch(IOException e){
            //e.printStackTrace();
        }
    }


}