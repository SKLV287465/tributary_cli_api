package tributary.core.rebalancingStrategy;

import java.util.List;

import tributary.core.Consumer;
import tributary.core.Partition;

public class RoundRobinStrategy implements RebalancingStrategy {
    @Override
    public void rebalance(List<Consumer> consumers, List<Partition> partitions) {
        int index = 0;
        for (Partition partition : partitions) {
            partition.setConsumer(consumers.get(index));
            index++;
            if (index >= consumers.size()) {
                index = 0;
            }
        }
    }

    public String getName() {
        return "RoundRobinStrategy";
    }
}
