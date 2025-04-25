import kotlin.test.*

data class Parcel(
    val id: String,
    val deliveryDeadline: Int,
    val size: Int
) : Comparable<Parcel> {
    override fun compareTo(other: Parcel): Int {
        return this.deliveryDeadline.compareTo(other.deliveryDeadline)
    }
}

class ParcelQueueTest {

    @Test
    fun testFifoQueueWithInt() {
        val queue = FifoParcelQueue<Int>()
        assertTrue(queue.isEmpty())
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)

        assertEquals(3, queue.size())
        assertEquals(1, queue.peek())
        assertEquals(1, queue.dequeue())
        assertEquals(2, queue.dequeue())
        assertEquals(1, queue.size())
    }

    @Test
    fun testLifoQueueWithString() {
        val queue = LifoParcelQueue<String>()
        queue.enqueue("first")
        queue.enqueue("second")
        queue.enqueue("third")

        assertEquals("third", queue.peek())
        assertEquals("third", queue.dequeue())
        assertEquals("second", queue.dequeue())
        assertTrue(!queue.isEmpty())
    }

    @Test
    fun testPriorityQueueWithInt() {
        val queue = PriorityParcelQueue<Int>()
        queue.enqueue(30)
        queue.enqueue(10)
        queue.enqueue(20)

        assertEquals(10, queue.peek())
        assertEquals(10, queue.dequeue())
        assertEquals(20, queue.dequeue())
        assertEquals(30, queue.dequeue())
        assertTrue(queue.isEmpty())
    }

    @Test
    fun testPriorityQueueWithString() {
        val queue = PriorityParcelQueue<String>()
        queue.enqueue("banana")
        queue.enqueue("apple")
        queue.enqueue("cherry")

        assertEquals("apple", queue.dequeue())
        assertEquals("banana", queue.dequeue())
        assertEquals("cherry", queue.dequeue())
    }

    @Test
    fun testPriorityQueueWithParcel() {
        val queue = PriorityParcelQueue<Parcel>()
        queue.enqueue(Parcel("P1", deliveryDeadline = 5, size = 10))
        queue.enqueue(Parcel("P2", deliveryDeadline = 3, size = 20))
        queue.enqueue(Parcel("P3", deliveryDeadline = 8, size = 15))

        assertEquals("P2", queue.dequeue()?.id)
        assertEquals("P1", queue.dequeue()?.id)
        assertEquals("P3", queue.dequeue()?.id)
    }

    @Test
    fun testEmptyBehavior() {
        val queue = FifoParcelQueue<String>()
        assertNull(queue.peek())
        assertNull(queue.dequeue())
        assertEquals(0, queue.size())
        assertTrue(queue.isEmpty())
    }
}
