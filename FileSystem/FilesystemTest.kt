import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FilesystemTest {

    private lateinit var fs: Filesystem

    @BeforeEach
    fun setUp() {
        fs = Filesystem()
    }

    // -----------------------
    // 1. createFolder tests
    // -----------------------

    @Test
    fun `createFolder creates nested folders`() {
        fs.createFolder("/a/b/c")
        val contents = fs.listFolder("/a/b")
        assertTrue(contents.contains("c"))
    }

    @Test
    fun `createFolder fails if path already exists as a file`() {
        fs.addFile("/a/file.txt", 10)
        assertThrows<IllegalArgumentException> {
            fs.createFolder("/a/file.txt")
        }
    }

    @Test
    fun `createFolder fails if folder already exists`() {
        fs.createFolder("/a/b")
        assertThrows<IllegalArgumentException> {
            fs.createFolder("/a/b")
        }
    }

    // -----------------------
    // 2. addFile tests
    // -----------------------

    @Test
    fun `addFile creates intermediate folders and file`() {
        fs.addFile("/a/b/file.txt", 20)
        val contents = fs.listFolder("/a/b")
        assertTrue(contents.contains("file.txt"))
    }

    @Test
    fun `addFile fails if path already exists as folder`() {
        fs.createFolder("/a/b")
        assertThrows<IllegalArgumentException> {
            fs.addFile("/a/b", 10)
        }
    }

    @Test
    fun `addFile fails if parent path is a file`() {
        fs.addFile("/a/file.txt", 5)
        assertThrows<IllegalArgumentException> {
            fs.addFile("/a/file.txt/nested.txt", 3)
        }
    }

    // -----------------------
    // 3. listFolder tests
    // -----------------------

    @Test
    fun `listFolder returns correct children`() {
        fs.addFile("/a/file1.txt", 1)
        fs.addFile("/a/file2.txt", 1)
        val list = fs.listFolder("/a")
        assertEquals(setOf("file1.txt", "file2.txt"), list.toSet())
    }

    @Test
    fun `listFolder fails if path is not a folder`() {
        fs.addFile("/a/file.txt", 1)
        assertThrows<IllegalArgumentException> {
            fs.listFolder("/a/file.txt")
        }
    }

    @Test
    fun `listFolder fails if path does not exist`() {
        assertThrows<IllegalArgumentException> {
            fs.listFolder("/does/not/exist")
        }
    }

    // -----------------------
    // 4. getSize tests
    // -----------------------

    @Test
    fun `getSize returns file size correctly`() {
        fs.addFile("/a/file.txt", 10)
        assertEquals(10, fs.getSize("/a/file.txt"))
    }

    @Test
    fun `getSize returns folder size recursively`() {
        fs.addFile("/a/b/1.txt", 5)
        fs.addFile("/a/b/2.txt", 10)
        fs.addFile("/a/b/c/3.txt", 15)
        assertEquals(30, fs.getSize("/a"))
    }

    @Test
    fun `getSize fails if path does not exist`() {
        assertThrows<IllegalArgumentException> {
            fs.getSize("/no/such/path")
        }
    }

    // -----------------------
    // 5. move tests
    // -----------------------

    @Test
    fun `move file into another folder`() {
        fs.addFile("/a/file.txt", 5)
        fs.createFolder("/b")
        fs.move("/a/file.txt", "/b")
        assertTrue(fs.listFolder("/b").contains("file.txt"))
        assertThrows<IllegalArgumentException> { fs.getSize("/a/file.txt") }
    }

    @Test
    fun `move fails if source doesn't exist`() {
        fs.createFolder("/dest")
        assertThrows<IllegalArgumentException> {
            fs.move("/no/file", "/dest")
        }
    }

    @Test
    fun `move fails if destination is not a folder`() {
        fs.addFile("/dest.txt", 1)
        fs.addFile("/src.txt", 1)
        assertThrows<IllegalArgumentException> {
            fs.move("/src.txt", "/dest.txt")
        }
    }

    @Test
    fun `move fails if destination has same name`() {
        fs.addFile("/a/file.txt", 1)
        fs.createFolder("/b")
        fs.addFile("/b/file.txt", 2)
        assertThrows<IllegalArgumentException> {
            fs.move("/a/file.txt", "/b")
        }
    }

    // -----------------------
    // 6. printTree tests
    // -----------------------

    @Test
    fun `printTree shows correct structure`() {
        fs.addFile("/a/b/c.txt", 1)
        fs.addFile("/a/d.txt", 1)
        val output = fs.printTree()
        assertTrue(output.contains("a"))
        assertTrue(output.contains("b"))
        assertTrue(output.contains("c.txt"))
        assertTrue(output.contains("d.txt"))
    }

    // -----------------------
    // Edge case tests
    // -----------------------

    @Test
    fun `creating folder with redundant slashes works`() {
        fs.createFolder("//a///b///c")
        assertTrue(fs.listFolder("/a/b").contains("c"))
    }

    @Test
    fun `move folder into its own subfolder fails`() {
        fs.createFolder("/a/b")
        assertThrows<IllegalArgumentException> {
            fs.move("/a", "/a/b")
        }
    }
}
