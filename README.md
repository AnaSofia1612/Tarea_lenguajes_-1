# DFA Minimization - Assignment 1

**Course:**  Formal Languages and Compilers  
**Class number:** 5730  
**Students:**  
- Ana Sofia Angarita Barrios  
- Nawal Oriana Valoyes Rentería  

---

## 🖥️ System and Tools

- **Operating System:** Windows 10  
- **Programming Language:** Java 17  
- **IDE/Editor Used:** Visual Studio Code  

---

## 🧩 Description

This program implements the DFA minimization algorithm described in *Kozen (1997), Lecture 14*.  
The algorithm identifies equivalent states in a deterministic finite automaton (DFA) and prints them in lexicographical order.

The implementation is based on the table-filling algorithm with dependency propagation, which marks non-equivalent state pairs and determines equivalent ones.

---

## ⚙️ Algorithm Explanation

1. **Mark initial pairs:**  
   Mark all pairs `(p, q)` where one state is final and the other is not.

2. **Build dependencies:**  
   For every pair `(p, q)` and every symbol `a` in the alphabet, track which pairs depend on `(δ(p, a), δ(q, a))`.

3. **Propagate marks:**  
   Using a queue (BFS), propagate the "non-equivalence" marks to all dependent pairs.

4. **Find equivalent states:**  
   Pairs that remain unmarked are equivalent states and are printed in lexicographical order.

---

## 🧮 Input Format

Each test case represents a DFA **without inaccessible states**, described as follows:

1. Number of states `n`  
2. The alphabet symbols (separated by spaces)  
3. The final states (separated by spaces)  
4. `n` lines describing the transition table.  
   Each line has one destination per symbol (in the same order as the alphabet).

**Example input:**

```
4
6
a b
1 2 5
0 1 2
1 3 4
2 4 3
3 5 5
4 5 5
5 5 5
```
---

## 🧾 Output Format

For each test case, the program prints one line with the pairs of equivalent states, separated by spaces:

Example output:
(1, 2) (3, 4)

---

## 🚀 How to Run the Program in Visual Studio Code

1. **Open the project:**  
   - Download or clone this repository.  
   - Open the folder `TAREA_LENGUAJES_#1` in Visual Studio Code.

2. **Verify the structure:**  
   It should look like this:
```
   TAREA_LENGUAJES_#1/
├── src/
│ └── Main.java
├── input.txt
└── README.md
```

3. **Run the program:**  
- Make sure you have **Java JDK 17 or higher** installed.  
- Click the **Run ▶️** button at the top of Visual Studio Code.  
- The program will automatically read the file `input.txt`  
  and display the equivalent state pairs of the minimized automaton in the terminal.

 **References**  
 - IA: Chatgpt
 - Book: Kozen(1997)




