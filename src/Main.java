import gameplay.Player;

public class Main {

    public static void main(String[] args) throws Exception{
        System.out.println("Hello World!");
        Player testPlayer1 = new Player();
        Player testPlayer2 = new Player("Nico", 4,0,0,1,5);
        while(true){
            if(!testPlayer1.isKOed() && !testPlayer2.isKOed()){
                testPlayer1.attack(testPlayer2);
                Thread.sleep(3000);
                if(!testPlayer1.isKOed() && !testPlayer2.isKOed()){ ;
                    testPlayer2.attack(testPlayer1);
                    Thread.sleep(3000);
                }
            }else{
                if(testPlayer1.isKOed()){
                    testPlayer1.revive();
                    Thread.sleep(3000);
                }
                if(testPlayer2.isKOed()){
                    testPlayer2.revive();
                    Thread.sleep(3000);
                }
            }

        }
    }
}
