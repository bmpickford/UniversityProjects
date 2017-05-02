/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Election;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.concurrent.CancellationException;

import asgn1Util.Strings;

/**
 * 
 * Subclass of <code>Election</code>, specialised to preferential, but not optional
 * preferential voting.
 * 
 * @author hogan
 * 
 */
public class PrefElection extends Election {

	/**
	 * Simple Constructor for <code>PrefElection</code>, takes name and also sets the 
	 * election type internally. 
	 * 
	 * @param name <code>String</code> containing the Election name
	 */


	public PrefElection(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#findWinner()
	 */
	@Override
	public String findWinner() {

		String win = "";
		win += showResultHeader();
		win += "Counting primary votes; " + numCandidates + " alternatives available\n";

		vc.countPrimaryVotes(cds);

		win += reportCountStatus();

		CandidateIndex lowestCand = selectLowestCandidate();

		try {
			while (clearWinner((vc.getFormalCount() / 2) + 1) == null) {
				win += prefDistMessage(cds.get(lowestCand));	//While there is no clear winner,
                vc.countPrefVotes(cds, lowestCand);				//keep distributing the votes
                lowestCand = selectLowestCandidate();			//to the next active preference
				win += reportCountStatus();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}


		try {
			winner = clearWinner((vc.getFormalCount() / 2) + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		win += reportWinner(winner);
		return win;
	}

	/* 
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#isFormal(asgn1Election.Vote)
	 */
	@Override
	public boolean isFormal(Vote v) {
		int i = 0;
		int j = 0;

		for (Integer v1 : v) {
			j = 0;
			i++;
			if (v1 == 0 || v1 > numCandidates || v1 == null){
				return false;
			}
			for (Integer v2 : v){ //Duplicate Checker
				j++;
				if (v2 == v1 && i != j){
					return false;
				}
			}
		}

		if (i < numCandidates) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
        String str = this.name + " - Preferential Voting";
		return str;
	}
	
	// Protected and Private/helper methods below///


	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#clearWinner(int)
	 */
	@Override
	protected Candidate clearWinner(int winVotes) {
		for (Candidate c : cds.values()){
			if (c.getVoteCount() >= winVotes){
				return c;
			}
		}
		return null;
	}

	/**
	 * Helper method to create a preference distribution message for display 
	 * 
	 * @param c <code>Candidate</code> to be eliminated
	 * @return <code>String</code> containing preference distribution message 
	 */
	private String prefDistMessage(Candidate c) {
		String str = "\nPreferences required: distributing " + c.getName()
				+ ": " + c.getVoteCount() + " votes\n";
		return str;
	}

	/**
	 * Helper method to create a string reporting the count progress
	 * 
	 * @return <code>String</code> containing count status  
	 */
	private String reportCountStatus() {
		String str = "\nPreferential election: " + this.name + "\n\n"
				+ candidateVoteSummary() + "\n";
		String inf = "Informal";
		String voteStr = "" + this.vc.getInformalCount();
		int length = ElectionManager.DisplayFieldWidth - inf.length()
				- voteStr.length();
		str += inf + Strings.createPadding(' ', length) + voteStr + "\n\n";

		String cast = "Votes Cast";
		voteStr = "" + this.numVotes;
		length = ElectionManager.DisplayFieldWidth - cast.length()
				- voteStr.length();
		str += cast + Strings.createPadding(' ', length) + voteStr + "\n\n";
		return str;
	}

	/**
	 * Helper method to select candidate with fewest votes
	 * 
	 * @return <code>CandidateIndex</code> of candidate with fewest votes
	 */
	private CandidateIndex selectLowestCandidate() {
		int index = 0;
		int temp = 0;

		CandidateIndex ci = new CandidateIndex(1);
		Candidate c1 = cds.get(cds.lastKey());

		if (cds.size() > 2) { //Makes sure there are mroe than 2 candidates
			for (Candidate c : cds.values()) {
				temp++;
				if (c.getVoteCount() <= c1.getVoteCount()) {
					index = temp;
					c1 = c;
				} //End if
			} //End for
		} else { 		//If only 2 candidates are left, sets index to 1 so the first candidates votes are distributed
			index = 1;	//Note: Only happens if there is a tie
		}
		ci.setValue(index);

		int i = 0;
		for (CandidateIndex ci2 : cds.keySet()){ //Ensures the candidate is still active
			if (ci.compareTo(ci2) == 0){
				i++;
				break;
			}
		}
		if (i == 0){
			index = index + 1;
			ci.setValue(index);
		}

		return ci;
	}
}