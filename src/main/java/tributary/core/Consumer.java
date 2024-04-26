package tributary.core;

public class Consumer {
    private String id;

    public Consumer(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public synchronized void consumeEvent(Topic subscribedTopic, String partitionId) {
        Partition partition = subscribedTopic.getPartition(partitionId);
        partition.consume(this.id);
    }

    public String getInfo() {
        return "Consumer Id: " + id + "\n";
    }
}
