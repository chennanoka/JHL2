package nan.com.jobhuntlog.Service;

/**
 * Created by NAN on 2017-01-08.
 */
public class AutoResetEvent {
    private final Object mmonitor = new Object();
    private volatile boolean misOpen = false;

    public AutoResetEvent(boolean open){
        misOpen = open;
    }

    public void waitOne() throws InterruptedException{
        synchronized (mmonitor) {
            while (!misOpen) {
                mmonitor.wait();
            }
            misOpen = false;
        }
    }

    public void waitOne(long timeout) throws InterruptedException{
        synchronized (mmonitor) {
            long t = System.currentTimeMillis();
            while (!misOpen) {
                mmonitor.wait(timeout);
                // Check for timeout
                if (System.currentTimeMillis() - t >= timeout)
                    break;
            }
            misOpen = false;
        }
    }

    public void set(){
        synchronized (mmonitor) {
            misOpen = true;
            mmonitor.notify();
        }
    }

    public void reset(){
        misOpen = false;
    }
}