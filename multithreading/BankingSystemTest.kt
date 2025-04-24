import org.junit.jupiter.api.*
import java.util.concurrent.*
import kotlin.test.*

class BankingSystemTest {

    private lateinit var bank: Bank
    private lateinit var accA: Account
    private lateinit var accB: Account

    @BeforeTest
    fun setup() {
        bank = Bank()
        accA = bank.createAccount("A", 1000)
        accB = bank.createAccount("B", 1000)
    }

    @Test
    fun `deposit increases balance correctly`() {
        accA.deposit(500)
        assertEquals(1500, accA.getBalance())
    }

    @Test
    fun `withdraw decreases balance correctly`() {
        val success = accA.withdraw(300)
        assertTrue(success)
        assertEquals(700, accA.getBalance())
    }

    @Test
    fun `withdraw fails if insufficient balance`() {
        val success = accA.withdraw(2000)
        assertFalse(success)
        assertEquals(1000, accA.getBalance())
    }

    @Test
    fun `transfer between accounts updates both balances atomically`() {
        accA.transferTo(accB, 200)
        assertEquals(800, accA.getBalance())
        assertEquals(1200, accB.getBalance())
    }

    @Test
    fun `concurrent deposits should maintain correct final balance`() {
        val threadCount = 10
        val depositPerThread = 100
        val threads = List(threadCount) {
            Thread {
                repeat(depositPerThread) {
                    accA.deposit(1)
                }
            }
        }

        threads.forEach { it.start() }
        threads.forEach { it.join() }

        assertEquals(1000 + threadCount * depositPerThread, accA.getBalance())
    }

    @Test
    fun `concurrent withdrawals should not overdraw account`() {
        val threadCount = 10
        val threads = List(threadCount) {
            Thread {
                repeat(100) {
                    accA.withdraw(1)
                }
            }
        }

        threads.forEach { it.start() }
        threads.forEach { it.join() }

        assertTrue(accA.getBalance() >= 0)
    }

    @Test
    fun `total balance remains constant after concurrent transfers`() {
        val threadCount = 20
        val transferAmount = 10

        val threads = List(threadCount) {
            Thread {
                repeat(50) {
                    accA.transferTo(accB, transferAmount)
                    accB.transferTo(accA, transferAmount)
                }
            }
        }

        threads.forEach { it.start() }
        threads.forEach { it.join() }

        val totalBalance = accA.getBalance() + accB.getBalance()
        assertEquals(2000, totalBalance)
    }

    @Test
    fun `no deadlock during circular transfers`() {
        val thread1 = Thread {
            repeat(100) { accA.transferTo(accB, 1) }
        }

        val thread2 = Thread {
            repeat(100) { accB.transferTo(accA, 1) }
        }

        thread1.start()
        thread2.start()
        thread1.join(3000)
        thread2.join(3000)

        assertFalse(thread1.isAlive || thread2.isAlive, "Potential deadlock detected")
    }
}
