package asgn1Tests;

import asgn1Election.*;
import asgn1Util.NumbersException;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by bmpic on 15/04/2016.
 */
public class PrefElectionTests {

    private ExtendedElection elec;


    @Before
    public void setup() throws FileNotFoundException, ElectionException, IOException, NumbersException {
        elec = new ExtendedElection("MinMorgulVale");
        elec.loadDefs();
        elec.loadVotes();
    }

    @Test
    public void testLoadVotesMinMorgulValeVotes() throws Exception{
        setup();
        VoteCollection vc = (VoteCollection) elec.getVoteCollection();
        assertTrue((vc.getFormalCount()==18)&&(vc.getInformalCount()==3));
    }

    @Test(expected = ElectionException.class)
    public void testLoadDefsMissingElectorateSummary() throws Exception {
        elec = new ExtendedElection("TestPref");
        elec.loadDefs();
    }

    @Test(expected = ElectionException.class)
    public void testLoadDefsIncorrectSeatName() throws Exception {
        elec = new ExtendedElection("TestPref2");
        elec.loadDefs();
    }

    @Test(expected = ElectionException.class)
    public void testLoadDefsIncorrectEnrolment() throws Exception {
        elec = new ExtendedElection("TestPref3");
        elec.loadDefs();
    }

    @Test(expected = ElectionException.class)
    public void testLoadDefsIncorrectNumCandidates() throws Exception {
        elec = new ExtendedElection("TestPref4");
        elec.loadDefs();
    }

    @Test(expected = ElectionException.class)
    public void testLoadDefsMissingSeatNameNumber() throws Exception {
        elec = new ExtendedElection("TestPref5");
        elec.loadDefs();
    }

    @Test(expected = ElectionException.class)
    public void testLoadDefsSeatNameLineNull() throws Exception {
        elec = new ExtendedElection("TestPref6");
        elec.loadDefs();
    }

    @Test(expected = ElectionException.class)
    public void testLoadDefsMissingEnrolmentNumber() throws Exception {
        elec = new ExtendedElection("TestPref7");
        elec.loadDefs();
    }

    @Test(expected = ElectionException.class)
    public void testLoadDefsEnrolmentLineNull() throws Exception {
        elec = new ExtendedElection("TestPref8");
        elec.loadDefs();
    }

    @Test(expected = ElectionException.class)
    public void testLoadDefsMissingNumCandidatesNumber() throws Exception {
        elec = new ExtendedElection("TestPref9");
        elec.loadDefs();
    }

    @Test(expected = ElectionException.class)
    public void testLoadDefsNumCandidatesLineNull() throws Exception {
        elec = new ExtendedElection("TestPref10");
        elec.loadDefs();
    }

    @Test(expected = ElectionException.class)
    public void testLoadDefsCandidateLineNull() throws Exception {
        elec = new ExtendedElection("TestPref11");
        elec.loadDefs();
    }

    @Test(expected = NumbersException.class)
    public void testLoadDefsStringInsteadOfIntegerEnrolment() throws Exception {
        elec = new ExtendedElection("TestPref12");
        elec.loadDefs();
    }

    @Test(expected = NumbersException.class)
    public void testLoadDefsStringInsteadOfIntegerNumCandidates() throws Exception {
        elec = new ExtendedElection("TestPref13");
        elec.loadDefs();
    }

    @Test(expected = ElectionException.class)
    public void testLoadVotesNullLine() throws Exception {
        elec = new ExtendedElection("TestPref");
        elec.loadVotes();
    }

    @Test(expected = ElectionException.class)
    public void testLoadVotesNotEnoughVotesInLine() throws Exception {
        elec = new ExtendedElection("TestPref2");
        elec.loadVotes();
    }

    @Test(expected = ElectionException.class)
    public void testLoadVotesTooManyVotesInLine() throws Exception {
        elec = new ExtendedElection("TestPref3");
        elec.loadVotes();
    }

    @Test
    public void testFindWinner() throws Exception {
        setup();
        String win = elec.findWinner();

        //Manually entered text file
        String fname = "Output.txt";
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
    public void testIsFormalNormalVote() throws Exception {
        setup();
        Vote v = new VoteList(3);
        v.addPref(1);
        v.addPref(2);
        v.addPref(3);

        assertTrue(elec.isFormal(v));
    }

    @Test
    public void testIsFormalDuplicatePrimaryVote() throws Exception {
        setup();
        Vote v = new VoteList(3);
        v.addPref(1);
        v.addPref(1);
        v.addPref(3);
        assertFalse(elec.isFormal(v));
    }

    @Test
    public void testIsFormalDuplicateOtherVote() throws Exception {
        setup();
        Vote v = new VoteList(3);
        v.addPref(1);
        v.addPref(3);
        v.addPref(3);
        assertFalse(elec.isFormal(v));
    }

    @Test
    public void testIsFormalOutOfRangeVote() throws Exception {
        setup();
        Vote v = new VoteList(3);
        v.addPref(1);
        v.addPref(6);
        v.addPref(3);
        assertFalse(elec.isFormal(v));
    }

    @Test
    public void testIsFormalNegativeVote() throws Exception {
        setup();
        Vote v = new VoteList(3);
        v.addPref(1);
        v.addPref(2);
        v.addPref(-3);
        assertFalse(elec.isFormal(v));
    }

    @Test
    public void testIsFormalZeroVote() throws Exception {
        setup();
        Vote v = new VoteList(3);
        v.addPref(1);
        v.addPref(2);
        v.addPref(0);
        assertFalse(elec.isFormal(v));
    }

    @Test
    public void testIsFormalNoPrimaryVote() throws Exception {
        setup();
        Vote v = new VoteList(3);
        v.addPref(2);
        v.addPref(2);
        v.addPref(3);
        assertFalse(elec.isFormal(v));
    }

    @Test
    public void testIsFormalMissingVote() throws Exception {
        setup();
        Vote v = new VoteList(3);
        v.addPref(1);
        assertFalse(elec.isFormal(v));
    }
}