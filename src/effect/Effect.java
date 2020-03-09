package effect;

import java.util.ArrayList;

public class Effect {//未完成
    private String name;
    private int stock;//层数
    private int duration;//剩余时间
    private EffectType type;
    private int strength;//能力的强度，具体处理根据类型而定
    private ArrayList<EffectTime> consumeTime = new ArrayList<>();

    public Effect(String name, int stock, int duration, EffectType type, int strength, EffectTime... consumeTime){
        this.name = name;
        this.stock = stock;
        this.duration = duration;
        this.type = type;
        this.strength = strength;
        for(EffectTime each: consumeTime)
            this.consumeTime.add(each);
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public int getDuration() {
        return duration;
    }

    public EffectType getType() {
        return type;
    }

    public int getStrength() {
        return strength*stock;
    }

    //在对应的时点消耗持续时长，返回值为是否耗尽
    public boolean consume(EffectTime timePoint){
        if(consumeTime.indexOf(timePoint)>0){
            duration--;
        }
        return duration<=0;//消耗所有持续时长时返回true，使player清除效果
    }
}
