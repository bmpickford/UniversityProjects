package asgn1Tests;

import asgn1Election.CandidateIndex;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bmpic on 15/04/2016.
 */
public class CandidateIndexTests {

    CandidateIndex ci = null;
    CandidateIndex ci2 = null;
    CandidateIndex ci3 = null;

    @Before
    public void setUp() throws Exception {
        ci = new CandidateIndex(1);
        ci2 = new CandidateIndex(1);
        ci3 = new CandidateIndex(3);
    }

    @Test
    public void testInRange() throws Exception {
        setUp();
        assertFalse(ci.inRange(0));
        assertTrue(ci.inRange(1));
        assertTrue(ci.inRange(15));
        assertFalse(ci.inRange(16));
        assertFalse(ci.inRange(-1));
        assertFalse(ci.inRange(2000));
    }

    @Test
    public void testCompareSame() throws Exception {
        setUp();
        assertEquals(ci.compareTo(ci2), 0);
    }

    @Test
    public void testCompareLower() throws Exception {
        setUp();
        assertEquals(ci.compareTo(ci3), -1);
    }

    @Test
    public void testCompareHigher() throws Exception {
        setUp();
        assertEquals(ci3.compareTo(ci), 1);
    }

    @Test
    public void testCopy() throws Exception {
        setUp();
        assertFalse(ci.equals(ci.copy()));
    }

    @Test
    public void testIncrementIndex() throws Exception {
        setUp();
        ci.incrementIndex();
        assertEquals(Integer.parseInt(ci.toString()), 2);
    }

    @Test
    public void testSetValue() throws Exception {
        setUp();
        ci.setValue(5);
        assertEquals(Integer.parseInt(ci.toString()), 5);
    }

    @Test
    public void testSetNullValue() throws Exception{
        setUp();
        ci.setValue(0);
        assertEquals(Integer.parseInt(ci.toString()), 0);
    }

    @Test
    public void testSetNegativeValue() throws Exception{
        setUp();
        ci.setValue(-5);
        assertEquals(Integer.parseInt(ci.toString()), -5);
    }

}