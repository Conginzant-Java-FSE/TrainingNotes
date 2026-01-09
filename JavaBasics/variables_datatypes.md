In Java, variables and data types are fundamental concepts. Here's a detailed overview:

### Variables

Variables in Java are containers that hold data that can be manipulated during the execution of a program. Each variable has a data type that determines the kind of data it can hold.

#### Variable Declaration

A variable must be declared before it is used. The syntax for declaring a variable is:

```java

dataType variableName;

```

For example:

```java

int age;

double salary;

char grade;

```

#### Variable Initialization

A variable can be initialized (assigned an initial value) at the time of declaration:

```java

int age = 25;

double salary = 55000.75;

char grade = 'A';

```

#### Variable Types

1\. **Local Variables**: Declared inside a method, constructor, or block and only accessible within that method, constructor, or block.

2\. **Instance Variables**: Declared inside a class but outside any method, constructor, or block. They are created when an object is instantiated and destroyed when the object is destroyed.

3\. **Class/Static Variables**: Declared with the `static` keyword inside a class but outside any method, constructor, or block. They are shared among all instances of a class.

### Data Types

Java is a strongly typed language, which means each variable must be declared with a data type.

#### Primitive Data Types

Java has eight primitive data types:

1\. **byte**

   - Size: 8 bits

   - Range: -128 to 127

2\. **short**

   - Size: 16 bits

   - Range: -32,768 to 32,767

3\. **int**

   - Size: 32 bits

   - Range: -2^31 to 2^31-1

4\. **long**

   - Size: 64 bits

   - Range: -2^63 to 2^63-1

5\. **float**

   - Size: 32 bits

   - Range: Approximately ±3.40282347E+38F (6-7 significant decimal digits)

6\. **double**

   - Size: 64 bits

   - Range: Approximately ±1.79769313486231570E+308 (15 significant decimal digits)

7\. **char**

   - Size: 16 bits

   - Range: 0 to 65,535 (unsigned)

   - Used to store a single character

8\. **boolean**

   - Size: 1 bit

   - Values: `true` or `false`

#### Non-Primitive Data Types

These include classes, interfaces, and arrays. Non-primitive types are also known as reference types because they refer to objects.

1\. **String**: Used to store a sequence of characters.

   ```java

   String message = "Hello, World!";

   ```

2\. **Arrays**: Containers that hold a fixed number of values of a single type.

   ```java

   int[] numbers = {1, 2, 3, 4, 5};

   String[] names = {"Alice", "Bob", "Charlie"};

   ```

3\. **Classes**: Blueprints for objects. A class defines a datatype by bundling data and methods that work on the data into one single unit.

   ```java

   public class Car {

       String color;

       int year;

       void display() {

           System.out.println("Color: " + color + ", Year: " + year);

       }

   }

   ```

4\. **Interfaces**: Abstract types that allow the specification of methods that one or more classes are expected to implement.

   ```java

   public interface Vehicle {

       void start();

       void stop();

   }

   ```

### Examples

#### Variable Declaration and Initialization

```java

public class Main {

    public static void main(String[] args) {

        int age = 25; // Declaration and initialization

        double salary = 50000.0;

        char grade = 'A';

        boolean isEmployed = true;

        System.out.println("Age: " + age);

        System.out.println("Salary: " + salary);

        System.out.println("Grade: " + grade);

        System.out.println("Is Employed: " + isEmployed);

    }

}

```

#### Using Arrays

```java

public class Main {

    public static void main(String[] args) {

        int[] numbers = {1, 2, 3, 4, 5};

        for (int number : numbers) {

            System.out.println(number);

        }

    }

}

```

#### Using a Class

```java

public class Main {

    public static void main(String[] args) {

        Car myCar = new Car();

        myCar.color = "Red";

        myCar.year = 2020;

        myCar.display();

    }

}

class Car {

    String color;

    int year;

    void display() {

        System.out.println("Color: " + color + ", Year: " + year);

    }

}

```

Understanding variables and data types in Java is crucial for writing efficient and error-free programs.