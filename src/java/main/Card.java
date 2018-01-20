/**
 * @author Kedar Gupta
 * @version December 2, 2014
 *
 * Contains methods and many tools for manipulating a Card object, used in
 * the Solitaire game
 */
public class Card {
  public boolean isFaceUp;
  public int rank;
  public String suit;

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
   *Finds appropriate file name for gif corresponding to card based
   * on suit and rank
   *
   * @return name of the file for the appropriate Card
   */
  public String getFileName()
  {
    if (!isFaceUp)
    {
      return "cards/back.gif"; //using escape sequence, not double slash
    }
    if (rank == 10) {
      return "cards/t" + suit + ".gif";
    }
    if (rank == 11) {
      return "cards/j" + suit + ".gif";
    }
    if (rank == 12) {
      return "cards/q" + suit + ".gif";
    }
    if (rank == 13) {
      return "cards/k" + suit + ".gif";
    }
    if (rank == 1) {
      return "cards/a" + suit + ".gif";
    }
    return "cards/" + rank + suit + ".gif";
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
   * @return true if face up; false otherwise
   */
  public boolean isFaceUp()
  {
    return isFaceUp;
  }

  /**
   * Checks if a Card is red
   * @return true if card is red; false otherwise
   */
  public boolean isRed()
  {
    return suit.equals("d") || suit.equals("h");
  }

  /**
   * Turns the card face down
   */
  public void turnDown()
  {
    isFaceUp = false;
  }

  /**
   * Turns the card face up
   */
  public void turnUp()
  {
    isFaceUp = true;
  }
}
