/**
 * @author Kedar Gupta
 * @version December 2, 2014
 *
 * Contains methods and many tools for manipulating a Card object, used in
 * the Solitaire game
 */
public class Card {
    public int rank;
    public String suit;
    public boolean isFaceUp;

    /**
     * Constructs a Card object
     * @param newRank rank of the card
     * @param newSuit suit of the card
     */
    public Card(int newRank, String newSuit)
    {
        rank = newRank;
        suit = newSuit;
        isFaceUp = false;
    }

    /**
     *
     * @return rank of the Card
     */
    public int getRank()
    {
        return rank;
    }

    /**
     *
     * @return suit of the Card
     */
    public String getSuit()
    {
        return suit;
    }

    /**
     * Checks if a Card is red
     * @return true if card is red; false otherwise
     */
    public boolean isRed()
    {
        return (suit.equals("d") || suit.equals("h"));
    }

    /**
     * @return true if face up; false otherwise
     */
    public boolean isFaceUp()
    {
        return isFaceUp;
    }

    /**
     * Turns the card face up
     */
    public void turnUp()
    {
        isFaceUp = true;
    }

    /**
     * Turns the card face down
     */
    public void turnDown()
    {
        isFaceUp = false;
    }

    /**
     *Finds appropriate file name for gif corresponding to card based
     * on suit and rank
     *
     * @return name of the file for the appropriate Card
     */
    public String getFileName()
    {
        if (!isFaceUp)return "../src/cards/back.gif"; //using escape sequence, not double slash
        if (rank == 10) return "../src/cards/t" + suit + ".gif";
        if (rank == 11) return "../src/cards/j" + suit + ".gif";
        if (rank == 12) return "../src/cards/q" + suit + ".gif";
        if (rank == 13) return "../src/cards/k" + suit + ".gif";
        if (rank == 1) return "../src/cards/a" + suit + ".gif";
        return "../src/cards/" + rank + suit + ".gif";
    }
}
