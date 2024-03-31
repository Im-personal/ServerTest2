public class Main {

    public static void main(String[] args) {
        Server s = new Server(1234);
        s.start();

        new Servlet(8080);
    }

}
