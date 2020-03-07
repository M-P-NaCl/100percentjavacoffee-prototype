package map;

import gameplay.Player;

public class HomePanel extends Panel {
    public HomePanel(int homeNum){
        super(PanelType.HOME);
        setHomeNumber(homeNum);
    }

    //进行目标检测
    @Override
    public void activate(Player activator) {
        activator.checkNorma();
    }

    @Override
    public String getDecription() {
        String description = super.getDecription();
        if(getHomeNumber()>0){
            description += "(Player "+getHomeNumber()+")";
        }
        return description;
    }
}
