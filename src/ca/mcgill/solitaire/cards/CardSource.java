package ca.mcgill.solitaire.cards;

public interface CardSource {
	Card draw();

	boolean isEmpty();
}
