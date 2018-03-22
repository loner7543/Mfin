package model.Auxiliary;

public class CounterNumerals
{
    public static int counterNumerals(double number)
    {
        int count=1;
        int numerals=(int)number/10;
        while(numerals!=0)
        {
            numerals/=10;
            count++;
        }
        return count;
    }
}
