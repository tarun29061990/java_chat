An Application to demonstrate the basic java networking. Here is a chatting system consists of server and client. We can use this chatting system as a localhost or on LAN.
I have configured it for localhost but if you want to run it on LAN then you have to add server IP in client.java file.

Open client.java and change this below mentioned line...
client=new Socket(InetAddress.getLocalHost(),12345); Replace InetAddress.getLocalHost()
to your server IP in client.java file.

This is a nice and neat code I have written. Feel free to use it and modify it. You can add various features like smiley, audio and video chat. I just made this app to help people who are a beginner in this java networking.