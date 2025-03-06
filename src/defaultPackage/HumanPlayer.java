package defaultPackage;

import java.util.Scanner;

public class HumanPlayer extends Player {

    @Override
    public Card putCard(Card lastCard, Deck gameDeck) {
        Scanner scanner = new Scanner(System.in);
        Card selectedCard = null;
        int choice;
        while (true) {
            System.out.println("*************************************************");
            System.out.println("Last card on the game table is : [" + lastCard + "]\n");
            
        System.out.println("Your Cards :");
        System.out.println("================================================================================================================================");
            for(int i = 0; i < getHand().size(); i++) {
                System.out.print("[(" + i + ") " + getHand().get(i) + "]  ");
            }
            
            System.out.println("\n================================================================================================================================");
            try {
                Thread.sleep(1500); 
            } catch (InterruptedException e) {
                
            }
            System.out.println("<--Enter the card index to play (0-" + (getHand().size() - 1) + "), or -1 to draw: -->");
            while (true) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice > -1 || choice < getHand().size()-1) {
                    break;
                }else{
                    System.out.println("***Please respect what the game request , Enter the card index to play (0-" +(getHand().size() - 1) + "), or -1 to draw***" );
                }
            }

            if (choice == -1) {
                Card newCard = gameDeck.popCard();
                addCard(newCard);
                System.out.println("You drew: " + newCard);
                return null; // skip turn after drawing
            } else if (choice >= 0 && choice < getHand().size()) {
                Card card = getHand().get(choice);
                if (isValidCard(card, lastCard)) {
                    selectedCard = removeFromHand(choice);
                    System.out.println("You played: " + selectedCard);
                    break;
                } else {
                    System.out.println("***Invalid card! Choose a card that matches the color or value.***");
                }
            } else {
                System.out.println("***Invalid choice. Try again.***");
            }
        }
        System.out.println("*************************************************");
        return selectedCard;
    }
}
