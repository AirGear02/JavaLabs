import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Arrays;
import java.util.List;


public class Server  {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Server() {
        try {
            serverSocket = new ServerSocket(1001);
            System.out.println(serverSocket);
        } catch (IOException e) {
            fail(e, "Could not start server");
        }
        this.run();
    }

    public static void fail(Exception e, String str) {
        System.out.println(str + ". " + e.getMessage());
    }

    public void run() {
        try {
            while (true) {
                clientSocket = serverSocket.accept();
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("Created new connection");
                String text = in.readLine();
                System.out.println("Received: " + text);
                if (text != null) {
                    for (String word : getSymmetricWords(text)) {
                        out.println(word);
                    }
                }
                out.close();
                System.out.println("Connection was closed");
            }
        } catch (IOException e) {
            fail(e, "Not listening");
        }
    }

    public List<String> getSymmetricWords(String text) {
        return Arrays.stream(text.split(" ")).filter(this::isPalindrome).toList();
    }

    public boolean isPalindrome(String word) {
        boolean isPalindrome = true;
        int wordLength = word.length();
        for (int i = 0; i < wordLength / 2; i++) {
            if (word.charAt(i) != word.charAt(wordLength - 1 - i)) {
                isPalindrome = false;
                break;
            }
        }
        return isPalindrome;
    }

    public static void main(String[] args) {
        Server server = new Server();
    }
}
