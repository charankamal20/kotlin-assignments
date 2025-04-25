import kotlin.test.*

class SimulationMonitoringTest {

    @BeforeTest
    fun setup() {
        Clock.reset()
    }

    @Test
    fun testClockTicksCorrectly() {
        assertEquals(0, Clock.now())
        Clock.tick()
        Clock.tick()
        assertEquals(2, Clock.now())
    }

    @Test
    fun testAverageWaitTimeAndProcessing() {
        val center = MonitoredSortingCenter("A", FifoParcelQueue())
        val router = Router()
        router.addCenter(center)

        val p1 = Parcel("P1", 10, 1, "B")
        val p2 = Parcel("P2", 12, 1, "B")

        center.acceptParcel(p1)
        Clock.tick()
        center.acceptParcel(p2)
        Clock.tick()
        center.forwardParcel(router) // P1 out at time 2
        Clock.tick()
        center.forwardParcel(router) // P2 out at time 3

        assertEquals(2, center.processedCount())
        assertEquals(1.5, center.averageWaitTime(), 0.001)
    }

    @Test
    fun testMaxQueueLength() {
        val center = MonitoredSortingCenter("X", FifoParcelQueue())
        val router = Router()
        router.addCenter(center)

        center.acceptParcel(Parcel("P1", 5, 2, "Y"))
        center.acceptParcel(Parcel("P2", 6, 2, "Y"))
        center.acceptParcel(Parcel("P3", 7, 2, "Y"))

        assertEquals(3, center.maxQueueLength())
    }

    @Test
    fun testSimulationRunAndDeliveryTracking() {
        val router = Router()
        val centerA = MonitoredSortingCenter("A", FifoParcelQueue())
        val centerB = MonitoredSortingCenter("B", FifoParcelQueue())

        router.addCenter(centerA)
        router.addCenter(centerB)
        router.connect("A", "B", 1)

        val parcel = Parcel("PX", 20, 1, "B")
        centerA.acceptParcel(parcel)

        repeat(5) { Clock.tick() }
        centerA.forwardParcel(router)

        assertEquals(1, centerA.processedCount())
        assertEquals(1, centerB.queueSize())
        assertTrue(centerA.averageWaitTime() > 0.0)
    }
}
