package map;

import gameplay.Player;

public class WarpmovePanel extends Panel {
    public WarpmovePanel(){
        super(PanelType.WARPMOVE);
        Panel.getWarpzones().add(this);
    }

    //传送至随机格子并进行一次移动
    @Override
    public void activate(Player activator) {
        Panel destination = Panel.getWarpzones().get( (int)(Math.random()*Panel.getWarpzones().size()) );
        //除非只有一个格子，否则必定到其他的格子处
        if(Panel.getWarpzones().size()>1){
            //重复256次后跳出，为了避免发生没有符合条件格子而死循环的状况
            int i = 0;
            //除了不能传送到自己以外，还不能传送到道路尽头
            while((destination==this || destination.getNextPanelCount()<1)&&i++<256){
                destination = Panel.getWarpzones().get( (int)(Math.random()*Panel.getWarpzones().size()) );
            }
        }
        activator.warpto(destination);
        activator.move();
    }
}
