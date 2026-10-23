/*******************************************************************************
 * Solitaire
 * 
 * Copyright (C) 2025 by Martin P. Robillard
 * 
 * See: https://github.com/prmr/Solitaire
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 *******************************************************************************/
package ca.mcgill.solitaire.cards;

import java.util.EnumMap;
import java.util.Map;

/**
 * An immutable description of a playing card. This abstraction is designed to
 * be independent of game logic, so it does not provide any service that relies
 * on the knowledge of the rules of any particular game.
 * 
 * This class implements the Flyweight design pattern: there can only ever be
 * one instance of a card that represents a specific real-world playing card
 * (such as Ace of Spades). In the absence of serialization and reflection, this
 * ensures that the behavior of the == operator is equivalent to that of the
 * equals method when two non-null card arguments are provided.
 */
public final class Card {
	private static final Map<Suit, Map<Rank, Card>> CARDS = new EnumMap<>(Suit.class);

	private final Rank aRank;
	private final Suit aSuit;

	private Card(Rank pRank, Suit pSuit) {
		aRank = pRank;
		aSuit = pSuit;
	}

	public static synchronized Card get(Rank pRank, Suit pSuit) {
		assert pRank != null && pSuit != null;
		return CARDS.computeIfAbsent(pSuit, key -> new EnumMap<>(Rank.class)).computeIfAbsent(pRank,
				key -> new Card(pRank, pSuit));
	}

	/**
	 * Obtain the rank of the card.
	 * 
	 * @return An object representing the rank of the card.
	 */
	public Rank rank() {
		return aRank;
	}

	/**
	 * Obtain the suit of the card.
	 * 
	 * @return An object representing the suit of the card
	 */
	public Suit suit() {
		return aSuit;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s of %s", titleCase(aRank), titleCase(aSuit));
	}

	private static String titleCase(Enum<?> pEnum) {
		return pEnum.name().charAt(0) + pEnum.name().substring(1).toLowerCase();
	}
}