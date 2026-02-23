# Angular Pipes

In Angular, **pipes** are used to transform data in templates.

They help you:

* Format dates
* Format currency
* Convert text case
* Filter or transform values
* Create reusable display logic

Pipes make templates clean and readable by moving formatting logic out of the component.

---

# 1. What is a Pipe?

A pipe takes input data and transforms it into a desired format.

Basic syntax:

```html
{{ value | pipeName }}
```

Example:

```html
{{ name | uppercase }}
```

This transforms `name` to uppercase in the UI.

---

# 2. Why Use Pipes?

Without pipes:

```html
{{ name.toUpperCase() }}
```

With pipes:

```html
{{ name | uppercase }}
```

Advantages:

* Cleaner templates
* Reusable logic
* Built-in formatting support
* Better readability

---

# 3. Built-in Angular Pipes

Angular provides many built-in pipes.

| Pipe      | Purpose                              |
| --------- | ------------------------------------ |
| uppercase | Convert text to uppercase            |
| lowercase | Convert text to lowercase            |
| titlecase | Capitalize first letter of each word |
| date      | Format dates                         |
| currency  | Format currency                      |
| percent   | Format percentage                    |
| decimal   | Format numbers                       |
| json      | Convert object to JSON string        |
| slice     | Slice strings or arrays              |
| async     | Handle Observables/Promises          |

---

# 4. String Pipes

## 4.1 Uppercase

```ts
name = 'angular framework';
```

```html
{{ name | uppercase }}
```

Output:

```
ANGULAR FRAMEWORK
```

---

## 4.2 Lowercase

```html
{{ name | lowercase }}
```

---

## 4.3 Titlecase

```html
{{ name | titlecase }}
```

Output:

```
Angular Framework
```

---

# 5. Number Pipes

## 5.1 Decimal Pipe

```ts
price = 1234.5678;
```

```html
{{ price | number }}
```

Format control:

```html
{{ price | number:'1.2-2' }}
```

Meaning:

```
minimumIntegerDigits.minimumFractionDigits-maximumFractionDigits
```

Example result:

```
1,234.57
```

---

## 5.2 Percent Pipe

```ts
progress = 0.75;
```

```html
{{ progress | percent }}
```

Output:

```
75%
```

---

# 6. Currency Pipe

```ts
amount = 2500;
```

```html
{{ amount | currency }}
```

Default currency depends on locale.

Specify currency:

```html
{{ amount | currency:'USD' }}
{{ amount | currency:'INR' }}
```

Control display format:

```html
{{ amount | currency:'USD':'symbol':'1.2-2' }}
```

---

# 7. Date Pipe

```ts
today = new Date();
```

```html
{{ today | date }}
```

Custom formats:

```html
{{ today | date:'short' }}
{{ today | date:'medium' }}
{{ today | date:'long' }}
{{ today | date:'fullDate' }}
{{ today | date:'dd/MM/yyyy' }}
```

---

# 8. JSON Pipe

Useful for debugging.

```ts
user = {
  name: 'David',
  age: 30
};
```

```html
<pre>{{ user | json }}</pre>
```

Displays formatted JSON.

---

# 9. Slice Pipe

Used for arrays or strings.

```ts
users = ['Alice', 'Bob', 'Charlie', 'David'];
```

```html
{{ users | slice:0:2 }}
```

Output:

```
Alice,Bob
```

---

# 10. Chaining Pipes

Multiple pipes can be combined.

```html
{{ today | date:'fullDate' | uppercase }}
```

Execution order: Left to right.

---

# 11. Async Pipe

Used for Observables and Promises.

Instead of manually subscribing:

```ts
this.userService.getUsers().subscribe(data => {
  this.users = data;
});
```

Use async pipe:

```html
<ul>
  <li *ngFor="let user of users$ | async">
    {{ user.name }}
  </li>
</ul>
```

Benefits:

* Automatically subscribes
* Automatically unsubscribes
* Prevents memory leaks

---

# 12. Creating a Custom Pipe

## Step 1: Generate Pipe

```bash
ng generate pipe reverse
```

---

## Step 2: Implement Logic

```ts
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'reverse'
})
export class ReversePipe implements PipeTransform {

  transform(value: string): string {
    return value.split('').reverse().join('');
  }
}
```

---

## Step 3: Use Custom Pipe

```html
{{ 'Angular' | reverse }}
```

Output:

```
ralugnA
```

---

# 13. Custom Pipe with Parameters

Modify pipe:

```ts
transform(value: string, uppercase: boolean): string {
  let result = value.split('').reverse().join('');
  return uppercase ? result.toUpperCase() : result;
}
```

Usage:

```html
{{ 'Angular' | reverse:true }}
```

---

# 14. Pure vs Impure Pipes

## Pure Pipe (Default)

* Runs only when input changes
* Better performance
* Recommended

---

## Impure Pipe

```ts
@Pipe({
  name: 'example',
  pure: false
})
```

* Runs on every change detection cycle
* Can affect performance
* Use carefully

---

# 15. Pipe Execution Flow

```text
Component Data
       ↓
Pipe transforms value
       ↓
Formatted Output in Template
```

---

# 16. Real Example: Dashboard

### component.ts

```ts
name = 'john doe';
salary = 50000;
joinedDate = new Date();
progress = 0.85;
```

### component.html

```html
<h2>{{ name | titlecase }}</h2>
<p>Salary: {{ salary | currency:'USD' }}</p>
<p>Joined: {{ joinedDate | date:'longDate' }}</p>
<p>Progress: {{ progress | percent }}</p>
```

---

# 17. When to Use Pipes

Use pipes for:

* Formatting display data
* Transforming UI values
* Debugging objects
* Handling async streams

Avoid pipes for:

* Heavy business logic
* Complex calculations
* Backend data manipulation

---

# 18. Best Practices

1. Keep pipes simple.
2. Use pure pipes unless necessary.
3. Avoid expensive operations inside pipes.
4. Use async pipe for Observables.
5. Prefer reusable custom pipes over repeated template logic.

---

# 19. Summary Table

| Category | Example              |
| -------- | -------------------- |
| String   | uppercase, lowercase |
| Number   | number, percent      |
| Currency | currency             |
| Date     | date                 |
| Debug    | json                 |
| Async    | async                |
| Custom   | reverse              |

---

# Final Summary

Angular pipes:

* Transform data in templates
* Improve readability
* Reduce component logic
* Provide built-in formatting
* Support custom reusable transformations

Pipes are essential for clean and professional Angular applications.

---
