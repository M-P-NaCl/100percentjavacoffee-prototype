import gameplay.GameField;
import gameplay.Player;
import map.DrawPanel;
import map.EncounterPanel;
import map.Panel;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{
        System.out.println("Orange Juice!");
        Thread.sleep(1000);
        System.out.println("And...");
        Thread.sleep(700);
        System.out.println("Fruitbat Factory!");
        Thread.sleep(2000);
        Panel testPanel1 = new DrawPanel();
        Panel testPanel2 = new DrawPanel();
        Panel testPanel3 = new EncounterPanel();
        testPanel1.addNextPanel(testPanel2,testPanel3);
        testPanel2.addNextPanel(testPanel1);
        testPanel3.addNextPanel(testPanel1);
        Player testPlayer1 = new Player(testPanel1);
        Player testPlayer2 = new Player("Nico", 4,0,0,1,5,testPanel1);

        GameField testField = new GameField();

        testField.addPlayer(testPlayer1, testPlayer2);
        testField.addPanel(testPanel1, testPanel2, testPanel3);

        testField.start();

        /*while(true){
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
        }*/
    }
}
