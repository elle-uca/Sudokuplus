package org.ln.sudokuplus.model;

public enum Level {

	EASY (40, "Facile"),
	NORMAL (45, "Normale"),
	MEDIUM (48, "Medio"),
	HARD (52, "Difficile"),
	EXPERT (58, "Esperto");

	private int toGuess;
	private String title;

	Level(int n, String title) {
		this.toGuess = n;
		this.title = title;
	}

	/**
	 * @return the toGuess
	 */
	public int getToGuess() {
		return toGuess;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return title;
	}

}
