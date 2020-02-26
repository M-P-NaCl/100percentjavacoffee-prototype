package gameplay;

public class Dice {

    public static int roll(int count, int fixedResult){
        int result = 0;
        int diceroll;
        for(int i = 0; i<count; i++){
            if(fixedResult<0)
                diceroll = (int)(Math.random()*6)+1;
            else
                diceroll = fixedResult;
            result += diceroll;
            System.out.print(diceroll + " ");
        }
        System.out.println("Result: "+result);
        return result;
    }

}
