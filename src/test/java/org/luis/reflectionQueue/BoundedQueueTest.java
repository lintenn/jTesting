package org.luis.reflectionQueue;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class BoundedQueueTest {

    /**
     * Test cases:
     * 1. BoundedQueue is created: queue = new BoundedQueue(10) -> queue.capacity = 10 and queue.buffer = [] and queue.nextItem = 0 and queue.nextFreeSlot = 0 and queue.numberOfItems = 0
     * 2. Put item in empty BoundedQueue: queue.put("item1") -> queue.buffer = ["item1"] and queue.numberOfItems = 1
     * 3. Put item in almost full BoundedQueue: queue.put("item9") -> queue.buffer = ["item0", "item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9"] and queue.numberOfItems = 10
     * 4. Put item in full BoundedQueue: queue.put("item10") -> FullQueueException
     * 5. Get item from empty BoundedQueue: queue.get() -> EmptyQueueException
     * 6. Get item from BoundedQueue with 1 item: queue.get() -> "item1" and queue.buffer = ["item1"] and queue.numberOfItems = 0
     * 7. Get item from BoundedQueue with 10 items: queue.get() -> "item0" and queue.buffer = ["item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9"] and queue.numberOfItems = 9
     * 8. Put item after get item from BoundedQueue with 10 items: queue.put("item10") -> queue.buffer = ["item10", "item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9"] and queue.numberOfItems = 10
     * 9. Put item after get item from BoundedQueue with 9 items: queue.put("item9") -> queue.buffer = ["item0", "item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9"] and queue.numberOfItems = 9
     */

    @Test
    public void whenBoundedQueueIsCreatedItsAttributesAreCorrectlySetted() {
        BoundedQueue<String> queue = new BoundedQueue<>(10);

        assertThat(ReflectionTestUtils.getField(queue, "capacity")).isEqualTo(10);
        assertThat(ReflectionTestUtils.getField(queue, "buffer")).isEqualTo(new ArrayList<>());
        assertThat(ReflectionTestUtils.getField(queue, "nextItem")).isEqualTo(0);
        assertThat(ReflectionTestUtils.getField(queue, "nextFreeSlot")).isEqualTo(0);
        assertThat(ReflectionTestUtils.getField(queue, "numberOfItems")).isEqualTo(0);
        assertThat(queue.getNumberOfItems()).isEqualTo(0);
    }

    @Test
    public void whenPutItemInEmptyBoundedQueueItIsAddedToTheBuffer() {
        BoundedQueue<String> queue = new BoundedQueue<>(10);
        queue.put("item1");

        assertThat(queue.getNumberOfItems()).isEqualTo(1);
        assertThat(ReflectionTestUtils.getField(queue, "buffer")).asList().containsExactly("item1");
    }

    @Test
    public void whenPutItemInBoundedQueueAndBufferIsAlmostFullItIsAddedToTheBuffer() {
        BoundedQueue<String> queue = new BoundedQueue<>(10);
        for (int i = 0; i < 10; i++) {
            queue.put("item" + i);
        }

        assertThat(queue.getNumberOfItems()).isEqualTo(10);
        assertThat(queue.isFull()).isTrue();
        assertThat(ReflectionTestUtils.getField(queue, "buffer")).asList().containsExactly("item0", "item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9");
    }

    @Test
    public void whenPutItemInBoundedQueueAndBufferIsFullItRaisesException() {
        BoundedQueue<String> queue = new BoundedQueue<>(10);
        for (int i = 0; i < 10; i++) {
            queue.put("item" + i);
        }

        assertThat(queue.getNumberOfItems()).isEqualTo(10);
        assertThat(queue.isFull()).isTrue();
        assertThatThrownBy(() -> queue.put("item10")).isInstanceOf(FullQueueException.class);
    }

    @Test
    public void whenGetItemFromEmptyBoundedQueueItRaisesException() {
        BoundedQueue<String> queue = new BoundedQueue<>(10);

        assertThat(queue.getNumberOfItems()).isEqualTo(0);
        assertThat(queue.isEmpty()).isTrue();
        assertThatThrownBy(() -> queue.get()).isInstanceOf(EmptyQueueException.class);
    }

    @Test
    public void whenGetItemFromBoundedQueueWith1ItemItReturnsThatItem() {
        BoundedQueue<String> queue = new BoundedQueue<>(10);
        queue.put("item1");
        String obtainedItem = queue.get();

        assertThat(queue.getNumberOfItems()).isEqualTo(0);
        assertThat(queue.isEmpty()).isTrue();
        assertThat(ReflectionTestUtils.getField(queue, "buffer")).asList().containsExactly("item1");
        assertThat(obtainedItem).isEqualTo("item1");
    }

    @Test
    public void whenGetItemFromFullBoundedQueueItReturnsTheFirstItem() {
        BoundedQueue<String> queue = new BoundedQueue<>(10);
        for (int i = 0; i < 10; i++) {
            queue.put("item" + i);
        }
        String obtainedItem = queue.get();

        assertThat(queue.getNumberOfItems()).isEqualTo(9);
        assertThat(queue.isFull()).isFalse();
        assertThat(queue.isEmpty()).isFalse();
        assertThat(ReflectionTestUtils.getField(queue, "buffer")).asList().containsExactly("item0", "item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9");
        assertThat(obtainedItem).isEqualTo("item0");
    }

    @Test
    public void whenPutItemAfterGetItemFromBoundedQueueAndBufferIsFullItReplaceFirstItem() {
        BoundedQueue<String> queue = new BoundedQueue<>(10);
        for (int i = 0; i < 10; i++) {
            queue.put("item" + i);
        }
        String item = queue.get();
        queue.put("item10");

        assertThat(item).isEqualTo("item0");
        assertThat(queue.getNumberOfItems()).isEqualTo(10);
        assertThat(queue.isFull()).isTrue();
        assertThat(queue.isEmpty()).isFalse();
        assertThat(ReflectionTestUtils.getField(queue, "buffer")).asList().containsExactly("item10", "item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9");

    }

    @Test
    public void whenPutItemAfterGetItemFromBoundedQueueAndBufferIsAlmostFullItAddAsLastItem() {
        BoundedQueue<String> queue = new BoundedQueue<>(10);
        for (int i = 0; i < 9; i++) {
            queue.put("item" + i);
        }
        String item = queue.get();
        queue.put("item9");

        assertThat(item).isEqualTo("item0");
        assertThat(queue.getNumberOfItems()).isEqualTo(9);
        assertThat(queue.isFull()).isFalse();
        assertThat(queue.isEmpty()).isFalse();
        assertThat(ReflectionTestUtils.getField(queue, "buffer")).asList().containsExactly("item0", "item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9");

    }


}