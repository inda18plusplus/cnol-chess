package chess.piece;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RookTest {
  @Test
  public void toChar() {
    whiteCharacterRepresentation();
    blackCharacterRepresentation();
  }

  private void blackCharacterRepresentation() {
    Rook rook = new Rook(Piece.Color.BLACK);

    assertEquals('r', rook.toChar());
  }

  private void whiteCharacterRepresentation() {
    Rook rook = new Rook(Piece.Color.WHITE);

    assertEquals('R', rook.toChar());
  }
}