package map;

import gameplay.Dice;
import gameplay.Player;

public class DropPanel extends Panel {
    public DropPanel(){
        super(PanelType.DROP);
    }

    //失去星星
    //未完成
    @Override
    public void activate(Player activator) {
        Dice.roll(activator.getDiceCount(),activator.getFixedDiceResult());
        System.out.println(activator.getDescription()+"should lose some stars!");
    }
}
