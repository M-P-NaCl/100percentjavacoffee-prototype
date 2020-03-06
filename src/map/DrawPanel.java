package map;

import gameplay.Player;

public class DrawPanel extends Panel {

    public DrawPanel(){
        super(PanelType.DRAW);
    }

    //触发格子效果抽卡
    //未完成
    @Override
    public void activate(Player activator) {
        System.out.println(activator.getDescription()+"should draw a card!");
    }
}
