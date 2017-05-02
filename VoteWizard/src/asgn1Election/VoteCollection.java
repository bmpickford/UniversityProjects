/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Election;

import sun.java2d.pipe.SpanShapeRenderer;

import java.util.*;

/**
 * 
 * <p>Class to manage a collection of <code>Vote</code>s as specified by the 
 * {@link asgn1Election.Collection} interface. This implementation is based on  
 * a <code>ArrayList<E></code> data structure to enable convenient additions to the 
 * list.</p>
 * 
 * <p>The private methods {@link #getPrimaryKey(Vote) } and 
 * {@link #getPrefthKey(Vote, TreeMap, int)} are crucial to your success. Some guidance 
 * is given for these methods through comments in situ, but this is generous, and well 
 * beyond what would be provided in real practice.</p>
 * 
 * <p>As before, please note the name clash between <code>asgn1Election.Collection</code>
 * and <code>java.util.Collection</code>. 
 * 
 * @author hogan
 *
 */
public class VoteCollection implements Collection {
	/** Holds all the votes in this seat */
	private ArrayList<Vote> voteList;

	ArrayList<Vote> elimVoteList = new ArrayList<Vote>();

	/** Number of candidates competing for this seat */
	private int numCandidates;

	/** Number of formal votes read during the election and stored in the collection */
	private int formalCount;

	/** Number of invalid votes received during the election */
	private int informalCount;

