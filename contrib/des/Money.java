/** 
    A CountableResource with a cute toString() to print it nicely.  Money is assumed to be countable 
    and thus have a minimum atomic value: for example the value "1" for US Dollars would be 1 penny,
    whereas a dollar would be 100. 
*/

public class Money extends CountableResource
    {
    /** Produces a unique and new type of money, with an initial amount and a currency symbol. */
    public Money(String currencySymbol, int initialAmount)
        {
        super(currencySymbol, initialAmount);
        }
        
    /** Copies a money resource from another, including the amount, currency symbol, and type. */
    public Money(Money other)
        {
        super(other);
        }

    /** Copies a money resource from another, including currency symbol and type, but setting a new amount. */
    public Money(Money other, double amount)
        {
        super(other, amount);
        }
                
    /**
       Prints the resource out in a pleasing manner. 
    */
    public String toString()
        {
        long amt = (long)amount;
        return name + (amt / 100L) + "." + (amt % 100L);
        }
                
    /** Copies a money resource from another, including the amount, currency symbol, and type. */
    public Money duplicate()
        {
        return new Money(this);
        }
    }
