package is.hi.hbv601g.timesrunningout;
import java.util.List;

public class Game {

    private List<String> mWords; //Words that are being played with
    private List<Boolean> mGuessed; //mGuessed[i] is True if mWords[i] has been guessed
    private List<Integer> mTeamResults; //mTeamResults[0] stores the result of team 1, mTeamResults[1] stores the result of team 2
    private int mCurrentInt;

    public Game(List<String> words, List<Boolean> guessed, List<Integer> teamResults, int currentInt) {
        mWords = words;
        mGuessed = guessed;
        mTeamResults = teamResults;
        mCurrentInt = currentInt;
    }

    public List<String> getWords() {
        return mWords;
    }

    public void setWords(List<String> words) {
        mWords = words;
    }

    public List<Boolean> getGuessed() {
        return mGuessed;
    }

    public void setGuessed(List<Boolean> guessed) {
        mGuessed = guessed;
    }

    public List<Integer> getTeamResults() {
        return mTeamResults;
    }

    public void setTeamResults(List<Integer> teamResults) {
        mTeamResults = teamResults;
    }

    public int getCurrentInt() {
        return mCurrentInt;
    }

    public void setCurrentInt(int currentInt) {
        mCurrentInt = currentInt;
    }
}