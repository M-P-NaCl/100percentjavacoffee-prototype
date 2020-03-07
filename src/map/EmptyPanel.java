package map;

import gameplay.Player;

public class EmptyPanel extends Panel{
    public EmptyPanel(){
        super(PanelType.EMPTY);
    }

    @Override
    public void activate(Player activator) {
        //什么都不会发生
    }
}
