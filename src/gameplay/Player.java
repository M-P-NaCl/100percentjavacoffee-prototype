package gameplay;

import card.Card;
import chara.Character;
import effect.Effect;
import effect.EffectTime;
import effect.EffectType;
import map.Panel;
import map.PanelType;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private static int totalPlayerNumber = 0;//玩家总数
    private static ArrayList<Player> players = new ArrayList<>();//总玩家

    //重置
    public static void reset() {
        totalPlayerNumber = 0;
        players.clear();
    }

    //NPC专用的构造
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
        playerNum = ++totalPlayerNumber;
        players.add(this);
    }

    public Player(String name, int maxHp, int atk, int def, int evd, int rec, Panel startPosition){
        this.character = new Character(name, maxHp, atk, def, evd, rec);
        this.hp = this.character.getMaxHp();

        this.currentPosition = startPosition;

        //分配玩家序号
        playerNum = ++totalPlayerNumber;
        players.add(this);
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
    public int checkAtkVariation(){
        int var = 0;
        for (Effect each:stockEffects) {
            if(each.getType()== EffectType.CHANGE_ATK_STAT){
                var += each.getStrength();
            }
        }
        return var;
    }

    public int checkDefVariation(){
        int var = 0;
        for (Effect each:stockEffects) {
            if(each.getType()== EffectType.CHANGE_DEF_STAT){
                var += each.getStrength();
            }
        }
        return var;
    }

    public int checkEvdVariation(){
        int var = 0;
        for (Effect each:stockEffects) {
            if(each.getType()== EffectType.CHANGE_EVD_STAT){
                var += each.getStrength();
            }
        }
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
    public int getDiceCount(){
        int diceCount = 1;
        for (Effect each:stockEffects) {
            if(each.getType()== EffectType.ADD_DICE_COUNT){
                diceCount += each.getStrength();
            }
            if(each.getType()== EffectType.MULTYPLY_DICE_COUNT){
                diceCount *= each.getStrength();
            }
        }
        return diceCount;
    }

    //检查影响骰子点数效果
    //负值代表无影响
    public int getFixedDiceResult(){
        int var = -1;
        for (Effect each:stockEffects) {
            if(each.getType()== EffectType.CHANGE_DICE_RESULT){
                var = each.getStrength();
            }
        }
        return var;
    }

    //检查影响造成伤害效果
    //对目标玩家施加临时的受伤增加效果
    public void getDamageGivenAdjustment(Player target){
        int var = 0;
        for (Effect each:stockEffects) {
            if(each.getType()== EffectType.CHANGE_DAMAGE_GIVEN){
                var += each.getStrength();
            }
        }
        if(var!=0){
            target.recieveEffect(new Effect("对方造成伤害提升",1,1,EffectType.CHANGE_DAMAGE_TAKEN,var,EffectTime.DAMAGE_CALCULATION_END));
        }
    }

    //检查影响受到伤害效果
    public int getDamageTakenAdjustment(){
        int damageAdjustment = 0;
        for (Effect each:stockEffects) {
            if(each.getType()== EffectType.CHANGE_DAMAGE_TAKEN){
                damageAdjustment += each.getStrength();
            }
        }
        return damageAdjustment;
    }

    //检查影响移动距离效果
    //未完成
    public int getMoveDistanceAdjustment(){
        int distanceAdjustment = 0;
        return distanceAdjustment;
    }

    //获得玩家当前信息
    //格式为「Player 玩家号-角色名(*HP|atk* def* evd*)」
    public String getDescription(){
        return (playerNum>0?("Player "+playerNum):"NPC")+"-"+character.getName() + "(" + getHp() + "HP|atk" + getAtk() + " def" + getDef() + " evd" + getEvd() + ")";
    }

    //获得效果
    public void recieveEffect(Effect newEffect){
        stockEffects.add(newEffect);
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

        //减少「伤害处理结束」时点效果的时间
        triggerEffect(EffectTime.DAMAGE_CALCULATION_END);
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

        //减少「伤害处理结束」时点效果的时间
        triggerEffect(EffectTime.DAMAGE_CALCULATION_END);
    }

    //伤害的处理
    //受到攻击的玩家的受伤增减的效果在这里处理
    public void takeDamage(int damage, boolean isBattleDamage){
        damage += getDamageTakenAdjustment();
        //伤害不能低于0
        if(damage<0){damage = 0;}
        hp -= damage;
        System.out.println(damage+" Damage!");

        //减少「受到伤害」时点效果的时间
        triggerEffect(EffectTime.DAMAGE_TAKEN);

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

    //恢复的处理
    public void heal(int amount){
        int currentDamage = getCharacter().getMaxHp() - getHp();
        int realHealAmount = currentDamage<amount?currentDamage:amount;
        hp += realHealAmount;
        System.out.println(getDescription()+" healed "+realHealAmount+" HP!");
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
        boolean interuptted = false;
        int distance = Dice.roll(getDiceCount(), getFixedDiceResult());
        distance += getMoveDistanceAdjustment();

        System.out.println("\t"+currentPosition.getDecription());
        if(distance<1){distance = 1;}
        for(int i=0;i<distance;i++){
            //移动格子
            currentPosition = currentPosition.nextPanel();
            System.out.println("\t->"+currentPosition.getDecription());
            try{Thread.sleep(500);}catch(Exception e){}

            //经过自己的HOME格
            if(currentPosition.getType() == PanelType.HOME && currentPosition.getHomeNumber() == playerNum){
                //询问是否主动停留
                System.out.println("Stop at HOME?\n" +
                        "(Enter \"YES\" to stop, other to continue moving)");
                System.out.print("->");
                if(input.nextLine().equals("YES")){
                    //不能break，因为还要判断经过玩家和TRAP
                    i += distance;
                }
            }

            //经过玩家时
            for(Player eachPlayer : players){
                if(eachPlayer != this && eachPlayer.currentPosition == this.currentPosition){
                    System.out.println("Stop to Battle "+eachPlayer.getDescription()+"?\n" +
                            "(Enter \"YES\" to stop, other to continue moving)");
                    System.out.print("->");
                    if(input.nextLine().equals("YES")){
                        //不能break，因为还要判断经过其它玩家和TRAP
                        i += distance;
                        GameField.battle(this,eachPlayer);
                        if(isKOed()){interuptted = true;}
                    }
                }
            }
        }

        //记录现在的位置
        Panel posBeforeTrap = currentPosition;
        //在判断TRAP卡效果时使用
        boolean moved = false;
        //如果没倒下会在这里触发TRAP卡（未完成）

        //如果位置发生变化或是倒下，则这次不触发格子效果
        if(moved || isKOed()){
            interuptted = true;
        }

        //如果没别的事情就触发格子效果
        if(!interuptted)
            currentPosition.activate(this);
    }

    //传送
    public void warpto(Panel destination){
        currentPosition = destination;
        System.out.println("Warped to "+currentPosition.getDecription()+"!");
    }

    //进行目标检测
    //未完成
    public void checkNorma(){
        System.out.println("Norma Check!");
    }

    //在对应时点触发对应效果的处理
    //暂时仅实现储备/持续效果的清理
    public void triggerEffect(EffectTime timePoint){
        stockEffects.removeIf(each -> each.consume(timePoint));
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
