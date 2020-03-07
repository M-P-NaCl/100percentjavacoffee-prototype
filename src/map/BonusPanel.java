package map;

import gameplay.Dice;
import gameplay.Player;

public class BonusPanel extends Panel {
    public BonusPanel(){
        super(PanelType.BONUS);
    }

    //获得星星
    //未完成
    @Override
    public void activate(Player activator) {
        Dice.roll(activator.getDiceCount(),activator.getFixedDiceResult());
        System.out.println(activator.getDescription()+"should get some stars!");
    }
}
