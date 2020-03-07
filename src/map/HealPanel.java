package map;

import gameplay.Player;

public class HealPanel extends Panel {
    public HealPanel(){
        super(PanelType.HEAL);
    }

    @Override
    public void activate(Player activator) {
        activator.heal(1);
    }
}
