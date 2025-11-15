package org.ln.sudokuplus.model;

/**
 * Enumeration defining the available difficulty levels and their metadata.
 */
public enum GameLevel {

        EASY(40, "Facile"),
        NORMAL(45, "Normale"),
        MEDIUM(48, "Medio"),
        HARD(52, "Difficile"),
        EXPERT(58, "Esperto");

        private final int toGuess;
        private final String title;

        GameLevel(int toGuess, String title) {
                this.toGuess = toGuess;
                this.title = title;
        }

	/**
	 * Retrieves the number of cells that should remain for the player to solve.
	 *
	 * @return the toGuess
	 */
	public int getToGuess() {
		return toGuess;
	}

	/**
	 * Provides the localized label used to describe the level.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the localized level title for UI rendering.
	 */
	@Override
	public String toString() {
		return title;
	}

}
