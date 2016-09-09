package com.mycompany.ca1.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextArea;
import shared.ProtocolStrings;

public class EchoClient{

    Socket socket;
    private int port;
    private InetAddress serverAddress;
    private Scanner input;
    private PrintWriter output;
    
    public Socket getSocket(){
        return socket;
    }

    public void connect(String address, int port) throws UnknownHostException, IOException {
        this.port = port;
        serverAddress = InetAddress.getByName(address);
        socket = new Socket(serverAddress, port);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);  //Set to true, to get auto flush behaviour
    }

    public void send(String msg) {
        output.println(msg);
    }
    
    public void stop() throws IOException {
        output.println(ProtocolStrings.ARGS.STOP);
    }

    public String receive() {
        String msg = input.nextLine(); //Blocking call (until it receives respond from server).
        if (msg.equals(ProtocolStrings.STOP)) {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return msg;
    }
    
    public void receiveClientList(JButton login, JList<String> list) {

        try {
            String msg = this.receive();
            //System.out.println(msg);

            String splitColon[] = msg.split(":");

            String splitComma[] = splitColon[1].split(",");
            if (splitComma.length == 0) {
                splitComma = new String[1];
                splitComma[0] = splitColon[1];
            }

            String[] clientList = new String[splitComma.length];

            int i = 0;
            for (String string : splitComma) {
                clientList[i] = string;
                i++;
            }
            list.setModel(new AbstractListModel<String>() {
                @Override
                public int getSize() {
                    return clientList.length;
                }

                @Override
                public String getElementAt(int index) {
                    return clientList[index];
                }
            });
        } catch (Exception e) {

        }
        login.setText("Logout");
    }
    
    public void parseMessage(String msg, JList<String> jList1, JTextArea jTextArea1){
        String[] splitColon = msg.split(":");
        String splitComma[];
        if(splitColon[0].equalsIgnoreCase(ProtocolStrings.ARGS.CLIENTLIST.name())){
            splitComma=splitColon[1].split(",");
            if (splitComma.length == 0) {
                splitComma = new String[1];
                splitComma[0] = splitColon[1];
            }
            String[] clientList = new String[splitComma.length];
            int i = 0;
            for (String string : splitComma) {
                clientList[i] = string;
                i++;
            }
            jList1.setModel(new AbstractListModel<String>() {
                @Override
                public int getSize() {
                    return clientList.length;
                }

                @Override
                public String getElementAt(int index) {
                    return clientList[index];
                }
            });
        }else if(splitColon[0].equalsIgnoreCase(ProtocolStrings.ARGS.MSGRESP.name())){
            jTextArea1.append(splitColon[1]+": "+splitColon[2]+"\n");
        }else{
            
        }
    }

    public boolean isStopped() {
        return socket.isClosed();
    }

    public static void main(String[] args) {
        int port = 8080;
        String ip = "localhost";
        if (args.length == 2) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        }
        try {
            EchoClient tester = new EchoClient();
            tester.connect(ip, port);
            System.out.println("Sending 'Hello world'");
            tester.send("Hello World");
            System.out.println("Waiting for a reply");
            System.out.println("Received: " + tester.receive()); //Important Blocking call         
            tester.stop();
            //System.in.read();      
        } catch (UnknownHostException ex) {
            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
