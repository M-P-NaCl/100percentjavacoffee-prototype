import gameplay.Player;

public class Main {

    public static void main(String[] args) throws Exception{
        System.out.println("Hello World!");
        Player testPlayer1 = new Player();
        Player testPlayer2 = new Player("Nico", 9,9,9,9,1);
        while(true){
            testPlayer1.attack(testPlayer2);
            Thread.sleep(5000);
            System.out.println();
            testPlayer2.attack(testPlayer1);
            Thread.sleep(5000);
            System.out.println();
        }
    }
}
