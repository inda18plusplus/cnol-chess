import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import org.junit.Test;
import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Position;
import piece.Queen;
import piece.Rook;
import static org.junit.Assert.assertEquals;

public class BoardTest {

  @Test
  public void place() {
    placeBlackPawn();
    placeWhitePawn();
  }

  @Test
  public void move() {
    movePawn();
    movePawnInvalidOrder();


    movePawnAttackingHostileToRightSide();
    movePawnAttackingFriendlyToRightSide();

    movePawnAttackingHostileToLeftSide();
    movePawnAttackingFriendlyToLeftSide();


    moveWhitePawnInvalidDirectionSide();
    moveWhitePawnInvalidDirectionBack();

    moveBlackPawnInvalidDirectionSide();
    moveBlackPawnInvalidDirectionBack();
  }

  @Test
  public void legalDestinations() {
    legalDestinationsPawnFirstMove();
    legalDestinationsPawnSecondMove();

    legalDestinationsKnight();
    legalDestinationsKnightOutOfBounds();

    legalDestinationsRook();
    legalDestinationsBishop();
    legalDestinationsQueen();

    legalDestinationsKing();
  }

  @Test
  public void getColorCheckType() {
    pawnCheckKing();

    kingInStalemate();
    kingInCheckmate();

    kingInCheckAvertByCapture();
  }

  private void kingInCheckAvertByCapture() {
    Board board = new Board();

    assert board.place(new King(Piece.Color.WHITE), new Position(0, 0));
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(3, 1));

    assert board.place(new Rook(Piece.Color.BLACK), new Position(2, 0));
    assert board.place(new Rook(Piece.Color.BLACK), new Position(2, 1));

