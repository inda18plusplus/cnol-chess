package piece;

import java.awt.datatransfer.Clipboard;
import org.junit.Test;
import static org.junit.Assert.*;

public class KnightTest {

  @Test
  public void toChar() {
    whiteCharacterRepresentation();
    blackCharacterRepresentation();
  }

  private void blackCharacterRepresentation() {
    Knight knight = new Knight(Piece.Color.BLACK);

    assertEquals('n', knight.toChar());
  }

  private void whiteCharacterRepresentation() {
    Knight knight = new Knight(Piece.Color.WHITE);

    assertEquals('N', knight.toChar());
  }
}