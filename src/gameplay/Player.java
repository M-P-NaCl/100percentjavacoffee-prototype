package gameplay;

import card.Card;
import chara.Character;
import effect.Effect;
import effect.EffectTime;

import java.util.ArrayList;

public class Player {

    private Character character;
    private int playerNum;
    private int hp;

    private ArrayList<Effect> stockEffects = new ArrayList<>();
    private ArrayList<Card> hand = new ArrayList<>();

    public Player(){//未完成
        this.character = new Character();
        this.hp = this.character.getMaxHp();
    }

    public Player(String name, int maxHp, int atk, int def, int evd, int rec){//未完成
        this.character = new Character(name, maxHp, atk, def, evd, rec);
        this.hp = this.character.getMaxHp();
    }

    public int checkAtkVariation(){//未完成
        int var = 0;
        return var;
    }//未完成

    public int checkDefVariation(){//未完成
        int var = 0;
        return var;
    }//未完成

    public int checkEvdVariation(){//未完成
        int var = 0;
        return var;
    }//未完成

    public int getHp(){
        return hp;
    }
    
    public int getAtk(){
        int atkStat = 0;
        atkStat += character.getAtk();
        atkStat += checkAtkVariation();
        return atkStat;
    }

    public int getDef(){
        int defStat = 0;
        defStat += character.getDef();
        defStat += checkDefVariation();
        return defStat;
    }

    public int getEvd(){
        int evdStat = 0;
        evdStat += character.getEvd();
        evdStat += checkEvdVariation();
        return evdStat;
    }

    public Character getCharacter() {
        return character;
    }

    public int getDiceCount(){//未完成
        int diceCount = 1;
        return diceCount;
    }//未完成

    public int getFixedDiceResult(){//未完成
        return -1;
    }//未完成

    public String getDescription(){
        return character.getName() + "(" + getHp() + "HP|atk" + getAtk() + " def" + getDef() + " evd" + getEvd() + ")";
    }

    public void attack(Player target){
        System.out.println(getDescription() + "\'s Attack!");
        int atkPoint = getAtk()+Dice.roll(getDiceCount(), getFixedDiceResult());
        if(atkPoint<1){atkPoint = 1;}
        System.out.println("The Attack Point is "+atkPoint+"!");
        target.tryDefend(atkPoint);
    }

    public void tryDefend(int atkPoint){//未完成，暂时仅防御
        defend(atkPoint);
    }//未完成，暂时仅防御

    public void defend(int atkPoint){
        System.out.println(getDescription() + "\'s Defend!");
        int defPoint = getDef()+Dice.roll(getDiceCount(), getFixedDiceResult());
        if(defPoint<1){defPoint = 1;}
        System.out.println("The Defend Point is "+defPoint+"!");
        int damage = atkPoint-defPoint>0 ? atkPoint-defPoint : 1;
        takeDamage(damage);
    }

    public void takeDamage(int damage){
        hp -= damage;
        System.out.println(damage+" Damage!");
    }

    public void triggerEffect(EffectTime timePoint){//未完成

    }//未完成

    public void saySomething(){//测试用
        System.out.println("What the fu");
    }//测试用

}
