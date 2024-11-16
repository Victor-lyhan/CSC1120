package Week10;

/*

import org.junit.platform.engine.support.hierarchical.Node;

public class GroupSum {

    public boolean groupSumRecursion(int start, int[] nums, int target) {
        if (target == 0) {
            return true;
        }

        if (start >= nums.length) {
            return false;
        }

        if (groupSumRecursion(start + 1, nums, target - nums[start])) {
            return true;
        }

        if (groupSumRecursion(start + 1, nums, target)) {
            return true;
        }

        return false;
    }

    public E max(Node<E> node, E max){
        if( node == null){
            return max;
        }

        max = node.data.compareTo(max) > 0? node.data: max;

        max = max(node.left, max);
        max = max(node.right,max);

        return max;
    }
}

 */

