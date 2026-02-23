
This guide covers:

* Introduction to TypeScript
* TypeScript vs JavaScript
* Installing TypeScript
* TypeScript Compiler (tsc)
* Node.js basics
* Understanding `package.json`
* Project setup from scratch

---

## 1. Introduction to TypeScript

**TypeScript** is a strongly typed superset of JavaScript developed by Microsoft.

It adds:

* Static typing
* Interfaces
* Generics
* Enums
* Access modifiers
* Advanced tooling support

TypeScript compiles into plain JavaScript, which runs in browsers and Node.js.

### Why TypeScript?

JavaScript is dynamically typed. Errors often appear at runtime.

TypeScript detects many errors during development (compile time), improving:

* Code safety
* Maintainability
* Developer productivity
* Scalability for large applications

---

## 2. TypeScript vs JavaScript

### JavaScript Example

```js
function add(a, b) {
    return a + b;
}

add(10, "20"); // No error, but result is "1020"
```

Problem:

* JavaScript does not enforce types.
* Bugs may occur at runtime.

---

### TypeScript Example

```ts
function add(a: number, b: number): number {
    return a + b;
}

add(10, "20"); // Compile-time error
```

TypeScript prevents incorrect usage before execution.

---

## Key Differences

| Feature             | JavaScript  | TypeScript     |
| ------------------- | ----------- | -------------- |
| Typing              | Dynamic     | Static         |
| Compilation         | Interpreted | Compiled to JS |
| Error Detection     | Runtime     | Compile-time   |
| Interfaces          | No          | Yes            |
| Generics            | No          | Yes            |
| Large-scale support | Limited     | Excellent      |

---

## 3. What is Node.js?

Node.js is a JavaScript runtime built on Chrome’s V8 engine.

It allows you to:

* Run JavaScript outside the browser
* Build backend applications
* Manage packages using npm

TypeScript development requires Node.js because:

* TypeScript compiler runs using Node
* npm installs TypeScript

---

## 4. Installing Node.js

1. Download from the official website:
   [https://nodejs.org](https://nodejs.org)
2. Install the LTS version.
3. Verify installation:

```bash
node -v
npm -v
```

You should see version numbers.

---

## 5. Installing TypeScript

There are two ways:

### Option 1: Global Installation

```bash
npm install -g typescript
```

Verify:

```bash
tsc -v
```

---

### Option 2: Project-based Installation (Recommended)

Inside a project:

```bash
npm install --save-dev typescript
```

This installs TypeScript locally inside the project.

---

## 6. Setting Up a TypeScript Project

### Step 1: Create Project Folder

```bash
mkdir ts-demo
cd ts-demo
```

---

### Step 2: Initialize Node Project

```bash
npm init -y
```

This creates:

```json
package.json
```

---

## 7. Understanding package.json

`package.json` is the configuration file of a Node project.

It contains:

* Project metadata
* Dependencies
* Dev dependencies
* Scripts
* Version info

Example:

```json
{
  "name": "ts-demo",
  "version": "1.0.0",
  "scripts": {
    "build": "tsc",
    "start": "node dist/index.js"
  },
  "devDependencies": {
    "typescript": "^5.0.0"
  }
}
```

### Important Sections

* `dependencies`: Required for production
* `devDependencies`: Required only for development
* `scripts`: Custom commands

Run scripts using:

```bash
npm run build
```

---

## 8. TypeScript Compiler (tsc)

The TypeScript compiler is called:

```
tsc
```

It converts `.ts` files into `.js` files.

---

### Compile a Single File

Create `index.ts`:

```ts
let message: string = "Hello TypeScript";
console.log(message);
```

Compile:

```bash
tsc index.ts
```

This generates:

```
index.js
```

Run:

```bash
node index.js
```

---

## 9. tsconfig.json (Compiler Configuration)

To configure TypeScript properly:

```bash
npx tsc --init
```

This generates:

```
tsconfig.json
```

Example:

```json
{
  "compilerOptions": {
    "target": "ES2020",
    "module": "CommonJS",
    "rootDir": "./src",
    "outDir": "./dist",
    "strict": true
  }
}
```

### Important Options

| Option  | Meaning                      |
| ------- | ---------------------------- |
| target  | JS version output            |
| module  | Module system                |
| rootDir | Source folder                |
| outDir  | Compiled folder              |
| strict  | Enables strict type checking |

---

## 10. Recommended Project Structure

```
ts-demo/
│
├── node_modules/
├── src/
│   └── index.ts
├── dist/
├── package.json
├── tsconfig.json
```

---

## 11. Running TypeScript Automatically

Instead of compiling manually:

```bash
tsc --watch
```

This automatically recompiles on file changes.

---

## 12. Development Workflow Summary

1. Write TypeScript inside `src/`
2. Compile using `tsc`
3. JavaScript is generated in `dist/`
4. Run using Node

Flow:

```
TypeScript (.ts)
        ↓
TypeScript Compiler (tsc)
        ↓
JavaScript (.js)
        ↓
Node.js Runtime
```

---

## 13. When to Use TypeScript

Use TypeScript for:

* Enterprise applications
* Backend APIs
* Angular projects
* Large React projects
* Microservices
* Team-based development

Avoid for:

* Very small scripts
* Quick prototypes

---

## 14. Conclusion

TypeScript provides:

* Compile-time safety
* Better tooling
* Scalable architecture
* Clear contracts using types and interfaces

Modern production-grade JavaScript development increasingly relies on TypeScript for reliability and maintainability.

---
