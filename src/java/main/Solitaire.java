/**
 * @author Kedar Gupta
 * @version December 2, 2014
 *
 * Contains methods for a complete, playable Solitaire game. Utilizing the
 * JFrame library and the specific data structures of Lists and Stacks,
 * this project allows the user to click through a game of Solitaire and
 * hopefully win.
 */
import java.util.ArrayList;
import java.util.Stack;

public class Solitaire
{
  public static void main(String[] args)
  {
    new Solitaire();
  }

  private final SolitaireDisplay display;
  private final Stack<Card>[] foundations;
  private final Stack<Card>[] piles;
  private Stack<Card> stock;
  private final Stack<Card> waste;

  public Solitaire()
  {
    foundations = new Stack[4];
    for (int i = 0; i < foundations.length; i++)
    {
      foundations[i] = new Stack();
    }
    piles = new Stack[7];
    for (int i = 0; i < piles.length; i++)
    {
      piles[i] = new Stack();
    }
    stock = new Stack<>();
    waste = new Stack<>();
    display = new SolitaireDisplay(this);
    stock = createStock();
    deal();
  }

  /**
   * Creates a "Deck" of Cards
   * @return ArrayList containing 52 standard cards
   */
  public Stack<Card> createStock()
  {
    final ArrayList<Card> cards = new ArrayList<>();
    for (int i = 1; i <= 4; i++)
    {
      for (int k = 1; k <= 13; k++)
      {
        String suit = "";
        if (i == 1) {
          suit = "h";
        }
        if (i == 2) {
          suit = "d";
        }
        if (i == 3) {
          suit = "c";
        }
        if (i == 4) {
          suit = "s";
        }
        final Card temp = new Card(k, suit);
        cards.add(temp);
      }
    }
    final Stack<Card> stock = new Stack<>();
    while(cards.size() != 0)
    {
      if (cards.size() == 1) {
        stock.push(cards.remove(0));
      } else
      {
        final int random = (int)(Math.random()*cards.size());
        stock.push(cards.remove(random));
      }
    }
    return stock;
  }

  /**
   * Deals the cards to the piles
   */
  public void deal()
  {
    for (int i = 0; i < piles.length; i++)
    {
      int counter = 0;
      piles[i] = new Stack<>();
      while (counter != i + 1)
      {
        final Card temp = stock.pop();
        piles[i].push(temp);
        counter++;
      }
      piles[i].peek().turnUp();
    }
  }

  /**
   * Deals three cards from stock to waste pile, turning each one up
   */
  public void dealThreeCards()
  {
    for (int i = 0; i < 3; i++)
    {
      if (! stock.isEmpty())
      {
        final Card temp = stock.pop();
        waste.push(temp);
        temp.turnUp();
      }
    }
  }

  //precondition:  0 <= index < 4
  //called when given foundation is clicked
  public void foundationClicked(int index)
  {
    System.out.println("foundation #" + index + " clicked");
    if (display.isWasteSelected())
    {
      if (canAddToFoundation(waste.peek(), index))
      {
        final Card temp = waste.pop();
        foundations[index].push(temp);
        display.unselect();
      }
    }
    if (display.isPileSelected())
    {
      final Stack<Card> selectedPile = piles[display.selectedPile()];
      if (canAddToFoundation(selectedPile.peek(), index))
      {
        final Card temp = selectedPile.pop();
        foundations[index].push(temp);
        if (! selectedPile.isEmpty()) {
          selectedPile.peek().turnUp();
        }
        display.unselect();
      }

    }
  }

  //precondition:  0 <= index < 4
  //postcondition: returns the card on top of the given
  //               foundation, or null if the foundation
  //               is empty
  public Card getFoundationCard(int index)
  {
    if (foundations[index].isEmpty()) {
      return null;
    }
    return foundations[index].peek();
  }

  //precondition:  0 <= index < 7
  //postcondition: returns a reference to the given pile
  public Stack<Card> getPile(int index)
  {
    return piles[index];
  }

