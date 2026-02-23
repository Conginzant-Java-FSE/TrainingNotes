
## Simple (Primitive) Types, Special Types, Object Types, and Union Types

This tutorial explains the foundational type system of TypeScript in a clear and structured manner. Understanding these core types is essential before moving to interfaces, generics, or advanced type manipulation.

---

# 1. Simple (Primitive) Types

Primitive types represent single, basic values.

## 1.1 number

Represents all numeric values (integers and floating-point).

```ts
let age: number = 30;
let price: number = 199.99;
```

TypeScript does not differentiate between `int` and `float`.

---

## 1.2 string

Represents textual data.

```ts
let firstName: string = "Arun";
let message: string = `Hello ${firstName}`;
```

Supports:

* Single quotes
* Double quotes
* Template literals

---

## 1.3 boolean

Represents true or false values.

```ts
let isActive: boolean = true;
let isLoggedIn: boolean = false;
```

Commonly used in:

* Conditions
* Flags
* Feature toggles

---

## 1.4 bigint

Used for very large integers.

```ts
let largeNumber: bigint = 12345678901234567890n;
```

The `n` suffix is mandatory.

---

## 1.5 symbol

Represents a unique and immutable value.

```ts
let uniqueId: symbol = Symbol("id");
```

Used for:

* Unique object keys
* Advanced meta-programming

---

# 2. Special Types

Special types provide flexibility or strict control.

---

## 2.1 any

Disables type checking for a variable.

```ts
let data: any = 10;
data = "Hello";
data = true;
```

Avoid using `any` in production code because:

* It removes compile-time safety
* It defeats the purpose of TypeScript

---

## 2.2 unknown

Safer alternative to `any`.

```ts
let value: unknown = "Hello";

if (typeof value === "string") {
    console.log(value.toUpperCase());
}
```

Difference from `any`:

* You must perform type checks before using it.

Recommended over `any`.

---

## 2.3 void

Used when a function does not return anything.

```ts
function logMessage(message: string): void {
    console.log(message);
}
```

---

## 2.4 null and undefined

Explicit types:

```ts
let emptyValue: null = null;
let notAssigned: undefined = undefined;
```

With `strictNullChecks` enabled (recommended), `null` and `undefined` must be handled explicitly.

---

## 2.5 never

Represents values that never occur.

Used when:

* A function throws an error
* A function never finishes execution

```ts
function throwError(message: string): never {
    throw new Error(message);
}
```

Also used in exhaustive checks.

---

# 3. Object Types

Object types define structured data.

---

## 3.1 Basic Object Type

```ts
let user: {
    name: string;
    age: number;
    isAdmin: boolean;
} = {
    name: "John",
    age: 25,
    isAdmin: false
};
```

Rules:

* All required properties must be present
* Types must match exactly

---

## 3.2 Optional Properties

```ts
let employee: {
    name: string;
    department?: string;
} = {
    name: "David"
};
```

`?` makes the property optional.

---

## 3.3 Readonly Properties

```ts
let config: {
    readonly apiKey: string;
} = {
    apiKey: "ABC123"
};

// config.apiKey = "XYZ"; // Error
```

Prevents modification after assignment.

---

## 3.4 Nested Object Types

```ts
let order: {
    id: number;
    customer: {
        name: string;
        email: string;
    };
} = {
    id: 1,
    customer: {
        name: "Alice",
        email: "alice@example.com"
    }
};
```

---

## 3.5 Array Type

Arrays can be defined in two ways:

```ts
let numbers: number[] = [1, 2, 3];

let names: Array<string> = ["A", "B", "C"];
```

Both are equivalent.

---

## 3.6 Function Type

```ts
let add: (a: number, b: number) => number;

add = function (x, y) {
    return x + y;
};
```

Defines parameter types and return type.

---

# 4. Union Types

Union types allow a variable to hold multiple possible types.

Syntax uses `|`.

---

## 4.1 Basic Union

```ts
let id: number | string;

id = 100;
id = "A123";
```

---

## 4.2 Union in Functions

```ts
function printId(id: number | string): void {
    console.log(id);
}
```

---

## 4.3 Type Narrowing

TypeScript requires narrowing before using type-specific methods.

```ts
function formatValue(value: number | string) {
    if (typeof value === "string") {
        return value.toUpperCase();
    } else {
        return value.toFixed(2);
    }
}
```

This is called type narrowing.

---

## 4.4 Union with Object Types

```ts
type Admin = {
    role: string;
};

type Customer = {
    purchaseCount: number;
};

let user: Admin | Customer;
```

To access properties safely:

```ts
if ("role" in user) {
    console.log(user.role);
}
```

---

# 5. Practical Example

```ts
type Status = "success" | "error";

function processResponse(
    status: Status,
    data: string | null
): void {
    if (status === "success" && data) {
        console.log("Data:", data.toUpperCase());
    } else {
        console.log("Operation failed");
    }
}
```

This example combines:

* Literal types
* Union types
* Null handling

---

# 6. Summary Table

| Category  | Types Covered                              |
| --------- | ------------------------------------------ |
| Primitive | number, string, boolean, bigint, symbol    |
| Special   | any, unknown, void, null, undefined, never |
| Object    | object literals, arrays, functions         |
| Union     | number | string, object unions             |

---

# 7. Best Practices

1. Prefer explicit types in large applications.
2. Avoid `any`.
3. Use `unknown` when type is uncertain.
4. Enable `strict` mode in `tsconfig.json`.
5. Use union types instead of overloading with `any`.

---
