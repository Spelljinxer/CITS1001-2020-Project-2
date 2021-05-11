
/**
 * AquariumViewer represents an interface for playing a game of Aquarium.
 *
 * @author (Reiden Rufin 22986337) (Aditi Malu 22526301)
 * @version 1.06 2020
 */
import java.awt.*;
import java.awt.event.*; 
import javax.swing.SwingUtilities;

public class AquariumViewer implements MouseListener
{
    private final int BOXSIZE = 40;          // the size of each square
    private final int OFFSET  = BOXSIZE * 2; // the gap around the board
    private       int WINDOWSIZE;            // set this in the constructor 

    private Aquarium puzzle; // the internal representation of the puzzle
    private int        size; // the puzzle is size x size
    private SimpleCanvas sc; // the display window

    private final Color bgColor = Color.white;   // background color
    private final static Color numColor = Color.black; // color of numbers
    private final static Color airColor = Color.red; // color of air
    private final static Color GRID_COLOUR = new Color(79, 77, 232); // color of grid lines (royal blue)
    private final static Color aquaColor = new Color(201, 114, 254); // color of aquarium borders (mauve)
    private final static Color waterColor = new Color(126, 234, 255); // color of water squares (electric blue)

    /**
     * Main constructor for objects of class AquariumViewer.
     * Sets all fields, and displays the initial puzzle.
     */
    public AquariumViewer(Aquarium puzzle)
    {
        this.puzzle = puzzle;
        size = puzzle.getSize();
        WINDOWSIZE = BOXSIZE * size + OFFSET * 2;
        sc = new SimpleCanvas("Aquarium Puzzle", WINDOWSIZE, WINDOWSIZE, bgColor); 
        sc.setFont(new Font("Times", 10, BOXSIZE / 6 * 3));
        sc.addMouseListener(this);
        displayPuzzle();
    }

    /**
     * Selects from among the provided files in folder Examples. 
     * xyz selects axy_z.txt. 
     */
    public AquariumViewer(int n)
    {
        this(new Aquarium("Examples/a" + n / 10 + "_" + n % 10 + ".txt"));
    }

    /**
     * Uses the provided example file on the LMS page.
     */
    public AquariumViewer()
    {
        this(61);
    }

