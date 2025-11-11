package view;

import controller.Controller;
import exceptions.MyException;
import model.adt.*;
import model.expressions.*;
import model.PrgState;
import model.statements.*;
import model.types.*;
import model.values.*;
import repository.IRepository;
import repository.Repository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // --- Hardcoded Example Programs ---

        // Example 1: int v; v=2; Print(v)
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        // Example 2: int a; int b; a=2+3*5; b=a+1; Print(b)
        // Note: Using 1 for '+', 3 for '*' as per ArithExp spec
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),
                                new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), 3), 1)), // a = 2 + (3 * 5)
                                new CompStmt(new AssignStmt("b", new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), 1)), // b = a + 1
                                        new PrintStmt(new VarExp("b"))))));

        // Example 3: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));

        // Example 4: Testing logical expressions
        // bool a; a=true; bool b; b=false; Print(a and b)
        // Note: Using 1 for 'and' as per LogicExp spec
        IStmt ex4 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("b", new BoolType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new AssignStmt("b", new ValueExp(new BoolValue(false))),
                                        new PrintStmt(new LogicExp(new VarExp("a"), new VarExp("b"), 1)))))); // 1 for 'and'

        // Example 5: Division by zero test
        // int v; v=10 / (2-2); Print(v)
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v",
                        new ArithExp(
                                new ValueExp(new IntValue(10)),
                                new ArithExp(new ValueExp(new IntValue(2)), new ValueExp(new IntValue(2)), 2), // 2 is '-'
                                4) // 4 is '/'
                ), new PrintStmt(new VarExp("v"))));

        // --- Text Menu ---
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Enter option: ");
            String option = scanner.nextLine();

            IStmt selectedStmt = null;
            String logFilePath = "";

            switch (option) {
                case "0":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                case "1":
                    selectedStmt = ex1;
                    logFilePath = "log1.txt";
                    break;
                case "2":
                    selectedStmt = ex2;
                    logFilePath = "log2.txt";
                    break;
                case "3":
                    selectedStmt = ex3;
                    logFilePath = "log3.txt";
                    break;
                case "4":
                    selectedStmt = ex4;
                    logFilePath = "log4.txt";
                    break;
                case "5":
                    selectedStmt = ex5;
                    logFilePath = "log5.txt";
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    continue; // Skip the execution part
            }

            // --- Execute the selected program ---
            if (selectedStmt != null) {
                try {
                    // 1. Create a fresh Program State for each run
                    MyIStack<IStmt> exeStack = new MyStack<>();
                    MyIDictionary<String, Value> symTable = new MyDictionary<>();
                    MyIList<Value> out = new MyList<>();
                    
                    // Use the correct PrgState from model.prgstate
                    PrgState myPrgState = new PrgState(exeStack, symTable, out, selectedStmt);

                    // 2. Create the Repository
                    IRepository repo = new Repository(myPrgState, logFilePath);

                    // 3. Create the Controller
                    Controller controller = new Controller(repo);

                    // 4. Run the 'allStep' execution
                    System.out.println("Executing program: " + selectedStmt);
                    controller.allStep();
                    System.out.println("Execution finished.");

                    // 5. Print the final output
                    // The 'out' list is modified by the controller during execution
                    System.out.println("Output: " + out.toString());

                } catch (MyException e) {
                    System.out.println("\n--- RUNTIME ERROR ---\n" + e.getMessage() + "\n---------------------");
                } catch (Exception e) {
                    System.out.println("\n--- UNEXPECTED SYSTEM ERROR ---\n" + e.getMessage() + "\n-------------------------------");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Helper method to print the menu.
     */
    private static void printMenu() {
        System.out.println("\n========= Toy Language Interpreter Menu =========");
        System.out.println("1. Run Program 1: (int v; v=2; Print(v))");
        System.out.println("2. Run Program 2: (int a; int b; a=2+3*5; b=a+1; Print(b))");
        System.out.println("3. Run Program 3: (bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v))");
        System.out.println("4. Run Program 4: (bool a; a=true; bool b; b=false; Print(a and b))");
        System.out.println("5. Run Program 5: (Division by zero error test)");
        System.out.println("0. Exit");
        System.out.println("=================================================");
    }
}