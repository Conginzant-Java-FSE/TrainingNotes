In Java, operators are special symbols that perform operations on one or more operands. Operators can be classified into several categories based on the type of operations they perform. Here is a detailed guide to Java operators.

## Categories of Java Operators

### 1. Arithmetic Operators

These operators are used to perform basic arithmetic operations.

| Operator | Description     | Example         |

|----------|-----------------|-----------------|

| `+`      | Addition        | `a + b`         |

| `-`      | Subtraction     | `a - b`         |

| `*`      | Multiplication  | `a * b`         |

| `/`      | Division        | `a / b`         |

| `%`      | Modulus (remainder) | `a % b`     |

### 2. Unary Operators

These operators operate on a single operand.

| Operator | Description                          | Example   |

|----------|--------------------------------------|-----------|

| `+`      | Unary plus (indicates positive value)| `+a`      |

| `-`      | Unary minus (negates an expression)  | `-a`      |

| `++`     | Increment (increases value by 1)     | `a++` or `++a` |

| `--`     | Decrement (decreases value by 1)     | `a--` or `--a` |

| `!`      | Logical complement (inverts boolean value) | `!flag` |

### 3. Relational Operators

These operators are used to compare two values.

| Operator | Description                         | Example     |

|----------|-------------------------------------|-------------|

| `==`     | Equal to                            | `a == b`    |

| `!=`     | Not equal to                        | `a != b`    |

| `>`      | Greater than                        | `a > b`     |

| `<`      | Less than                           | `a < b`     |

| `>=`     | Greater than or equal to            | `a >= b`    |

| `<=`     | Less than or equal to               | `a <= b`    |

### 4. Logical Operators

These operators are used to combine multiple boolean expressions.

| Operator | Description                          | Example       |

|----------|--------------------------------------|---------------|

| `&&`     | Logical AND                          | `a && b`      |

| `||`     | Logical OR                           | `a || b`      |

| `!`      | Logical NOT                          | `!a`          |

### 5. Bitwise Operators

These operators are used to perform bit-level operations on integers.

| Operator | Description                         | Example     |

|----------|-------------------------------------|-------------|

| `&`      | Bitwise AND                         | `a & b`     |

| `|`      | Bitwise OR                          | `a | b`     |

| `^`      | Bitwise XOR                         | `a ^ b`     |

| `~`      | Bitwise complement                  | `~a`        |

| `<<`     | Left shift                          | `a << 2`    |

| `>>`     | Right shift                         | `a >> 2`    |

| `>>>`    | Unsigned right shift                | `a >>> 2`   |

### 6. Assignment Operators

These operators are used to assign values to variables.

| Operator | Description                         | Example      |

|----------|-------------------------------------|--------------|

| `=`      | Simple assignment                   | `a = b`      |

| `+=`     | Add and assign                      | `a += b`     |

| `-=`     | Subtract and assign                 | `a -= b`     |

| `*=`     | Multiply and assign                 | `a *= b`     |

| `/=`     | Divide and assign                   | `a /= b`     |

| `%=`     | Modulus and assign                  | `a %= b`     |

| `&=`     | Bitwise AND and assign              | `a &= b`     |

| `|=`     | Bitwise OR and assign               | `a |= b`     |

| `^=`     | Bitwise XOR and assign              | `a ^= b`     |

| `<<=`    | Left shift and assign               | `a <<= 2`    |

| `>>=`    | Right shift and assign              | `a >>= 2`    |

| `>>>=`   | Unsigned right shift and assign     | `a >>>= 2`   |

### 7. Conditional (Ternary) Operator

This operator is a shorthand for the if-then-else statement.

| Operator | Description                         | Example                   |

|----------|-------------------------------------|---------------------------|

| `?:`     | Ternary conditional                 | `condition ? trueValue : falseValue` |

### 8. Instanceof Operator

This operator is used to test whether an object is an instance of a specific class or subclass.

| Operator | Description                         | Example           |

|----------|-------------------------------------|-------------------|

| `instanceof` | Check instance                  | `obj instanceof ClassName` |

### Examples and Usage

Here are some examples demonstrating the usage of various operators in Java:

#### Arithmetic Operators

```java

int a = 10;

int b = 5;

System.out.println("a + b = " + (a + b)); // 15

System.out.println("a - b = " + (a - b)); // 5

System.out.println("a * b = " + (a * b)); // 50

System.out.println("a / b = " + (a / b)); // 2

System.out.println("a % b = " + (a % b)); // 0

```

#### Unary Operators

```java

int a = 10;

System.out.println("a = " + a); // 10

System.out.println("++a = " + ++a); // 11

System.out.println("a++ = " + a++); // 11

System.out.println("a = " + a); // 12

System.out.println("--a = " + --a); // 11

System.out.println("a-- = " + a--); // 11

System.out.println("a = " + a); // 10

System.out.println("!true = " + !true); // false

```

#### Relational Operators

```java

int a = 10;

int b = 5;

System.out.println("a == b: " + (a == b)); // false

System.out.println("a != b: " + (a != b)); // true

System.out.println("a > b: " + (a > b)); // true

System.out.println("a < b: " + (a < b)); // false

System.out.println("a >= b: " + (a >= b)); // true

System.out.println("a <= b: " + (a <= b)); // false

```

#### Logical Operators

```java

boolean a = true;

boolean b = false;

System.out.println("a && b: " + (a && b)); // false

System.out.println("a || b: " + (a || b)); // true

System.out.println("!a: " + !a); // false

```

#### Bitwise Operators

```java

int a = 5; // 0101 in binary

int b = 3; // 0011 in binary

System.out.println("a & b: " + (a & b)); // 1 (0001 in binary)

System.out.println("a | b: " + (a | b)); // 7 (0111 in binary)

System.out.println("a ^ b: " + (a ^ b)); // 6 (0110 in binary)

System.out.println("~a: " + ~a); // -6 (two's complement representation)

System.out.println("a << 2: " + (a << 2)); // 20 (10100 in binary)

System.out.println("a >> 2: " + (a >> 2)); // 1 (0001 in binary)

System.out.println("a >>> 2: " + (a >>> 2)); // 1 (0001 in binary)

```

#### Assignment Operators

```java

int a = 10;

a += 5; // a = a + 5

System.out.println("a = " + a); // 15

a -= 5; // a = a - 5

System.out.println("a = " + a); // 10

a *= 2; // a = a * 2

System.out.println("a = " + a); // 20

a /= 2; // a = a / 2

System.out.println("a = " + a); // 10

a %= 3; // a = a % 3

System.out.println("a = " + a); // 1

```

#### Conditional (Ternary) Operator

```java

int a = 10;

int b = 20;

int max = (a > b) ? a : b;

System.out.println("Max of a and b is: " + max); // 20

```

#### Instanceof Operator

```java

String str = "Hello";

boolean result = str instanceof String;

System.out.println("str is an instance of String: " + result); // true

```

### Summary

Java operators are fundamental to manipulating data and controlling the flow of your program. Understanding how to use these operators correctly will help