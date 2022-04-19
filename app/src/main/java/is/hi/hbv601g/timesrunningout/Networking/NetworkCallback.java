package is.hi.hbv601g.timesrunningout.Networking;

public interface NetworkCallback<T> {

    void onSuccess(T result);

    void onFailure(String errorString);
}