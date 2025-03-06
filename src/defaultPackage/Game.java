package defaultPackage;

import defaultPackage.Card.Value;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Game {
    private List<Player> competitors;
    private Deck gameDeck;
    private Card lastCard;
    private Player winner;

    public Game() {
        competitors = new ArrayList<>();
        setupGame();
    }

    public void setupGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("================================================================");
        System.out.println("====================WELCOM TO A NEW UNO GAME====================");
        System.out.println("Provide the number of players,(number should be between 2 and 4 !):");

        int numberOfCompetitors = scanner.nextInt();
        while (numberOfCompetitors < 2 || numberOfCompetitors > 4) {
            System.out.println("Invalid Number, Please respect what the game request !");
            System.out.println("Reprovide the Number of Players :(number should be between 2 and 4 !):");
            numberOfCompetitors = scanner.nextInt();
        }


        System.out.println("Provide the number of Humans player, Number should be between (1-" + numberOfCompetitors + "): ");
        int numberOfHumans = scanner.nextInt();

        while (numberOfHumans < 1 || numberOfHumans > numberOfCompetitors) {
            System.out.println("Invalid Number, Please respect what the game request !");
            System.out.println("Reprovide the Number of Humans :(number should be between 1 and" + numberOfCompetitors + "):");
            numberOfHumans = scanner.nextInt();
        }
        pause();
        scanner.nextLine();
        System.out.println("================================================================");
        System.out.println("Provide the player's names");
        System.out.println("--------------------------");

        for (int i = 0; i < numberOfHumans; i++) {
            System.out.println("Enter the name of player " + (i + 1) + ": ");
            String name = scanner.nextLine();
            HumanPlayer human = new HumanPlayer();
            human.setName(name);
            competitors.add(human);
        }

        for (int i = 0; i < numberOfCompetitors - numberOfHumans; i++) {
            BotPlayer bot = new BotPlayer();
            bot.setName("Bot" + (i + 1));
            competitors.add(bot);
        }
        try {
            Thread.sleep(1000); // Pause for 3 seconds (3000 milliseconds)
        } catch (InterruptedException e) {

        }
        Collections.shuffle(competitors);
        System.out.println("================================================================");
        System.out.println("This game is gona be between :");
        for(int i = 0; i < competitors.size(); i ++) {
            System.out.println("*" + competitors.get(i).getName());
        }
        gameDeck = new Deck();
        for (Player player : competitors) {
            for (int j = 0; j < 7; j++) {
                player.addCard(gameDeck.popCard());
            }
        }
        System.out.println("Giving cards ...");
        try {
            Thread.sleep(1000); // Pause for 3 seconds (3000 milliseconds)
        } catch (InterruptedException e) {
            System.out.println("Thread was interrupted!");
        }
        System.out.println("================================================================");
        lastCard = gameDeck.popCard();
        System.out.println("*************Game setup complete. Starting the game!************");
        System.out.println("================================================================");
        
    }
    
    public boolean handleReverse(int indexOfReverser){
        Player reverser = competitors.get(indexOfReverser);
        Collections.reverse(this.competitors);
        indexOfReverser = competitors.indexOf(reverser)+1;
        return false;
    }
    
    public void pause(){
        try {
            Thread.sleep(1500); 
        } catch (InterruptedException e) {
            
        }
    }
    
    public void playGame() {
        boolean plusTwo = false;
        int Acc = 0;
        boolean skipped = false;
        boolean reverse = false;
        switch (lastCard.getValue()) {
            case Skip :
                skipped = true;
                break;
            case DrawTwo :
                plusTwo = true;
            case Reverse :
                reverse = true;
            default:
                break;
        }
        while (winner == null) {
            for (int i = 0; i < competitors.size(); i++) {
                if (reverse) {
                    reverse = handleReverse(i);
                    continue;
                }
                Player currentPlayer = competitors.get(i);
                
                System.out.println("\n" + currentPlayer.getName() + "'s turn.");
                pause();
                if (!skipped) {
                    if (plusTwo) {
                        Acc += 2;
                        if (currentPlayer instanceof HumanPlayer) {
                            System.out.println("Checking for DrawTwo counters...");
                            if (currentPlayer.counter(Value.DrawTwo).size() != 0) {
                                System.out.println("Would you put your counter or draw two cards:\n[0]: choose from " + currentPlayer.counter(Value.DrawTwo) + "\n[other]: Draw two");
                                Scanner s = new Scanner(System.in);
                                char choice = s.nextLine().charAt(0);
                                int choicee;
                                if (choice == '0') {
                                    while (true) {
                                        System.out.println("Enter the index of the chosen card (0," + (currentPlayer.counter(Value.DrawTwo).size() - 1) + "):");
                                        choicee = s.nextInt();
                                        s.nextLine();
                                        if (choicee >= 0 && choicee < currentPlayer.counter(Value.DrawTwo).size()) {
                                            break;
                                        } else {
                                            System.out.println("Invalid index");
                                        }
                                    }
                                    lastCard = currentPlayer.counter(Value.DrawTwo).get(choicee);
                                    currentPlayer.removeFromHand(lastCard);
                                } else {
                                    for (int index = 0; index < Acc; index++) {
                                        currentPlayer.addCard(gameDeck.popCard());
                                    }
                                    plusTwo = false;
                                    System.out.println(currentPlayer.getName() + " drew "+Acc+" cards.");
                                    Acc = 0;
                                }
                            } else {
                                System.out.println("No counters found");
                                for (int index = 0; index < Acc; index++) {
                                    currentPlayer.addCard(gameDeck.popCard());
                                }
                                plusTwo = false;
                                System.out.println(currentPlayer.getName() + " drew "+Acc+" cards.");
                                Acc = 0;
                            }
                        } else if (currentPlayer instanceof BotPlayer) {
                            System.out.println(currentPlayer.getName() + " has drew " + Acc + " cards.");
                            for (int index = 0; index < Acc; index++) {
                                currentPlayer.addCard(gameDeck.popCard());
                            }
                            Acc = 0;
                            plusTwo = false;
                        }
                    } else {
                        Card playedCard = currentPlayer.putCard(lastCard, gameDeck);
                        if (playedCard != null) {
                            lastCard = playedCard;
                            if (playedCard.getValue() == Card.Value.DrawTwo) {
                                plusTwo = true;
                            } else if (playedCard.getValue() == Card.Value.Skip) {
                                skipped = true;
                            } else if (playedCard.getValue() == Card.Value.Reverse) {
                                reverse = true;
                            }
                            if (currentPlayer.getHand().isEmpty()) {
                                winner = currentPlayer;
                                System.out.println("\ud83c\udf89 " + currentPlayer.getName() + " wins the game! \ud83c\udf89");
                                return;
                            }
                        }
                    }
                } else {
                    System.out.println("*** Skipped ***");
                    skipped = false;
                }
            }
        }
    }


    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }
}
