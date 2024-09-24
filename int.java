//Write a Java program to read data from a file and count the number of words, lines and characters.

javaCopyimport java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileStats {
    public static void main(String[] args) {
        String filename = "sample.txt";
        int wordCount = 0;
        int lineCount = 0;
        int charCount = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                charCount += line.length();
                String[] words = line.split("\\s+");
                wordCount += words.length;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        
        System.out.println("Words: " + wordCount);
        System.out.println("Lines: " + lineCount);  
        System.out.println("Characters: " + charCount);
    }
}



//Create a class called Employee with attributes like id, name, and salary. Implement methods to give a salary raise and print employee details. Create a few Employee objects and demonstrate the functionality.

javaCopyclass Employee {
    private int id;
    private String name;
    private double salary;
    
    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
    
    public void raiseSalary(double percentage) {
        salary += salary * (percentage/100);
    }
    
    public void printDetails() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Salary: $" + salary);
    }
}

public class EmployeeDemo {
    public static void main(String[] args) {
        Employee e1 = new Employee(101, "John Doe", 50000);
        Employee e2 = new Employee(102, "Jane Smith", 60000);
        
        System.out.println("Before raise:");
        e1.printDetails();
        e2.printDetails();
        
        e1.raiseSalary(10);
        e2.raiseSalary(15);
        
        System.out.println("\nAfter raise:");
        e1.printDetails();
        e2.printDetails();
    }
}



//Write a Java program that creates a GUI window with a button. When the button is clicked, it should change the background color of the window randomly.

javaCopyimport javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ColorChangeGUI extends JFrame {
    private JButton changeButton;
    
    public ColorChangeGUI() {
        setTitle("Color Changer");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        changeButton = new JButton("Change Color");
        changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color randomColor = new Color(
                    (int)(Math.random() * 256),
                    (int)(Math.random() * 256),
                    (int)(Math.random() * 256)
                );
                getContentPane().setBackground(randomColor);
            }
        });
        
        add(changeButton);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new ColorChangeGUI();
    }
}



//Implement a simple calculator program that can perform addition, subtraction, multiplication, and division operations. Use exception handling to deal with division by zero.

javaCopyimport java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();
        
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();
        
        System.out.print("Enter operation (+, -, *, /): ");
        char operation = scanner.next().charAt(0);
        
        double result = 0;
        
        try {
            switch(operation) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if(num2 == 0) {
                        throw new ArithmeticException("Division by zero!");
                    }
                    result = num1 / num2;
                    break;
                default:
                    System.out.println("Invalid operation!");
                    return;
            }
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}



//Create a multithreaded program where one thread generates random numbers and another thread calculates their sum. Use proper synchronization.

javaCopyimport java.util.ArrayList;
import java.util.List;
import java.util.Random;

class NumberGenerator implements Runnable {
    private List<Integer> numbers;
    private final int COUNT = 10;
    
    public NumberGenerator(List<Integer> numbers) {
        this.numbers = numbers;
    }
    
    public void run() {
        Random rand = new Random();
        for (int i = 0; i < COUNT; i++) {
            synchronized(numbers) {
                int num = rand.nextInt(100);
                System.out.println("Generated: " + num);
                numbers.add(num);
                numbers.notify();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class SumCalculator implements Runnable {
    private List<Integer> numbers;
    private int sum = 0;
    
    public SumCalculator(List<Integer> numbers) {
        this.numbers = numbers;
    }
    
    public void run() {
        while (true) {
            synchronized(numbers) {
                try {
                    numbers.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!numbers.isEmpty()) {
                    int num = numbers.remove(0);
                    sum += num;
                    System.out.println("Sum: " + sum);
                }
            }
        }
    }
}

public class MultithreadedSum {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        
        Thread genThread = new Thread(new NumberGenerator(numbers));
        Thread sumThread = new Thread(new SumCalculator(numbers));
        
        genThread.start();
        sumThread.start();
    }
}
