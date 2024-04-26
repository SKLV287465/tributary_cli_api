package tributary.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Topic {
    private String id;
    private String type;
    private List<Partition> partitions = new ArrayList<Partition>();

    public Topic(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public List<Partition> getPartitions() {
        return partitions;
    }

    public void addPartition(Partition partition) {
        this.partitions.add(partition);
    }

    public void addEvent(Event<?> event) {
        Random random = new Random();
        int randomInt = random.nextInt() % partitions.size();
        Partition partition = partitions.get(randomInt);
        partition.addEvent(event);
    }

    public void addEvent(Event<?> event, String partitionId) {
        Partition partition = partitions.stream().filter(p -> p.getId().equals(partitionId)).findFirst().orElse(null);
        if (partition == null)
            return;
        partition.addEvent(event);
    }

    public Partition getPartition(String partitionId) {
        return partitions.stream().filter(p -> p.getId().equals(partitionId)).findFirst().orElse(null);
    }

    public String getInfo() {
        String result = "Topic id: " + id + "\nTopic type: " + type + "\n\nPartitions in this Topic:\n";

        for (Partition partition : partitions) {
            result += partition.getInfo();
        }

        return result;
    }

}
