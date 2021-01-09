package com.yunxin.utils.tcp;

import com.yunxin.utils.bytes.Bytes;

import java.io.DataInputStream;
import java.net.Socket;

/**
 * 基于4字节长度头的socket客户端
 */
public class LengthBasedSocketClient implements Runnable{
    private Socket socket;
    private String host;
    private int port;
    private Thread thread;
    private SocketHandler handler = null;
    private PacketHeaderConfiguration packetHeaderConfiguration = new PacketHeaderConfiguration();

    public void connect(String host,int port,SocketConfigurer socketConfigurer, PacketHeaderConfiguration packetHeaderConfiguration, SocketHandler socketHandler){
        try{
            if(packetHeaderConfiguration!=null){
                this.packetHeaderConfiguration = packetHeaderConfiguration;
            }

            this.host = host;
            this.port = port;
            handler = socketHandler;
            socket = new Socket(host,port);
            socket.setKeepAlive(true);
            socket.setSoTimeout(0);
            socket.setReceiveBufferSize(1024*10);
            if(socketConfigurer!=null){
                socketConfigurer.config(socket);
            }
            this.thread = new Thread(this);
            this.thread.start();
        }catch (Throwable t){
            if(socketHandler!=null){
                socketHandler.onException(t);
            }
        }
    }

    public void connect(String host,int port,SocketHandler socketHandler){
        this.connect(host,port,null,null,socketHandler);
    }

    public void run() {
        DataInputStream is = null;
        try {

            if(socket!=null){
                is = new DataInputStream(socket.getInputStream());
            }

        } catch (Throwable e) {
            e.printStackTrace();
            if(handler!=null){
                handler.onDisconnected();
            }
            return;
        }
        if(handler!=null){

            handler.onConnected(socket);
        }

        byte[] lengthFieldBytes = new byte[packetHeaderConfiguration.lengthFieldBytesCount];

        while ((is!=null)){
            try{
                byte[] header = new byte[packetHeaderConfiguration.headerBytesCount];
                is.readFully(header);
                System.arraycopy(header,packetHeaderConfiguration.lengthFieldOffSet,lengthFieldBytes,0,packetHeaderConfiguration.lengthFieldBytesCount);
                long pLen = 0;
                if(packetHeaderConfiguration.lengthFieldBytesCount==1){
                    pLen = Bytes.bytes2UnSignedByte(lengthFieldBytes);
                }else if(packetHeaderConfiguration.lengthFieldBytesCount==2){
                    pLen = Bytes.bytes2UnSignedShort(lengthFieldBytes);
                }else if(packetHeaderConfiguration.lengthFieldBytesCount==4){
                    pLen = Bytes.bytes2UnSignedInt(lengthFieldBytes);
                }
                long bodyLength = 0;
                if(packetHeaderConfiguration.lengthType== PacketHeaderConfiguration.HeaderAndBody){
                    bodyLength = pLen;
                }else if(packetHeaderConfiguration.lengthType== PacketHeaderConfiguration.OnlyBody){
                    bodyLength = pLen - packetHeaderConfiguration.headerBytesCount;
                }
                byte[] body = new byte[(int) bodyLength];
                is.readFully(body);

                if(handler!=null) {
                    handler.onMessage(header,body);
                }

            }catch (Throwable t){
                if(handler!=null){
                    handler.onException(t);
                }
                break;
            }
        }
        if(handler!=null){
            handler.onDisconnected();
        }

    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public static interface SocketHandler {
        public void onMessage(byte[] header, byte[] body);
        public void onConnected(Socket socket);
        public void onDisconnected();
        public void onException(Throwable t);
    }

    public static interface SocketConfigurer{
        public void config(Socket socket);
    }

    public static class PacketHeaderConfiguration {

        public static int HeaderAndBody = 0;
        public static int OnlyBody = 1;
        public static int Length_Field_ByteLength_1 = 1;
        public static int Length_Field_ByteLength_2 = 2;
        public static int Length_Field_ByteLength_4 = 4;

        private int headerBytesCount = 4;
        private int lengthFieldBytesCount = Length_Field_ByteLength_4;
        private int lengthType = HeaderAndBody;
        private int lengthFieldOffSet = 0;

        public int getHeaderBytesCount() {
            return headerBytesCount;
        }

        public void setHeaderBytesCount(int headerBytesCount) {
            this.headerBytesCount = headerBytesCount;
        }

        public int getLengthFieldBytesCount() {
            return lengthFieldBytesCount;
        }

        public void setLengthFieldBytesCount(int lengthFieldBytesCount) {
            this.lengthFieldBytesCount = lengthFieldBytesCount;
        }

        public int getLengthType() {
            return lengthType;
        }

        public void setLengthType(int lengthType) {
            this.lengthType = lengthType;
        }

        public int getLengthFieldOffSet() {
            return lengthFieldOffSet;
        }

        public void setLengthFieldOffSet(int lengthFieldOffSet) {
            this.lengthFieldOffSet = lengthFieldOffSet;
        }
    }

    public static void main(String[] args) {
        LengthBasedSocketClient client = new LengthBasedSocketClient();
        client.connect("192.168.16.8", 61323, new SocketHandler() {
            @Override
            public void onMessage(byte[] header, byte[] body) {
                System.out.println("receiver: =>header["+header.length+"]+body["+body.length+"]");
            }

            @Override
            public void onConnected(Socket socket) {
                System.out.println("connected: "+socket);
            }

            @Override
            public void onDisconnected() {
                System.out.println("disconnected.");
            }

            @Override
            public void onException(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
