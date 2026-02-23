
# 1. What is a Component in Angular?

In Angular, a **component** is the fundamental building block of the UI.

Every Angular application is built using components.

A component controls:

* A part of the screen (UI)
* The data for that UI
* The behavior (logic) of that UI

You can think of a component as:

```
HTML (View)
+ TypeScript (Logic)
+ CSS (Style)
= Component
```

---

# 2. Structure of an Angular Component

When you create a component, Angular generates four files:

```
users/
│
├── users.component.ts
├── users.component.html
├── users.component.css
└── users.component.spec.ts
```

### 1. users.component.ts

Contains the logic.

### 2. users.component.html

Contains the template (UI).

### 3. users.component.css

Contains styles.

### 4. users.component.spec.ts

Contains unit tests.

---

# 3. Creating a Component

Use Angular CLI:

```bash
ng generate component users
```

or short form:

```bash
ng g c users
```

Angular CLI automatically:

* Creates files
* Registers component in module

---

# 4. Understanding the Component File

Example:

```ts
import { Component } from '@angular/core';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent {
  title = 'User List';
}
```

---

## Explanation

### @Component Decorator

It defines metadata about the component.

* selector → HTML tag name
* templateUrl → HTML file
* styleUrls → CSS file

---

## selector

```
selector: 'app-users'
```

To use this component inside another component:

```html
<app-users></app-users>
```

---

# 5. Root Component

When you create an Angular project, Angular creates:

```
app.component.ts
```

This is the root component.

It is loaded first when the app starts.

---

# 6. Displaying Data in Template (Interpolation)

In `users.component.ts`:

```ts
title = 'User Management';
```

In `users.component.html`:

```html
<h1>{{ title }}</h1>
```

This is called interpolation.

It binds TypeScript data to HTML.

---

# 7. Property Binding

Used to bind values to HTML properties.

```ts
imageUrl = 'https://example.com/image.png';
```

```html
<img [src]="imageUrl">
```

Syntax:

```
[property]="value"
```

---

# 8. Event Binding

Used to handle events.

```ts
onClick() {
  console.log('Button clicked');
}
```

```html
<button (click)="onClick()">Click Me</button>
```

Syntax:

```
(event)="method()"
```

---

# 9. Two-Way Data Binding

Requires FormsModule.

In `app.module.ts`:

```ts
import { FormsModule } from '@angular/forms';

imports: [FormsModule]
```

In component:

```ts
name = '';
```

In template:

```html
<input [(ngModel)]="name">
<p>{{ name }}</p>
```

Syntax:

```
[(ngModel)]="variable"
```

---

# 10. Using ngIf (Conditional Rendering)

```ts
isLoggedIn = true;
```

```html
<p *ngIf="isLoggedIn">Welcome User</p>
```

If condition is false, element is removed from DOM.

---

# 11. Using ngFor (Looping)

```ts
users = ['Alice', 'Bob', 'Charlie'];
```

```html
<ul>
  <li *ngFor="let user of users">
    {{ user }}
  </li>
</ul>
```

---

# 12. Component Communication

## Parent to Child using @Input

Child component:

```ts
import { Input } from '@angular/core';

@Input() userName!: string;
```

Child template:

```html
<p>{{ userName }}</p>
```

Parent template:

```html
<app-child [userName]="'David'"></app-child>
```

---

## Child to Parent using @Output

Child:

```ts
import { Output, EventEmitter } from '@angular/core';

@Output() notify = new EventEmitter<string>();

sendData() {
  this.notify.emit('Hello Parent');
}
```

Parent:

```html
<app-child (notify)="receiveMessage($event)"></app-child>
```

---

# 13. Component Lifecycle Hooks

Angular provides lifecycle hooks.

Common ones:

| Hook        | Purpose                             |
| ----------- | ----------------------------------- |
| ngOnInit    | Runs after component initialization |
| ngOnChanges | Runs when input properties change   |
| ngOnDestroy | Runs before component is destroyed  |

Example:

```ts
import { OnInit } from '@angular/core';

export class UsersComponent implements OnInit {

  ngOnInit(): void {
    console.log('Component initialized');
  }
}
```

---

# 14. Styling Components

Each component has its own CSS file.

Example:

```css
h1 {
  color: blue;
}
```

Angular uses view encapsulation by default.

This means:

* Styles are scoped to the component.
* They do not affect other components.

---

# 15. Inline Template and Styles

Instead of separate files:

```ts
@Component({
  selector: 'app-demo',
  template: `<h1>Hello</h1>`,
  styles: [`h1 { color: red; }`]
})
```

---

# 16. Best Practices for Components

1. Keep components small and focused.
2. Move business logic to services.
3. Use Input/Output for communication.
4. Follow single responsibility principle.
5. Avoid very large components.

---

# 17. Real Example: Simple Counter Component

### counter.component.ts

```ts
count = 0;

increment() {
  this.count++;
}

decrement() {
  this.count--;
}
```

### counter.component.html

```html
<h2>Counter: {{ count }}</h2>

<button (click)="increment()">+</button>
<button (click)="decrement()">-</button>
```

---

# 18. Component-Based Architecture

Angular apps are:

* Modular
* Reusable
* Maintainable

Example structure:

```
app/
│
├── header/
├── sidebar/
├── users/
├── products/
└── shared/
```

Each folder represents a component or feature module.

---

# 19. Summary

Angular components:

* Control a part of the UI
* Combine template, logic, and style
* Use data binding
* Support lifecycle hooks
* Communicate via Input and Output
* Encourage modular architecture

Components are the foundation of Angular applications. Mastering them is essential before learning routing, services, and forms.

---
