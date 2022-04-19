package is.hi.hbv601g.timesrunningout.Services;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import is.hi.hbv601g.timesrunningout.Networking.NetworkCallback;
import is.hi.hbv601g.timesrunningout.Networking.NetworkManager;
import is.hi.hbv601g.timesrunningout.Persistence.Game;
import is.hi.hbv601g.timesrunningout.Persistence.Word;

public class WordService {

    private List<String> mWords;
    private static final String TAG = "WordService";

    public WordService(List<String> Words) {
        mWords = Words;
    }

    public List<String> getWords() {
        return mWords;
    }

    //public boolean isDuplicate(Word word) {} TODO: needed for custom gamemode

    public Game setGuessed(int index, Game game) {
        List<Boolean> guessed = game.getGuessed();
        guessed.set(index, true);
        game.setGuessed(guessed);
        return game;
    }

    public Game setUnguessed(int index, Game game) {
        List<Boolean> guessed = game.getGuessed();
        guessed.set(index, false);
        game.setGuessed(guessed);
        return game;
    }

    public boolean isAllGuessed(Game game) {
        List<Boolean> guessed = game.getGuessed();
        for (Boolean w : guessed) {
            if (!w) return false;
        }
        return true;
    }


    public Game setAllUnguessed(Game game) {
        List<Boolean> guessed = game.getGuessed();
        List<Boolean> guessedNew = new ArrayList<Boolean>(Arrays.asList(new Boolean[guessed.size()]));
        Collections.fill(guessedNew, Boolean.FALSE);
        game.setGuessed(guessedNew);
        return game;
    }

    //public String findByID(long ID) {}

    //public Word save(Word word) {} TODO: needed for custom gamemode

    //public void delete(Word word) {} TODO: might be needed

}
