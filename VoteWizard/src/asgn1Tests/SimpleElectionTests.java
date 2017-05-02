package asgn1Tests;

import asgn1Election.Election;
import asgn1Election.SimpleElection;
import asgn1Election.Vote;
import asgn1Election.VoteList;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.junit.Assert.*;

/**
 * Created by bmpic on 16/04/2016.
 */
public class SimpleElectionTests {

    Election elec;

    @Before
    public void setUp() throws Exception {
        elec = new SimpleElection("MinMorgulValeSimple");
        elec.loadDefs();
        elec.loadVotes();
    }

    @Test
    public void testFindWinner() throws Exception {
        String win = elec.findWinner();

        //Manually entered text file
        String fname = "OutputSimple.txt";
        String path = ".//data//" + fname;
        BufferedReader br = null;
        String line;

        // Lines according to specification
        // Get buffered reader to begin with
        br = new BufferedReader(new FileReader(path));

        String out = "";

        // Process file into string
        while ((line = br.readLine()) != null) {
            out += line;
            out += "\n";
        }
        assertEquals(win, out);
    }

    @Test
    public void testIsFormalNormal() throws Exception {
        setUp();
        Vote v = new VoteList(3);
        v.addPref(1);
        v.addPref(2);
        v.addPref(3);
        assertTrue(elec.isFormal(v));
    }

    @Test
    public void testIsFormalDuplicatePrimaryVote() throws Exception {
        setUp();
        Vote v = new VoteList(3);
        v.addPref(1);
        v.addPref(1);
        v.addPref(3);
        assertFalse(elec.isFormal(v));
    }

    @Test
    public void testIsFormalDuplicateOtherVote() throws Exception {
        setUp();
        Vote v = new VoteList(3);
        v.addPref(1);
        v.addPref(3);
        v.addPref(3);
        assertTrue(elec.isFormal(v));
    }

    @Test
    public void testIsFormalNegativeVote() throws Exception {
        setUp();
        Vote v = new VoteList(3);
        v.addPref(1);
        v.addPref(-2);
        v.addPref(3);
        assertFalse(elec.isFormal(v));
    }

    @Test
    public void testIsFormalOutOfRangeVote() throws Exception {
        setUp();
        Vote v = new VoteList(3);
        v.addPref(1);
        v.addPref(8);
        v.addPref(3);
        assertFalse(elec.isFormal(v));
    }

    @Test
    public void testIsFormalZeroVote() throws Exception {
        setUp();
        Vote v = new VoteList(3);
        v.addPref(1);
        v.addPref(0);
        v.addPref(3);
        assertFalse(elec.isFormal(v));
    }

    @Test
    public void testIsFormalNotEnoughVotes() throws Exception {
        setUp();
        Vote v = new VoteList(3);
        v.addPref(1);
        v.addPref(3);
        assertFalse(elec.isFormal(v));
    }

    @Test
    public void testIsFormalNoPrimaryVote() throws Exception {
        setUp();
        Vote v = new VoteList(3);
        v.addPref(2);
        v.addPref(3);
        v.addPref(3);
        assertFalse(elec.isFormal(v));
    }
}