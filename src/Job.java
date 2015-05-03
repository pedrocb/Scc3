import com.sun.javafx.sg.prism.NGShape;
import desmoj.core.dist.ContDistErlang;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;
import desmoj.core.statistic.Tally;

import java.util.concurrent.TimeUnit;

/**
 * Created by Pedro on 19/04/2015.
 */
public abstract class Job extends SimProcess {
    protected int[] positions;
    protected ContDistErlang[] distErlangs;
    private int currentIndexPosition =0;
    public double arrivalinqueue;
    public Tally totaldelayinqueue;
    public Tally delayinqueue;
    public Job(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    @Override
    public void lifeCycle() {
        Modelo modelo = (Modelo)getModel();
        Boolean finished = false;
        while(!finished){
            if(currentIndexPosition<positions.length-1){
                hold(new TimeSpan(getServiceTime(), TimeUnit.MINUTES)); //Time in station
                modelo.jobqueue.insert(this);
                if(!modelo.agvqueue.isEmpty()){
                    AGV agv = modelo.agvqueue.first();
                    agv.activate();
                    modelo.agvqueue.remove(agv);
                }
                passivate();

            }
            else{
                finished = true;
            }
        }
    }

    public double getServiceTime(){
        return distErlangs[currentIndexPosition-1].sample();
    }

    public int getCurrentPosition(){
        return positions[currentIndexPosition];
    }

    public boolean move(){
        currentIndexPosition++;
        if(currentIndexPosition>=positions.length){
            return false;
        }
        else{
            return true;
        }
    }

    public int getNextPosition(){
        if(currentIndexPosition+1>=positions.length){
            return -1;
        }
        else{
            return positions[currentIndexPosition+1];
        }
    }

}
