package chess.piece;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class KingTest {
  @Test
  public void toChar() {
    whiteCharacterRepresentation();
    blackCharacterRepresentation();
  }

  private void blackCharacterRepresentation() {
    King king = new King(Piece.Color.BLACK);

    assertEquals('k', king.toChar());
  }

  private void whiteCharacterRepresentation() {
    King king = new King(Piece.Color.WHITE);

    assertEquals('K', king.toChar());
  }
}