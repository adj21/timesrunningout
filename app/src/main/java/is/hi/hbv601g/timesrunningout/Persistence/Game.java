package is.hi.hbv601g.timesrunningout.Persistence;
import java.util.*;

public class Game {

    private List<String> mWords; //Words that are being played with
    private List<Boolean> mGuessed; //mGuessed[i] is True if mWords[i] has been guessed
    private List<Integer> mTeamResults; //mTeamResults[0] stores the result of team 1, mTeamResults[1] stores the result of team 2
    private int mCurrentRound;
    private int mCurrentIndex;
    private boolean mCurrentTeam;

    public Game(List<String> words) {
        mWords = words;

        List<Boolean> guessed = new ArrayList<Boolean>(Arrays.asList(new Boolean[mWords.size()]));
        Collections.fill(guessed, Boolean.FALSE); //set the guessed values as false for the beginning of the game

        mGuessed = guessed;

        List<Integer> teamResults = Arrays.asList(0,0);//initialize the score at 0

        mTeamResults = teamResults;
        mCurrentRound = 1;//we start at round 1
        mCurrentIndex = 0;
        mCurrentTeam = false;
    }

    public Game() {
        List<Integer> teamResults = Arrays.asList(0,0);//initialize the score at 0

        mTeamResults = teamResults;
        mCurrentRound = 1;//we start at round 1
        mCurrentIndex = 0;
        mCurrentTeam = false;
    }

    public List<String> getWords() {
        return mWords;
    }

    public String getWord(int i) {
        return mWords.get(i);
    }

    public void setWords(List<String> words) {
        mWords = words;
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    public void setCurrentIndex(int index) {
        mCurrentIndex = index;
    }

    public int getCurrentRound() {
        return mCurrentRound;
    }

    public List<Boolean> getGuessed() {
        return mGuessed;
    }

    public Boolean getGuessed(int i) {
        return mGuessed.get(i);
    }

    public void setGuessed(List<Boolean> guessed) {
        mGuessed = guessed;
    }

    public List<Integer> getTeamResults() {
        return mTeamResults;
    }

    public void incrementTeamResults(int team) {
        int score = mTeamResults.get(team-1);
        mTeamResults.set(team-1,score+1);
    }

    public boolean getCurrentTeam() {
        return mCurrentTeam;
    }

    public void setCurrentTeam(boolean team) {
        mCurrentTeam = team;
    }

    public void setCurrentRound(int currentRound) {
        mCurrentRound = currentRound;
    }
}