package ca.mcgill.solitaire.exercise;

import java.util.EnumMap;
import java.util.Map;

import ca.mcgill.solitaire.cards.Card;
import ca.mcgill.solitaire.cards.Rank;
import ca.mcgill.solitaire.cards.Suit;

public class Exercise08 {

    private static final int ROUNDS = 1_000_000;

    static final class EagerCard {
        private static final Map<Suit, Map<Rank, EagerCard>> CARDS = new EnumMap<>(Suit.class);

        static {
            for (Suit suit : Suit.values()) {
                Map<Rank, EagerCard> forSuit = CARDS.computeIfAbsent(suit, key -> new EnumMap<>(Rank.class));
                for (Rank rank : Rank.values()) {
                    forSuit.put(rank, new EagerCard(rank, suit));
                }
            }
        }

        private final Rank aRank;
        private final Suit aSuit;

        private EagerCard(Rank pRank, Suit pSuit) {
            aRank = pRank;
            aSuit = pSuit;
        }

        static EagerCard get(Rank pRank, Suit pSuit) {
            return CARDS.get(pSuit).get(pRank);
        }
    }

    private static int getAllEager() {
        int count = 0;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                if (EagerCard.get(rank, suit) != null) {
                    count++;
                }
            }
        }
        return count;
    }

    private static int getAllLazy() {
        int count = 0;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                if (Card.get(rank, suit) != null) {
                    count++;
                }
            }
        }
        return count;
    }

    private interface Task {
        int run();
    }

    private static void measure(String pName, Task pTask) {
        long start = System.currentTimeMillis();
        int count = pTask.run();
        long firstMs = System.currentTimeMillis() - start;
        start = System.currentTimeMillis();
        long total = 0;
        for (int i = 0; i < ROUNDS; i++) {
            total += pTask.run();
        }
        long laterMs = System.currentTimeMillis() - start;

        System.out.printf("%-5s | cards=%d | first 52: %d ms | %d more rounds of 52: %d ms | check=%d%n", pName, count,
                firstMs, ROUNDS, laterMs, total);
    }

    public static void main(String[] args) {
        String mode = args.length > 0 ? args[0] : "both";
        if (mode.equals("eager") || mode.equals("both")) {
            measure("eager", Exercise08::getAllEager);
        }
        if (mode.equals("lazy") || mode.equals("both")) {
            measure("lazy", Exercise08::getAllLazy);
        }
    }
}