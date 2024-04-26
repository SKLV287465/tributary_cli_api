package tributary.core;

import java.util.List;

public class ParallelProduce implements Runnable {
    private List<String> producerIds;
    private List<String> topicIds;
    private List<String> filenames;
    private Core core;

    public ParallelProduce(List<String> producerIds, List<String> topicIds, List<String> filenames, Core core) {
        this.producerIds = producerIds;
        this.topicIds = topicIds;
        this.filenames = filenames;
        this.core = core;
    }

    @Override
    public void run() {
        if ((filenames.size()) == 0)
            return;
        String file = filenames.remove(0);
        String producerId = producerIds.remove(0);
        String topicId = topicIds.remove(0);
        Thread next = new Thread(new ParallelProduce(producerIds, topicIds, filenames, core));
        next.start();
        core.produceEvent(producerId, topicId, file);
        try {
            next.join();
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
}
