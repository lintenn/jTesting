package org.luis.deque;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases:
 *	getNext {0,null,null} -> null
 *	getPrevious {0,null,null} -> null
 *	getItem {null,null,null} -> null
 *	getItem {0,null,null} -> 0
 *	getNext {1, {0,null,this}, null } -> {0,null,this}
 *	getPrevious {1,null, {0,this,null} } -> {0,this,null}
 *	setItem(1) {0,null,null} -> {1,null,null}
 *	setNext( {1,null,this} ) {0,null,null} -> {0, {1,null,this}, null}
 * 	setPrevious( {0,this, null} )  {1,null,null} -> {1, null, {0,this,null}}
 *	isFirstNode {0,null,null} -> true
 *	isFirstNode {0, {1,null,this}, null} -> true
 *	isFirstNode {1,null, {0,this,null} } -> false
 *	isLastNode {0,null,null} -> true
 *	isLastNode {0,null, {1,this,null}} -> true
 *	isLastNode {1,{0,null,this},null} -> false
 *	isNotATerminalNode {1,null,null} -> false
 *	isNotATerminalNode {1,null,{0,this,null}} -> false
 *	isNotATerminalNode {0,{1,null,this},null} -> false
 *	isNotATerminalNode {1,{2,null,this},{0,this,null}} -> true
 * 	getNext {0,{1,null,this},null} -> {1,null,this}
 *	getPrevious {1,null,{0,this,null}} -> {0,this,null}
 */

class DequeNodeTest {

    private DequeNode<Integer> dequeNode;

    /*
    @BeforeEach
    public void setup() {
        dequeNode = new DequeNode<>(0, null, null);
    }
     */

    @AfterEach
    public void finish() {
        dequeNode = null;
    }

    // Given dequeNode has no next and no previous, When we call getNext(), Then it should return null
    @Test
    public void shouldGetNextReturnNullIfDequeNodeIsIsolated() {
        dequeNode = new DequeNode<>(0, null, null);
        DequeNode<Integer> obtainedValue = dequeNode.getNext();

        assertNull(obtainedValue);
    }

    // Given dequeNode has no next and no previous, When we call getPrevious(), Then it should return null
    @Test
    public void shouldGetPreviousReturnNullIfDequeNodeIsIsolated() {
        dequeNode = new DequeNode<>(0, null, null);
        DequeNode<Integer> obtainedValue = dequeNode.getPrevious();

        assertNull(obtainedValue);
    }

    // Given dequeNode has null item, When we call getItem(), Then it should return null
    @Test
    public void shouldGetItemReturnNullIfDequeNodeHasNullItem() {
        dequeNode = new DequeNode<>(null, null, null);
        Integer obtainedValue = dequeNode.getItem();

        assertNull(obtainedValue);
    }

    // Given dequeNode has item, When we call getItem(), Then it should return the item
    @Test
    public void shouldGetItemReturnItemIfDequeNodeHasItem() {
        dequeNode = new DequeNode<>(0, null, null);
        Integer expectedValue = 0;
        Integer obtainedValue = dequeNode.getItem();

        assertEquals(expectedValue, obtainedValue);
    }

    // Given dequeNode has next reference, When we call getNext(), Then it should return the next reference
    @Test
    public void shouldGetNextReturnNextIfDequeNodeHasNext() {
        DequeNode<Integer> next = new DequeNode<>(0,null,dequeNode);
        dequeNode = new DequeNode<>(1,next,null);
        DequeNode<Integer> expectedValue = next;
        DequeNode<Integer> obtainedValue = dequeNode.getNext();

        assertEquals(expectedValue,obtainedValue);
    }

    // Given dequeNode has previous reference, When we call getPrevious(), Then it should return the previous reference
    @Test
    public void shouldGetPreviousReturnPreviousIfDequeNodeHasPrevious() {
        DequeNode<Integer> previous = new DequeNode<>(0, dequeNode,null);
        dequeNode = new DequeNode<>(1,null,previous);
        DequeNode<Integer> expectedValue = previous;
        DequeNode<Integer> obtainedValue = dequeNode.getPrevious();

        assertEquals(expectedValue,obtainedValue);
    }

    // Given dequeNode is not null, When we call setItem(1), Then it should change the dequeNode's item
    @Test
    public void shouldSetItemChangeTheDequeNodeItem() {
        dequeNode = new DequeNode<>(0,null,null);
        dequeNode.setItem(1);
        int expectedValue = 1;
        int obtainedValue = dequeNode.getItem();

        assertEquals(expectedValue,obtainedValue);
    }

    // Given dequeNode is not null, When we call setNext(next), Then it should change the dequeNode's next reference
    @Test
    public void shouldSetNextChangeTheDequeNodeNext() {
        DequeNode<Integer> next = new DequeNode<>(1,null,dequeNode);
        dequeNode = new DequeNode<>(0,null,null);
        dequeNode.setNext(next);
        DequeNode<Integer> expectedValue = next;
        DequeNode<Integer> obtainedValue = dequeNode.getNext();

        assertEquals(expectedValue,obtainedValue);
    }

