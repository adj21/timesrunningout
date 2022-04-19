package is.hi.hbv601g.timesrunningout.Persistence;

import com.google.gson.annotations.SerializedName;

public class Word {

    @SerializedName("id")
    private int mId;
    @SerializedName("value")
    private String mValue;

    public Word(int id, String value) {
        mId = id;
        mValue = value;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }
}
