
## What is a Type Alias?

A **type alias** allows you to give a name to any type.

```ts
type User = {
  id: number;
  name: string;
};
```

Now you can reuse:

```ts
const user: User = {
  id: 1,
  name: "Arul"
};
```

---

## 1.1 Primitive Alias

```ts
type ID = number;
type Username = string;
```

---

## 1.2 Union Types

```ts
type Status = "pending" | "approved" | "rejected";

let orderStatus: Status = "approved";
```

Restricts values to specific strings.

---

## 1.3 Intersection Types

Combine multiple types:

```ts
type Person = {
  name: string;
};

type Employee = {
  employeeId: number;
};

type Staff = Person & Employee;

const staff: Staff = {
  name: "John",
  employeeId: 101
};
```

---

## 1.4 Function Type Alias

```ts
type AddFunction = (a: number, b: number) => number;

const add: AddFunction = (a, b) => a + b;
```

---

## 1.5 Tuple Type Alias

```ts
type Coordinates = [number, number];

const point: Coordinates = [10, 20];
```

---

## When to Use Type Alias?

* For unions
* For intersections
* For complex reusable function types
* For mapped types

---

# 2. Interfaces in TypeScript

## What is an Interface?

An **interface** defines the structure of an object.

```ts
interface User {
  id: number;
  name: string;
}
```

---

## 2.1 Optional Properties

```ts
interface Product {
  id: number;
  name: string;
  description?: string;
}
```

---

## 2.2 Readonly Properties

```ts
interface Config {
  readonly apiUrl: string;
}
```

---

## 2.3 Method Definition

```ts
interface Calculator {
  add(a: number, b: number): number;
}
```

---

## 2.4 Interface Extension

```ts
interface Person {
  name: string;
}

interface Employee extends Person {
  employeeId: number;
}
```

---

## 2.5 Interface for Function

```ts
interface Multiply {
  (a: number, b: number): number;
}

const multiply: Multiply = (a, b) => a * b;
```

---

## 2.6 Interface for Classes

```ts
interface Animal {
  makeSound(): void;
}

class Dog implements Animal {
  makeSound() {
    console.log("Woof");
  }
}
```

---

# 3. Type Alias vs Interface (Important Interview Topic)

| Feature             | Type Alias  | Interface |
| ------------------- | ----------- | --------- |
| Object definition   | Yes         | Yes       |
| Union types         | Yes         | No        |
| Intersection types  | Yes         | Limited   |
| Extends             | Yes (via &) | Yes       |
| Declaration merging | No          | Yes       |
| Recommended for OOP | Less        | More      |

---

## Declaration Merging (Interface Only)

```ts
interface User {
  name: string;
}

interface User {
  age: number;
}

const user: User = {
  name: "Arul",
  age: 30
};
```

Interfaces merge automatically.

---

# 4. Functions in TypeScript

TypeScript allows full typing of parameters and return values.

---

## 4.1 Basic Function

```ts
function greet(name: string): string {
  return `Hello ${name}`;
}
```

---

## 4.2 Optional Parameters

```ts
function greetUser(name: string, age?: number) {
  if (age) {
    return `${name} is ${age}`;
  }
  return name;
}
```

---

## 4.3 Default Parameters

```ts
function calculateTax(amount: number, tax: number = 0.18) {
  return amount * tax;
}
```

---

## 4.4 Rest Parameters

```ts
function sum(...numbers: number[]): number {
  return numbers.reduce((a, b) => a + b, 0);
}
```

---

## 4.5 Function Overloading

```ts
function format(value: string): string;
function format(value: number): string;

function format(value: any): string {
  return value.toString();
}
```

---

## 4.6 Generic Functions

```ts
function identity<T>(value: T): T {
  return value;
}

identity<number>(10);
identity<string>("Hello");
```

---

# 5. Decorators in TypeScript

Decorators are special functions that modify classes, methods, properties, or parameters.

Used heavily in Angular.

---

## Enable Decorators

In `tsconfig.json`:

```json
{
  "experimentalDecorators": true
}
```

---

## Types of Decorators

1. Class Decorator
2. Method Decorator
3. Property Decorator
4. Parameter Decorator

---

## 5.1 Class Decorator

```ts
function Logger(constructor: Function) {
  console.log("Class Created:", constructor.name);
}

@Logger
class Person {
  constructor() {}
}
```

When class is defined → decorator runs.

---

## 5.2 Method Decorator

```ts
function LogMethod(
  target: any,
  propertyKey: string,
  descriptor: PropertyDescriptor
) {
  console.log(`Method ${propertyKey} is decorated`);
}

class Calculator {
  @LogMethod
  add(a: number, b: number) {
    return a + b;
  }
}
```

---

## 5.3 Property Decorator

```ts
function ReadOnly(target: any, propertyKey: string) {
  Object.defineProperty(target, propertyKey, {
    writable: false
  });
}

class Config {
  @ReadOnly
  apiUrl = "https://api.com";
}
```

---

## 5.4 Parameter Decorator

```ts
function LogParam(target: any, propertyKey: string, parameterIndex: number) {
  console.log(`Parameter at index ${parameterIndex} in ${propertyKey}`);
}

class UserService {
  getUser(@LogParam id: number) {
    return id;
  }
}
```

---

# Real-World Decorator Example (Logging)

```ts
function LogExecutionTime(
  target: any,
  propertyKey: string,
  descriptor: PropertyDescriptor
) {
  const original = descriptor.value;

  descriptor.value = function (...args: any[]) {
    const start = performance.now();
    const result = original.apply(this, args);
    const end = performance.now();
    console.log(`${propertyKey} took ${end - start} ms`);
    return result;
  };
}
```

---

# Decorators in Angular

Angular heavily uses decorators:

* Angular

Examples:

```ts
@Component()
@Injectable()
@Input()
@Output()
```

These are metadata decorators.

---

# Best Practices

### Type Alias

* Use for unions and complex mapped types
* Use for function signatures

### Interface

* Use for object contracts
* Use for classes
* Prefer in OOP-heavy code

### Functions

* Always specify return type in production
* Use generics for reusable logic
* Avoid `any`

### Decorators

* Use for cross-cutting concerns (logging, validation)
* Keep pure and reusable
* Avoid heavy logic inside decorators

---

# Interview Questions

1. Difference between type and interface?
2. Can type extend interface?
3. What is declaration merging?
4. What are decorators?
5. Difference between method and property decorators?
6. What is generic function?
7. What is function overloading?

---

# Summary

Type Alias → Flexible type definitions
Interface → Object contracts
Functions → Strong typing, generics, overloads
Decorators → Meta-programming capability

---
