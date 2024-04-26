package tributary.core;

import java.util.List;

public class ParallelConsume implements Runnable {
    private List<String> consumerIds;
    private List<String> partitionIds;
    private Core core;

    public ParallelConsume(List<String> consumerIds, List<String> partitionIds, Core core) {
        this.consumerIds = consumerIds;
        this.partitionIds = partitionIds;
        this.core = core;
    }

    @Override
    public void run() {
        if ((consumerIds.size()) == 0)
            return;
        String toConsume = consumerIds.remove(0);
        String partitionId = partitionIds.remove(0);
        Thread next = new Thread(new ParallelConsume(consumerIds, partitionIds, core));
        next.start();
        core.consumeEvent(toConsume, partitionId);
        try {
            next.join();
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

}
