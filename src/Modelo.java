import desmoj.core.dist.ContDistErlang;
import desmoj.core.dist.ContDistExponential;
import desmoj.core.simulator.*;

import java.util.concurrent.TimeUnit;

public class Modelo extends Model {
    private ContDistExponential jobArrivalTime;
    public static int A = 0;
    public static int B = 1;
    public static int C = 2;
    public static int D = 3;
    public static int E = 4;
    public static int STATION = 5;

    public static ContDistErlang[] distErlangs1;
    public static ContDistErlang[] distErlangs2;
    public static ContDistErlang[] distErlangs3;




    protected ProcessQueue<Job> jobqueue;

    protected ProcessQueue<Job> estacao[];


    protected ProcessQueue<AGV> agvqueue;

    public static int[][] distances =
            {{0,45,50,90,100,135},
            {45,0,50,100,90,100,135},
            {50,50,0,50,50,90},
            {90,100,50,0,45,50},
            {100,90,50,45,0,50},
            {135,135,90,50,50,0}};

    public static void main(String[] args){
        Modelo modelo = new Modelo();
        Experiment experiment = new Experiment("Experiment",TimeUnit.SECONDS,TimeUnit.MINUTES,null);
        modelo.connectToExperiment(experiment);
        experiment.setShowProgressBar(true);
        experiment.stop(new TimeInstant(2900, TimeUnit.HOURS));
        experiment.tracePeriod(new TimeInstant(0), new TimeInstant(20, TimeUnit.HOURS));

        experiment.start();
        experiment.report();
        experiment.finish();
    }

    public Modelo(){
        super(null, "Modelo", true, true);
    }

    @Override
    public void init() {

        jobArrivalTime = new ContDistExponential(this,"Job Arrival Time",15.0,true,false);
        jobArrivalTime.setNonNegative(true);

        agvqueue = new ProcessQueue<AGV>(this,"Agv Queue",true,true);

        estacao = new ProcessQueue[5];
        estacao[A] = new ProcessQueue<Job>(this,"Estação A Queue",QueueBased.FIFO,3,true,true);
        estacao[B] = new ProcessQueue<Job>(this,"Estação B Queue",QueueBased.FIFO,3,true,true);
        estacao[C] = new ProcessQueue<Job>(this,"Estação C Queue",QueueBased.FIFO,4,true,true);
        estacao[D] = new ProcessQueue<Job>(this,"Estação D Queue",QueueBased.FIFO,4,true,true);
        estacao[E] = new ProcessQueue<Job>(this,"Estação E Queue",QueueBased.FIFO,1,true,true);

        distErlangs1 = new ContDistErlang[4];
        distErlangs1[0] = new ContDistErlang(getModel(),"1º Erlang Job 1", 2,30.0,true,true);
        distErlangs1[1] = new ContDistErlang(getModel(),"2º Erlang Job 1",2,36.0,true,true);
        distErlangs1[2] = new ContDistErlang(getModel(),"3º Erlang Job 1",2,51.0,true,true);
        distErlangs1[3] = new ContDistErlang(getModel(),"4º Erlang Job 1",2,30.0,true,true);

        distErlangs2 = new ContDistErlang[3];
        distErlangs2[0] = new ContDistErlang(getModel(),"1º Erlang Job 2", 2,66.0,true,true);
        distErlangs2[1] = new ContDistErlang(getModel(),"2º Erlang Job 2",2,48.0,true,true);
        distErlangs2[2] = new ContDistErlang(getModel(),"3º Erlang Job 2",2,45.0,true,true);

        distErlangs3 = new ContDistErlang[5];
        distErlangs3[0] = new ContDistErlang(getModel(),"1º Erlang Job 3", 2,72.0,true,true);
        distErlangs3[1] = new ContDistErlang(getModel(),"2º Erlang Job 3",2,15.0,true,true);
        distErlangs3[2] = new ContDistErlang(getModel(),"3º Erlang Job 3",2,42.0,true,true);
        distErlangs3[3] = new ContDistErlang(getModel(),"4º Erlang Job 3",2,54.0,true,true);
        distErlangs3[4] = new ContDistErlang(getModel(),"5º Erlang Job 3",2,60.0,true,true);

        jobqueue = new ProcessQueue<Job>(this,"Job Queue",true,true);
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
