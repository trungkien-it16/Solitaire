package ca.mcgill.solitaire.cards;

public class BiCardSource implements CardSource {

	private final CardSource aSource1;
	private final CardSource aSource2;

	public BiCardSource(CardSource pSource1, CardSource pSource2) {
		aSource1 = pSource1;
		aSource2 = pSource2;
	}

	@Override
	public Card draw() {
		if (!aSource1.isEmpty()) {
			return aSource1.draw();
		}
		if (!aSource2.isEmpty()) {
			return aSource2.draw();
		}
		throw new IllegalStateException("Cannot draw from an empty BiCardSource");
	}

	@Override
	public boolean isEmpty() {
		return aSource1.isEmpty() && aSource2.isEmpty();
	}
}
