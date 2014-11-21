import py4j.GatewayServer;

public class Pyva {

  private Player1 pyva1;
  private Player2 pyva2;

  public Pyva() {
         pyva1 = new Player1();
         pyva2 = new Player2();
  }

  public Player1 player1() {
         return pyva1;
  }

  public Player2 player2() {
         return pyva2;
  }
       
  public static void main(String[] args) {
         Pyva pyvaPlayer = new Pyva();
         GatewayServer gatewayServer = new GatewayServer(pyvaPlayer); 
         gatewayServer.start();
         System.out.println("Pyva pipe started");        
  }
}