package tributary.core;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import tributary.core.rebalancingStrategy.RangeStrategy;
import tributary.core.rebalancingStrategy.RoundRobinStrategy;

public class Core {
    private TributaryCluster tributaryCluster = new TributaryCluster();
    private List<ConsumerGroup> consumerGroups = new ArrayList<ConsumerGroup>();
    private List<Producer> producers = new ArrayList<Producer>();

    public Core() {
        super();
    }

    public CoreResponse createTopic(String id, String type) {
        if (!type.equals("Integer") && !type.equals("String")) {
            System.out.println("Invalid type. Type must be either \"Integer\" or \"String\". No Topic created.");
        } else {
            Topic newTopic = new Topic(id, type);
            tributaryCluster.addTopic(newTopic);
            System.out.println("Topic with id " + id + " and type " + type + " successfully created.");
        }
        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }

    public CoreResponse createPartition(String topicId, String partitionId) {
        Partition newPartition = new Partition(partitionId);
        tributaryCluster.addPartitionToTopic(topicId, newPartition);
        System.out.println(
                "Partition with partitionId " + partitionId + " successfully added to topicId " + topicId + ".");
        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }

    public CoreResponse createConsumerGroup(String id, String topicId, String rebalancingStrategy) {
        if (!rebalancingStrategy.equals("Range") && !rebalancingStrategy.equals("RoundRobin")) {
            System.out.println(
                    "Invalid rebalancingStrategy. RebalancingStrategy must be either \"Range\" or \"RoundRobin\"");
        } else {
            Topic topic = tributaryCluster.getTopicById(topicId);
            if (topic == null) {
                System.out.println("Invalid topicId. A topic with that topicId does not exist.");
                return new CoreResponse(tributaryCluster, consumerGroups, producers);
            }
            ConsumerGroup consumerGroup = new ConsumerGroup(id, topic.getType(), topic, rebalancingStrategy);
            consumerGroups.add(consumerGroup);
            System.out.println("A new consumerGroup with id " + id + " was created and assigned to topicId " + topicId
                    + " with rebalancingStrategy " + rebalancingStrategy);
        }
        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }

    public CoreResponse createConsumer(String consumerGroupId, String consumerId) {
        ConsumerGroup consumerGroup = consumerGroups.stream().filter(cg -> cg.getId().equals(consumerGroupId))
                .findFirst().orElse(null);
        if (consumerGroup == null) {
            System.out.println("Invalid consumerGroupId, a consumerGroup with that id does not exist");
        } else {
            Consumer consumer = new Consumer(consumerId);
            System.out.println("Consumer with id " + consumerId + " created and assigned to " + consumerGroupId);
            consumerGroup.addConsumer(consumer);
        }
        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }

    public CoreResponse deleteConsumer(String consumerId) {
        ConsumerGroup consumerGroup = consumerGroups.stream().filter(cg -> cg.hasConsumer(consumerId)).findFirst()
                .orElse(null);
        if (consumerGroup == null) {
            System.out.println("No consumerGroup has that consumerId");
        } else {
            consumerGroup.deleteConsumer(consumerId);
            System.out.println("Consumer " + consumerId + " successfully deleted.");
        }
        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }

    public CoreResponse createProducer(String id, String type, String allocationStrategy) {
        if (!type.equals("Integer") && !type.equals("String")) {
            System.out.println("Invalid type. Type must be either \"Integer\" or \"String\".");
        } else if (!allocationStrategy.equals("Random") && !allocationStrategy.equals("Manual")) {
            System.out
                    .println("Invalid allocationStrategy. AllocationStrategy must be either \"Random\" or \"Manual\".");
        } else {
            Producer producer = new Producer(id, type, allocationStrategy);
            producers.add(producer);
            System.out.println("New producer with id " + id + " and type " + type + " and allocationStrategy "
                    + allocationStrategy + " successfully created.");
        }
        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }

    public CoreResponse produceEvent(String producerId, String topicId, String filename) {
        JSONObject messageObject = Event.filenameToJSONObject(filename);
        Topic topic = tributaryCluster.getTopicById(topicId);
        Producer producer = producers.stream().filter(p -> p.getId().equals(producerId)).findFirst().orElse(null);
        producer.sendMessage(messageObject, topic);
        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }

