package GameOfLife;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

// cloc Cell.java
//-------------------------------------------------------------------------------
//Language                     files          blank        comment           code
//-------------------------------------------------------------------------------
//Java                             1             12              0             37
//-------------------------------------------------------------------------------
//
// Also 4 Tests

public class CellTest {
    @Test
    // Eine neue "Konstruierte" Zelle ist tot
    public void newCellIsDead() {
        Cell c = new Cell(0, 0);
        assertFalse(c.getStatus());
    }

    @Test
    // Nachdem eine tote Zelle revived wird, ist sie Lebendig
    public void cellIsAliveAfterRevival() {
        Cell c = new Cell(0, 0);
        assertFalse(c.getStatus());
        c.revive();
        assertTrue(c.getStatus());
    }

    @Test
    // Eine Tote Zelle soll ein Leerzeichen repräsentieren, während eine lebende Zelle von einem "X" repräsentiert werden soll
    public void cellHasCorrectStringRepresentation() {
        Cell x = new Cell(0, 0);
        Cell y = new Cell(0, 0).revive();

        assertEquals(" ", x.getAsString());
        assertEquals("X", y.getAsString());
    }

    @Test
    // Eine Tote Zelle entspricht einer 0, und eine Lebende Zelle einer 1
    public void cellHasCorrectNumberRepresentation() {
        Cell x = new Cell(0, 0);
        Cell y = new Cell(0, 0).revive();

        assertEquals(0, x.getAsInt());
        assertEquals(1, y.getAsInt());
    }
}
