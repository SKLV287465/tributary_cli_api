package tributary.core.rebalancingStrategy;

import java.util.List;

import tributary.core.Consumer;
import tributary.core.Partition;

public class RangeStrategy implements RebalancingStrategy {
    @Override
    public void rebalance(List<Consumer> consumers, List<Partition> partitions) {
        if (consumers.size() == 0)
            return;
        int allocationNumber = partitions.size() / consumers.size();
        int remainder = partitions.size() % consumers.size();
        int index = 0;
        for (int i = 0; i < partitions.size(); i++) {
            if (index >= consumers.size())
                return;
            if (allocationNumber < i) {
                if (remainder > 0) {
                    partitions.get(i).setConsumer(consumers.get(index));
                    remainder--;
                    allocationNumber += i;
                    index++;
                } else {
                    index++;
                    partitions.get(i).setConsumer(consumers.get(index));
                }
            } else {
                partitions.get(i).setConsumer(consumers.get(index));
            }
        }
    }

    public String getName() {
        return "RangeStrategy";
    }
}
