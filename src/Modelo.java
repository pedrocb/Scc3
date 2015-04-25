import desmoj.core.dist.ContDistExponential;
import desmoj.core.dist.ContDistUniform;
import desmoj.core.simulator.*;

import java.util.concurrent.TimeUnit;

public class Modelo extends Model {
    private ContDistExponential jobArrivalTime;
    protected ProcessQueue<Job> jobqueue;
    public static int A = 0;
    public static int B = 1;
    public static int C = 2;
    public static int D = 3;
    public static int E = 4;
    public static int STATION = 5;

    protected ProcessQueue<Job> estacaoA;
    protected ProcessQueue<Job> estacaoB;
    protected ProcessQueue<Job> estacaoC;
    protected ProcessQueue<Job> estacaoD;
    protected ProcessQueue<Job> estacaoE;


    protected ProcessQueue<AGV> agvqueue;

    public static int[][] distances = {{0,45,50,90,100,135},
            {45,0,50,100,90,100,135},
            {50,50,0,50,50,90},
            {90,100,50,0,45,50},
            {100,90,50,45,0,50},
            {135,135,90,50,50,0}};

    public static void main(String[] args){
        Modelo modelo = new Modelo();
        Experiment experiment = new Experiment("Experiment",TimeUnit.MINUTES,TimeUnit.HOURS,null);
        modelo.connectToExperiment(experiment);
        experiment.setShowProgressBar(true);
        experiment.stop(new TimeInstant(2900, TimeUnit.HOURS));
        experiment.tracePeriod(new TimeInstant(0), new TimeInstant(20));

        experiment.start();
        experiment.report();
        experiment.finish();
    }

    public Modelo(){
        super(null, "Modelo", true, true);
    }

    @Override
    public void init() {
        jobqueue = new ProcessQueue<Job>(this,"Job Queue",true,true);
        jobArrivalTime = new ContDistExponential(this,"Job Arrival Time",15.0,true,false);

        agvqueue = new ProcessQueue<AGV>(this,"Agv Queue",true,true);
        estacaoA = new ProcessQueue<Job>(this,"Estação A Queue",QueueBased.FIFO,3,true,true);
        estacaoB = new ProcessQueue<Job>(this,"Estação B Queue",QueueBased.FIFO,3,true,true);
        estacaoC = new ProcessQueue<Job>(this,"Estação C Queue",QueueBased.FIFO,4,true,true);
        estacaoD = new ProcessQueue<Job>(this,"Estação D Queue",QueueBased.FIFO,4,true,true);
        estacaoE = new ProcessQueue<Job>(this,"Estação E Queue",QueueBased.FIFO,1,true,true);

    }


    public double getJobArrivalTime(){ return jobArrivalTime.sample();}

    @Override
    public String description() {
        return null;
    }

    @Override
    public void doInitialSchedules() {
        JobGenerator generator = new JobGenerator(this,"Job Generator",true);
        generator.activate();
        AGV agv = new AGV(this,"AGV",true);
        agv.activate();
    }
}
