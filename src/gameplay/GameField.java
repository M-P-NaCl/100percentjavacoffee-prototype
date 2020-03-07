package gameplay;

import map.Panel;

import java.util.ArrayList;
import java.util.Scanner;

public class GameField {

    //战斗
    //未完成
    public static void battle(Player firstPlayer,Player secondPlayer){
        //将在此处添加进入战斗时效果的检查
        if(!firstPlayer.isKOed()&&!secondPlayer.isKOed())
            firstPlayer.attack(secondPlayer);
        new Scanner(System.in).nextLine();

        if(!firstPlayer.isKOed()&&!secondPlayer.isKOed())
            secondPlayer.attack(firstPlayer);
        new Scanner(System.in).nextLine();
    }


    //游戏中的玩家
    private ArrayList<Player> players = new ArrayList<>();
    //地图
    private ArrayList<Panel> map = new ArrayList<>();
    private Scanner input = new Scanner(System.in);

    //getter
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Panel> getMap() {
        return map;
    }

    //添加玩家
    public void addPlayer(Player... newPlayers){
        for(Player player:newPlayers)
            players.add(player);
    }

    //地图有关代码仍未实现
    //暂时使用单独添加格子的方式代替
    public void addPanel(Panel... newPanels){
        for(Panel panel:newPanels)
            map.add(panel);
    }
    public void addPanel(ArrayList<Panel> panelList){
        for(Panel panel:panelList)
            map.add(panel);
    }


    public void start(){
        if(players.size()<1){
            System.out.println("No Players!");
        }
        if(map.size()<1){
            System.out.println("No Panels!");
        }
        while(true){
            //玩家轮流行动
            for(Player turnplayer:players){
                System.out.println();
                System.out.print(turnplayer.getDescription()+"'s turn!");
                input.nextLine();
                //倒了就复活
                if(turnplayer.isKOed())
                    turnplayer.revive();
                else{
                    //进入回合，暂时以进行移动代替
                    turnplayer.move();
                }
            }
        }
    }
}
