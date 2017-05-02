/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Election;

import java.util.Collection;

import asgn1Util.Strings;

/**
 * 
 * Subclass of <code>Election</code>, specialised to simple, first past the post voting
 * 
 * @author hogan
 * 
 */
public class SimpleElection extends Election {

	/**
	 * Simple Constructor for <code>SimpleElection</code>, takes name and also sets the 
	 * election type internally. 
	 * 
	 * @param name <code>String</code> containing the Election name
	 */
	public SimpleElection(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#findWinner()
	 */
	@Override
	public String findWinner() {
		String str = "";

		str += showResultHeader();

		str += "Counting primary votes; " + numCandidates + " alternatives available\n";
		vc.countPrimaryVotes(cds);

		str += reportCountResult();

		Candidate winCand = null;
		try {
			winCand = clearWinner(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		str += reportWinner(winCand);
		return str;
	}

	/* 
	 * (non-Javadoc)
	 * @see asgn1Election.Election#isFormal(asgn1Election.Vote)
	 */
	@Override
	public boolean isFormal(Vote v) {
		int index = 0; //Counter to ensure there is only one "1" vote
		int counter = 0; //Checks number of votes
		for (Integer v1 : v) {
			counter++;
			if (v1 < 1 || v1 > numCandidates){
				return false;
			}
			if (v1 == 1){
				index++;
			}
		}
		if (counter < numCandidates){
			return false;
		}
		if (index == 1){
			return true;
		}

		return false;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = this.name + " - Simple Voting";
		return str;
	}
	
	// Protected and Private/helper methods below///

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#clearWinner(int)
	 */
	@Override
	protected Candidate clearWinner(int wVotes) {
		Candidate winner = cds.get(cds.firstKey());
		int index = 0;
		for (Candidate c : cds.values()){
			if (c.getVoteCount() > winner.getVoteCount()){
				winner = c;
			}
		}
		return winner;
	}

	/**
	 * Helper method to create a string reporting the count result
	 * 
	 * @return <code>String</code> containing summary of the count
	 */
	private String reportCountResult() {
		String str = "\nSimple election: " + this.name + "\n\n"
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
}