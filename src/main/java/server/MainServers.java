package server;

// Main Server class
public class MainServers {

    public static void main(String[] args) {
        Servers tcpServer = new Servers();
        tcpServer.start();
    }
}