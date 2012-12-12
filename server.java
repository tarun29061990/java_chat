import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

class serverb extends JFrame
{
        private JTextField enterField;
        private JTextArea displayArea;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private ServerSocket server;
        private Socket connection;
        private int counter=1;

        public serverb()
        {
                super("serverb");
                enterField=new JTextField();
                enterField.setEditable(false);
                enterField.addActionListener(
                new ActionListener()
                {
                        public void actionPerformed(ActionEvent event)
                        {
                                sendData(event.getActionCommand());
                                enterField.setText("");
                        }
                });
                add(enterField,BorderLayout.SOUTH);
                displayArea=new JTextArea();
                add(new JScrollPane(displayArea),BorderLayout.CENTER);
                setSize(400,300);
                setVisible(true);
        }
        public void runServer()
        {
                try
                {
                        server=new ServerSocket(12345,100);
                        while(true)
                        {
                                try
                                {
                                        waitForConnection();
                                        getStreams();
                                        processConnection();
                                }
                                catch(Exception e)
                                {
                                        System.out.println(""+e);
                                }
                                finally
                                {
                                        closeConnection();
                                        counter++;
                                }
                        }
                }
                catch(Exception e)
                {
                        System.out.println(""+e);
                }
        }
        private void waitForConnection() throws IOException
        {
                displayArea.append("waiting for connection\n");
                connection=server.accept();
                displayArea.append("connection"+counter+"received from:"+connection.getInetAddress().getHostName());

        }
        private void getStreams() throws IOException
        {
                out=new ObjectOutputStream(connection.getOutputStream());
                out.flush();
                in=new ObjectInputStream(connection.getInputStream());
        }
        private void processConnection() throws IOException
        {
                String message="connection successful";
                sendData(message);
                enterField.setEditable(true);
                do
                {
                        try
                        {
                                message=(String)in.readObject();
                                displayArea.append("\n"+message);
                        }
                        catch(Exception e)
                        {
                                System.out.println(""+e);
                        }
                }while(!message.equals("CLIENT--exit"));
        }
        private void closeConnection()
        {
                displayArea.append("\n terminating connection \n");
                enterField.setEditable(false);
                try
                {
                        out.close();
                        in.close();
                        connection.close();
                }
                catch(Exception e)
                {
                        System.out.println(""+e);
                }
        }
        private void sendData(String message)
        {
                try
                {
                        out.writeObject("TARUN--"+message);
                        out.flush();
                        displayArea.append("\n TARUN--"+message);
                }
                catch(Exception e)
                {
                        System.out.println(""+e);
                }
        }
}
class server
{
        public static void main(String arg[])
        {
                serverb app=new serverb();
                app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                app.runServer();
        }
}
