package ca.mcgill.solitaire.cards;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.Test;

public class BiCardSourceTest {

	private static final Card CARD_1 = Card.get(Rank.ACE, Suit.CLUBS);
	private static final Card CARD_2 = Card.get(Rank.TWO, Suit.HEARTS);

	private static class StubCardSource implements CardSource {
		private final Deque<Card> aCards = new ArrayDeque<>();

		StubCardSource(Card... pCards) {
			for (Card card : pCards) {
				aCards.addLast(card);
			}
		}

		@Override
		public Card draw() {
			return aCards.removeFirst();
		}

		@Override
		public boolean isEmpty() {
			return aCards.isEmpty();
		}
	}

	@Test
	void testDraw_Source1NotEmpty() {
		BiCardSource source = new BiCardSource(new StubCardSource(CARD_1), new StubCardSource(CARD_2));
		assertSame(CARD_1, source.draw());
	}

	@Test
	void testDraw_BothEmpty() {
		BiCardSource source = new BiCardSource(new StubCardSource(), new StubCardSource());
		assertThrows(IllegalStateException.class, source::draw);
	}

	/** Source 1 empty => the card comes from source 2. */
	@Test
	void testDraw_Source1Empty() {
		BiCardSource source = new BiCardSource(new StubCardSource(), new StubCardSource(CARD_2));
		assertSame(CARD_2, source.draw());
	}

	@Test
	void testIsEmpty_Source1NotEmpty() {
		BiCardSource source = new BiCardSource(new StubCardSource(CARD_1), new StubCardSource());
		assertFalse(source.isEmpty());
	}

	/** First operand true, second operand false. */
	@Test
	void testIsEmpty_Source1EmptySource2NotEmpty() {
		BiCardSource source = new BiCardSource(new StubCardSource(), new StubCardSource(CARD_2));
		assertFalse(source.isEmpty());
	}

	/** Both operands true. */
	@Test
	void testIsEmpty_BothEmpty() {
		BiCardSource source = new BiCardSource(new StubCardSource(), new StubCardSource());
		assertTrue(source.isEmpty());
	}
}
