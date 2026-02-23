
## Introduction, Setup, Installation, Angular CLI, and Creating Your First Application

---

# 1. Introduction to Angular

Angular is a TypeScript-based front-end framework developed and maintained by Google.

It is used to build:

* Single Page Applications (SPA)
* Enterprise web applications
* Progressive Web Apps (PWA)
* Large-scale frontend systems

Angular is:

* Component-based
* TypeScript-first
* Structured and opinionated
* Suitable for enterprise architecture

---

# 2. Why Angular?

Angular provides:

* Built-in routing
* Dependency injection
* Reactive forms
* HTTP client
* Strong typing via TypeScript
* CLI tooling
* Production-ready architecture

It is commonly used in enterprise-level applications.

---

# 3. Prerequisites

Before installing Angular, you must install:

* Node.js
* npm (comes with Node.js)

---

# 4. Node.js and npm

## What is Node.js?

Node.js is a JavaScript runtime that allows JavaScript to run outside the browser.

Angular uses Node.js for:

* Installing packages
* Running development servers
* Using Angular CLI

---

## What is npm?

npm (Node Package Manager) comes bundled with Node.js.

It is used to:

* Install Angular CLI
* Manage project dependencies
* Run scripts

---

## Installing Node.js

1. Visit: [https://nodejs.org](https://nodejs.org)
2. Download the LTS version.
3. Install it.
4. Verify installation:

```bash
node -v
npm -v
```

You should see version numbers.

---

# 5. Installing Angular CLI

## What is Angular CLI?

Angular CLI is the official command-line interface for Angular.

It helps you:

* Create projects
* Generate components
* Run development server
* Build for production
* Manage configurations

---

## Install Angular CLI Globally

```bash
npm install -g @angular/cli
```

Verify installation:

```bash
ng version
```

You should see Angular CLI details.

---

# 6. Creating Your First Angular Application

## Step 1: Create a New Project

```bash
ng new my-first-app
```

You will be prompted:

* Add Angular routing? → Yes (recommended)
* Stylesheet format? → CSS (or SCSS)

Angular CLI will:

* Create folder structure
* Install dependencies
* Generate base files

---

## Step 2: Navigate to Project

```bash
cd my-first-app
```

---

## Step 3: Run Development Server

```bash
ng serve
```

Or

```bash
ng serve --open
```

Open browser at:

```
http://localhost:4200
```

You should see the Angular welcome page.

---

# 7. Angular Project Structure

When you create a project, Angular generates:

```
my-first-app/
│
├── node_modules/
├── src/
│   ├── app/
│   │   ├── app.component.ts
│   │   ├── app.component.html
│   │   ├── app.component.css
│   │   └── app.module.ts
│   │
│   ├── assets/
│   ├── index.html
│   └── main.ts
│
├── angular.json
├── package.json
├── tsconfig.json
└── README.md
```

---

## Important Files Explained

### 1. package.json

Contains:

* Project metadata
* Dependencies
* Scripts

Example:

```json
{
  "scripts": {
    "start": "ng serve",
    "build": "ng build"
  }
}
```

---

### 2. angular.json

Project configuration file.

Controls:

* Build settings
* Assets
* Environment configuration
* Output folder

---

### 3. main.ts

Entry point of the application.

Bootstraps the root module.

---

### 4. app.module.ts

Root module of the application.

Registers:

* Components
* Services
* Imports

---

### 5. app.component.ts

Root component class.

---

# 8. Understanding Angular Architecture

Angular applications are built using:

## 1. Modules

Container for components and services.

## 2. Components

Building blocks of UI.

Each component has:

* TypeScript file (.ts)
* HTML template (.html)
* CSS/SCSS file (.css)

## 3. Services

Reusable business logic.

## 4. Templates

HTML with Angular bindings.

---

# 9. Creating a Component

Use Angular CLI:

```bash
ng generate component users
```

Or shorter:

```bash
ng g c users
```

Angular CLI automatically:

* Creates component files
* Updates module declarations

---

# 10. Data Binding in Angular

Angular supports four types of data binding:

1. Interpolation

   ```
   {{ title }}
   ```

2. Property Binding

   ```
   [value]="username"
   ```

3. Event Binding

   ```
   (click)="submit()"
   ```

4. Two-Way Binding

   ```
   [(ngModel)]="name"
   ```

---

# 11. Building the Application

For production build:

```bash
ng build
```

Output folder:

```
dist/
```

Optimized and minified files are generated.

---

# 12. Angular Development Workflow

1. Install Node.js
2. Install Angular CLI
3. Create project using `ng new`
4. Develop using components
5. Run with `ng serve`
6. Build using `ng build`

---

# 13. Common Angular CLI Commands

| Command                    | Purpose                |
| -------------------------- | ---------------------- |
| ng new app-name            | Create new app         |
| ng serve                   | Run development server |
| ng build                   | Build for production   |
| ng generate component name | Create component       |
| ng generate service name   | Create service         |
| ng test                    | Run unit tests         |

---

# 14. Recommended Development Setup

Install:

* Node.js LTS
* Angular CLI
* VS Code
* Angular Language Service extension

---

# 15. Summary

Angular is:

* A TypeScript-based framework
* Structured and scalable
* Enterprise-ready
* Component-driven

To get started:

1. Install Node.js
2. Install Angular CLI
3. Create a project
4. Use components to build UI
5. Serve and test locally
6. Build for production

---
