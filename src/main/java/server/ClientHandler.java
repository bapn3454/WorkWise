package server;

import helpers.Controller;
import helpers.MessageServer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import static helpers.MessageServer.afficher;
import static helpers.MessageServer.clientMessage;

// ClientHandler class
class ClientHandler extends Thread {

    final Socket socket;
    BufferedReader cin;
    PrintStream cout;
    BufferedReader stdin;
    MessageServer messageServer = new MessageServer();
    private static final int MAX_TIMEOUT = 10000;	// milliseconds

    public ArrayList<Socket> Clients = new ArrayList<Socket>();

    // Constructor
    public ClientHandler(Socket s, BufferedReader cin, PrintStream cout, BufferedReader stdin) {
        this.socket = s;
        this.cin = cin;
        this.cout = cout;
        this.stdin = stdin;
    }



    @Override
    public void run(){
        String s;
        String tmp = null;
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            try {
                Controller controller = new Controller();
                // Ask user what he wants
                s = cin.readLine();
                if(s != null) {
                    if (s.equalsIgnoreCase("Deconnexion")) {
                        clientMessage("Deconnexion Client");
                        cout.println("Aurevoir#");
                        break;
                    }
                    //this.socket.setSoTimeout(MAX_TIMEOUT);

                    //System.out.println("Client : " + s);
                    clientMessage(s);
                    String[] strings = s.split("#");

                    switch (strings[0]) {
                        case "1":
                            if (strings.length == 1 && strings[0].equalsIgnoreCase("1")) {
                                tmp = messageServer.CONNEXION;
                                //System.out.println("Server : " + tmp);
                                afficher(tmp);
                                cout.println(tmp);
                            } else if (strings.length == 2 && strings[1].equalsIgnoreCase("Inscription")) {
                                tmp = messageServer.INSCRIPTION;
                                //System.out.println("Server : " + tmp);
                                afficher(tmp);
                                cout.println(tmp);
                            } else {
                                stringBuilder = controller.etudiant(strings);
                                tmp = stringBuilder.toString();
                                //System.out.println("Server : " + tmp);
                                afficher(tmp);
                                cout.println(tmp);
                            }
                            break;
                        case "2":
                            if (strings.length == 1 && strings[0].equalsIgnoreCase("2")) {
                                tmp = messageServer.CONNEXION;
                                //System.out.println("Server : " + tmp);
                                afficher(tmp);
                                cout.println(tmp);
                            } else if (strings.length == 2 && strings[1].equalsIgnoreCase("Inscription")) {
                                tmp = messageServer.INSCRIPTION;
                                //System.out.println("Server : " + tmp);
                                afficher(tmp);
                                cout.println(tmp);
                            } else {
                                stringBuilder = controller.encarant(strings);
                                tmp = stringBuilder.toString();
                                //System.out.println("Server : " + tmp);
                                afficher(tmp);
                                cout.println(tmp);
                            }
                            break;
                        case "3":
                            if (strings.length == 1 && strings[0].equalsIgnoreCase("3")) {
                                tmp = messageServer.CONNEXION;
                                //System.out.println("Server : " + tmp);
                                afficher(tmp);
                                cout.println(tmp);
                            } else if (strings.length == 2 && strings[1].equalsIgnoreCase("Inscription")) {
                                tmp = messageServer.INSCRIPTION;
                                //System.out.println("Server : " + tmp);
                                afficher(tmp);
                                cout.println(tmp);
                            } else {
                                stringBuilder = controller.jury(strings);
                                tmp = stringBuilder.toString();
                                //System.out.println("Server : " + tmp);
                                afficher(tmp);
                                cout.println(tmp);
                            }
                            break;
                        default:
                            cout.println("FORMATINCORRECT#");
                            break;

                    }

                }else{
                    if(s == null){
                        clientMessage("Interruption/coupure connexion du client");
                    }
                    //this.indice = 1;
                    break;
                }

            }  catch (SocketTimeoutException exception) {
                // Output expected SocketTimeoutExceptions.
                cout.println("[TimeOut]Reponse client => server");
            } catch (SocketException e) {
               // e.printStackTrace();
                clientMessage("Interruption/coupure connexion du client");
            } catch (IOException e) {
                //e.printStackTrace();
            }


        }

        try {
            socket.close();
            cin.close();
            cout.close();
            stdin.close();
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("Il ya un souci avec la connexion du client");
        }
    }
}
