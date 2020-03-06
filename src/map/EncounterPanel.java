package map;

import gameplay.Player;

import java.util.ArrayList;

public class EncounterPanel extends Panel{
    public EncounterPanel(){
        super(PanelType.ENCOUNTER);
    }

    //全局NPC池
    //暂时只有测试用
    private static Player npcUPRPRC = Player.createNPCPlayer("UPRPRC",5,1,1,1);



    //触发格子效果战斗
    //未完成
    @Override
    public void activate(Player activator) {
        System.out.println(activator.getDescription()+"should battle an enemy!");

        //仅为临时用
        //以后可能会整合成正常战斗
        activator.attack(npcUPRPRC);
        if(!npcUPRPRC.isKOed()){
            npcUPRPRC.attack(activator);
        }
        if(npcUPRPRC.isKOed()){
            npcUPRPRC.silentRevive();
        }
    }
}
