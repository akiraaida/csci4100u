package ca.uoit.csci4100u.assign02;

/**
 * A simple observer interface so that classes can subscribe to the async task's callback
 */
public interface TaskListener {

    /**
     * The function that will be called after the async task completes, which will be used to update
     * the subscribers' knowledge of the price in bit coin
     * @param bitCoin The price in bit coin
     */
    void taskUpdater(float bitCoin);
}
