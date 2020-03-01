package map;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Panel {
    private static int totalPanelNumber = 0;//格子总数
    private int panelNumber;//每个格子的唯一序号
    private PanelType type;//格子类型
    private int homeNumber = 0;//为0代表不是HOME格或者HOME格不属于任何玩家
    private int nextPanelCount = 0;//有多少个移动前方的格子
    private ArrayList<Panel> nextPanels = new ArrayList<>();//具体的移动前方格子

    public Panel(PanelType type){
        this.type = type;
        this.panelNumber = ++totalPanelNumber;
    }

    //抽象方法，触发格子效果
    public abstract void activate();

    //添加下一个格子
    //同时也会更新格子个数
    public void addNextPanel(Panel... next){
        for (Panel eachNext: next) {
            nextPanelCount++;
            nextPanels.add(eachNext);
        }
    }

    //获得格子信息，调试用
    public String getDecription(){
        return "Panel "+ panelNumber +": "+type;
    }

    //返回下一个格子，如果下一个格子有多个（分岔路），则要求输入并选择
    public Panel nextPanel(){
        //死路时
        if(nextPanelCount == 0){return this;}

        //不为分岔路时
        if(nextPanelCount == 1){return nextPanels.get(0);}

        //分岔路
        else{
            Scanner input = new Scanner(System.in);
            System.out.println("Please Choose Your Direction:");
            int i = 0;

            //输出选项信息
            for(Panel next: getNextPanels()){
                System.out.println(i++ + " - " +next.getDecription());
            }
            System.out.print("->");

            //没有合法输入会请求输入至结束
            while(true){
                try{
                    int selection = -1;
                    try{
                        selection = input.nextInt();
                    }catch(Exception e){}

                    //去除该行剩余部分
                    input.nextLine();

                    //判断输入是否在范围内
                    if(selection > 0 && selection<= getNextPanels().size()){
                        return getNextPanels().get(selection-1);
                    }else{ throw new Exception();}
                }catch(Exception e){
                    System.out.println("Invalid Input!");
                }
            }
        }
    }

    //getter和setter
    public static int getTotalPanelNumber() {
        return totalPanelNumber;
    }

    public int getPanelNumber() {
        return panelNumber;
    }

    public PanelType getType() {
        return type;
    }

    public int getHomeNumber() {
        return homeNumber;
    }

    public int getNextPanelCount() {
        return nextPanelCount;
    }

    public ArrayList<Panel> getNextPanels() {
        return nextPanels;
    }

    public void setType(PanelType type) {
        this.type = type;
    }

    public void setHomeNumber(int homeNumber) {
        this.homeNumber = homeNumber;
    }
}
