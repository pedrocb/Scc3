import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Pedro on 19/04/2015.
 */
public class JobGenerator extends SimProcess{

    public JobGenerator(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    @Override
    public void lifeCycle() {
        Modelo modelo = (Modelo)getModel();
        while(true){
            Job job;
            double aux = new Random().nextDouble();
            if(aux<0.3){
                job = new Job1(modelo,"Job 1",true);
            }
            else if(aux>=0.3 &&aux<0.8){
                job = new Job2(modelo,"Job 2",true);
            }
            else{
                job = new Job3(modelo,"Job 3",true);
            }
            modelo.jobqueue.insert(job);
            hold(new TimeSpan(modelo.getJobArrivalTime(), TimeUnit.MINUTES));
        }
    }
}
