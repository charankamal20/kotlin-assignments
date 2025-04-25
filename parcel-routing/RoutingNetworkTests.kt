import kotlin.test.*

class RoutingNetworkTest {

    @Test
    fun testAcceptParcelAddsToQueue() {
        val center = SortingCenter("A", FifoParcelQueue())
        val parcel = Parcel("P1", 5, 10, "B")

        center.acceptParcel(parcel)
        assertEquals(1, center.queueSize())
    }

    @Test
    fun testRouterConnectsCenters() {
        val router = Router()
        val centerA = SortingCenter("A", FifoParcelQueue())
        val centerB = SortingCenter("B", FifoParcelQueue())

        router.addCenter(centerA)
        router.addCenter(centerB)
        router.connect("A", "B", 1)

        assertNotNull(router.getCenter("A"))
        assertNotNull(router.getCenter("B"))
        assertEquals("B", router.nextHop("A", "B"))
    }

    @Test
    fun testForwardParcelMovesParcelToNextCenter() {
        val router = Router()
        val centerA = SortingCenter("A", FifoParcelQueue())
        val centerB = SortingCenter("B", FifoParcelQueue())

        router.addCenter(centerA)
        router.addCenter(centerB)
        router.connect("A", "B", 1)

        val parcel = Parcel("P99", 10, 5, "B")
        centerA.acceptParcel(parcel)

        assertEquals(1, centerA.queueSize())
        centerA.forwardParcel(router)

        assertEquals(0, centerA.queueSize())
        assertEquals(1, centerB.queueSize())
    }

    @Test
    fun testDijkstraFindsShortestPath() {
        val router = Router()
        val c1 = SortingCenter("A", FifoParcelQueue())
        val c2 = SortingCenter("B", FifoParcelQueue())
        val c3 = SortingCenter("C", FifoParcelQueue())
        val c4 = SortingCenter("D", FifoParcelQueue())

        router.addCenter(c1)
        router.addCenter(c2)
        router.addCenter(c3)
        router.addCenter(c4)

        router.connect("A", "B", 2)
        router.connect("B", "C", 2)
        router.connect("A", "C", 10)
        router.connect("C", "D", 1)

        assertEquals("B", router.nextHop("A", "D")) // A → B → C → D is shortest
    }
}
