# TypeScript – Classes and Access Modifiers 

TypeScript adds **object-oriented programming (OOP)** capabilities on top of JavaScript.

---

# 1. What is a Class in TypeScript?

A class is a blueprint for creating objects.

```ts
class Person {
  name: string;
  age: number;

  constructor(name: string, age: number) {
    this.name = name;
    this.age = age;
  }

  greet(): string {
    return `Hello, I am ${this.name}`;
  }
}

const p1 = new Person("Arul", 30);
console.log(p1.greet());
```

---

## Behind the Scenes

TypeScript compiles to JavaScript classes (ES6 or older depending on config).

---

# 2. Class Structure Overview

```
class ClassName {
  properties
  constructor()
  methods
}
```

---

# 3. Access Modifiers in TypeScript

TypeScript provides three access modifiers:

| Modifier  | Accessible Inside Class | Subclass | Outside |
| --------- | ----------------------- | -------- | ------- |
| public    | Yes                     | Yes      | Yes     |
| private   | Yes                     | No       | No      |
| protected | Yes                     | Yes      | No      |

---

# 4. Public (Default Modifier)

If no modifier is specified, it is `public`.

```ts
class Employee {
  public name: string;

  constructor(name: string) {
    this.name = name;
  }
}

const emp = new Employee("John");
console.log(emp.name); // Allowed
```

---

# 5. Private Modifier

`private` means accessible only inside the class.

```ts
class BankAccount {
  private balance: number = 0;

  deposit(amount: number) {
    this.balance += amount;
  }

  getBalance() {
    return this.balance;
  }
}

const account = new BankAccount();
account.deposit(500);

// account.balance ❌ Error
```

Why use private?

* Protect internal state
* Encapsulation
* Prevent misuse

---

# 6. Protected Modifier

Accessible inside class and subclasses.

```ts
class Animal {
  protected name: string;

  constructor(name: string) {
    this.name = name;
  }
}

class Dog extends Animal {
  bark() {
    console.log(`${this.name} says Woof`);
  }
}

const dog = new Dog("Tommy");
// dog.name ❌ Error
```

---

# 7. Constructor in TypeScript

Constructor initializes object.

```ts
class Car {
  brand: string;

  constructor(brand: string) {
    this.brand = brand;
  }
}
```

---

# 8. Parameter Properties (Shortcut)

Instead of writing:

```ts
class User {
  name: string;
  age: number;

  constructor(name: string, age: number) {
    this.name = name;
    this.age = age;
  }
}
```

Use shortcut:

```ts
class User {
  constructor(public name: string, private age: number) {}
}
```

TypeScript automatically creates properties.

---

# 9. Readonly Modifier

Prevents modification after initialization.

```ts
class Config {
  readonly apiUrl: string;

  constructor(url: string) {
    this.apiUrl = url;
  }
}

const config = new Config("https://api.com");
// config.apiUrl = "new" ❌ Error
```

---

# 10. Getters and Setters

Used for controlled access.

```ts
class Student {
  private _marks: number = 0;

  get marks(): number {
    return this._marks;
  }

  set marks(value: number) {
    if (value >= 0 && value <= 100) {
      this._marks = value;
    }
  }
}

const s = new Student();
s.marks = 90;
console.log(s.marks);
```

Why use?

* Validation
* Computed properties
* Encapsulation

---

# 11. Static Properties and Methods

Belong to class, not object.

```ts
class MathUtils {
  static PI: number = 3.14;

  static square(num: number): number {
    return num * num;
  }
}

console.log(MathUtils.PI);
console.log(MathUtils.square(5));
```

Accessed using class name.

---

# 12. Inheritance

Using `extends`.

```ts
class Person {
  constructor(public name: string) {}
}

class Developer extends Person {
  constructor(name: string, public skill: string) {
    super(name);
  }

  code() {
    console.log(`${this.name} is coding in ${this.skill}`);
  }
}
```

---

# 13. Method Overriding

```ts
class Animal {
  makeSound() {
    console.log("Some sound");
  }
}

class Cat extends Animal {
  override makeSound() {
    console.log("Meow");
  }
}
```

`override` keyword ensures method exists in parent.

---

# 14. Abstract Classes

Abstract classes cannot be instantiated.

```ts
abstract class Shape {
  abstract calculateArea(): number;
}

class Circle extends Shape {
  constructor(private radius: number) {
    super();
  }

  calculateArea(): number {
    return Math.PI * this.radius * this.radius;
  }
}
```

Used for enforcing structure in subclasses.

---

# 15. Real-World Example: E-Commerce Order System

```ts
abstract class Payment {
  constructor(protected amount: number) {}

  abstract pay(): void;
}

class CreditCardPayment extends Payment {
  pay() {
    console.log(`Paid ${this.amount} using Credit Card`);
  }
}

class UpiPayment extends Payment {
  pay() {
    console.log(`Paid ${this.amount} using UPI`);
  }
}

const payment: Payment = new CreditCardPayment(5000);
payment.pay();
```

This is polymorphism.

---

# 16. Difference Between private and #private

TypeScript private:

```ts
private balance: number;
```

JavaScript native private:

```ts
#balance: number;
```

`#` is runtime enforced.
`private` is compile-time enforced.

---

# 17. Best Practices

* Use `private` for internal data
* Use `protected` for inheritance cases
* Prefer parameter properties
* Use `readonly` when value should not change
* Use abstract classes for framework-like structure
* Avoid making everything public

---

# 18. Common Interview Questions

1. Difference between public, private, protected?
2. What is encapsulation?
3. What is parameter property?
4. Difference between abstract class and interface?
5. What is static keyword?
6. Can private members be inherited?
7. What is method overriding?

---

# 19. Summary

Classes provide:

* Structure
* Encapsulation
* Reusability
* Polymorphism
* Strong typing

Access Modifiers provide:

* Security
* Controlled access
* Better architecture

---
