package org.luis.deque;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases:
 *	peekFirst [] -> Exception
 *	peekLast [] -> Exception
 *	append(null) [] -> Exception
 *	append({1,null,null}) [] -> [{1,null,null}]
 *	append({2,null,null}) [{0,{1,null,this},null}, {1,null,{0,this,null}}] -> [{0,next,null}, {1,next,prev}, {2,null,prev}]
 *	appendLeft(null) [] -> Exception
 *	appendLeft({1,null,null}) [] -> [{1,null,null}]
 *	appendLeft({2,null,null}) [{1,{0,null,this},null}, {0,null,{1,this,null}}] -> [{2,next,null}, {1,next,prev}, {0,null,prev}]
 *	deleteFirst [] -> Exception
 *	deleteFirst [{0,null,null}] -> []
 *	deleteFirst [{1,{0,null,this},null}, {0,null,{1,this,null}}] -> [{0,null,null}]
 *	deleteLast [] -> Exception
 *	deleteLast [{0,null,null}] -> []
 *	deleteLast [{0,{1,null,this},null}, {1,null,{0,this,null}}] -> [{0,null,null}]
 *	size [] -> 0
 *	size [{1,null,null}] -> 1
 *	size [{0,{1,null,this},null},{1,null,{0,this,null}}] -> 2
 *   //Complex Operations
 *  getAt(1) [{0,{1,null,this},null},{1,null,{0,this,null}}] -> {1,null,{0,this,null}}
 *  getAt(0) [] -> Exception
 *  getAt(2)  [{0,{1,null,this},null},{1,null,{0,this,null}}] -> Exception
 *	find(0) [{0,{1,null,this},null},{1,null,{0,this,null}}] -> {0,{1,null,this},null}
 *	find(2) [{0,{1,null,this},null},{1,null,{0,this,null}}] -> null
 *	find(0) [] -> null
 *  delete({1,next,prev}) [{0,next,null}, {1,next,prev}, {2,null,prev}] -> [{0,next,null},{2,null,prev}]
 *  delete({0,next,null}) [{0,next,null}, {1,next,prev}, {2,null,prev}] -> [{1,next,null},{2,null,prev}]
 *	delete({2,null,prev}) [{0,next,null}, {1,next,prev}, {2,null,prev}] -> [{0,next,null},{1,null,prev}]
 *	delete({3,null,null}) [{0,next,null}, {1,next,prev}, {2,null,prev}] -> Exception
 *	delete({0,null,null}) [] -> Exception
 *  sort [{0,next,null}, {1,next,prev}, {2,null,prev}] -> [{0,next,null}, {1,next,prev}, {2,null,prev}]
 * 	sort [{2,next,null}, {1,next,prev}, {0,null,prev}] -> [{0,next,null}, {1,next,prev}, {2,null,prev}]
 * 	sort [{1,next,null}, {2,next,prev}, {0,null,prev}] -> [{0,next,null}, {1,next,prev}, {2,null,prev}]
 * 	sort [] -> []
 */

public class DoubleLinkedListQueueTest {

    DoubleLinkedListQueue<Integer> list;

    @BeforeEach
    public void setup() {
        list = new DoubleLinkedListQueue<>();
    }

    @AfterEach
    public void finish() {
        list = null;
    }

    // Given the list is empty, When we call peekFirst(), Then it should raise an exception
    @Test
    public void shouldPeekFirstRaiseExceptionIfListIsEmpty(){

        assertThrows(RuntimeException.class, () -> list.peekFirst());
    }

    // Given the list is empty, When we call peekLast(), Then it should raise an exception
    @Test
    public void shouldPeekLastRaiseExceptionIfListIsEmpty(){

        assertThrows(RuntimeException.class, () -> list.peekLast());
    }

    // Given the input is null, When we call append(null), Then it should raise an exception
    @Test
    public void shouldAppendRaiseExceptionIfInputIsNull(){

        assertThrows(RuntimeException.class, () -> list.append(null));
    }

    // Given the input is null, When we call appendLeft(null), Then it should raise an exception
    @Test
    public void shouldAppendLeftRaiseExceptionIfInputIsNull(){

        assertThrows(RuntimeException.class, () -> list.appendLeft(null));
    }

    // Given the list is empty, When we call append(node), Then it should add the node at the end
    @Test
    public void shouldAppendAddNodeIfListIsEmpty(){
        DequeNode<Integer> node = new DequeNode<>(1,null,null);
        list.append(node);

        DequeNode<Integer> expectedValue = node;
        DequeNode<Integer> obtainedValue= list.peekLast();

        assertEquals(expectedValue,obtainedValue);
    }

