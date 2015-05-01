import com.sun.javafx.sg.prism.NGShape;
import desmoj.core.dist.ContDistErlang;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

import java.util.concurrent.TimeUnit;

/**
 * Created by Pedro on 19/04/2015.
 */
public abstract class Job extends SimProcess {
    protected int[] positions;
    protected ContDistErlang[] distErlangs;
    private int currentIndexPosition =0;
    public Job(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    @Override
    public void lifeCycle() {
        Modelo modelo = (Modelo)getModel();
        while(currentIndexPosition>=positions.length){
            hold(new TimeSpan(getServiceTime(),TimeUnit.MINUTES)); //Time in station
            modelo.jobqueue.insert(this);
            passivate();
        }
    }

    public double getServiceTime(){
        return distErlangs[currentIndexPosition].sample();
    }

    public int getCurrentPosition(){
        return positions[currentIndexPosition];
    }


    public int getNextPosition(){
        currentIndexPosition++;
        if(currentIndexPosition>=positions.length){
            return -1;
        }
        else{
            return positions[currentIndexPosition];
        }
    }

}
