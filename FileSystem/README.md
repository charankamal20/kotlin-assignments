# Filesystem Explorer Problem

## Problem Overview

The **Filesystem Explorer** is a class that simulates the creation, manipulation, and exploration of a virtual filesystem. The goal is to design a filesystem with basic functionalities such as creating folders, adding files, listing contents of a folder, getting the size of files and folders, moving files, and printing the entire structure of the filesystem.

### Key Concepts

The filesystem will follow these basic concepts:

- **Folder**: A container for files and other folders. A folder can be nested inside another folder.
- **File**: A file has a name and a size. It resides within a folder and cannot contain other files or folders.

### Operations

You will implement the following operations for the filesystem:

1. **createFolder(path: String)**: Creates a folder at the specified path. If the path is invalid or if any intermediate folder is missing, the method should create those folders as well.

2. **addFile(path: String, size: Int)**: Adds a file with the specified size at the given path. If the parent folder does not exist, it should create the necessary folders.

3. **listFolder(path: String): List<String>**: Lists all the files and folders in the specified folder. If the path is not a folder, or if the folder does not exist, it should throw an error.

4. **getSize(path: String): Int**: Returns the total size of a file or folder. If the path is a file, it returns the file size. If it’s a folder, it sums the sizes of all files within the folder, including nested subfolders.

5. **move(source: String, destination: String)**: Moves a file or folder from one location to another. If the destination already contains a file/folder with the same name, or if the source doesn’t exist, it should throw an error.

6. **printTree(): String**: Prints the entire structure of the filesystem in a human-readable format. The output should show the hierarchical structure of the files and folders.

---

## Requirements

### Functional Requirements

1. **createFolder**:
   - Handles both creating new folders and ensuring the creation of any missing intermediate folders.
   - Throws an error if a folder or file already exists at the path.
   - Handles paths with redundant slashes.

2. **addFile**:
   - Adds a new file at a specified location.
   - Ensures that the parent folder exists or creates it.
   - Throws an error if a folder already exists at the given path.
   
3. **listFolder**:
   - Lists all contents (files and subfolders) of a folder.
   - Throws an error if the path does not exist or if the path is not a folder.

4. **getSize**:
   - Returns the size of a file or the combined size of files inside a folder.
   - Throws an error if the path is invalid or does not exist.

5. **move**:
   - Moves a file/folder to another location.
   - Throws an error if the destination already contains a file/folder with the same name, or if the source path does not exist.

6. **printTree**:
   - Outputs a string representation of the entire filesystem in a tree-like structure.
   - Each folder should be indented to show hierarchy.

### Error Handling

The filesystem should throw `IllegalArgumentException` or similar appropriate exceptions for invalid operations, such as:

- Trying to list contents of a file.
- Moving a file/folder to a location where the destination already has a file/folder with the same name.
- Trying to operate on non-existing paths.

### Assumptions

- Folder names and file names must not be empty or null.
- The filesystem supports basic operations (creating folders, adding files, etc.), but does not require advanced features such as permissions or version control.
- Paths use the `/` character as a separator, and can have redundant slashes (e.g., `//a///b///c`).
- All file sizes are integers, and the total size of folders is the sum of the sizes of the contained files.

---

## Directory Structure

Here's an example of how the filesystem might look:

/a
  ├── file1.txt (10)
  ├── file2.txt (20)
  └── /b
      └── file3.txt (5)
  └── /c
      └── file4.txt (15)


In this example:
- The folder `/a` contains two files: `file1.txt` and `file2.txt`.
- The folder `/a/b` contains `file3.txt`.
- The folder `/a/c` contains `file4.txt`.
- The total size of folder `/a` is 50 (10 + 20 + 5 + 15).

---

## Points to Study Before Attempting This Problem

Before attempting to solve this problem, it is important to understand the following concepts:

1. **Data Structures**:
   - **Trees**: A hierarchical structure like folders and files can be modeled as a tree, where each folder is a node, and each file or subfolder is a child node.
   - **Recursion**: Some operations like calculating the size of a folder or printing the tree structure can be implemented using recursion.
   - **HashMaps or HashSets**: Storing files and folders can be efficiently handled with these structures, as they allow quick lookups for existing files/folders.

2. **Error Handling**:
   - **Exception Handling**: Understand how to throw and catch exceptions when invalid operations occur, like attempting to create a file where a folder already exists.

3. **String Manipulation**:
   - **Path Parsing**: You'll need to manipulate paths, handling cases with redundant slashes and ensuring paths are normalized.
   - **File System Representation**: How to represent a filesystem in a human-readable tree format.

---

## How to Run

1. Clone this repository.
2. Implement the `Filesystem` class and its methods according to the problem description.
3. Implement unit tests for the methods.
4. Run tests using your preferred test runner (e.g., JUnit or IntelliJ IDEA).

---

## Optional Enhancements (Stretch Goals)
 - Add delete(path) with recursive cleanup.

 - Add file metadata (createdAt, modifiedAt).

 - Cache folder sizes and invalidate on mutation.

 - Add path autocompletion (trie-like logic).
