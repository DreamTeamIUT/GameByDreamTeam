package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

/**
 * Created by Dylan on 30/01/2017.
 */
public class Timers {
    private static Timers timers;

    private Timers() {}

    public static Timers getInstance() {
        if(timers == null)
            timers = new Timers();

        return timers;
    }

    public void setTimer(final TimerFunction timerFunction, float delay) {
        Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    timerFunction.run();
                }
        }, delay);
    }

    public interface TimerFunction {
        void run();
    }
}
