package effect;

public enum EffectTime{
    TURN_START,
    TURN_END,
    BATTLE_START,
    BATTLE_END,
    DAMAGE_CALCULATION_END,//仅作为造成伤害提升的实际实现而使用
    DAMAGE_TAKEN,
    CHARACTER_START
}