package chess.piece;

import chess.piece.move.KangarooMove;
import chess.piece.move.Move;
import java.util.HashSet;
import java.util.Set;

public class Kangaroo extends Piece {
  public Kangaroo(Color color) {
    super(color);
  }

  private Kangaroo(Kangaroo kangaroo) {
    super(kangaroo.color);
  }

  @Override public char toChar() {
    switch (super.color) {
      case BLACK:
        return 'j';
      case WHITE:
        return 'J';
      default:
        return '?';
    }
  }

  @Override public Set<Move> getMoveSet() {
    Set<Move> moves = new HashSet<>();

    moves.add(new KangarooMove(-1, -1));
    moves.add(new KangarooMove(0, -1));
    moves.add(new KangarooMove(1, -1));

    moves.add(new KangarooMove(-1, 0));
    moves.add(new KangarooMove(1, 0));

    moves.add(new KangarooMove(-1, 1));
    moves.add(new KangarooMove(0, 1));
    moves.add(new KangarooMove(1, 1));

    return moves;
  }

  @Override public Piece makeCopy() {
    return new Kangaroo(this);
  }
}
