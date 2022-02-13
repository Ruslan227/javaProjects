package info.kgeorgiy.ja.aliev.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class HelloUDPServer implements HelloServer {
    ExecutorService mainThread;
    ThreadPoolExecutor threadPool;
    DatagramSocket datagramSocket;

    public static void main(String[] args) {
        int port = HelloService.getServerNumberArgument(args[1]);
        int threads = HelloService.getServerNumberArgument(args[2]);
        new HelloUDPServer().start(port, threads);
    }

    /**
     * @param port    server port.
     * @param threads number of working threads.
     */
    @Override
    public void start(int port, int threads) {
        try {
            datagramSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.err.println("Socket connection failed\n\t" + e.getMessage());
            return;
        }

        if (threads != 1) {
            var arrayBlockingQueue = new ArrayBlockingQueue<Runnable>(HelloService.SIZE);
            threadPool = new ThreadPoolExecutor(
                    threads, threads, 1, TimeUnit.MINUTES, arrayBlockingQueue,
                    new ThreadPoolExecutor.DiscardPolicy()
            );
        }
        mainThread = Executors.newSingleThreadExecutor();
        mainThread.submit(() -> getTask(datagramSocket));
    }

    private void getSmallTask(DatagramPacket requestPacket) {
        byte[] emptyData = new byte[0];
        String requestStr = new String(requestPacket.getData(), requestPacket.getOffset(),
                requestPacket.getLength(), StandardCharsets.UTF_8);
        DatagramPacket responsePacket = new DatagramPacket(
                emptyData, 0, requestPacket.getSocketAddress());
        responsePacket.setData((HelloService.prefix + requestStr).getBytes(StandardCharsets.UTF_8));
        HelloService.sendPacket(datagramSocket, responsePacket);
    }

    private void getTask(DatagramSocket datagramSocket) {
        while (!Thread.currentThread().isInterrupted() && !datagramSocket.isClosed()) {
            try {
                byte[] requestData = new byte[datagramSocket.getReceiveBufferSize()];
                DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length);
                HelloService.receivePacket(datagramSocket, requestPacket);
                if (threadPool != null)
                    threadPool.execute(() -> getSmallTask(requestPacket));
                else
                    getSmallTask(requestPacket);
            } catch (SocketException e) {
                System.err.println("Socket connection failed " + e.getMessage());
            }
        }
    }

    @Override
    public void close() {
        datagramSocket.close();
        HelloService.shutdownAndAwaitTermination(threadPool);
        HelloService.shutdownAndAwaitTermination(mainThread);
    }
}