    // Given dequeNode is not null, When we call setPrevious(previous), Then it should change the dequeNode's previous reference
    @Test
    public void shouldSetPreviousChangeTheDequeNodePrevious() {
        DequeNode<Integer> previous = new DequeNode<>(0, dequeNode,null);
        dequeNode = new DequeNode<>(1,null,null);
        dequeNode.setPrevious(previous);
        DequeNode<Integer> expectedValue = previous;
        DequeNode<Integer> obtainedValue = dequeNode.getPrevious();

        assertEquals(expectedValue,obtainedValue);
    }

    // Given dequeNode has no next and no previous, When we call isFirstNode(), Then it should return true
    @Test
    public void shouldIsFirstNodeReturnTrueIfDequeNodeIsIsolated() {
        dequeNode = new DequeNode<>(0, null, null);

        assertTrue(dequeNode.isFirstNode());
    }

    // Given dequeNode has next but no previous, When we call isFirstNode(), Then it should return true
    @Test
    public void shouldIsFirstNodeReturnTrueIfDequeNodeHasNextButNoPrevious() {
        DequeNode<Integer> next = new DequeNode<>(1,null,dequeNode);
        dequeNode = new DequeNode<>(0,next,null);

        assertTrue(dequeNode.isFirstNode());
    }

    // Given dequeNode has previous reference, When we call isFirstNode(), Then it should return false
    @Test
    public void shouldIsFirstNodeReturnFalseIfDequeNodeHasPrevious() {
        DequeNode<Integer> previous = new DequeNode<>(0, dequeNode,null);
        dequeNode = new DequeNode<>(1, null, previous);

        assertFalse(dequeNode.isFirstNode());
    }

    // Given dequeNode has no next and no previous, When we call isLastNode(), Then it should return true
    @Test
    public void shouldIsLastNodeReturnTrueIfDequeNodeIsIsolated() {
        dequeNode = new DequeNode<>(0, null, null);

        assertTrue(dequeNode.isLastNode());
    }

    // Given dequeNode has previous but no next, When we call isLastNode(), Then it should return true
    @Test
    public void shouldIsLastNodeReturnTrueIfDequeNodeHasPreviousButNoNext() {
        DequeNode<Integer> previous = new DequeNode<>(1,dequeNode,null);
        dequeNode = new DequeNode<>(0,null,previous);

        assertTrue(dequeNode.isLastNode());
    }

    // Given dequeNode has next reference, When we call isLastNode(), Then it should return false
    @Test
    public void shouldIsLastNodeReturnFalseIfDequeNodeHasNext() {
        DequeNode<Integer> next = new DequeNode<>(0, null,dequeNode);
        dequeNode = new DequeNode<>(1, next, null);

        assertFalse(dequeNode.isLastNode());
    }

    // Given dequeNode has no next and no previous, When we call isNotATerminalNode(), Then it should return false
    @Test
    public void shouldIsNotATerminalNodeReturnFalseIfDequeNodeIsIsolated() {
        dequeNode = new DequeNode<>(1, null, null);

        assertFalse(dequeNode.isNotATerminalNode());
    }

    // Given dequeNode has previous but no next, When we call isNotATerminalNode(), Then it should return false
    @Test
    public void shouldIsNotATerminalNodeReturnFalseIfDequeNodeHasPreviousButNoNext() {
        DequeNode<Integer> previous = new DequeNode<>(0,dequeNode,null);
        dequeNode = new DequeNode<>(1, null, previous);

        assertFalse(dequeNode.isNotATerminalNode());
    }

    // Given dequeNode has next but no previous, When we call isNotATerminalNode(), Then it should return false
    @Test
    public void shouldIsNotATerminalNodeReturnFalseIfDequeNodeHasNextButNoPrevious() {
        DequeNode<Integer> next = new DequeNode<>(1, null,dequeNode);
        dequeNode = new DequeNode<>(0, next, null);

        assertFalse(dequeNode.isNotATerminalNode());
    }

    // Given dequeNode has next and previous, When we call isNotATerminalNode(), Then it should return false
    @Test
    public void shouldIsNotATerminalNodeReturnFalseIfDequeNodeHasNextAndPrevious() {
        DequeNode<Integer> previous = new DequeNode<>(0,dequeNode,null);
        DequeNode<Integer> next = new DequeNode<>(2, null,dequeNode);
        dequeNode = new DequeNode<>(1, next, previous);

        assertTrue(dequeNode.isNotATerminalNode());
    }

    // Given dequeNode has previous, When we call getNext() of previous, Then it should return the dequeNode
    @Test
    public void shouldGetNextOfPreviousReturnDequeNodeIfDequeNodeHasPrevious() {
        DequeNode<Integer> previous = new DequeNode<>(0, dequeNode, null);
        dequeNode = new DequeNode<>(1, null, previous);
        previous.setNext(dequeNode);

        DequeNode<Integer> expectedValue = dequeNode;
        DequeNode<Integer> obtainedValue = previous.getNext();

        assertEquals(expectedValue, obtainedValue);
    }

    // Given dequeNode has next, When we call getPrevious() of next, Then it should return the dequeNode
    @Test
    public void shouldGetPreviousOfNextReturnDequeNodeIfDequeNodeHasNext() {
        DequeNode<Integer> next = new DequeNode<>(1, null, dequeNode);
        dequeNode = new DequeNode<>(0, next, null);
        next.setPrevious(dequeNode);

        DequeNode<Integer> expectedValue = dequeNode;
        DequeNode<Integer> obtainedValue = next.getPrevious();

        assertEquals(expectedValue, obtainedValue);
    }

}