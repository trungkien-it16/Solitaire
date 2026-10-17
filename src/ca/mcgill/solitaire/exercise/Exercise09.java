package ca.mcgill.solitaire.exercise;

public class Exercise09 {

    public static void main(String[] args) {
        GameModel a = GameModel.instance();
        GameModel b = GameModel.instance();

        a.newGame();

        System.out.println("Cung mot doi tuong? " + (a == b));
    }
}