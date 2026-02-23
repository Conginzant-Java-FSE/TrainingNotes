# Angular Directives

Directives are one of the most powerful features of Angular.
They allow you to change the behavior, structure, and appearance of DOM elements.

Understanding directives is essential for building dynamic Angular applications.

---

# 1. What is a Directive?

A **directive** is a class that adds behavior to elements in your Angular application.

Angular components are technically directives with templates.

Directives allow you to:

* Show or hide elements
* Repeat elements
* Change styles dynamically
* Add custom DOM behavior

---

# 2. Types of Directives in Angular

Angular has **three types** of directives:

| Type                 | Purpose                        | Example              |
| -------------------- | ------------------------------ | -------------------- |
| Component            | Has template                   | `@Component`         |
| Structural Directive | Changes DOM structure          | `*ngIf`, `*ngFor`    |
| Attribute Directive  | Changes appearance or behavior | `ngClass`, `ngStyle` |

---

# 3. Structural Directives

Structural directives modify the DOM layout by:

* Adding elements
* Removing elements
* Replacing elements

They are identified by `*` prefix.

---

## 3.1 ngIf

### Purpose

Conditionally displays an element.

### Syntax

```html
<p *ngIf="condition">Visible Text</p>
```

---

### Example

#### component.ts

```ts
isLoggedIn = true;
```

#### component.html

```html
<p *ngIf="isLoggedIn">Welcome User</p>
```

If `isLoggedIn` becomes false, the element is removed from DOM.

---

## 3.2 ngIf with else

```html
<p *ngIf="isLoggedIn; else loginTemplate">
  Welcome User
</p>

<ng-template #loginTemplate>
  Please login
</ng-template>
```

---

## 3.3 ngFor

### Purpose

Loops over collections.

### Syntax

```html
<li *ngFor="let item of items">
  {{ item }}
</li>
```

---

### Example

#### component.ts

```ts
users = ['Alice', 'Bob', 'Charlie'];
```

#### component.html

```html
<ul>
  <li *ngFor="let user of users">
    {{ user }}
  </li>
</ul>
```

---

## Using index

```html
<li *ngFor="let user of users; let i = index">
  {{ i }} - {{ user }}
</li>
```

---

## Using trackBy (Performance Optimization)

```html
<li *ngFor="let user of users; trackBy: trackByFn">
  {{ user.name }}
</li>
```

```ts
trackByFn(index: number, item: any) {
  return item.id;
}
```

Improves performance in large lists.

---

## 3.4 ngSwitch

### Purpose

Switch-case rendering.

---

### Example

#### component.ts

```ts
role = 'admin';
```

#### component.html

```html
<div [ngSwitch]="role">

  <p *ngSwitchCase="'admin'">Admin Panel</p>
  <p *ngSwitchCase="'user'">User Dashboard</p>
  <p *ngSwitchDefault>No Access</p>

</div>
```

---

# 4. Attribute Directives

Attribute directives change:

* Appearance
* Style
* Behavior

They do not remove elements from DOM.

---

## 4.1 ngClass

Dynamically add/remove CSS classes.

### Example

#### component.ts

```ts
isActive = true;
```

#### component.html

```html
<p [ngClass]="{ 'active': isActive, 'inactive': !isActive }">
  Status
</p>
```

---

## 4.2 ngStyle

Apply styles dynamically.

```html
<p [ngStyle]="{ color: 'blue', fontSize: '20px' }">
  Styled Text
</p>
```

---

## 4.3 Property Binding as Attribute Directive

```html
<button [disabled]="isDisabled">
  Submit
</button>
```

---

# 5. Difference: Structural vs Attribute Directives

| Feature     | Structural  | Attribute        |
| ----------- | ----------- | ---------------- |
| Changes DOM | Yes         | No               |
| Uses *      | Yes         | No               |
| Example     | ngIf, ngFor | ngClass, ngStyle |

---

# 6. How Structural Directives Work Internally

This:

```html
<p *ngIf="isVisible">Hello</p>
```

Is converted internally to:

```html
<ng-template [ngIf]="isVisible">
  <p>Hello</p>
</ng-template>
```

Angular uses `<ng-template>` to control rendering.

---

# 7. Creating a Custom Attribute Directive

## Step 1: Generate Directive

```bash
ng generate directive highlight
```

---

## Step 2: Modify Directive

```ts
import { Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[appHighlight]'
})
export class HighlightDirective {

  constructor(private el: ElementRef) {
    this.el.nativeElement.style.backgroundColor = 'yellow';
  }
}
```

---

## Step 3: Use Directive

```html
<p appHighlight>
  Highlighted Text
</p>
```

The paragraph will have yellow background.

---

# 8. Custom Directive with HostListener

Add interaction:

```ts
import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appHover]'
})
export class HoverDirective {

  constructor(private el: ElementRef) {}

  @HostListener('mouseenter')
  onMouseEnter() {
    this.el.nativeElement.style.color = 'red';
  }

  @HostListener('mouseleave')
  onMouseLeave() {
    this.el.nativeElement.style.color = 'black';
  }
}
```

---

# 9. Custom Structural Directive

More advanced example:

```ts
import { Directive, TemplateRef, ViewContainerRef, Input } from '@angular/core';

@Directive({
  selector: '[appIf]'
})
export class IfDirective {

  @Input() set appIf(condition: boolean) {
    if (condition) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef
  ) {}
}
```

Usage:

```html
<p *appIf="true">Custom If Directive</p>
```

---

# 10. Important Built-in Directives Summary

| Directive | Type       | Purpose               |
| --------- | ---------- | --------------------- |
| ngIf      | Structural | Conditional rendering |
| ngFor     | Structural | Loop rendering        |
| ngSwitch  | Structural | Switch-case logic     |
| ngClass   | Attribute  | Dynamic classes       |
| ngStyle   | Attribute  | Dynamic styles        |

---

# 11. Best Practices

1. Use trackBy with ngFor for large lists.
2. Avoid heavy logic in templates.
3. Keep custom directives focused.
4. Use structural directives carefully in large DOM trees.
5. Prefer ngClass over manual DOM manipulation.

---

# 12. Real Example: Complete Template

```html
<div *ngIf="users.length > 0; else noUsers">

  <ul>
    <li *ngFor="let user of users; let i = index">
      {{ i + 1 }} - {{ user }}
    </li>
  </ul>

</div>

<ng-template #noUsers>
  <p>No users found</p>
</ng-template>
```

---

# Final Summary

Angular directives allow you to:

* Control DOM rendering
* Apply dynamic styles
* Handle conditional views
* Build reusable UI behavior
* Create custom reusable logic

They are fundamental to dynamic Angular applications.

---
