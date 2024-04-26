package tributary.core;

import java.util.ArrayList;
import java.util.List;

public class TributaryCluster {
    private List<Topic> topics = new ArrayList<Topic>();

    public void addTopic(Topic topic) {
        if (!topics.stream().anyMatch(t -> t.getId().equals(topic.getId()))) {
            topics.add(topic);
        }
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void addPartitionToTopic(String topicId, Partition partition) {
        Topic topic = topics.stream().filter(t -> t.getId().equals(topicId)).findFirst().orElse(null);
        if (topic == null)
            return;
        topic.addPartition(partition);
    }

    public Topic getTopicById(String topicId) {
        return topics.stream().filter(t -> t.getId().equals(topicId)).findFirst().orElse(null);
    }

    public Partition getPartition(String partitionId) {
        Partition partition = null;
        for (Topic topic : topics) {
            partition = topic.getPartition(partitionId);
            if (partition != null)
                return partition;
        }
        return partition;
    }
}
