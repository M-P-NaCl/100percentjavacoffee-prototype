package map;

import gameplay.Player;

public class MovePanel extends Panel {
    public MovePanel(){
        super(PanelType.MOVE);
    }

    //进行一次移动
    @Override
    public void activate(Player activator) {
        activator.move();
    }
}