    public CoreResponse produceEvent(String producerId, String topicId, String filename, String partitionId) {
        JSONObject messageObject = Event.filenameToJSONObject(filename);
        Topic topic = tributaryCluster.getTopicById(topicId);
        Producer producer = producers.stream().filter(p -> p.getId().equals(producerId)).findFirst().orElse(null);
        producer.sendMessage(messageObject, topic, partitionId);
        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }

    public synchronized CoreResponse consumeEvent(String consumerId, String partitionId) {
        ConsumerGroup consumerGroup = consumerGroups.stream().filter(cg -> cg.hasConsumer(consumerId)).findFirst()
                .orElse(null);

        if (consumerGroup == null) {
            System.out.println("No consumerGroup has a consumer with that consumerId");
        } else {
            consumerGroup.consumeEvent(consumerId, partitionId);
            System.out.println(
                    "Consumer with id " + consumerId + " has consumed event from partition with id " + partitionId);
        }

        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }

    public synchronized CoreResponse consumeEvents(String consumerId, String partitionId, String numberOfEvents) {
        int nEvents = Integer.parseInt(numberOfEvents);
        for (int i = 0; i < nEvents; i++) {
            consumeEvent(consumerId, partitionId);
        }
        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }

    public CoreResponse showTopic(String topicId) {
        Topic topic = tributaryCluster.getTopicById(topicId);
        System.out.println(topic.getInfo());
        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }

    public CoreResponse showConsumerGroup(String consumerGroupId) {
        ConsumerGroup consumerGroup = consumerGroups.stream().filter(cg -> cg.getId().equals(consumerGroupId))
                .findFirst().orElse(null);

        System.out.println(consumerGroup.getInfo());
        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }

    public CoreResponse parallelProduce(List<String> producerIds, List<String> topicIds, List<String> filenames) {
        Thread parallelProduceThread = new Thread(new ParallelProduce(new ArrayList<String>(producerIds),
                new ArrayList<String>(topicIds), new ArrayList<String>(filenames), this));
        parallelProduceThread.start();
        try {
            parallelProduceThread.join();
            System.out.println("Parallel producing successful");
        } catch (InterruptedException e) {
            System.err.println(e);
            System.out.println("Parallel producing unsuccessful");
        }
        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }

    public CoreResponse parallelConsume(List<String> consumerIds, List<String> partitionIds) {
        Thread parallelConsumeThread = new Thread(
                new ParallelConsume(new ArrayList<String>(consumerIds), new ArrayList<String>(partitionIds), this));
        parallelConsumeThread.start();
        try {
            parallelConsumeThread.join();
            System.out.println("Consuming successful");
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }

    public CoreResponse setConsumerGroupRebalancing(String consumerGroupId, String rebalancingStrategy) {
        ConsumerGroup consumerGroup = consumerGroups.stream().filter(cg -> cg.getId().equals(consumerGroupId))
                .findFirst().orElse(null);
        if (consumerGroup == null) {
            System.out.println("Invalid consumerGroupId");
        } else {
            switch (rebalancingStrategy) {
            case "Range":
                consumerGroup.setRebalancingStrategy(new RangeStrategy());
                System.out.println("ConsumerGroup with id " + consumerGroupId
                        + " has had its rebalancingStrategy set to " + rebalancingStrategy);
                break;
            case "RoundRobin":
                consumerGroup.setRebalancingStrategy(new RoundRobinStrategy());
                System.out.println("ConsumerGroup with id " + consumerGroupId
                        + " has had its rebalancingStrategy set to " + rebalancingStrategy);
                break;
            default:
                System.out.println("Invalid rebalancingStrategy");
            }
        }
        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }

    public CoreResponse playback(String consumerId, String partitionId, String offset) {
        Partition partition = tributaryCluster.getPartition(partitionId);
        partition.playback(consumerId, offset);
        return new CoreResponse(tributaryCluster, consumerGroups, producers);
    }
}
