package map;

import gameplay.Player;

public class DamagePanel extends Panel {
    public DamagePanel(){
        super(PanelType.DAMAGE);
    }

    @Override
    public void activate(Player activator) {
        activator.takeDamage(1,false);
    }
}
