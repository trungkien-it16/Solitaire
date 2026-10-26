package ca.mcgill.solitaire.cards;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

public class TestPokerHand {

	private static Card card(Rank pRank, Suit pSuit) {
		return Card.get(pRank, pSuit);
	}

	private static PokerHand hand(Card... pCards) {
		return new PokerHand(pCards);
	}

	private static boolean isStraightFlush(PokerHand pHand) {
		try {
			Method method = PokerHand.class.getDeclaredMethod("isStraightFlush");
			method.setAccessible(true);
			return (boolean) method.invoke(pHand);
		}
		catch (NoSuchMethodException | IllegalAccessException exception) {
			throw new AssertionError("Cannot access PokerHand.isStraightFlush", exception);
		}
		catch (InvocationTargetException exception) {
			throw new AssertionError("isStraightFlush threw an exception", exception.getCause());
		}
	}

	@Test
	void testIsStraightFlush_True() {
		PokerHand hand = hand(card(Rank.THREE, Suit.SPADES), card(Rank.FOUR, Suit.SPADES), card(Rank.FIVE, Suit.SPADES),
				card(Rank.SIX, Suit.SPADES), card(Rank.SEVEN, Suit.SPADES));
		assertTrue(isStraightFlush(hand));
	}

	@Test
	void testIsStraightFlush_AllStraightFlushes() {
		Rank[] ranks = Rank.values();
		int count = 0;
		for (Suit suit : Suit.values()) {
			for (int start = 0; start + 4 < ranks.length; start++) {
				PokerHand hand = hand(card(ranks[start], suit), card(ranks[start + 1], suit),
						card(ranks[start + 2], suit), card(ranks[start + 3], suit), card(ranks[start + 4], suit));
				assertTrue(isStraightFlush(hand));
				count++;
			}
		}
		assertTrue(count == 36);
	}

	@Test
	void testIsStraightFlush_WrongSuit() {
		PokerHand hand = hand(card(Rank.THREE, Suit.SPADES), card(Rank.FOUR, Suit.HEARTS), card(Rank.FIVE, Suit.SPADES),
				card(Rank.SIX, Suit.SPADES), card(Rank.SEVEN, Suit.SPADES));
		assertFalse(isStraightFlush(hand));
	}

	@Test
	void testIsStraightFlush_WrongRank() {
		PokerHand hand = hand(card(Rank.THREE, Suit.SPADES), card(Rank.FOUR, Suit.SPADES), card(Rank.FIVE, Suit.SPADES),
				card(Rank.SEVEN, Suit.SPADES), card(Rank.EIGHT, Suit.SPADES));
		assertFalse(isStraightFlush(hand));
	}
}
