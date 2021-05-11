{\rtf1\ansi\ansicpg1252\deff0\nouicompat\deflang3081{\fonttbl{\f0\fnil\fcharset0 Calibri;}}
{\*\generator Riched20 10.0.18362}\viewkind4\uc1 
\pard\sa200\sl276\slmult1\f0\fs22\lang9\par
/**\par
 * AquariumViewer represents an interface for playing a game of Aquarium.\par
 *\par
 * @author (Reiden Rufin 22986337) (Aditi Malu 22526301)\par
 * @version 1.06 2020\par
 */\par
import java.awt.*;\par
import java.awt.event.*; \par
import javax.swing.SwingUtilities;\par
\par
public class AquariumViewer implements MouseListener\par
\{\par
    private final int BOXSIZE = 40;          // the size of each square\par
    private final int OFFSET  = BOXSIZE * 2; // the gap around the board\par
    private       int WINDOWSIZE;            // set this in the constructor \par
\par
    private Aquarium puzzle; // the internal representation of the puzzle\par
    private int        size; // the puzzle is size x size\par
    private SimpleCanvas sc; // the display window\par
\par
    private final Color bgColor = Color.white;   // background color\par
    private final static Color numColor = Color.black; // color of numbers\par
    private final static Color airColor = Color.red; // color of air\par
    private final static Color GRID_COLOUR = new Color(79, 77, 232); // color of grid lines (royal blue)\par
    private final static Color aquaColor = new Color(201, 114, 254); // color of aquarium borders (mauve)\par
    private final static Color waterColor = new Color(126, 234, 255); // color of water squares (electric blue)\par
\par
    /**\par
     * Main constructor for objects of class AquariumViewer.\par
     * Sets all fields, and displays the initial puzzle.\par
     */\par
    public AquariumViewer(Aquarium puzzle)\par
    \{\par
        this.puzzle = puzzle;\par
        size = puzzle.getSize();\par
        WINDOWSIZE = BOXSIZE * size + OFFSET * 2;\par
        sc = new SimpleCanvas("Aquarium Puzzle", WINDOWSIZE, WINDOWSIZE, bgColor); \par
        sc.setFont(new Font("Times", 10, BOXSIZE / 6 * 3));\par
        sc.addMouseListener(this);\par
        displayPuzzle();\par
    \}\par
\par
    /**\par
     * Selects from among the provided files in folder Examples. \par
     * xyz selects axy_z.txt. \par
     */\par
    public AquariumViewer(int n)\par
    \{\par
        this(new Aquarium("Examples/a" + n / 10 + "_" + n % 10 + ".txt"));\par
    \}\par
\par
    /**\par
     * Uses the provided example file on the LMS page.\par
     */\par
    public AquariumViewer()\par
    \{\par
        this(61);\par
    \}\par
\par
    /**\par
     * Returns the current state of the puzzle.\par
     */\par
    public Aquarium getPuzzle()\par
    \{\par
        return puzzle;\par
    \}\par
\par
    /**\par
     * Returns the size of the puzzle.\par
     */\par
    public int getSize()\par
    \{\par
        return size;\par
    \}\par
\par
    /**\par
     * Returns the current state of the canvas.\par
     */\par
    public SimpleCanvas getCanvas()\par
    \{\par
        return sc;\par
    \}\par
\par
    /**\par
     * Displays the initial puzzle; see the LMS page for the format.\par
     */\par
    private void displayPuzzle()\par
    \{\par
        for (int i = 0; i < size; i++)\par
            for (int j = 0; j < size; j++)\par
            \{\par
                updateSquare(i, j);\par
            \}    \par
        displayGrid();\par
        displayNumbers();\par
        displayAquariums();\par
        displayButtons();\par
    \}\par
\par
    /**\par
     * Displays the grid in the middle of the window.\par
     */\par
    public void displayGrid()\par
    \{\par
        for (int i = 0; i <= size; i++) \par
            sc.drawLine(0 + OFFSET, i * BOXSIZE + OFFSET, BOXSIZE * size + OFFSET, i * BOXSIZE + OFFSET, GRID_COLOUR);\par
        for (int j = 0; j <= size; j++) \par
            sc.drawLine(j * BOXSIZE + OFFSET, 0 + OFFSET,  j * BOXSIZE + OFFSET, BOXSIZE * size + OFFSET, GRID_COLOUR);\par
    \}\par
\par
    /**\par
     * Displays the numbers around the grid.\par
     */\par
    public void displayNumbers()\par
    \{\par
        for (int i = 0; i < puzzle.getRowTotals().length; i++)\par
            sc.drawString(puzzle.getRowTotals()[i], BOXSIZE + BOXSIZE / 2, OFFSET + BOXSIZE / 2 + BOXSIZE * i, numColor);\par
        for (int j = 0; j < puzzle.getColumnTotals().length; j++)\par
            sc.drawString(puzzle.getColumnTotals()[j], OFFSET + BOXSIZE / 2 + BOXSIZE * j, BOXSIZE + BOXSIZE /2, numColor);\par
    \}\par
\par
    /**\par
     * Displays the aquariums.\par
     */\par
\par
    public void displayAquariums()\par
    \{\par
        sc.drawRectangle(OFFSET - 2, OFFSET - 2, WINDOWSIZE  - OFFSET + 2, OFFSET + 2, aquaColor);\par
        sc.drawRectangle(OFFSET + 2, OFFSET + 2, OFFSET - 2, WINDOWSIZE - OFFSET - 2,  aquaColor);\par
        sc.drawRectangle(WINDOWSIZE - OFFSET + 2, WINDOWSIZE - OFFSET + 2, OFFSET - 2, WINDOWSIZE - OFFSET - 2, aquaColor);\par
        sc.drawRectangle(WINDOWSIZE - OFFSET + 2, OFFSET + 2, WINDOWSIZE - OFFSET - 2, WINDOWSIZE - OFFSET - 2, aquaColor);\par
\par
        for(int r = 0; r < size; r++)\par
        \{\par
            for(int c = 0; c < size; c++) \par
            \{\par
\par
                if( (c-1) >= 0 && puzzle.getAquariums()[r][c] != puzzle.getAquariums() [r][c-1] ) \par
                \{\par
                    sc.drawRectangle(OFFSET+(BOXSIZE*c)-2, OFFSET+(BOXSIZE*r)-2, OFFSET+(BOXSIZE*c)+2, OFFSET+(BOXSIZE*r)+BOXSIZE+2, aquaColor);\par
                \}\par
\par
                if( (r-1) >= 0 && puzzle.getAquariums()[r][c] != puzzle.getAquariums() [r-1][c] ) \par
                \{\par
                    sc.drawRectangle(OFFSET+(BOXSIZE*c)-2, OFFSET+(BOXSIZE*r)-2, OFFSET+(BOXSIZE*c)+BOXSIZE+2, OFFSET+(BOXSIZE*r)+2, aquaColor);\par
                \}\par
            \}\par
        \}\par
    \}\par
\par
    /**\par
     * Displays the buttons below the grid.\par
     */\par
    public void displayButtons()\par
    \{\par
        this.sc.drawString("SOLVED?", OFFSET, OFFSET+(BOXSIZE*size)+(BOXSIZE/2), Color.black);\par
        this.sc.drawString("CLEAR", OFFSET+(BOXSIZE*(size-1)), OFFSET+(BOXSIZE*size)+(BOXSIZE/2), Color.black);\par
    \}\par
\par
    /**\par
     * Updates the display of Square r,c. \par
     * Sets the display of this square to whatever is in the squares array. \par
     */\par
    public void updateSquare(int r, int c)\par
    \{ \par
        if (puzzle.getSpaces()[r][c] == Space.EMPTY) \par
        \{\par
            sc.drawRectangle(OFFSET+(BOXSIZE*c)+1, OFFSET+(BOXSIZE*r)+1,OFFSET+(BOXSIZE*c)+BOXSIZE-1, OFFSET+(BOXSIZE*r)+BOXSIZE-1, Color.white);\par
        \}\par
        else if (puzzle.getSpaces()[r][c] == Space.WATER) \par
        \{\par
            sc.drawRectangle(OFFSET+(BOXSIZE*c)+1, OFFSET+(BOXSIZE*r)+1,OFFSET+(BOXSIZE*c)+BOXSIZE-1, OFFSET+(BOXSIZE*r)+BOXSIZE-1, waterColor);\par
        \}\par
        else if (puzzle.getSpaces()[r][c] == Space.AIR)\par
        \{\par
            sc.drawRectangle(OFFSET+(BOXSIZE*c)+1, OFFSET+(BOXSIZE*r)+1,OFFSET+(BOXSIZE*c)+BOXSIZE-1, OFFSET+(BOXSIZE*r)+BOXSIZE-1, Color.white);\par
            sc.drawDisc(OFFSET+(BOXSIZE*c)+BOXSIZE/2, OFFSET+(BOXSIZE*r)+BOXSIZE/2, 5, airColor);\par
        \}\par
        else \par
        \{\par
            sc.drawRectangle(OFFSET+(BOXSIZE*c)+1, OFFSET+(BOXSIZE*r)+1,OFFSET+(BOXSIZE*c)+BOXSIZE-1, OFFSET+(BOXSIZE*r)+BOXSIZE-1, Color.white);\par
        \}\par
    \}\par
\par
    /**\par
     * Responds to a mouse click. \par
     * If it's on the board, make the appropriate move and update the screen display. \par
     * If it's on SOLVED?,   check the solution and display the result. \par
     * If it's on CLEAR,     clear the puzzle and update the screen display. \par
     */\par
    public void mousePressed(MouseEvent e) \par
    \{\par
        if ((e.getX()>=OFFSET && e.getX() < OFFSET + (BOXSIZE*size)) && (e.getY() >= OFFSET && e.getY() < OFFSET + (BOXSIZE*size))) \par
        \{\par
            for(int r=0;r<size;r++) \par
            \{\par
                for(int c=0;c<size;c++) \par
                \{\par
                    if((e.getX() >= OFFSET + (BOXSIZE*c) && e.getX() < OFFSET + (BOXSIZE*c) + BOXSIZE) && \par
                    (e.getY() >= OFFSET + (BOXSIZE*r) && e.getY() < OFFSET + (BOXSIZE*r) + BOXSIZE)) \par
                    \{\par
                        if(SwingUtilities.isLeftMouseButton(e))\par
                        \{\par
                            puzzle.leftClick(r, c);\par
                            updateSquare(r, c);\par
                        \}\par
                        else if(SwingUtilities.isRightMouseButton(e))\par
                        \{\par
                            puzzle.rightClick(r, c);\par
                            updateSquare(r, c);\par
                        \}\par
                        break;\par
                    \}\par
                \}\par
            \}\par
        \} \par
        else if((e.getX() >= OFFSET && e.getX() < OFFSET + BOXSIZE) && \par
        (e.getY() >= OFFSET + (BOXSIZE*size) + 10 && e.getY() < (OFFSET + (BOXSIZE*size)+20))) \par
        \{\par
           sc.drawRectangle(OFFSET, OFFSET+(BOXSIZE*size)+20, OFFSET+(BOXSIZE*size), OFFSET+(BOXSIZE*size)+45,Color.white);\par
           sc.drawString(CheckSolution.isSolution(puzzle), OFFSET, OFFSET+(BOXSIZE*size)+40, Color.black);\par
        \} \par
        else if((e.getX() >= OFFSET+(BOXSIZE*(size-1)) &&\par
            e.getX() < OFFSET + (BOXSIZE*size)) && (e.getY() >= OFFSET+(BOXSIZE*size)+  10 && \par
            e.getY() < (OFFSET + (BOXSIZE*size) + 20))) \par
        \{\par
            puzzle.clear();\par
            for(int r=0;r<size;r++) \par
            \{\par
                for(int c=0;c<size;c++) \par
                \{\par
                    updateSquare(r, c);\par
                \}\par
            \}\par
            sc.drawRectangle(OFFSET, OFFSET+(BOXSIZE*size)+20, OFFSET+(BOXSIZE*size), OFFSET+(BOXSIZE*size)+45,Color.white);\par
        \}\par
    \}\par
\par
    public void mouseClicked(MouseEvent e) \{\}\par
\par
    public void mouseReleased(MouseEvent e) \{\}\par
\par
    public void mouseEntered(MouseEvent e) \{\}\par
\par
    public void mouseExited(MouseEvent e) \{\}\par
\par
\}\par
}
 