    assertEquals(
        Board.CheckType.CHECK,
        board.getColorCheckType(Piece.Color.WHITE)
    );
  }

  private void kingInCheckmate() {
    Board board = new Board();

    assert board.place(new King(Piece.Color.WHITE), new Position(0, 0));

    assert board.place(new Rook(Piece.Color.BLACK), new Position(2, 0));
    assert board.place(new Rook(Piece.Color.BLACK), new Position(2, 1));

    assertEquals(
        Board.CheckType.CHECKMATE,
        board.getColorCheckType(Piece.Color.WHITE)
    );
  }

  private void kingInStalemate() {
    Board board = new Board();

    assert board.place(new King(Piece.Color.WHITE), new Position(0, 0));

    assert board.place(new Rook(Piece.Color.BLACK), new Position(2, 1));
    assert board.place(new Rook(Piece.Color.BLACK), new Position(1, 2));

    assertEquals(
        Board.CheckType.STALEMATE,
        board.getColorCheckType(Piece.Color.WHITE)
    );
  }

  private void pawnCheckKing() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.BLACK), new Position(2, 3));
    assert board.place(new King(Piece.Color.WHITE), new Position(3, 4));

    assertEquals(
        Board.CheckType.CHECK,
        board.getColorCheckType(Piece.Color.WHITE)
    );

    assertEquals(
        Board.CheckType.NONE,
      board.getColorCheckType(Piece.Color.BLACK)
    );
  }

  private void legalDestinationsPawnSecondMove() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.WHITE), new Position(4, 6));
    assert board.move(new Position(4, 6), new Position(4, 5));

    assertEquals(
        new HashSet<>(Collections.singletonList(
            new Position(4, 4)
        )),
        board.legalDestinations(new Position(4, 5))
    );
  }

  private void legalDestinationsPawnFirstMove() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.WHITE), new Position(4, 6));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(4, 5),
            new Position(4, 4)
        )),
        board.legalDestinations(new Position(4, 6))
    );
  }

  private void legalDestinationsKing() {
    Board board = new Board();

    assert board.place(new King(Piece.Color.WHITE), new Position(4, 4));

    assert board.place(new Rook(Piece.Color.BLACK), new Position(4, 3));
    assert board.place(new Rook(Piece.Color.BLACK), new Position(3, 3));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(5, 4),
            new Position(5, 5)
        )),
        board.legalDestinations(new Position(4, 4))
    );
  }

  private void legalDestinationsQueen() {
    Board board = new Board();

    assert board.place(new Queen(Piece.Color.WHITE), new Position(4, 4));

    assertEquals(
        new HashSet<>(Arrays.asList(
            // Diagonal
            new Position(0, 0),
            new Position(1, 1),
            new Position(2, 2),
            new Position(3, 3),

            new Position(5, 5),
            new Position(6, 6),
            new Position(7, 7),


            new Position(7, 1),
            new Position(6, 2),
            new Position(5, 3),

            new Position(3, 5),
            new Position(2, 6),
            new Position(1, 7),


            // Line
            new Position(0, 4),
            new Position(1, 4),
            new Position(2, 4),
            new Position(3, 4),

            new Position(5, 4),
            new Position(6, 4),
            new Position(7, 4),


            new Position(4, 0),
            new Position(4, 1),
            new Position(4, 2),
            new Position(4, 3),

            new Position(4, 5),
            new Position(4, 6),
            new Position(4, 7)
        )),
        board.legalDestinations(new Position(4, 4))
    );
  }

  private void legalDestinationsBishop() {
    Board board = new Board();

    assert board.place(new Bishop(Piece.Color.WHITE), new Position(4, 4));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(0, 0),
            new Position(1, 1),
            new Position(2, 2),
            new Position(3, 3),

            new Position(5, 5),
            new Position(6, 6),
            new Position(7, 7),


            new Position(7, 1),
            new Position(6, 2),
            new Position(5, 3),

            new Position(3, 5),
            new Position(2, 6),
            new Position(1, 7)
        )),
        board.legalDestinations(new Position(4, 4))
    );
  }

  private void legalDestinationsRook() {
    Board board = new Board();

    assert board.place(new Rook(Piece.Color.WHITE), new Position(4, 4));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(0, 4),
            new Position(1, 4),
            new Position(2, 4),
            new Position(3, 4),

            new Position(5, 4),
            new Position(6, 4),
            new Position(7, 4),


            new Position(4, 0),
            new Position(4, 1),
            new Position(4, 2),
            new Position(4, 3),

            new Position(4, 5),
            new Position(4, 6),
            new Position(4, 7)
        )),
        board.legalDestinations(new Position(4, 4))
    );
  }

  private void legalDestinationsKnightOutOfBounds() {
    Board board = new Board();

    assert board.place(new Knight(Piece.Color.WHITE), new Position(7, 6));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(6, 4),
            new Position(5, 5),
            new Position(5, 7)
        )),
        board.legalDestinations(new Position(7, 6))
    );
  }

  private void legalDestinationsKnight() {
    Board board = new Board();

    assert board.place(new Knight(Piece.Color.WHITE), new Position(4, 4));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(3, 2),
            new Position(5, 2),
            new Position(2, 3),
            new Position(6, 3),
            new Position(2, 5),
            new Position(6, 5),
            new Position(3, 6),
            new Position(5, 6)
        )),
        board.legalDestinations(new Position(4, 4))
    );
  }

  private void movePawnAttackingFriendlyToLeftSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(4, 3));
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(3, 2));

    assert !board.move(new Position(4, 3), new Position(3, 2));

    assertEquals(
        "........\n" +
            "........\n" +
            "...P....\n" +
            "....P...\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void movePawnAttackingHostileToLeftSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(4, 3));
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    assert board.move(new Position(4, 3), new Position(3, 2));

    assertEquals(
        "........\n" +
            "........\n" +
            "...P....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void movePawnAttackingFriendlyToRightSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(2, 3));
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(3, 2));

    assert !board.move(new Position(2, 3), new Position(3, 2));

    assertEquals(
        "........\n" +
            "........\n" +
            "...P....\n" +
            "..P.....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void movePawnAttackingHostileToRightSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(2, 3));
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    assert board.move(new Position(2, 3), new Position(3, 2));

    assertEquals(
        "........\n" +
            "........\n" +
            "...P....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void movePawnInvalidOrder() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 1));

    assert !board.move(new Position(3, 1), new Position(6, 2));
    assert board.move(new Position(6, 6), new Position(6, 5));

    assertEquals(
        "........\n" +
            "...p....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "......P.\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void moveBlackPawnInvalidDirectionBack() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    assert !board.move(new Position(3, 2), new Position(3, 1));

    assertEquals(
        "........\n" +
            "........\n" +
            "...p....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void moveBlackPawnInvalidDirectionSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    assert !board.move(new Position(3, 2), new Position(4, 2));

    assertEquals(
        "........\n" +
            "........\n" +
            "...p....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void moveWhitePawnInvalidDirectionBack() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));

    assert !board.move(new Position(6, 6), new Position(6, 7));

    assertEquals(
        "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "......P.\n" +
            "........\n",
        board.toString()
    );
  }

  private void moveWhitePawnInvalidDirectionSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));

    assert !board.move(new Position(6, 6), new Position(5, 6));

    assertEquals(
        "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "......P.\n" +
            "........\n",
        board.toString()
    );
  }

  private void movePawn() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 1));

    assert board.move(new Position(6, 6), new Position(6, 5));
    assert board.move(new Position(3, 1), new Position(3, 2));

    assertEquals(
        "........\n" +
            "........\n" +
            "...p....\n" +
            "........\n" +
            "........\n" +
            "......P.\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void placeBlackPawn() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    assertEquals(
        "........\n" +
            "........\n" +
            "...p....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void placeWhitePawn() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.WHITE), new Position(5, 6));

    assertEquals(
        "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            ".....P..\n" +
            "........\n",
        board.toString()
    );
  }
}