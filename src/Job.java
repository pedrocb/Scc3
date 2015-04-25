import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

/**
 * Created by Pedro on 19/04/2015.
 */
public abstract class Job extends SimProcess {
    protected int[] positions;
    private int currentIndexPosition =-1;
    public Job(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    @Override
    public void lifeCycle() {

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
