package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author Kelvin Nguyen
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.

        //Sets the perspective to the side the user chooses. It is later set back to north.
        board.setViewingPerspective(side);
        // iterates through all the tiles on the board.
        for (int i = board.size()-1; i >= 0; i -= 1){
            int merged = -1;
            for (int j = board.size()-2; j >= 0; j -= 1){
                if (board.tile(i, j) != null) {
                    int max = j;
                    Tile t = board.tile(i, j);
                    // Checks if there are any empty spaces in front of a tile and sets the max distance
                    // that tile can travel before it either reaches the edge of the board or another tile
                    for (int g = j; g < board.size(); g += 1){
                        if (board.tile(i, g) == null){
                            max = g;
                        }
                    }
                    // Checks if the tile in front has an equal value, and has not been merged yet.
                    // If a tile merges, the merged variable is set to that merged tile's row index.
                    if (max != board.size()-1){
                        if (board.tile(i, max+1).value() == t.value() && max+1 != merged){
                            board.move(i, max+1, t);
                            merged = max+1;
                            score += t.value()*2;
                            changed = true;
                            // Checks if the tile should move. If it doesn't move then the board is unchanged
                        } else if(max == j){
                            changed = false;
                        }
                        // There are no tiles in front that are equal in value, so it moves to the closest null tile
                        else {
                            board.move(i, max, t);
                            changed = true;
                        }
                        // If the max is on the edge, the tile moves there because the path towards the
                        // edge is all null.
                    } else {
                        board.move(i, max, t);
                        changed = true;
                    }
                }
            }
        }
        board.setViewingPerspective(Side.NORTH);
        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }
    public boolean moveToNextVal(int c, int r){
        int max = r;
        Tile t = board.tile(c, r);
        for (int i = r; i < board.size(); i += 1){
            if (board.tile(c, i) == null){
                max = i;
            }
        }
        if (max != board.size()-1){
            if (board.tile(c, max+1).value() == t.value()){
                board.move(c, max+1, t);
                score += t.value()*2;
                return true;
            } else if(max == r){
                return false;
            }
            else {
                board.move(c, max, t);
                return true;
            }
        } else {
            board.move(c, max, t);
            return true;
        }
    }



    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        for (int i = 0; i < b.size(); i += 1){
            for (int j = 0; j < b.size(); j += 1){
                if (b.tile(i, j) == null){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for (int i = 0; i < b.size(); i += 1){
            for (int j = 0; j < b.size(); j += 1){
                if (b.tile(i, j) != null){
                    if (b.tile(i, j).value() == Model.MAX_PIECE){return true;}
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        if(Model.emptySpaceExists(b)) {
            return true;
        }
        for (int i = 0; i < b.size(); i += 1){
            for (int j = 0; j < b.size(); j += 1){
                if (b.tile(i, j) != null){
                    if (tileHasEqualAdjacentTiles(b.tile(i, j), b, i, j)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /** Helper func that takes an individual tile and checks if the tile has any adjacent tiles that
     * are equal in value to that tile*/
    public static boolean tileHasEqualAdjacentTiles(Tile tile, Board b, int i, int j){
        if (i >= 0 && i != b.size()-1) {
            if (b.tile(i+1, j) != null)
                if (tile.value() == b.tile(i+1, j).value()){
                    return true;
                }
        } else if (i <= b.size()-1 && i != 0){
            if (tile.value() == b.tile(i-1, j).value()){
                return true;
            }
        }
        if (j >= 0 && j != b.size()-1) {
            if (b.tile(i, j+1) != null)
                if (tile.value() == b.tile(i, j+1).value()){
                    return true;
                }
        } else if (j <= b.size()-1 && j !=0){
            if (tile.value() == b.tile(i, j-1).value()){
                return true;
            }
        }
        return false;
    }

    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
