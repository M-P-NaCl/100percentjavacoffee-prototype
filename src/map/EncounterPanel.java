package map;

import gameplay.GameField;
import gameplay.Player;

import java.util.ArrayList;

public class EncounterPanel extends Panel{

    public EncounterPanel(){
        super(PanelType.ENCOUNTER);
    }

    //全局NPC池
    //暂时只有测试用
    private static ArrayList<Player> enemys = new ArrayList<>();
    static{
        enemys.add(Player.createNPCPlayer("UPRPRC",3,-1,-1,-1));
        enemys.add(Player.createNPCPlayer("Cocoa",3,-1,1,-1));
        enemys.add(Player.createNPCPlayer("Ribbon",3,-1,-1,1));
        enemys.add(Player.createNPCPlayer("Ashuri",3,1,-1,-1));
    }
    //Player npcUPRPRC = Player.createNPCPlayer("UPRPRC",3,1,1,1);



    //触发格子效果战斗
    //未完成
    @Override
    public void activate(Player activator) {
        System.out.println(activator.getDescription()+"should battle an enemy!");

        Player selectedNPC = enemys.get( (int)(Math.random()*enemys.size()) );
        //仅为临时用
        GameField.battle(activator,selectedNPC);
        if(selectedNPC.isKOed()){
            selectedNPC.silentRevive();
        }
    }
}
