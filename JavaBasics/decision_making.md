Java selection statements allow a program to choose different paths of execution based on conditions. The main types of selection statements in Java are `if`, `if-else`, `if-else-if`, `switch`, and the ternary operator. Here's an overview of each:

### 1. `if` Statement

The `if` statement executes a block of code if its condition evaluates to `true`.

#### Syntax:

```java

if (condition) {

    // Code to be executed if condition is true

}

```

#### Example:

```java

int age = 18;

if (age >= 18) {

    System.out.println("You are an adult.");

}

```

### 2. `if-else` Statement

The `if-else` statement executes one block of code if the condition is `true` and another block if the condition is `false`.

#### Syntax:

```java

if (condition) {

    // Code to be executed if condition is true

} else {

    // Code to be executed if condition is false

}

```

#### Example:

```java

int age = 16;

if (age >= 18) {

    System.out.println("You are an adult.");

} else {

    System.out.println("You are a minor.");

}

```

### 3. `if-else-if` Statement

The `if-else-if` ladder allows multiple conditions to be checked sequentially. When a condition is `true`, the corresponding block of code is executed.

#### Syntax:

```java

if (condition1) {

    // Code to be executed if condition1 is true

} else if (condition2) {

    // Code to be executed if condition2 is true

} else if (condition3) {

    // Code to be executed if condition3 is true

} else {

    // Code to be executed if all conditions are false

}

```

#### Example:

```java

int score = 75;

if (score >= 90) {

    System.out.println("Grade: A");

} else if (score >= 80) {

    System.out.println("Grade: B");

} else if (score >= 70) {

    System.out.println("Grade: C");

} else if (score >= 60) {

    System.out.println("Grade: D");

} else {

    System.out.println("Grade: F");

}

```

### 4. `switch` Statement

The `switch` statement allows a variable to be tested for equality against a list of values. Each value is called a case, and the variable being switched on is checked for each case.

#### Syntax:

```java

switch (variable) {

    case value1:

        // Code to be executed if variable == value1

        break;

    case value2:

        // Code to be executed if variable == value2

        break;

    // You can have any number of case statements

    default:

        // Code to be executed if none of the cases are true

}

```

#### Example:

```java

int day = 3;

switch (day) {

    case 1:

        System.out.println("Monday");

        break;

    case 2:

        System.out.println("Tuesday");

        break;

    case 3:

        System.out.println("Wednesday");

        break;

    case 4:

        System.out.println("Thursday");

        break;

    case 5:

        System.out.println("Friday");

        break;

    case 6:

        System.out.println("Saturday");

        break;

    case 7:

        System.out.println("Sunday");

        break;

    default:

        System.out.println("Invalid day");

}

```

### 5. Ternary Operator

The ternary operator is a shorthand for the `if-else` statement. It is used to assign a value to a variable based on a condition.

#### Syntax:

```java

variable = (condition) ? expression1 : expression2;

```

#### Example:

```java

int age = 18;

String type = (age >= 18) ? "Adult" : "Minor";

System.out.println(type);

```

### Examples in Practice

#### Nested `if-else` Statements

Nested `if-else` statements are `if-else` statements inside another `if-else` statement.

#### Example:

```java

int number = 0;

if (number > 0) {

    System.out.println("Positive");

} else {

    if (number < 0) {

        System.out.println("Negative");

    } else {

        System.out.println("Zero");

    }

}

```

### Practical Applications

- **Decision Making**: Determine which block of code to execute based on conditions.

- **Menu Selection**: Implement menus where users can select different options.

- **Input Validation**: Validate user input and provide appropriate feedback.

Understanding and effectively using selection statements is crucial for controlling the flow of a Java program and making it respond dynamically to different inputs and conditions.