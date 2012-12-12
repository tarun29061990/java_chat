import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

class clientb extends JFrame
{
        private JTextField enterField;
        private JTextArea displayArea;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private String message="";
        private Socket client;
        

        public clientb()
        {
                super("clientb");
		
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
        public void runclient()
        {
                try
                {
                       
                        connectToServer();
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
                                        
                }
                        
        }
        private void connectToServer() throws IOException
        {
                displayArea.append("attempting connection\n");
                client=new Socket(InetAddress.getLocalHost(),12345);
                displayArea.append("connected to"+client.getInetAddress().getHostName());

        }
        private void getStreams() throws IOException
        {
                out=new ObjectOutputStream(client.getOutputStream());
                out.flush();
                in=new ObjectInputStream(client.getInputStream());
        }
        private void processConnection() throws IOException
        {
                
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
                }while(!message.equals("TARUN--exit"));
        }
        private void closeConnection()
        {
                displayArea.append("\n terminating connection \n");
                enterField.setEditable(false);
                try
                {
                        out.close();
                        in.close();
                        client.close();
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
                        out.writeObject("CLIENT--"+message);
                        out.flush();
                        displayArea.append("\n CLIENT--"+message);
                }
                catch(Exception e)
                {
                        System.out.println(""+e);
                }
        }
}
class client
{
        public static void main(String arg[])
        {
                clientb app=new clientb();
                app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                app.runclient();
        }
}
