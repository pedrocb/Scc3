import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.QueueBased;

/**
 * Created by Pedro on 02/05/2015.
 */
public class Station {

    private ProcessQueue<Job> waitline;
    private ProcessQueue<Job> occupiedjobs;

    public double blockedtime = 0;
    public double idletime = 0;
    public double workingtime = 0;

    public double startedworking=0;
    public double startedidling=0;
    public double startedblocking=0;

    public Station(Model modelo,String name,int size) {
        waitline = new ProcessQueue<Job>(modelo,name + " wait line",true,true);
        occupiedjobs = new ProcessQueue<Job>(modelo,name + " occupied jobs",QueueBased.FIFO,size,true,true);
    }

    public boolean occupy(Job job){
        return occupiedjobs.insert(job);
    }

    public boolean iswaitlineempty(){
        return waitline.isEmpty();
    }

    public Job getFirstJobWaiting(){
        Job job = waitline.first();
        waitline.remove(job);
        return job;
    }

    public void wait(Job job){
        waitline.insert(job);
    }

    public void desoccupy(Job job){
        occupiedjobs.remove(job);
    }
}
