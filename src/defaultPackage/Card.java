package defaultPackage;

import java.security.DrbgParameters.Reseed;

public class Card {

    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RESET = "\u001B[0m"; 

    private String colore;


    enum Color {
        Red, Blue, Green, Yellow;

        private static Color[] colors = Color.values();

        public static Color getColors(int i) {
            return Color.colors[i];
        }
    }

    enum Value {
        Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, DrawTwo, Skip, Reverse;

        private static Value[] values = Value.values();

        public static Value getValue(int i) {
            return Value.values[i];
        }
    }

    // Attributes of Card
    private final Color color;
    private final Value value;

    public Card(Color color, Value value) {
        this.color = color;
        this.value = value;
    }

    public Color getColor() {
        return this.color;
    }

    public Value getValue() {
        return this.value;
    }

    public String toString() {

        switch (color) {
            case Red:
                colore = RED;
                break;
            case Blue : 
                colore = BLUE;
                break;
            case Green : 
                colore = GREEN;
                break;
            case Yellow : 
                colore = YELLOW;
                break;
        
            default:
                break;
        }
        return colore + color + " " + value + RESET;
    }

    public boolean isDrawTwo() {
        return this.value == Value.DrawTwo;
    }
}