	/**
	 * Simple Constructor for the <code>VoteCollection</code> class.
	 * Most information added through mutator methods. 
	 * 
	 * @param numCandidates <code>int</code> number of candidates competing for 
	 * this seat
	 * @throws ElectionException if <code>NOT inRange(numCandidates)</code>
	 */
	public VoteCollection(int numCandidates) throws ElectionException {
		CandidateIndex c = new CandidateIndex(1);
		if (c.inRange(numCandidates)) {
			this.numCandidates = numCandidates;
			voteList = new ArrayList<Vote>();
		} else {
			throw new ElectionException("Number of candidates is not in range");
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Collection#countPrefVotes(java.util.TreeMap, asgn1Election.CandidateIndex)
	 */
	@Override
	public void countPrefVotes(TreeMap<CandidateIndex, Candidate> cds,
			CandidateIndex elim) {

		Candidate cand = null; //Initialises Candidate
		int preference = 2; //Starts with 2nd Preference

		for (Vote v : voteList) {
			CandidateIndex ci = getPrimaryKey(v);
			if (ci.compareTo(elim) == 0) { 							//If elim was voted no.1
				elimVoteList.add(v); 								//Adds vote to arraylist of eliminated candidates who were voted 1
				cand = cds.get(getPrefthKey(v, cds, preference)); 	//Find candidate of next active candidate
				cand.incrementVoteCount(); 							//Increments their voteCount
			} //End if
		} // End for

		for (Vote v : elimVoteList){								//If candidate is eliminated AND
			CandidateIndex ci = getPrefthKey(v, cds, preference);	//voted 2nd, find next active cand
			if (ci.compareTo(elim) == 0) {							//and increments their vote
				cand = cds.get(getPrefthKey(v, cds, preference + 1));
				cand.incrementVoteCount();
			} //End if
		} //End for
		cds.remove(elim); //Removes the candidate from the treeMap
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Collection#countPrimaryVotes(java.util.TreeMap)
	 */
	@Override
	public void countPrimaryVotes(TreeMap<CandidateIndex, Candidate> cds) {
		Candidate cand = cds.get(cds.firstKey()); //Initialises cand
		CandidateIndex ci = new CandidateIndex(0);//Initialises ci

		for (Vote v : voteList) {
			ci = getPrimaryKey(v);		//Finds the candidate that was
			cand = cds.get(ci);			//voted 1 in the vote, and
			cand.incrementVoteCount();	//increments their voteCount
		} //End for

		cds.put(ci, cand);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Collection#emptyTheVoteList()
	 */
	@Override
	public void emptyTheCollection() {
		voteList.clear();
		numCandidates = 0;
		informalCount = 0;
		formalCount = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Collection#getFormalCount()
	 */
	@Override
	public int getFormalCount() {
		return formalCount;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Collection#getInformalCount()
	 */
	@Override
	public int getInformalCount() {
		return informalCount;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Collection#includeFormalVote(asgn1Election.Vote)
	 */
	@Override
	public void includeFormalVote(Vote v) {
		voteList.add(v);
		formalCount++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Collection#updateInformalCount()
	 */
	@Override
	public void updateInformalCount() {
		informalCount++;
	}
	
	/**
	 * 
	 * <p>Important helper method to find the candidate in the current vote 
	 * corresponding to a given preference. Ideally we seek the candidate who is 
	 * preference <emphasis>pref</emphasis>, but often some of the higher preferences
	 * for a given vote may already have been eliminated. So this method finds not 
	 * the <emphasis>pref-th</emphasis> candidate, but the pref-th 
	 * <emphasis>active</emphasis> candidate</p>
	 * 
	 * <p>You must therefore find a way to deal with the candidate set, to work out 
	 * which ones are still active and which aren't. This method is quite specific 
	 * to the preferential election and so does not get used for the simple ballot.</p>
	 * 
	 * @param v <code>Vote</code> to be examined to find the pref-th active candidate
	 * @param cds <code>TreeMap</code> set of all active candidates
	 * @param pref <code>int</code> specifies the preference we are looking for
	 * @return <code>(key = prefth preference still active) OR null</code>
	 * 
	 */
	private CandidateIndex getPrefthKey(Vote v,TreeMap<CandidateIndex, Candidate> cds, int pref) {
		CandidateIndex ci = new CandidateIndex(1);
		ci = findNextActivePref(v, cds, pref);

		while (ci == null){
			ci = findNextActivePref(v, cds, pref); 	//If the next most active pref is not found,
			pref = pref + 1;						//keep looping through the voters preferences
		} //End while								//until the next active candidate is found

		return ci;
	}

	/**Private method to find the next active preference in the treemap
	 *Used to help reduce the amount of code and complexity in
	 *getPrefthKey
	 * @param v <Code>Vote</Code>
	 * @param cds <Code>Treemap</Code>
	 * @param pref <Code>int</Code> of the preffered preference
     * @return <Code>CandidateIndex</Code> of the next active candidate in a vote
     */
	private CandidateIndex findNextActivePref(Vote v,TreeMap<CandidateIndex, Candidate> cds, int pref){
		int index = 0;
		CandidateIndex ci = new CandidateIndex(1);
		for (Integer vote : v) {
			index++;
			if (vote == pref) {
				ci.setValue(index);
				for (CandidateIndex c : cds.keySet()){ 	//Loops through the cds
					if (c.compareTo(ci) == 0){			//and only returns a CandidateIndex
						return ci;						//if that CandidateIndex is currently active
					} //End if
				} //End for
			} //End if
		} //End for
		return null; //Otherwise return null
	}
	/**
	 * <p>Important helper method to find the first choice candidate in the current 
	 * vote. This is always undertaken prior to distribution of preferences and so it 
	 * is not necessary to test whether the candidate remains in the set.</p>
	 * 
	 * @param v <code>Vote</code> from which first pref is to be obtained
	 * @return <code>CandidateIndex</code> of the first preference candidate
	 */
	private CandidateIndex getPrimaryKey(Vote v) {
		CandidateIndex ci = new CandidateIndex(0);
		int index = 0;
		for (Integer v1 : v) {
			index++;
			if (v1.equals(1)) {
				ci.setValue(index);
				break;
			}
		}
		return ci;
    }


	/**
	 * <p>Simple method to find the count of either formal or informal vote</p>
	 *
	 * @param formal <Code>boolean</Code> which tells the method whether its counting formal or informal votes
	 * @return Count of votes
     */


}
