# Angular Data Binding  
---

# 1. What is Data Binding?

Data binding is the mechanism that synchronizes:

```text
Component (TypeScript)
        ↕
Template (HTML)
```

It allows:

* Displaying data in the UI
* Updating UI dynamically
* Handling user input
* Synchronizing input fields with variables

---

# 2. Types of Data Binding in Angular

Angular provides **four types** of data binding:

| Type             | Direction        | Syntax                   |
| ---------------- | ---------------- | ------------------------ |
| Interpolation    | Component → View | `{{ value }}`            |
| Property Binding | Component → View | `[property]="value"`     |
| Event Binding    | View → Component | `(event)="method()"`     |
| Two-Way Binding  | Both directions  | `[(ngModel)]="variable"` |

---

# 3. 1. Interpolation

## Purpose

Used to display component data inside HTML.

## Syntax

```html
{{ expression }}
```

---

## Example

### component.ts

```ts
export class AppComponent {
  title = 'User Management System';
  count = 10;
}
```

### component.html

```html
<h1>{{ title }}</h1>
<p>Total Users: {{ count }}</p>
```

---

## Important Rules

* Only expressions allowed
* No statements
* Cannot use assignments
* Supports arithmetic

Example:

```html
<p>{{ count + 5 }}</p>
```

---

# 4. 2. Property Binding

## Purpose

Bind component values to HTML element properties.

## Syntax

```html
[property]="value"
```

---

## Example 1: Image Binding

### component.ts

```ts
imageUrl = 'https://angular.io/assets/images/logos/angular/angular.png';
```

### component.html

```html
<img [src]="imageUrl">
```

---

## Example 2: Disable Button

```ts
isDisabled = true;
```

```html
<button [disabled]="isDisabled">Submit</button>
```

---

## Why Not Use Interpolation for Properties?

This is incorrect:

```html
<img src="{{ imageUrl }}">
```

While it may work sometimes, property binding is the recommended and safer approach.

---

# 5. 3. Event Binding

## Purpose

Listen to user events and call component methods.

## Syntax

```html
(event)="method()"
```

---

## Example 1: Click Event

### component.ts

```ts
count = 0;

increment() {
  this.count++;
}
```

### component.html

```html
<button (click)="increment()">Increase</button>
<p>{{ count }}</p>
```

---

## Example 2: Keyboard Event

```html
<input (keyup)="onKeyUp()">
```

---

## Passing Event Object

```html
<input (keyup)="captureValue($event)">
```

```ts
captureValue(event: any) {
  console.log(event.target.value);
}
```

---

# 6. 4. Two-Way Data Binding

## Purpose

Synchronizes data in both directions:

* Component → View
* View → Component

## Syntax

```html
[(ngModel)]="variable"
```

This is a combination of:

```html
[property] + (event)
```

---

## Step 1: Import FormsModule

In app.module.ts:

```ts
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [FormsModule]
})
```

---

## Example

### component.ts

```ts
name = '';
```

### component.html

```html
<input [(ngModel)]="name">
<p>You typed: {{ name }}</p>
```

When user types:

* Input field updates variable
* Variable updates UI instantly

---

# 7. Data Binding Flow Explained

## Interpolation & Property Binding

```text
Component → View
```

One-way binding.

---

## Event Binding

```text
View → Component
```

User interaction triggers method.

---

## Two-Way Binding

```text
Component ↔ View
```

Bidirectional synchronization.

---

# 8. Real Example: User Form

### component.ts

```ts
export class UserComponent {
  username = '';
  isAdmin = false;

  submit() {
    console.log(this.username, this.isAdmin);
  }
}
```

### component.html

```html
<h2>User Form</h2>

<input [(ngModel)]="username" placeholder="Enter name">

<label>
  <input type="checkbox" [(ngModel)]="isAdmin">
  Is Admin
</label>

<button (click)="submit()">Submit</button>

<p>Name: {{ username }}</p>
<p>Admin: {{ isAdmin }}</p>
```

---

# 9. Attribute Binding

Used for attributes that are not DOM properties.

Syntax:

```html
[attr.attributeName]="value"
```

Example:

```html
<td [attr.colspan]="2">Data</td>
```

---

# 10. Class Binding

Used to add/remove CSS classes dynamically.

## Syntax

```html
[class.className]="condition"
```

---

## Example

```ts
isActive = true;
```

```html
<p [class.active]="isActive">Status</p>
```

---

# 11. Style Binding

Used to dynamically apply styles.

## Syntax

```html
[style.property]="value"
```

---

## Example

```ts
color = 'red';
```

```html
<p [style.color]="color">Important Text</p>
```

---

# 12. ngClass and ngStyle

## ngClass Example

```html
<p [ngClass]="{ 'active': isActive, 'disabled': !isActive }">
  User Status
</p>
```

---

## ngStyle Example

```html
<p [ngStyle]="{ color: 'blue', fontSize: '20px' }">
  Styled Text
</p>
```

---

# 13. Common Mistakes

1. Forgetting FormsModule for ngModel
2. Using assignment inside interpolation
3. Confusing attribute binding and property binding
4. Not using safe navigation for null values

---

# 14. Safe Navigation Operator

Used when data may be undefined.

```html
<p>{{ user?.name }}</p>
```

Prevents runtime errors.

---

# 15. Summary Table

| Binding Type  | Syntax        | Direction        |
| ------------- | ------------- | ---------------- |
| Interpolation | `{{ }}`       | Component → View |
| Property      | `[property]`  | Component → View |
| Event         | `(event)`     | View → Component |
| Two-Way       | `[(ngModel)]` | Both             |

---

# 16. Best Practices

* Use property binding instead of interpolation for DOM properties
* Avoid excessive two-way binding in large forms
* Keep logic inside component, not template
* Use strict mode in TypeScript

---

# Final Understanding

Data binding is the core communication mechanism between:

* Component logic
* HTML template
* User interactions

Mastering data binding is essential before moving to:

* Directives
* Forms
* Routing
* Services
* HTTP integration

---
