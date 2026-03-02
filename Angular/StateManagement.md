# Angular State Management 


State management in Angular means **managing shared data across components in a predictable way**.

In small–medium applications, you don’t need NgRx. A **Service + RxJS BehaviorSubject** pattern is clean, scalable, and production-ready.

---

## 1. Core Concepts

### 1. RxJS

RxJS is a reactive programming library used heavily in Angular.

It provides:

* Observables
* Subjects
* Operators
* Reactive streams

---

### 2. BehaviorSubject

`BehaviorSubject`:

* Holds a **current value**
* Emits the latest value to new subscribers
* Requires an initial value

Why use it for state?
Because state always has a "current value".

---

## 2. Why Service-Based State?

Instead of:

* Passing data via @Input/@Output everywhere
* Deep nested component communication
* Multiple API calls for same data

We centralize state in a service.

Architecture:

![Image](https://s3.amazonaws.com/angularminds.com/blog/media/NGRX%20store%20state%20management%20lifecycle-20240814104431470.png)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1200/1%2AmD6hH2jyPWoyKeE-r-ptxw.png)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/1%2Al8BUaoCeEEFM-o5D4XKLPw.png)

![Image](https://www.tutorialspoint.com/angular/images/component-service.jpg)

Flow:

Component → Service → BehaviorSubject → All Subscribers

---

# Real-World Example 1: Shopping Cart

---

## Step 1: Define State Model (TypeScript)

`cart.model.ts`

```ts
export interface CartItem {
  id: number;
  name: string;
  price: number;
  quantity: number;
}
```

TypeScript ensures type safety.

---

## Step 2: Create State Service

`cart.service.ts`

```ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CartItem } from './cart.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  // Private state
  private cartSubject = new BehaviorSubject<CartItem[]>([]);

  // Public observable
  cart$ = this.cartSubject.asObservable();

  constructor() {}

  get currentCart(): CartItem[] {
    return this.cartSubject.value;
  }

  addItem(item: CartItem) {
    const updatedCart = [...this.currentCart, item];
    this.cartSubject.next(updatedCart);
  }

  removeItem(id: number) {
    const updatedCart = this.currentCart.filter(item => item.id !== id);
    this.cartSubject.next(updatedCart);
  }

  clearCart() {
    this.cartSubject.next([]);
  }
}
```

---

## Step 3: Use in Component

`product.component.ts`

```ts
constructor(private cartService: CartService) {}

addToCart() {
  this.cartService.addItem({
    id: 1,
    name: 'Laptop',
    price: 50000,
    quantity: 1
  });
}
```

---

## Step 4: Display in Cart Component

```ts
cartItems$ = this.cartService.cart$;
```

Template:

```html
<div *ngFor="let item of cartItems$ | async">
  {{ item.name }} - {{ item.price }}
</div>
```

Why async pipe?

* Auto subscribe
* Auto unsubscribe
* Prevents memory leaks

---

# Real-World Example 2: Authentication State

---

## Auth State Model

```ts
export interface AuthState {
  isLoggedIn: boolean;
  user: string | null;
}
```

---

## Auth Service

```ts
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authSubject = new BehaviorSubject<AuthState>({
    isLoggedIn: false,
    user: null
  });

  auth$ = this.authSubject.asObservable();

  login(username: string) {
    this.authSubject.next({
      isLoggedIn: true,
      user: username
    });
  }

  logout() {
    this.authSubject.next({
      isLoggedIn: false,
      user: null
    });
  }
}
```

---

## Navbar Component

```ts
authState$ = this.authService.auth$;
```

```html
<div *ngIf="authState$ | async as auth">
  <span *ngIf="auth.isLoggedIn">
    Welcome {{ auth.user }}
  </span>
  <button *ngIf="!auth.isLoggedIn">Login</button>
</div>
```

Now:

* Login page updates
* Navbar updates
* Dashboard updates
  All automatically.

---

# Advanced Pattern: Immutable State Updates

Never mutate state directly:

❌ Wrong:

```ts
this.currentCart.push(item);
```

✅ Correct:

```ts
this.cartSubject.next([...this.currentCart, item]);
```

This ensures change detection works properly.

---

# Derived State with RxJS Operators

You can compute totals using RxJS:

```ts
import { map } from 'rxjs/operators';

total$ = this.cart$.pipe(
  map(items =>
    items.reduce((total, item) =>
      total + item.price * item.quantity, 0
    )
  )
);
```

Template:

```html
Total: {{ total$ | async }}
```

This is reactive derived state.

---

# State Persistence (LocalStorage Example)

Enhance cart service:

```ts
constructor() {
  const savedCart = localStorage.getItem('cart');
  if (savedCart) {
    this.cartSubject.next(JSON.parse(savedCart));
  }

  this.cart$.subscribe(cart => {
    localStorage.setItem('cart', JSON.stringify(cart));
  });
}
```

Now cart survives page refresh.

---

# State Management Best Practices

1. Keep BehaviorSubject private
2. Expose only Observable (`asObservable()`)
3. Use immutable updates
4. Use TypeScript interfaces
5. Avoid subscribing in components (use async pipe)
6. Separate feature state into separate services
7. Avoid business logic inside components

---

# When to Use This Pattern?

Use Service-Based State when:

* Small to medium apps
* Few shared states
* Simple business logic
* No complex side effects

---

# When NOT Enough?

When app grows large:

Use:

* NgRx
* NGXS
* Akita

---

# Interview Questions

1. Why BehaviorSubject instead of Subject?
2. Why keep subject private?
3. What happens if multiple components subscribe?
4. Why async pipe preferred?
5. What is immutable update?
6. How to derive computed state?
7. How to persist state?

---

# Summary

Service-Based State Management:

* Centralized
* Reactive
* Type-safe (TypeScript)
* Scalable
* Easy to test
* Clean architecture

---