    // Given the list is not empty, When we call append(node2), Then it should add the node2 at the end
    @Test
    public void shouldAppendAddNodeIfListIsNotEmpty(){
        DequeNode<Integer> node = new DequeNode<>(0,null,null);
        DequeNode<Integer> node1 = new DequeNode<>(1,null,null);
        DequeNode<Integer> node2 = new DequeNode<>(2,null,null);
        list.append(node);
        list.append(node1);
        list.append(node2);

        DequeNode<Integer> expectedValue = node2;
        DequeNode<Integer> obtainedValue= list.peekLast();

        assertEquals(expectedValue,obtainedValue);
    }

    // Given the list is empty, When we call appendLeft(node), Then it should add the node at the beginning
    @Test
    public void shouldAppendLeftAddNodeIfListIsEmpty(){
        DequeNode<Integer> node = new DequeNode<>(1,null,null);
        list.appendLeft(node);

        DequeNode<Integer> expectedValue = node;
        DequeNode<Integer> obtainedValue= list.peekFirst();

        assertEquals(expectedValue,obtainedValue);
    }

    // Given the list is not empty, When we call appendLeft(node), Then it should add the node at the end
    @Test
    public void shouldAppendLeftAddNodeIfListIsNotEmpty(){
        DequeNode<Integer> node = new DequeNode<>(0,null,null);
        DequeNode<Integer> node1 = new DequeNode<>(1,null,null);
        DequeNode<Integer> node2 = new DequeNode<>(2,null,null);
        list.appendLeft(node);
        list.appendLeft(node1);
        list.appendLeft(node2);

        DequeNode<Integer> expectedValue = node2;
        DequeNode<Integer> obtainedValue= list.peekFirst();

        assertEquals(expectedValue,obtainedValue);
    }

    // Given the list is empty, When we call deleteFirst(), Then it should raise an exception
    @Test
    public void shouldDeleteFirstRiseExceptionIfListIsEmpty(){

        assertThrows(RuntimeException.class, () -> list.deleteFirst());
    }

    // Given the list has one node, When we call deleteFirst(), Then it should delete the first node
    @Test
    public void shouldDeleteFirstDeleteNodeIfListHasOneNode(){

        DequeNode<Integer> node = new DequeNode<>(0,null,null);

        list.appendLeft(node);
        list.deleteFirst();
        int expectedValue = 0;
        int obtainedValue = list.size();

        assertEquals(expectedValue, obtainedValue);
    }

    // Given the list has more than one node, When we call deleteFirst(), Then it should delete the first node
    @Test
    public void shouldDeleteFirstDeleteNodeIfListHasMoreThanOneNode(){

        DequeNode<Integer> node = new DequeNode<>(0,null,null);
        DequeNode<Integer> node1 = new DequeNode<>(1,null,null);

        list.appendLeft(node);
        list.appendLeft(node1);
        list.deleteFirst();
        DequeNode<Integer> expectedValue = node;
        DequeNode<Integer> obtainedValue = list.peekFirst();

        assertEquals(expectedValue, obtainedValue);
    }

    // Given the list is empty, When we call deleteLast(), Then it should raise an exception
    @Test
    public void shouldDeleteLastRaiseExceptionIfListIsEmpty(){

        assertThrows(RuntimeException.class, () -> list.deleteLast());
    }

    // Given the list has one node, When we call deleteLast(), Then it should delete the last node
    @Test
    public void shouldDeleteLastDeleteNodeIfListHasOneNode(){

        DequeNode<Integer> node = new DequeNode<>(0,null,null);

        list.appendLeft(node);
        list.deleteLast();
        int expectedValue = 0;
        int obtainedValue = list.size();

        assertEquals(expectedValue, obtainedValue);
    }

    // Given the list has more than one node, When we call deleteLast(), Then it should delete the last node
    @Test
    public void shouldDeleteLastDeleteNodeIfListIsHasMoreThanOneNode(){

        DequeNode<Integer> node = new DequeNode<>(0,null,null);
        DequeNode<Integer> node1 = new DequeNode<>(1,null,null);

        list.append(node);
        list.append(node1);
        list.deleteLast();
        DequeNode<Integer> expectedValue = node;
        DequeNode<Integer> obtainedValue = list.peekLast();

        assertEquals(expectedValue, obtainedValue);
    }

    // Given the list is empty, When we call size(), Then it should return zero
    @Test
    public void shouldSizeReturnZeroIfListIsEmpty(){

        int expectedValue= 0;
        int obtainedValue= list.size();

        assertEquals(expectedValue,obtainedValue);
    }

