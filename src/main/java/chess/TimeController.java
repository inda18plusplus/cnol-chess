package chess;

import chess.piece.Piece.Color;
import java.util.Timer;
import java.util.TimerTask;

class TimeController {

  private long totalTime;
  private long timeIncreasePerMove;
  private long whiteTimeTracker;
  private long blackTimeTracker;
  private long opponentTimeTracker;
  private boolean active;

  private Timer startTimer;

  /**
   * Creates a time controller that will keep track of much time each player got left to play with.
   *
   * @param totalTime The total amount of time each player begins with, in minutes.
   * @param timeIncreasePerMove The amount of time each player is given when a move is made, in
   * seconds.
   */
  TimeController(double totalTime, double timeIncreasePerMove) {
    this.totalTime = (long) (totalTime * 60000);
    this.timeIncreasePerMove = (long) (timeIncreasePerMove * 1000);
    active = true;
  }

  TimeController() {
  }

  void beginIn(long delay) {
    if (hasBegun() || !active) {
      return;
    }

    if (delay == 0) {
      if (startTimer != null) {
        startTimer.cancel();
      }

      whiteTimeTracker = blackTimeTracker = System.currentTimeMillis();
      return;
    }

    if (startTimer != null) {
      startTimer.cancel();
    }

    startTimer = new Timer();
    startTimer.schedule(new TimerTask() {
      @Override
      public void run() {
        whiteTimeTracker = blackTimeTracker = System.currentTimeMillis();
      }
    }, delay);
  }

  void moveMade(Color color) {
    if (!hasBegun()) {
      return;
    }

    long timeTaken = System.currentTimeMillis() - opponentTimeTracker;

    if (color == Color.WHITE) {
      whiteTimeTracker += timeIncreasePerMove;
      blackTimeTracker += timeTaken;
    } else if (color == Color.BLACK) {
      blackTimeTracker += timeIncreasePerMove;
      whiteTimeTracker += timeTaken;
    }

    opponentTimeTracker = System.currentTimeMillis();
  }

  boolean hasLost(Color color) {
    if (!hasBegun()) {
      return false;
    }

    if (color == Color.WHITE) {
      return System.currentTimeMillis() - whiteTimeTracker >= totalTime;
    }

    if (color == Color.BLACK) {
      return System.currentTimeMillis() - blackTimeTracker >= totalTime;
    }

    return false;
  }

  private boolean hasBegun() {
    return active && whiteTimeTracker > 0 && blackTimeTracker > 0 && totalTime > 0;
  }

}
