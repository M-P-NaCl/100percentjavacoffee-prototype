import effect.Effect;
import effect.EffectTime;
import effect.EffectType;
import gameplay.GameField;
import gameplay.Player;
import map.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{
        System.out.println("Orange Juice!");
        Thread.sleep(1000);
        System.out.println("And...");
        Thread.sleep(700);
        System.out.println("Fruitbat Factory!");
        Thread.sleep(2000);

        ArrayList<Panel> testPanels = new ArrayList<>();
        int[] panelList = {5,1,2,10,3,7,6,5,10,4,8,9};
                /*PanelType.WARP,
                PanelType.BONUS,
                PanelType.DROP,
                PanelType.HOME,
                PanelType.DRAW,
                PanelType.WARPMOVE,
                PanelType.MOVE,
                PanelType.WARP,
                PanelType.HOME,
                PanelType.ENCOUNTER,
                PanelType.HEAL,
                PanelType.DAMAGE*/
        int homeNum = 0;
        for(int each : panelList){
            Panel newPanel;
            switch(each){
                case 1:
                    newPanel = new BonusPanel();
                    break;
                case 2:
                    newPanel = new DropPanel();
                    break;
                case 3:
                    newPanel = new DrawPanel();
                    break;
                case 4:
                    newPanel = new EncounterPanel();
                    break;
                case 5:
                    newPanel = new WarpPanel();
                    break;
                case 6:
                    newPanel = new MovePanel();
                    break;
                case 7:
                    newPanel = new WarpmovePanel();
                    break;
                case 8:
                    newPanel = new HealPanel();
                    break;
                case 9:
                    newPanel = new DamagePanel();
                    break;
                case 10:
                    newPanel = new HomePanel(++homeNum);
                    break;
                case 0:
                    newPanel = new EmptyPanel();
                    break;
                default:
                    newPanel = new EmptyPanel();
            }
            testPanels.add(newPanel);
        }

        int[][] panelRelation = {{1},{2},{3,8,10},{4},{5},{6},{7},{},{9},{5},{11},{5}};

        for(int i=0;i<panelRelation.length;i++){
            for(int each: panelRelation[i]){
                testPanels.get(i).addNextPanel(testPanels.get(each));
            }
        }

        Player testPlayer1 = new Player(testPanels.get(0));
        Player testPlayer2 = new Player("Nico", 4,0,0,1,5,testPanels.get(0));

        testPlayer2.recieveEffect(new Effect("Nico is kawaii", 1, 999, EffectType.CHANGE_DAMAGE_GIVEN,3));
        testPlayer1.recieveEffect(new Effect("QP is kawaii too", 1, 1, EffectType.CHANGE_DAMAGE_TAKEN,-1, EffectTime.DAMAGE_TAKEN));

        GameField testField = new GameField();

        testField.addPlayer(testPlayer1, testPlayer2);
        testField.addPanel(testPanels);

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