    // Given the list has one element, When we call size(), Then it should return one
    @Test
    public void shouldSizeReturnOneIfListHasOneElement(){
        DequeNode<Integer> node= new DequeNode<>(1,null,null);
        list.append(node);
        int expectedValue= 1;
        int obtainedValue= list.size();

        assertEquals(expectedValue,obtainedValue);
    }

    // Given the list has two elements, When we call size(), Then it should return two
    @Test
    public void shouldSizeReturnTwoIfListHasTwoElements(){
        DequeNode<Integer> node= new DequeNode<>(0,null,null);
        DequeNode<Integer> node1= new DequeNode<>(1,null,null);

        list.append(node);
        list.append(node1);
        int expectedValue= 2;
        int obtainedValue= list.size();

        assertEquals(expectedValue,obtainedValue);
    }

    // Given the list has two elements, When we call getAt(1), Then it should return the node at position i
    @Test
    public void shouldGetAtReturnNodeAtPositionIfListHasElements() {
        DequeNode<Integer> node = new DequeNode<>(0, null, null);
        DequeNode<Integer> node1 = new DequeNode<>(1, null, null);
        list.append(node);
        list.append(node1);
        DequeNode<Integer> expectedValue = node1;
        DequeNode<Integer> obtainedValue = list.getAt(1);

        assertEquals(expectedValue,obtainedValue);
    }

    // Given the list has no elements, When we call getAt(i), Then it should raise an exception
    @Test
    public void shouldGetAtRaiseExceptionIfListHasNoElements(){
        assertThrows(RuntimeException.class, () -> list.getAt(0));
    }

    // Given the list has two elements, When we call getAt(2), Then it should raise an exception
    @Test
    public void shouldGetAtRaiseExceptionIfPositionIsOutOfBounds(){
        DequeNode<Integer> node= new DequeNode<>(0,null,null);
        DequeNode<Integer> node1= new DequeNode<>(1,null,null);
        list.append(node);
        list.append(node1);

        assertThrows(RuntimeException.class, () -> list.getAt(2));
    }

    // Given the list has two elements, When we call find(0), Then it should return node with item 0
    @Test
    public void shouldFindReturnNodeWithTheSameItemAsInputIfTheItemIsInTheList(){
        DequeNode<Integer> node= new DequeNode<>(0,null,null);
        DequeNode<Integer> node1= new DequeNode<>(1,null,null);

        list.append(node);
        list.append(node1);
        DequeNode<Integer> expectedValue = node;
        DequeNode<Integer> obtainedValue = list.find(0);

        assertEquals(expectedValue,obtainedValue);
    }

    // Given the list has two elements, When we call find(2), Then it should return null
    @Test
    public void shouldFindReturnNullIfTheItemIsNotInTheList(){
        DequeNode<Integer> node= new DequeNode<>(0,null,null);
        DequeNode<Integer> node1= new DequeNode<>(1,null,null);

        list.append(node);
        list.append(node1);
        DequeNode<Integer> expectedValue = null;
        DequeNode<Integer> obtainedValue = list.find(2);

        assertEquals(expectedValue,obtainedValue);
    }

    // Given the list has zero elements, When we call find(0), Then it should return null
    @Test
    public void shouldFindReturnNullIfTheListIsEmpty(){

        DequeNode<Integer> expectedValue = null;
        DequeNode<Integer> obtainedValue = list.find(0);

        assertEquals(expectedValue,obtainedValue);
    }

    // Given the list has elements, When we call delete(node) and node is in the list, Then it should delete the node from the list
    @Test
    public void shouldDeleteTheGivenNodeIfTheNodeIsInTheList(){
        DequeNode<Integer> node = new DequeNode<>(0,null,null);
        DequeNode<Integer> node1 = new DequeNode<>(1,null,null);
        DequeNode<Integer> node2 = new DequeNode<>(2,null,null);
        list.append(node);
        list.append(node1);
        list.append(node2);

        list.delete(node1);
        int expectedValue = 2;
        int obtainedValue = list.size();

        assertEquals(expectedValue,obtainedValue);
    }

    // Given the list has elements, When we call delete(node) and node is the first element in the list, Then it should delete the node from the list
    @Test
    public void shouldDeleteTheGivenNodeIfTheNodeIsTheFirstElementInTheList() {
        DequeNode<Integer> node = new DequeNode<>(0,null,null);
        DequeNode<Integer> node1 = new DequeNode<>(1,null,null);
        DequeNode<Integer> node2 = new DequeNode<>(2,null,null);
        list.append(node);
        list.append(node1);
        list.append(node2);
        list.delete(node);
        DequeNode<Integer> expectedValue = node1;
        DequeNode<Integer> obtainedValue = list.peekFirst();

        assertEquals(expectedValue, obtainedValue);
    }

