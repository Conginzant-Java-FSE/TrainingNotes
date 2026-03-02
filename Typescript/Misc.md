# TypeScript Miscellaneous


# 1. Basic Generics in TypeScript

## What Are Generics?

Generics allow you to write **reusable, type-safe code**.

Instead of using `any`, generics preserve the type passed into a function or class.

---

## 1.1 Simple Generic Function

Without generics:

```ts
function identity(value: any): any {
  return value;
}
```

Problem: Loses type safety.

With generics:

```ts
function identity<T>(value: T): T {
  return value;
}

const num = identity<number>(10);
const str = identity<string>("Hello");
```

`T` is a type variable.

---

## 1.2 Type Inference

You usually don’t need to specify `<T>` manually:

```ts
const result = identity(100); // T inferred as number
```

---

## 1.3 Multiple Generics

```ts
function merge<T, U>(obj1: T, obj2: U): T & U {
  return { ...obj1, ...obj2 };
}

const merged = merge({ name: "Arul" }, { age: 30 });
```

---

## 1.4 Generic Constraints

Restrict generic type using `extends`.

```ts
function getLength<T extends { length: number }>(item: T): number {
  return item.length;
}

getLength("Hello");      // OK
getLength([1, 2, 3]);    // OK
// getLength(123);  Error
```

---

## 1.5 Generic Interfaces

```ts
interface ApiResponse<T> {
  data: T;
  status: number;
}

const response: ApiResponse<string> = {
  data: "Success",
  status: 200
};
```

---

## 1.6 Generic Classes

```ts
class Box<T> {
  constructor(public value: T) {}

  getValue(): T {
    return this.value;
  }
}

const numberBox = new Box<number>(100);
```

---

# 2. Array Generics in TypeScript

Arrays in TypeScript are generic.

---

## 2.1 Basic Array Types

Two syntaxes:

```ts
let numbers: number[] = [1, 2, 3];
let names: Array<string> = ["A", "B"];
```

`Array<string>` is generic syntax.

---

## 2.2 Generic Array Function

```ts
function getFirstElement<T>(arr: T[]): T {
  return arr[0];
}

const first = getFirstElement([10, 20, 30]);
```

---

## 2.3 Generic Array Utility

```ts
function removeLast<T>(arr: T[]): T[] {
  return arr.slice(0, -1);
}
```

Works for any type.

---

## 2.4 Readonly Arrays

```ts
const items: ReadonlyArray<number> = [1, 2, 3];
// items.push(4);  Not allowed
```

---

## 2.5 Array of Objects with Generics

```ts
interface User {
  id: number;
  name: string;
}

const users: Array<User> = [
  { id: 1, name: "John" }
];
```

---

# 3. `keyof` in TypeScript

## What is `keyof`?

`keyof` extracts the **keys of an object type** as a union of string literals.

---

## 3.1 Basic Example

```ts
interface User {
  id: number;
  name: string;
  age: number;
}

type UserKeys = keyof User;
```

`UserKeys` becomes:

```ts
"id" | "name" | "age"
```

---

## 3.2 Using keyof in Function

```ts
function getProperty<T, K extends keyof T>(
  obj: T,
  key: K
): T[K] {
  return obj[key];
}

const user = { id: 1, name: "Arul" };

const name = getProperty(user, "name"); // string
```

Why powerful?

* Ensures key exists
* Fully type-safe

---

## 3.3 Real-World Example (Dynamic Form Field Access)

```ts
interface Product {
  id: number;
  title: string;
  price: number;
}

function updateField<T, K extends keyof T>(
  obj: T,
  key: K,
  value: T[K]
) {
  obj[key] = value;
}
```

Prevents invalid field updates.

---

# 4. Utility Types in TypeScript

Utility types are built-in helper types.

---

## 4.1 Partial<T>

Makes all properties optional.

```ts
interface User {
  id: number;
  name: string;
}

const updateUser: Partial<User> = {
  name: "Updated"
};
```

Used in PATCH APIs.

---

## 4.2 Required<T>

Makes all properties required.

```ts
type CompleteUser = Required<User>;
```

---

## 4.3 Readonly<T>

Makes properties readonly.

```ts
const user: Readonly<User> = {
  id: 1,
  name: "Arul"
};

// user.name = "New"; -> not allowed
```

---

## 4.4 Pick<T, K>

Select specific properties.

```ts
type UserPreview = Pick<User, "id" | "name">;
```

---

## 4.5 Omit<T, K>

Remove specific properties.

```ts
type UserWithoutId = Omit<User, "id">;
```

---

## 4.6 Record<K, T>

Creates object type with specific keys.

```ts
type Role = "admin" | "user";

type RolePermissions = Record<Role, boolean>;

const permissions: RolePermissions = {
  admin: true,
  user: false
};
```

---

## 4.7 Exclude<T, U>

Removes types from union.

```ts
type Status = "pending" | "approved" | "rejected";
type ActiveStatus = Exclude<Status, "rejected">;
```

---

## 4.8 Extract<T, U>

Extracts matching types.

```ts
type Numbers = Extract<string | number | boolean, number>;
```

---

## 4.9 ReturnType<T>

Gets return type of function.

```ts
function getUser() {
  return { id: 1, name: "Arul" };
}

type UserType = ReturnType<typeof getUser>;
```

---

# 5. Combining Generics + keyof + Utility Types

Real-world API update example:

```ts
function updateEntity<T>(
  entity: T,
  updates: Partial<T>
): T {
  return { ...entity, ...updates };
}
```

Fully type-safe update system.

---

# 6. Advanced Example – Generic Repository Pattern

```ts
interface Entity {
  id: number;
}

class Repository<T extends Entity> {
  private items: T[] = [];

  add(item: T) {
    this.items.push(item);
  }

  getById(id: number): T | undefined {
    return this.items.find(item => item.id === id);
  }
}
```

Used in backend services.

---

# 7. Interview-Level Differences

| Concept        | Purpose                    |
| -------------- | -------------------------- |
| Generics       | Reusable type-safe code    |
| Array Generics | Strongly typed collections |
| keyof          | Key extraction             |
| Utility Types  | Type transformations       |

---

# 8. Best Practices

* Avoid `any`, prefer generics
* Use `keyof` for dynamic property access
* Use `Partial` for update DTOs
* Use `Pick` for response shaping
* Combine generics with constraints
* Use `Record` for config maps

---

# 9. Common Interview Questions

1. What are generics?
2. Difference between `T[]` and `Array<T>`?
3. What does `keyof` do?
4. What is `Partial` used for?
5. Difference between `Pick` and `Omit`?
6. What is `Record`?
7. How does `ReturnType` work?
8. What are generic constraints?

---

# Final Summary

Generics → Type-safe reusability
Array Generics → Flexible collections
keyof → Safe dynamic property access
Utility Types → Transform existing types

These features make TypeScript powerful for:

* Angular state management
* API contracts
* Enterprise applications
* Clean architecture

-