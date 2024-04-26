package tributary.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tributary.core.rebalancingStrategy.RangeStrategy;
import tributary.core.rebalancingStrategy.RebalancingStrategy;
import tributary.core.rebalancingStrategy.RoundRobinStrategy;

public class ConsumerGroup {
    private String consumerGroupId;
    private Topic subscribedTopic;
    private String type;
    private List<Consumer> consumerList = new ArrayList<Consumer>();
    private RebalancingStrategy rebalancingStrategy;

    public ConsumerGroup(String consumerGroupId, String type, Topic subscribedTopic, String rebalancingStrategy) {
        this.consumerGroupId = consumerGroupId;
        this.type = type;
        this.subscribedTopic = subscribedTopic;
        if (rebalancingStrategy.equals("Range")) {
            this.rebalancingStrategy = new RangeStrategy();
        } else if (rebalancingStrategy.equals("RoundRobin")) {
            this.rebalancingStrategy = new RoundRobinStrategy();
        } else {
            System.out.println(
                    "Somehow a value that was neither Range nor RoundRobin got into the ConsumerGroup constructor");
            System.exit(1);
        }
    }

    public String getId() {
        return consumerGroupId;
    }

    public String getType() {
        return type;
    }

    public RebalancingStrategy getRebalancingStrategy() {
        return rebalancingStrategy;
    }

    public void addConsumer(Consumer consumer) {
        consumerList.add(consumer);
        rebalancingStrategy.rebalance(consumerList, subscribedTopic.getPartitions());
    }

    public boolean hasConsumer(String consumerId) {
        return consumerList.stream().anyMatch(c -> c.getId().equals(consumerId));
    }

    public void deleteConsumer(String consumerId) {
        Iterator<Consumer> iterator = consumerList.iterator();
        while (iterator.hasNext()) {
            Consumer c = iterator.next();
            if (c.getId().equals(consumerId)) {
                iterator.remove();
                break;
            }
        }
        rebalancingStrategy.rebalance(consumerList, subscribedTopic.getPartitions());
    }

    public synchronized void consumeEvent(String consumerId, String partitionId) {
        Consumer consumer = consumerList.stream().filter(c -> c.getId().equals(consumerId)).findFirst().orElse(null);
        consumer.consumeEvent(subscribedTopic, partitionId);
    }

    public String getInfo() {
        String result = "ConsumerGroupId: " + consumerGroupId + "\nSubscribedTopic: " + subscribedTopic.getId()
                + "\nRebalancingStrategy: " + rebalancingStrategy.getName() + "\n\nConsumers in this ConsumerGroup:\n";

        for (Consumer consumer : consumerList) {
            result += consumer.getInfo();
        }

        return result;
    }

    public void setRebalancingStrategy(RebalancingStrategy rebalancingStrategy) {
        this.rebalancingStrategy = rebalancingStrategy;
    }
}
