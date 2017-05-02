package asgn1Tests;

import asgn1Election.CandidateIndex;
import asgn1Election.Vote;
import asgn1Election.VoteList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bmpic on 15/04/2016.
 */
public class VoteListTests {

    Vote v = null;

    @Before
    public void setUp() throws Exception {
        v = new VoteList(3);
    }

    @Test
    public void testAddPref() throws Exception {
        setUp();
        assertTrue(v.addPref(1));
    }

    @Test
    public void testAddNegativePref() throws Exception {
        setUp();
        assertFalse(v.addPref(-1));
    }

    @Test
    public void testNullPref() throws Exception {
        setUp();
        assertFalse(v.addPref(0));
    }

    @Test
    public void testAddOutOfRangePref() throws Exception {
        setUp();
        assertFalse(v.addPref(100));
    }

    @Test
    public void testCopyVote() throws Exception {
        setUp();
        assertFalse(v.equals(v.copyVote()));
    }

    @Test
    public void testInvertVote() throws Exception {
        addPrefs();
        v = v.invertVote();

        Vote v1 = new VoteList(3);
        v1.addPref(2);
        v1.addPref(3);
        v1.addPref(1);

        assertEquals(v1.toString(), v.toString());
    }

    @Test
    public void testIteratorNotNull() throws Exception {
        setUp();
        assertFalse(v.iterator() == null);
    }

    @Test
    public void testGetPref() throws Exception {
        addPrefs();
        CandidateIndex ci = new CandidateIndex(2);
        assertEquals(0, ci.compareTo(v.getPreference(2)));
    }

    @Test
    public void testGetNullPref() throws Exception {
        addPrefs();
        assertEquals(null, v.getPreference(-1));
    }

    @Test
    public void testGetOutOfBoundsPref() throws Exception {
        addPrefs();
        assertEquals(null, v.getPreference(10));
    }

    private void addPrefs() throws Exception{ //Adds preferences to v for testing
        setUp();
        v.addPref(3);
        v.addPref(1);
        v.addPref(2);
    }
}