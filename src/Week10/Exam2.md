## Exam 2

### Tree
#### Terms
* Full Tree - every node has either 0 or 2 children
* Perfect Tree - both full and all its leaf nodes are at the same level
* Complete Tree - all levels except possibly the last are completely filled, 
and all nodes in the last level are as far left as possible.
* Preorder Traversal - Left, Visit, Right - Root-Left-Right
* Inorder Traversal - Visit, Left, Right - Left-Root-Right
* Postorder - left-right-root
### Code
    public E maxOrder(Node<E> node, E max){
        if( node == null){
            return max;
        }

        max = node.data.compareTo(max) > 0? node.data: max;

        max = maxOrder(node.left, max);
        max = maxOrder(node.right,max);

        return max;
    }

### Recursion
    public int count8(int n) {
        if(n == 0){ return 0;}
        if(n % 10 == 8){
            if((n/10)%10 == 8){ return 2 + count8(n/10);}
            return 1 + count8(n/10);
        } else {
            return count8(n/10);
        }
    }

    public boolean groupSumRecursion(int start, int[] nums, int target) {
        if (target == 0) { return true; }
        if (start >= nums.length) { return false; }
        if (groupSumRecursion(start + 1, nums, target - nums[start])) {
            return true;
        }
        if (groupSumRecursion(start + 1, nums, target)) {
            return true;
        }
        return false;
    } 

    public boolean splitOdd10(int[] nums) {
    return helper(nums, 0, 0, 0);
    }

    private boolean helper(int[] nums, int index, int group1Sum, int group2Sum) {
    if (index == nums.length) {
    return (group1Sum % 10 == 0 && group2Sum % 2 == 1) ||
    (group2Sum % 10 == 0 && group1Sum % 2 == 1);
    }
    if (helper(nums, index + 1, group1Sum + nums[index], group2Sum)) {
        return true;
    }
    if (helper(nums, index + 1, group1Sum, group2Sum + nums[index])) {
        return true;
    }
    return false;
}

### Iterator
    Collection<String> collectiona = new ArrayList<>();
    String[] words = {"word", "poop", "economics", "iterator", "data",
            "football", "cat", "hola", "nurse"};
    collectiona.addAll(Arrays.asList(words));
    Iterator<String> it = list2.iterator(); // get iterator
    while(it.hasNext()) {
        System.out.println(it.next());
    }
    for(String s :collectiona) {
        System.out.println(s);
    }

### Queue
    1.add(E element)
	Inserts the specified element into the queue.
    If the queue cannot accommodate the new element (in bounded queues), 
    it throws an exception.

	2.offer(E element)
	Inserts the specified element into the queue. 
    If the queue cannot accommodate the new element, 
    it returns false instead of throwing an exception.

	3.remove()
	Removes and returns the head (front) of the queue. 
    Throws an exception if the queue is empty.

	4.poll()
	Removes and returns the head (front) of the queue. 
    Returns null if the queue is empty.

	5.element()
	Retrieves, but does not remove, the head of the queue. 
    Throws an exception if the queue is empty.

	6.peek()
	Retrieves, but does not remove, the head of the queue. 
    Returns null if the queue is empty.

### Stack
	1.push(E element)
    Pushes (adds) the specified element onto the top of the stack.

	2.pop()
	Removes and returns the element at the top of the stack. 
    Throws an exception if the stack is empty.

	3.peek()
	Retrieves, but does not remove, the element at the top of the stack. 
    Throws an exception if the stack is empty.

	4.isEmpty()
	Checks if the stack is empty. Returns true if the stack has no elements.

	5.search(Object obj)
	Returns the 1-based position of the object from the top of the stack. 
    Returns -1 if the object is not in the stack.

### Combined
    public class Queue {
        public static void main(String[] args) {
        java.util.Queue<String> sentenceQueue = new LinkedList<>();
    
            try (BufferedReader reader = new BufferedReader(new FileReader("sentences.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sentenceQueue.offer(line);
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
                return;
            }
    
            int lineNumber = 1;
            while (!sentenceQueue.isEmpty()) {
                String sentence = sentenceQueue.poll();
                int wordCount = countWords(sentence);
                System.out.println("Line " + lineNumber + ": " + wordCount + " " + (wordCount == 1 ? "word" : "words"));
                lineNumber++;
            }
        }
    
        private static int countWords(String line) {
            if (line == null || line.trim().isEmpty()) {
                return 0;
            }
            String[] words = line.trim().split("\\s+");
            return words.length;
        }
}
  