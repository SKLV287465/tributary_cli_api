package tributary;

import java.util.List;

import tributary.core.ConsumerGroup;
import tributary.core.CoreResponse;
import tributary.core.Partition;
import tributary.core.Producer;
import tributary.core.Topic;
import tributary.core.TributaryCluster;
import tributary.core.Event;

public class TestUtils {
    public static TributaryCluster getTributaryCluster(CoreResponse res) {
        return res.getTributaryCluster();
    }

    public static List<ConsumerGroup> getConsumerGroups(CoreResponse res) {
        return res.getConsumerGroups();
    }

    public static List<Producer> getProducers(CoreResponse res) {
        return res.getProducers();
    }

    public static ConsumerGroup getConsumerGroupById(CoreResponse res, String consumerGroupId) {
        return res.getConsumerGroups().stream().filter(cg -> cg.getId().equals(consumerGroupId)).findFirst()
                .orElse(null);
    }

    public static Producer getProducerById(CoreResponse res, String producerId) {
        return res.getProducers().stream().filter(p -> p.getId().equals(producerId)).findFirst().orElse(null);
    }

    public static Topic getTopicById(CoreResponse res, String topicId) {
        return res.getTributaryCluster().getTopics().stream().filter(t -> t.getId().equals(topicId)).findFirst()
                .orElse(null);
    }

    public static Partition getPartitionFromTopic(CoreResponse res, String partitionId, String topicId) {
        Topic topic = res.getTributaryCluster().getTopicById(topicId);
        return topic.getPartition(partitionId);
    }

    public static Event<?> getEventFromPartition(CoreResponse res, String eventId, String partitionId) {
        for (Topic topic : res.getTributaryCluster().getTopics()) {
            Partition partition = getPartitionFromTopic(partitionId, topic);

            if (partition == null) {
                continue;
            }

            Event<?> event = getEventFromPartition(eventId, partition);
            if (event != null) {
                return event;
            }
        }

        return null;
    }

    // HELPERS
    private static Partition getPartitionFromTopic(String partitionId, Topic topic) {
        return topic.getPartitions().stream().filter(partition -> partition.getId().equals(partitionId)).findFirst()
                .orElse(null);
    }

    private static Event<?> getEventFromPartition(String eventId, Partition partition) {
        return partition.getEvents().stream().filter(event -> event.getId().equals(eventId)).findFirst().orElse(null);
    }
}
