# TypeScript `tsconfig.json` & Compiler Options 

When working with TypeScript, the compiler (`tsc`) converts `.ts` files into JavaScript.
The behavior of this compiler is controlled by a configuration file called:

```
tsconfig.json
```

This tutorial explains:

* What is tsconfig?
* How to create it
* Important compiler options
* Strict mode
* Module systems
* Target configuration
* Real-world Angular/Node setups
* Best practices

---

# 1. What is `tsconfig.json`?

`tsconfig.json` is a configuration file for the TypeScript compiler.

It tells TypeScript:

* Which files to compile
* What JavaScript version to generate
* Module system to use
* Strictness rules
* Output directory
* Source maps configuration

---

# 2. Creating tsconfig.json

Initialize in project root:

```bash
tsc --init
```

This generates:

```json
{
  "compilerOptions": {}
}
```

---

# 3. Basic Structure of tsconfig.json

```json
{
  "compilerOptions": {
    // compiler settings
  },
  "include": [],
  "exclude": []
}
```

---

# 4. Core Compiler Options (Most Important)

---

## 4.1 `target`

Defines which JavaScript version TypeScript should generate.

```json
{
  "compilerOptions": {
    "target": "ES2020"
  }
}
```

Common values:

* ES5
* ES6 (ES2015)
* ES2017
* ES2020
* ESNext

Example:

If target = ES5 → classes become function-based.

---

## 4.2 `module`

Defines module system.

```json
{
  "compilerOptions": {
    "module": "CommonJS"
  }
}
```

Common values:

* CommonJS → Node.js
* ESNext → Modern frontend
* AMD
* UMD

### Node.js Project:

```json
"module": "CommonJS"
```

### Angular / Modern Frontend:

```json
"module": "ESNext"
```

---

## 4.3 `outDir`

Where compiled JS files go.

```json
{
  "compilerOptions": {
    "outDir": "./dist"
  }
}
```

---

## 4.4 `rootDir`

Where TS source files are located.

```json
{
  "compilerOptions": {
    "rootDir": "./src"
  }
}
```

---

## 4.5 `strict`

Enables all strict type checking options.

```json
{
  "compilerOptions": {
    "strict": true
  }
}
```

Highly recommended for production.

---

# 5. Strict Mode Options (Very Important)

If `"strict": true`, these are enabled:

---

## 5.1 `noImplicitAny`

Disallows variables without type.

```json
"noImplicitAny": true
```

Bad:

```ts
function add(a, b) {
  return a + b;
}
```

Error because `a` and `b` are `any`.

---

## 5.2 `strictNullChecks`

Prevents null/undefined misuse.

```json
"strictNullChecks": true
```

```ts
let name: string = null; // ❌ Error
```

---

## 5.3 `strictPropertyInitialization`

Ensures class properties are initialized.

```ts
class User {
  name: string; // ❌ Error unless initialized
}
```

---

## 5.4 `noImplicitThis`

Prevents unsafe `this`.

---

# 6. Module Resolution

---

## 6.1 `moduleResolution`

```json
"moduleResolution": "node"
```

Tells TypeScript how to find modules.

Values:

* node
* classic

Always use `node` for modern projects.

---

# 7. Interop Options

---

## 7.1 `esModuleInterop`

Very important for Node projects.

```json
"esModuleInterop": true
```

Allows:

```ts
import express from "express";
```

Without it, you must use:

```ts
import * as express from "express";
```

---

# 8. Source Maps

---

## 8.1 `sourceMap`

```json
"sourceMap": true
```

Generates `.map` files for debugging.

Useful for:

* Chrome DevTools
* VS Code debugging

---

# 9. Include & Exclude

---

## 9.1 Include

```json
{
  "include": ["src/**/*"]
}
```

---

## 9.2 Exclude

```json
{
  "exclude": ["node_modules", "dist"]
}
```

---

# 10. Declaration Files

---

## 10.1 `declaration`

Generates `.d.ts` files.

```json
"declaration": true
```

Used when building libraries.

---

# 11. JSX Support

For React projects:

```json
"jsx": "react-jsx"
```

---

# 12. Experimental Decorators

Required for Angular:

```json
"experimentalDecorators": true,
"emitDecoratorMetadata": true
```

Without this, decorators won't work.

---

# 13. Remove Comments

```json
"removeComments": true
```

Removes comments in compiled JS.

---

# 14. Allow JS Files

```json
"allowJs": true
```

Lets you mix JS and TS.

---

# 15. Real-World tsconfig for Node Project

```json
{
  "compilerOptions": {
    "target": "ES2020",
    "module": "CommonJS",
    "rootDir": "src",
    "outDir": "dist",
    "strict": true,
    "esModuleInterop": true,
    "moduleResolution": "node",
    "sourceMap": true
  },
  "include": ["src"],
  "exclude": ["node_modules"]
}
```

---

# 16. Real-World tsconfig for Angular-like Frontend

```json
{
  "compilerOptions": {
    "target": "ES2022",
    "module": "ESNext",
    "strict": true,
    "moduleResolution": "node",
    "experimentalDecorators": true,
    "emitDecoratorMetadata": true,
    "sourceMap": true
  }
}
```

---

# 17. How Compilation Works

Flow:

```
TypeScript (.ts)
        ↓
tsc compiler
        ↓
JavaScript (.js)
```

Command:

```bash
tsc
```

Watch mode:

```bash
tsc --watch
```

---

# 18. Important Lesser-Known Options

---

## 18.1 `skipLibCheck`

```json
"skipLibCheck": true
```

Speeds up compilation by skipping type checking of node_modules.

---

## 18.2 `resolveJsonModule`

```json
"resolveJsonModule": true
```

Allows:

```ts
import data from './data.json';
```

---

## 18.3 `forceConsistentCasingInFileNames`

Prevents case mismatch issues.

---

# 19. Common Interview Questions

1. What is tsconfig.json?
2. What does strict mode do?
3. Difference between target and module?
4. What is esModuleInterop?
5. Why use source maps?
6. What is declaration file?
7. What happens if noImplicitAny is false?

---

# 20. Best Practices

* Always enable `"strict": true`
* Use modern `target` (ES2020+)
* Enable `esModuleInterop`
* Use `outDir`
* Use `sourceMap` in development
* Disable sourceMap in production if needed
* Use `skipLibCheck` for faster builds

---

# Final Summary

`tsconfig.json` controls:

* Compilation behavior
* Type strictness
* Module system
* Output location
* Debugging support
* Decorator support

It is essential for:

* Angular apps
* Node.js backends
* Enterprise TypeScript projects

---
