package gameplay;

import card.Card;
import chara.Character;
import effect.Effect;
import effect.EffectTime;
import map.Panel;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private static int totalPanelNumber = 0;//玩家总数

    private Scanner input = new Scanner(System.in);

    private Character character;
    private int playerNum;
    private int hp;

    //对于手动与自动的影响
    private boolean autoDecition = false;

    private Panel currentPosition;//未完成

    private int reviveCount = 0;
    private boolean isKOed = false;
    private ArrayList<Effect> stockEffects = new ArrayList<>();
    private ArrayList<Card> hand = new ArrayList<>();

    //生成方法暂时均仅为测试用
    public Player(Panel startPosition){
        this.character = new Character();
        this.hp = this.character.getMaxHp();

        this.currentPosition = startPosition;

        //分配玩家序号
        playerNum = ++totalPanelNumber;
    }

    public Player(String name, int maxHp, int atk, int def, int evd, int rec, Panel startPosition){
        this.character = new Character(name, maxHp, atk, def, evd, rec);
        this.hp = this.character.getMaxHp();

        this.currentPosition = startPosition;

        //分配玩家序号
        playerNum = ++totalPanelNumber;
    }

    //NPC的构造
    private Player(String name, int maxHp, int atk, int def, int evd){
        this.character = new Character(name, maxHp, atk, def, evd, 0);
        this.hp = this.character.getMaxHp();
        //NPC暂不存在更多信息
    }

    //创建NPC玩家
    //NPC与普通玩家不同处理
    //未完成
    public static Player createNPCPlayer(String name, int maxHp, int atk, int def, int evd){
        Player newNPC = new Player(name,maxHp,atk,def,evd);
        newNPC.toggleAutoMode();
        //此处添加效果处理NPC的星星与胜场问题
        return newNPC;
    }

    //创建电脑玩家
    //区别在于自动行动
    //AI什么的不存在的
    public static Player createCPUPlayer(String name, int maxHp, int atk, int def, int evd, int rec, Panel startPosition){
        Player newCPU = new Player(name,maxHp,atk,def,evd,rec,startPosition);
        newCPU.toggleAutoMode();
        return newCPU;
    }

    //获取倒下状态
    public boolean isKOed(){return isKOed;}

    //检测影响攻防闪效果
    //未完成
    public int checkAtkVariation(){
        int var = 0;
        return var;
    }

    public int checkDefVariation(){
        int var = 0;
        return var;
    }

    public int checkEvdVariation(){
        int var = 0;
        return var;
    }

    //获取当前HP与攻防闪信息
    //使用基础值+变化值进行计算（当前HP除外）
    public int getHp(){
        return hp;
    }
    
    public int getAtk(){
        int atkStat = character.getAtk();
        atkStat += checkAtkVariation();
        return atkStat;
    }

    public int getDef(){
        int defStat = character.getDef();
        defStat += checkDefVariation();
        return defStat;
    }

    public int getEvd(){
        int evdStat = character.getEvd();
        evdStat += checkEvdVariation();
        return evdStat;
    }

    //character的getter方法，预留
    public Character getCharacter() {
        return character;
    }

    //检查影响骰子个数效果
    //未完成
    public int getDiceCount(){
        int diceCount = 1;
        return diceCount;
    }

    //检查影响骰子点数效果
    //负值代表无影响
    //未完成
    public int getFixedDiceResult(){
        return -1;
    }

    //检查影响造成伤害效果
    //对目标玩家施加临时的受伤增加效果
    //未完成
    public void getDamageGivenAdjustment(Player target){

    }

    //检查影响受到伤害效果
    //未完成
    public int getDamageTakenAdjustment(){
        int damageAdjustment = 0;
        return damageAdjustment;
    }

    //检查影响移动距离效果
    //未完成
    public int getMoveDistanceAdjustment(){
        int distanceAdjustment = 0;
        return distanceAdjustment;
    }

    //获得玩家当前信息
    //格式为「角色名(*HP|atk* def* evd*)」
    public String getDescription(){
        return character.getName() + "(" + getHp() + "HP|atk" + getAtk() + " def" + getDef() + " evd" + getEvd() + ")";
    }

    //对对应玩家发动战斗中的攻击
    //在这里投掷骰子并且检测增加造成伤害的效果
    public void attack(Player target){
        System.out.println();
        System.out.println(getDescription() + "\'s Attack!");
        //计算基础攻击点数
        int atkPoint = getAtk()+Dice.roll(getDiceCount(), getFixedDiceResult());
        //若不足1变为1
        if(atkPoint<1){atkPoint = 1;}
        System.out.println("The Attack Point is "+atkPoint+"!");
        //检测造成伤害变化的效果并给予对方对应效果
        getDamageGivenAdjustment(target);
        //调用攻击对象的防御方法
        target.tryDefend(atkPoint);
    }

    //受到攻击的玩家作出反应
    //在这里选择防御或者闪避
    //未完成强制防御或闪避的效果
    public void tryDefend(int atkPoint){
        if(autoDecition){
            if(atkPoint<=getEvd()+(int)(Math.random()*2) || getHp()==1){
                evade(atkPoint);
            }else{
                defend(atkPoint);
            }
            return;
        }
        System.out.println();
        System.out.println(getDescription());
        System.out.print("(D - Defend, E - Evade)-> ");

        //不做出有效选择不会退出循环
        boolean selected = false;
        while(!selected){
            String command = input.nextLine();
            switch (command){
                case "D":
                    selected = true;
                    defend(atkPoint);
                    break;
                case "E":
                    selected = true;
                    evade(atkPoint);
                    break;
                default:
                    System.out.print("Invalid Input! Please try again-> ");

            }
        }
    }

    //选择防御时的处理
    public void defend(int atkPoint){
        System.out.println();
        System.out.println(getDescription() + "\'s Defend!");
        //计算基础防御点数
        int defPoint = getDef()+Dice.roll(getDiceCount(), getFixedDiceResult());
        //若低于1变为1
        if(defPoint<1){defPoint = 1;}
        System.out.println("The Defend Point is "+defPoint+"!");
        //计算基础伤害
        int damage = atkPoint-defPoint>0 ? atkPoint-defPoint : 1;
        //调用伤害处理方法，且为战斗伤害
        takeDamage(damage, true);
    }

    //选择闪避时的处理
    public void evade(int atkPoint){
        System.out.println();
        System.out.println(getDescription() + "\'s Evade!");
        //计算基础防御点数
        int evdPoint = getEvd()+Dice.roll(getDiceCount(), getFixedDiceResult());
        //若低于1变为1
        if(evdPoint<1){evdPoint = 1;}
        System.out.println("The Evade Point is "+evdPoint+"!");
        //若闪避失败，调用伤害处理方法，且为战斗伤害
        if(atkPoint >= evdPoint){
            takeDamage(atkPoint, true);
        }
        //闪避成功时，以后可能会添加对应效果
        else{
            System.out.println("MISS!!");
        }

    }

    //伤害的处理
    //受到攻击的玩家的受伤增减的效果在这里处理
    public void takeDamage(int damage, boolean isBattleDamage){
        damage += getDamageTakenAdjustment();
        hp -= damage;
        System.out.println(damage+" Damage!");
        //若HP变为0或以下则处理击倒
        if(hp<=0)
            knockOut(isBattleDamage);

    }

    //被击倒的处理
    public void knockOut(boolean isBattleKO){
        //状态变为已倒下
        isKOed = true;
        //HP归零
        hp = 0;
        //REC重置为0
        reviveCount = 0;
        //根据击倒类型处理，未完成
        if(isBattleKO){
            System.out.println("Battle KO!");
        }else{
            System.out.println("KO!");
        }
    }

    //复活
    public void revive(){
        //即使大于6也暂时视为6
        int reviveNeed = character.getRec()-reviveCount>6 ? 6 : character.getRec()-reviveCount;
        System.out.println();
        System.out.println("Get "+reviveNeed+" or more to recover");
        int reviveDice = Dice.roll(getDiceCount(), getFixedDiceResult());
        if(reviveDice >= reviveNeed){
            System.out.println("Recovered");
            isKOed = false;
            hp = character.getMaxHp();
        }else {
            System.out.println("Recover Failed");
            reviveCount++;
        }
    }

    //隐藏直接复活
    public void silentRevive(){
        isKOed = false;
        hp = character.getMaxHp();
    }

    //移动
    //未完成?
    public void move(){
        int distance = Dice.roll(getDiceCount(), getFixedDiceResult());
        distance += getMoveDistanceAdjustment();
        if(distance<1){distance = 1;}
        for(int i=0;i<distance;i++){
            //移动格子
            currentPosition = currentPosition.nextPanel();
        }
        currentPosition.activate(this);
    }

    //在对应时点触发对应效果的处理
    //未完成
    public void triggerEffect(EffectTime timePoint){

    }

    //切换玩家自动行动的状态
    public void toggleAutoMode(){
        autoDecition = !autoDecition;
    }

    //测试用
    public void saySomething(){//测试用
        System.out.println("What the fuc");
    }

}
