# 📦 Intelligent Parcel Routing System

## 🎯 Aims

- Implement and evaluate various data structures: queues, priority queues, hash maps, graphs.
- Explore algorithm design, simulation, and performance benchmarking.
- Apply real-world problem-solving skills in the context of logistics and routing.

---

## 🌍 Real-World Scenario

Imagine a national postal service that manages thousands of parcels per day. Each parcel needs to be routed from a **source warehouse** to a **destination** through a **network of sorting centers** (nodes). Each sorting center has limited processing capacity and implements different queueing disciplines to manage the parcels waiting to be forwarded.

The system must support:

- FIFO, LIFO, and Priority queue routing
- Dynamically routing parcels based on destination proximity, urgency, or size
- Logging and statistics collection (wait times, hops, re-routing due to congestion)

---

## ✅ Checkpoint 1: Core Data Structures

### 🔧 API Requirements

#### `ParcelQueue<T>` Interface
```kotlin
interface ParcelQueue<T> {
    fun enqueue(item: T)
    fun peek(): T?
    fun dequeue(): T?
    fun isEmpty(): Boolean
    fun size(): Int
}
```

#### Required Implementations

*   `FifoParcelQueue<T> : ParcelQueue<T>`
    
*   `LifoParcelQueue<T> : ParcelQueue<T>`
    
*   `PriorityParcelQueue<T> : ParcelQueue<T>`
    
    *   Requires `T : Comparable<T>`
        

#### Test Types

*   `Int`
    
*   `String`
    
*   `Parcel` (custom class shown below)
    

#### Sample Parcel Class

```kotlin

data class Parcel(     
    val id: String,     
    val deliveryDeadline: Int,     
    val size: Int ) : Comparable<Parcel> {     
    override fun compareTo(other: Parcel): Int {         
        return this.deliveryDeadline.compareTo(other.deliveryDeadline)     
    } 
}

```
* * *

### ✅ Evaluation Criteria

Criteria

 - Correct interface implementation

 - FIFO, LIFO, Priority logic

 - Proper use of generics

* * *

✅ Checkpoint 2: Routing Network
-------------------------------

### 🔧 API Requirements

#### `SortingCenter` Class

```kotlin

class SortingCenter(     
    val name: String,     
    private val queue: ParcelQueue<Parcel> ) {     
        fun acceptParcel(parcel: Parcel)     
        fun forwardParcel(router: Router)     
        fun getQueueSize(): Int     
        fun getProcessedCount(): Int 
    }
)

```
#### `Router` Class

```kotlin
class Router {     
    fun addConnection(from: SortingCenter, to: SortingCenter, cost: Int)     
    fun getShortestPath(from: SortingCenter, to: SortingCenter): List<SortingCenter> 
}
```

*   Internal graph: adjacency list
    
*   Routing: Dijkstra’s algorithm
    

* * *

### ✅ Evaluation Criteria

Criteria

 - Graph using adjacency list

 - Correct Dijkstra routing

 - Pluggable queue discipline in center

 - Working parcel forwarding logic

* * *

✅ Checkpoint 3: Simulation and Monitoring
-----------------------------------------

### 🔧 API Requirements

#### `Clock` Object

```kotlin
object Clock {
    var time: Int = 0
    fun tick(): Int {
        time += 1
        return time
    }
}
```

#### Updated Parcel

```kotlin
data class Parcel(
    val id: String,
    val deliveryDeadline: Int,
    val size: Int,
    val source: String,
    val destination: String,
    var entryTime: Int = 0,
    var hops: Int = 0
)

```

#### `SimulationRunner`

```kotlin
class SimulationRunner(
    val centers: List<SortingCenter>,
    val router: Router
) {
    fun spawnParcel(parcel: Parcel, atCenter: String)
    fun stepSimulation()
    fun runUntilComplete()
    fun report(): SimulationReport
}
```

#### `SimulationReport`

```kotlin
data class SimulationReport(
    val totalDelivered: Int,
    val avgTimeInSystem: Double,
    val congestedCenters: List<String>
)
```

* * *

### ✅ Evaluation Criteria

Criteria

 - Virtual Clock implementation

 - Accurate simulation & tracking

 - Priority and size-based routing logic

 - Correct stats and reporting

* * *

🧠 Phase 4 (Optional Bonus): Extensions
---------------------------------------

### 🔄 Ideas

*   **Probabilistic Routing**: Choose next hop based on congestion probabilities
    
*   **Dynamic Rerouting**: When a node’s queue > threshold, recalculate route
    
*   **MeasurableParcelQueue**: Track queue sizes over time
    

* * *

### ✅ Bonus Evaluation Criteria

Criteria

 - Creative features (1–2 extensions)

 - Clean, modular design

* * *