  //returns the card on top of the stock,
  //or null if the stock is empty
  public Card getStockCard()
  {
    if (stock.size() == 0) {
      return null;
    }
    return stock.peek();
  }

  //returns the card on top of the waste,
  //or null if the waste is empty
  public Card getWasteCard()
  {
    if (waste.size() == 0) {
      return null;
    }
    return waste.peek();
  }

  //precondition:  0 <= index < 7
  //called when given pile is clicked
  public void pileClicked(int index)
  {
    System.out.println("pile #" + index + " clicked");
    if (display.isWasteSelected()) {
      final Card temp = waste.peek();
      if (canAddToPile(temp, index))
      {
        piles[index].push(waste.pop());
        piles[index].peek().turnUp();
      }
      display.unselect();
      display.selectPile(index);
    }
    else if (display.isPileSelected())
    {
      final int oldPile = display.selectedPile();
      if (index != oldPile)
      {
        final Stack<Card> temp = removeFaceUpCards(oldPile);
        if (canAddToPile(temp.peek(), index))
        {
          addToPile(temp, index);if (!piles[oldPile].isEmpty()) {
            piles[oldPile].peek().turnUp();
          }

          display.unselect();
        }
        else
        {
          addToPile(temp, oldPile);
          display.unselect();
          display.selectPile(index);

        }
      } else {
        display.unselect();
      }
    }
    else
    {
      display.selectPile(index);
      piles[index].peek().turnUp();
    }
  }

  /**
   * Transfers all cards from waste pile back to stock pile, in order
   */
  public void resetStock()
  {
    while (! waste.isEmpty())
    {
      final Card temp = waste.pop();
      temp.turnDown();
      stock.push(temp);
    }
  }

  //called when the stock is clicked
  public void stockClicked()
  {
    System.out.println("stock clicked");
    display.unselect();
    if (! display.isWasteSelected() && ! display.isPileSelected())
    {
      if (stock.isEmpty()) {
        resetStock();
      } else {
        dealThreeCards();
      }
    }

  }

  //called when the waste is clicked
  public void wasteClicked()
  {
    System.out.println("waste clicked");
    if (! waste.isEmpty())
    {
      if (! display.isWasteSelected()) {
        display.selectWaste();
      } else {
        display.unselect();
      }
    }
  }

  //precondition:  0 <= index < 7
  //postcondition: Removes elements from cards, and adds
  //               them to the given pile.
  private void addToPile(Stack<Card> cards, int index)
  {
    while (! cards.isEmpty())
    {
      piles[index].push(cards.pop());
    }
  }

  //precondition:  0 <= index < 4
  //postcondition: Returns true if the given card can be
  //               legally moved to the top of the given
  //               foundation
  private boolean canAddToFoundation(Card card, int index)
  {
    if (foundations[index].isEmpty()) {
      return card.getRank() == 1;
    }
    final Card temp = foundations[index].peek();
    return temp.getRank() + 1 == card.getRank() && temp.getSuit().equals(card.getSuit());
  }

  //precondition:  0 <= index < 7
  //postcondition: Returns true if the given card can be
  //               legally moved to the top of the given
  //               pile
  private boolean canAddToPile(Card card, int index)
  {
    final Stack<Card> pile = piles[index];
    if (pile.isEmpty()) {
      return card.getRank() == 13;
    }
    final Card top = pile.peek();
    if (! top.isFaceUp()) {
      return false;
    }
    return card.isRed() != top.isRed() && card.getRank() == top.getRank()-1;
  }

  //precondition:  0 <= index < 7
  //postcondition: Removes all face-up cards on the top of
  //               the given pile; returns a stack
  //               containing these cards
  private Stack<Card> removeFaceUpCards(int index)
  {
    final Stack<Card> cards = new Stack<>();
    while (! piles[index].isEmpty() && piles[index].peek().isFaceUp())
    {
      cards.push(piles[index].pop());
    }
    return cards;
  }

}