    // Given the list has elements, When we call delete(node) and node is the last element in the list, Then it should delete the node from the list
    @Test
    public void shouldDeleteTheGivenNodeIfTheNodeIsTheLastElementInTheList() {
        DequeNode<Integer> node = new DequeNode<>(0,null,null);
        DequeNode<Integer> node1 = new DequeNode<>(1,null,null);
        DequeNode<Integer> node2 = new DequeNode<>(2,null,null);
        list.append(node);
        list.append(node1);
        list.append(node2);
        list.delete(node2);
        DequeNode<Integer> expectedValue = node1;
        DequeNode<Integer> obtainedValue = list.peekLast();

        assertEquals(expectedValue, obtainedValue);
    }

    // Given the list has elements, When we call delete(node) and node is not in the list, Then it should raise an exception
    @Test
    public void shouldDeleteRaiseExceptionIfTheGivenNodeIsNotInTheListAndItHasElements() {
        DequeNode<Integer> node = new DequeNode<>(0,null,null);
        DequeNode<Integer> node1 = new DequeNode<>(1,null,null);
        DequeNode<Integer> node2 = new DequeNode<>(2,null,null);
        list.append(node);
        list.append(node1);
        list.append(node2);

        assertThrows(RuntimeException.class, () -> list.delete(new DequeNode<>(3, null, null)));
    }

    // Given the list is empty, When we call delete(node), Then it should raise an exception
    @Test
    public void shouldDeleteRaiseExceptionIfTheListIsEmpty() {
        DequeNode<Integer> node = new DequeNode<>(0,null,null);

        assertThrows(RuntimeException.class, () -> list.delete(node));
    }

    class Comparador<Integer> implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return (int)o1 - (int)o2;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Comparador<?>;
        }
    }

    // Given the list is Empty, When we call sort(s), Then it should return an empty list
    @Test
    public void shouldSortReturnEmptyListIfTheListIsEmpty() {
        Comparator<Integer> s = new Comparador<>();

        list.sort(s);

        int expectedValue = 0;
        int obtainedValue = list.size();

        assertEquals(expectedValue, obtainedValue);
    }

    // Given the list has elements and it is ordered (Ascendent), When we call sort(s), Then it should return the same list
    @Test
    public void shouldSortReturnTheSameListIfListIsOrderedAscendent() {
        DequeNode<Integer> node = new DequeNode<>(0,null,null);
        DequeNode<Integer> node1 = new DequeNode<>(1,null,null);
        DequeNode<Integer> node2 = new DequeNode<>(2,null,null);
        list.append(node);
        list.append(node1);
        list.append(node2);
        Comparator<Integer> s = new Comparador<>();

        list.sort(s);

        assertEquals(0, list.getAt(0).getItem());
        assertEquals(1, list.getAt(1).getItem());
        assertEquals(2, list.getAt(2).getItem());
    }

    // Given the list has elements and it is ordered (Descendent), When we call sort(s), Then it should return the list ordered (Ascendent)
    @Test
    public void shouldSortReturnTheListOrderedAscendentIfListIsTwoOneZero() {
        DequeNode<Integer> node = new DequeNode<>(2,null,null);
        DequeNode<Integer> node1 = new DequeNode<>(1,null,null);
        DequeNode<Integer> node2 = new DequeNode<>(0,null,null);
        list.append(node);
        list.append(node1);
        list.append(node2);
        Comparator<Integer> s = new Comparador<>();

        list.sort(s);

        assertEquals(0, list.getAt(0).getItem());
        assertEquals(1, list.getAt(1).getItem());
        assertEquals(2, list.getAt(2).getItem());
    }

    // Given the list has elements and is not ordered, When we call sort(s), Then it should return the list ordered (Ascendent)
    @Test
    public void shouldSortReturnTheListOrderedAscendentIfListIsOneTwoZero() {
        DequeNode<Integer> node = new DequeNode<>(1,null,null);
        DequeNode<Integer> node1 = new DequeNode<>(2,null,null);
        DequeNode<Integer> node2 = new DequeNode<>(0,null,null);
        list.append(node);
        list.append(node1);
        list.append(node2);
        Comparator<Integer> s = new Comparador<>();

        list.sort(s);

        assertEquals(0, list.getAt(0).getItem());
        assertEquals(1, list.getAt(1).getItem());
        assertEquals(2, list.getAt(2).getItem());
    }

}
