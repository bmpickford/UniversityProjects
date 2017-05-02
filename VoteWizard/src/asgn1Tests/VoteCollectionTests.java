package asgn1Tests;

import asgn1Election.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Created by bmpic on 17/04/2016.
 */
public class VoteCollectionTests {

    Collection vc;
    ExtendedElection elec;
    TreeMap<CandidateIndex, Candidate> cds;

    @Before
    public void setUp() throws Exception {
        elec = new ExtendedElection("MinMorgulVale");
        elec.loadDefs();
        elec.loadVotes();
        cds = new TreeMap<CandidateIndex, Candidate>();
        vc = elec.getVoteCollection();
    }

    @Test
    public void testCountPrimaryVotesElection() throws Exception {
        setUp();
        int index = 1;

        for (Candidate c : elec.getCandidates()){
            cds.put(new CandidateIndex(index), c);
            index++;
        }
        vc.countPrimaryVotes(cds);

        ArrayList<Candidate> cands= new ArrayList<Candidate>();
        cands.add(new Candidate("a", "b", "c", 8));
        cands.add(new Candidate("a", "b", "c", 7));
        cands.add(new Candidate("a", "b", "c", 3));

        assertTrue(checkVotes(cands));
    }

    @Test
    public void testCountPrefVotesPrefElection() throws Exception {
        setUp();
        int index = 1;

        for (Candidate c : elec.getCandidates()){
            cds.put(new CandidateIndex(index), c);
            index++;
        }
        vc.countPrimaryVotes(cds);
        vc.countPrefVotes(cds, new CandidateIndex(3));

        ArrayList<Candidate> cands= new ArrayList<Candidate>();
        cands.add(new Candidate("a", "b", "c", 10));
        cands.add(new Candidate("a", "b", "c", 8));

        assertTrue(checkVotes(cands));
    }

    private boolean checkVotes(ArrayList<Candidate> cands){
        int index = 0;
        int index2 = 0;
        int counter = 0;
        for (Candidate c : elec.getCandidates()){
            index++;
            index2 = 0;
            for (Candidate c1 : cands){
                index2++;
                if (index2 == index){
                    counter++; //Loops through the loaded cand and
                    break;     //manually made cands to ensure they are the same
                }
            }
        }
        if (counter == cds.size()){
            return true;
        }
        return false;
    }

    @Test
    public void testEmptyTheCollection() throws Exception {
        setUp();
        assertTrue(vc != null);
        vc.emptyTheCollection();
        assertTrue(vc.getFormalCount() == 0);
        assertTrue(vc.getInformalCount() == 0);
    }

    @Test
    public void testIncludeFormalVoteUpdatesInformalCount() throws Exception {
        Vote v = new VoteList(3);
        v.addPref(1);
        v.addPref(2);
        v.addPref(3);
        vc.emptyTheCollection();
        vc.includeFormalVote(v);

        assertEquals(1, vc.getFormalCount());
    }

    @Test
    public void testUpdateInformalCount() throws Exception {
        setUp();
        assertEquals(3, vc.getInformalCount());
    }
}