    /**
     * Returns the current state of the puzzle.
     */
    public Aquarium getPuzzle()
    {
        return puzzle;
    }

    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Returns the current state of the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        return sc;
    }

    /**
     * Displays the initial puzzle; see the LMS page for the format.
     */
    private void displayPuzzle()
    {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                updateSquare(i, j);
            }    
        displayGrid();
        displayNumbers();
        displayAquariums();
        displayButtons();
    }

    /**
     * Displays the grid in the middle of the window.
     */
    public void displayGrid()
    {
        for (int i = 0; i <= size; i++) 
            sc.drawLine(0 + OFFSET, i * BOXSIZE + OFFSET, BOXSIZE * size + OFFSET, i * BOXSIZE + OFFSET, GRID_COLOUR);
        for (int j = 0; j <= size; j++) 
            sc.drawLine(j * BOXSIZE + OFFSET, 0 + OFFSET,  j * BOXSIZE + OFFSET, BOXSIZE * size + OFFSET, GRID_COLOUR);
    }

    /**
     * Displays the numbers around the grid.
     */
    public void displayNumbers()
    {
        for (int i = 0; i < puzzle.getRowTotals().length; i++)
            sc.drawString(puzzle.getRowTotals()[i], BOXSIZE + BOXSIZE / 2, OFFSET + BOXSIZE / 2 + BOXSIZE * i, numColor);
        for (int j = 0; j < puzzle.getColumnTotals().length; j++)
            sc.drawString(puzzle.getColumnTotals()[j], OFFSET + BOXSIZE / 2 + BOXSIZE * j, BOXSIZE + BOXSIZE /2, numColor);
    }

    /**
     * Displays the aquariums.
     */

    public void displayAquariums()
    {
        sc.drawRectangle(OFFSET - 2, OFFSET - 2, WINDOWSIZE  - OFFSET + 2, OFFSET + 2, aquaColor);
        sc.drawRectangle(OFFSET + 2, OFFSET + 2, OFFSET - 2, WINDOWSIZE - OFFSET - 2,  aquaColor);
        sc.drawRectangle(WINDOWSIZE - OFFSET + 2, WINDOWSIZE - OFFSET + 2, OFFSET - 2, WINDOWSIZE - OFFSET - 2, aquaColor);
        sc.drawRectangle(WINDOWSIZE - OFFSET + 2, OFFSET + 2, WINDOWSIZE - OFFSET - 2, WINDOWSIZE - OFFSET - 2, aquaColor);

        for(int r = 0; r < size; r++)
        {
            for(int c = 0; c < size; c++) 
            {

                if( (c-1) >= 0 && puzzle.getAquariums()[r][c] != puzzle.getAquariums() [r][c-1] ) 
                {
                    sc.drawRectangle(OFFSET+(BOXSIZE*c)-2, OFFSET+(BOXSIZE*r)-2, OFFSET+(BOXSIZE*c)+2, OFFSET+(BOXSIZE*r)+BOXSIZE+2, aquaColor);
                }

                if( (r-1) >= 0 && puzzle.getAquariums()[r][c] != puzzle.getAquariums() [r-1][c] ) 
                {
                    sc.drawRectangle(OFFSET+(BOXSIZE*c)-2, OFFSET+(BOXSIZE*r)-2, OFFSET+(BOXSIZE*c)+BOXSIZE+2, OFFSET+(BOXSIZE*r)+2, aquaColor);
                }
            }
        }
    }

    /**
     * Displays the buttons below the grid.
     */
    public void displayButtons()
    {
        this.sc.drawString("SOLVED?", OFFSET, OFFSET+(BOXSIZE*size)+(BOXSIZE/2), Color.black);
        this.sc.drawString("CLEAR", OFFSET+(BOXSIZE*(size-1)), OFFSET+(BOXSIZE*size)+(BOXSIZE/2), Color.black);
    }

    /**
     * Updates the display of Square r,c. 
     * Sets the display of this square to whatever is in the squares array. 
     */
    public void updateSquare(int r, int c)
    { 
        if (puzzle.getSpaces()[r][c] == Space.EMPTY) 
        {
            sc.drawRectangle(OFFSET+(BOXSIZE*c)+1, OFFSET+(BOXSIZE*r)+1,OFFSET+(BOXSIZE*c)+BOXSIZE-1, OFFSET+(BOXSIZE*r)+BOXSIZE-1, Color.white);
        }
        else if (puzzle.getSpaces()[r][c] == Space.WATER) 
        {
            sc.drawRectangle(OFFSET+(BOXSIZE*c)+1, OFFSET+(BOXSIZE*r)+1,OFFSET+(BOXSIZE*c)+BOXSIZE-1, OFFSET+(BOXSIZE*r)+BOXSIZE-1, waterColor);
        }
        else if (puzzle.getSpaces()[r][c] == Space.AIR)
        {
            sc.drawRectangle(OFFSET+(BOXSIZE*c)+1, OFFSET+(BOXSIZE*r)+1,OFFSET+(BOXSIZE*c)+BOXSIZE-1, OFFSET+(BOXSIZE*r)+BOXSIZE-1, Color.white);
            sc.drawDisc(OFFSET+(BOXSIZE*c)+BOXSIZE/2, OFFSET+(BOXSIZE*r)+BOXSIZE/2, 5, airColor);
        }
        else 
        {
            sc.drawRectangle(OFFSET+(BOXSIZE*c)+1, OFFSET+(BOXSIZE*r)+1,OFFSET+(BOXSIZE*c)+BOXSIZE-1, OFFSET+(BOXSIZE*r)+BOXSIZE-1, Color.white);
        }
    }

    /**
     * Responds to a mouse click. 
     * If it's on the board, make the appropriate move and update the screen display. 
     * If it's on SOLVED?,   check the solution and display the result. 
     * If it's on CLEAR,     clear the puzzle and update the screen display. 
     */
    public void mousePressed(MouseEvent e) 
    {
        if ((e.getX()>=OFFSET && e.getX() < OFFSET + (BOXSIZE*size)) && (e.getY() >= OFFSET && e.getY() < OFFSET + (BOXSIZE*size))) 
        {
            for(int r=0;r<size;r++) 
            {
                for(int c=0;c<size;c++) 
                {
                    if((e.getX() >= OFFSET + (BOXSIZE*c) && e.getX() < OFFSET + (BOXSIZE*c) + BOXSIZE) && 
                    (e.getY() >= OFFSET + (BOXSIZE*r) && e.getY() < OFFSET + (BOXSIZE*r) + BOXSIZE)) 
                    {
                        if(SwingUtilities.isLeftMouseButton(e))
                        {
                            puzzle.leftClick(r, c);
                            updateSquare(r, c);
                        }
                        else if(SwingUtilities.isRightMouseButton(e))
                        {
                            puzzle.rightClick(r, c);
                            updateSquare(r, c);
                        }
                        break;
                    }
                }
            }
        } 
        else if((e.getX() >= OFFSET && e.getX() < OFFSET + BOXSIZE) && 
        (e.getY() >= OFFSET + (BOXSIZE*size) + 10 && e.getY() < (OFFSET + (BOXSIZE*size)+20))) 
        {
           sc.drawRectangle(OFFSET, OFFSET+(BOXSIZE*size)+20, OFFSET+(BOXSIZE*size), OFFSET+(BOXSIZE*size)+45,Color.white);
           sc.drawString(CheckSolution.isSolution(puzzle), OFFSET, OFFSET+(BOXSIZE*size)+40, Color.black);
        } 
        else if((e.getX() >= OFFSET+(BOXSIZE*(size-1)) &&
            e.getX() < OFFSET + (BOXSIZE*size)) && (e.getY() >= OFFSET+(BOXSIZE*size)+  10 && 
            e.getY() < (OFFSET + (BOXSIZE*size) + 20))) 
        {
            puzzle.clear();
            for(int r=0;r<size;r++) 
            {
                for(int c=0;c<size;c++) 
                {
                    updateSquare(r, c);
                }
            }
            sc.drawRectangle(OFFSET, OFFSET+(BOXSIZE*size)+20, OFFSET+(BOXSIZE*size), OFFSET+(BOXSIZE*size)+45,Color.white);
        }
    }

    public void mouseClicked(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

}
