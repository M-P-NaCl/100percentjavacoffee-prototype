package chara;

import effect.Effect;

import java.util.ArrayList;

public class Character {

    private String name;
    private int atk, def, evd, rec;
    private int maxHp;

    private ArrayList<Effect> characterEffects = new ArrayList<>();

    public Character(){
        this.name = "QP";
        this.maxHp = 5;
        this.atk = 0;
        this.def = 0;
        this.evd = 0;
        this.rec = 5;
    }

    public Character(String name, int maxHp, int atk, int def, int evd, int rec){
        this.name = name;
        this.maxHp = maxHp;
        this.atk = atk;
        this.def = def;
        this.evd = evd;
        this.rec = rec;
    }

    public void addCharacterEffects(Effect newEffect){
        characterEffects.add(newEffect);
    }

    public ArrayList<Effect> getCharacterEffects() {
        return characterEffects;
    }

    public String getName() {
        return name;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getEvd() {
        return evd;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getRec() {
        return rec;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public void setEvd(int evd) {
        this.evd = evd;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void setRec(int rec) {
        this.rec = rec;
    }
}
