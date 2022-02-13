package info.kgeorgiy.ja.aliev.hello;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloService {
    public static final String ERROR_MESSAGE_CLIENT_USAGE = "usage: HelloUDPClient ip" +
            " port requests parallel_threads_amount requests_per_thread";
    public static final String ERROR_MESSAGE_SERVER_USAGE = "HelloUDPServer port parallel_threads_amount";
    public static final String prefix = "Hello, ";
    public static int SIZE = 5_000_000;

    private static int getNumberArgument(String arg, String customMessage) {
        int res = -1;
        try {
            res = Integer.parseInt(arg);
            if (res < 1) {
                throw new IllegalArgumentException("all number values must be positive");
            }
        } catch (NumberFormatException e) {
            System.err.println(customMessage);
        }
        return res;
    }

    public static int getClientNumberArgument(String arg) {
        return getNumberArgument(arg, ERROR_MESSAGE_CLIENT_USAGE);
    }

    public static int getServerNumberArgument(String arg) {
        return getNumberArgument(arg, ERROR_MESSAGE_SERVER_USAGE);
    }

    public static void sendPacket(DatagramSocket datagramSocket, DatagramPacket datagramPacket) {
        try {
            datagramSocket.send(datagramPacket);
        } catch (PortUnreachableException e) {
            System.err.println("illegal port while sending package: \n\t" + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O while sending package: \n\t" + e.getMessage());
        }
    }

    public static void receivePacket(DatagramSocket datagramSocket, DatagramPacket datagramPacket) {
        try {
            datagramSocket.receive(datagramPacket);
        } catch (SocketTimeoutException e) {
            System.err.println("timeout has expired while receiving package: \n\t" + e.getMessage());
        } catch (PortUnreachableException e) {
            System.err.println("illegal port while receiving package: \n\t" + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O while receiving package: \n\t" + e.getMessage());
        }
    }

    public static void shutdownAndAwaitTermination(ExecutorService pool) {
        if (pool == null)
            return;
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(10, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            pool.shutdownNow();
        }
    }
}
