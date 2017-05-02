package asgn1Tests;

import asgn1Election.Candidate;
import asgn1Election.ElectionException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bmpic on 15/04/2016.
 */
public class CandidateTests {

    Candidate cand = null;
    Candidate cand2 = null;

    @Before
    public void setUp() throws Exception {
        cand = new Candidate("name", "party", "abbrev", 1);
        cand2 = new Candidate("name", "party", "abbrev", 1);
    }

    @Test
    public void testNegativeVoteCount() throws Exception {
        Boolean except = false;
        try {
            Candidate cand3 = new Candidate("name", "party", "abbrev", -1);
        } catch (ElectionException e) {
            except = true;
        }
        assertTrue(except);
    }


    @Test
    public void testNullName() throws Exception {
        Boolean except = false;
        try {
            Candidate cand3 = new Candidate(null, "party", "abbrev", 1);
        } catch (ElectionException e) {
            except = true;
        }
        assertTrue(except);
    }

    @Test
    public void testNullParty() throws Exception {
        Boolean except = false;
        try {
            Candidate cand3 = new Candidate("name", null, "abbrev", 1);
        } catch (ElectionException e) {
            except = true;
        }
        assertTrue(except);
    }

    @Test
    public void testNullAbbrev() throws Exception {
        Boolean except = false;
        try {
            Candidate cand3 = new Candidate("name", "party", null, 1);
        } catch (ElectionException e) {
            except = true;
        }
        assertTrue(except);
    }

    @Test
    public void testNullVoteCount() throws Exception {
        Boolean except = false;
        try {
            Candidate cand3 = new Candidate("name", "party", "abbrev", 0);
        } catch (ElectionException e) {
            except = true;
        }
        assertFalse(except);
    }

    @Test
    public void testCopy() throws Exception {
        setUp();
        assertFalse(cand.equals(cand.copy()));
    }

    @Test
    public void testIncrementVoteCount() throws Exception {
        setUp();
        cand.incrementVoteCount();
        assertEquals(cand.getVoteCount(), 2);
    }
}