package info.kgeorgiy.ja.aliev.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloUDPClient implements HelloClient {

    public static void main(String[] args) {
        if (args.length != 6) {
            throw new IllegalArgumentException(HelloService.ERROR_MESSAGE_CLIENT_USAGE);
        }
        String hostName = args[0];
        int port = HelloService.getClientNumberArgument(args[1]);
        String requests = args[2];
        int threadsAmount = HelloService.getClientNumberArgument(args[3]);
        int requestsPerThread = HelloService.getClientNumberArgument(args[4]);

        new HelloUDPClient().run(hostName, port, requests, threadsAmount, requestsPerThread);
    }

    /**
     * @param host     server host
     * @param port     server port
     * @param prefix   request prefix
     * @param threads  number of request threads
     * @param requests number of requests per thread.
     */
    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        ExecutorService threadPool = Executors.newFixedThreadPool(threads);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);

        for (int threadNum = 0; threadNum < threads; threadNum++) {
            int finalThreadNum = threadNum;

            threadPool.submit(() -> {
                try (DatagramSocket datagramSocket = new DatagramSocket()) {
                    datagramSocket.setSoTimeout(1000);

                    byte[] responseData = new byte[datagramSocket.getReceiveBufferSize()];
                    DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);

                    for (int requestNum = 0; requestNum < requests; requestNum++) {
                        String requestStr = String.format("%s%d_%d", prefix, finalThreadNum, requestNum);
                        byte[] requestData = requestStr.getBytes(StandardCharsets.UTF_8);
                        DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, inetSocketAddress);
                        String responseStr = "";

                        while (!validateResponseStr(responseStr, requestStr)) {
                            HelloService.sendPacket(datagramSocket, requestPacket);
                            HelloService.receivePacket(datagramSocket, responsePacket);

                            responseStr = new String(responsePacket.getData(),
                                    responsePacket.getOffset(), responsePacket.getLength(), StandardCharsets.UTF_8);

                        }
                        System.out.println(responseStr);
                    }

                } catch (SocketException e) {
                    System.err.println("DatagramSocket initialization: \n\t" + e.getMessage());
                }
            });
        }
        HelloService.shutdownAndAwaitTermination(threadPool);
    }

    private boolean validateResponseStr(String responseStr, String requestStr) {
        return responseStr.equals(HelloService.prefix + requestStr);
    }
}