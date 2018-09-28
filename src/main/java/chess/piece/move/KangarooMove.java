package chess.piece.move;

import chess.piece.Piece;
import chess.piece.Position;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Jumps one step over a friendly piece, capturing a hostile piece it lands on.
 */
public class KangarooMove extends Move {
  private final int deltaColumn;
  private final int deltaRow;

  public KangarooMove(int deltaColumn, int deltaRow) {
    this.deltaColumn = deltaColumn;
    this.deltaRow = deltaRow;
  }

  /**
   * Create a new KangarooMove.
   *
   * @param deltaColumn The length and direction of a step along the columns
   * @param deltaRow The length and direction of a step along the rows
   * @param captureRule Determines what pieces this move can capture
   */
  public KangarooMove(int deltaColumn, int deltaRow, CaptureRule captureRule) {
    this(deltaColumn, deltaRow);
    super.captureRule = captureRule;
  }

  @Override public Set<Position> getDestinations(Position origin, int boundWidth, int boundHeight,
                                                 Function<Position, Piece> getPiece) {
    Piece sourcePiece = getPiece.apply(origin);
    Set<Position> positions = new HashSet<>();

    Position delta = new Position(deltaColumn, deltaRow);
    Position adjacentPosition = origin.add(delta);

    Piece adjacentPiece = getPiece.apply(adjacentPosition);

    if (adjacentPiece != null) {
      if (adjacentPiece.getColor() == sourcePiece.getColor()) {
        Position resultingPosition = adjacentPosition.add(delta);
        if (resultingPosition.inBound(0, 0, boundWidth, boundHeight)) {
          Piece resultingPiece = getPiece.apply(resultingPosition);
          if (sourcePiece.canCapture(resultingPiece, this.captureRule)) {
            positions.add(resultingPosition);
          }
        }
      }
    }

    return positions;

  }

  @Override public void perform(Position oldPosition, Position newPosition,
                                Function<Position, Piece> getPiece,
                                BiConsumer<Piece, Position> setPiece) {
    Piece sourcePiece = getPiece.apply(oldPosition);

    setPiece.accept(sourcePiece, newPosition);
    setPiece.accept(null, oldPosition);

    sourcePiece.onMove(oldPosition, newPosition, getPiece);
  }

}
