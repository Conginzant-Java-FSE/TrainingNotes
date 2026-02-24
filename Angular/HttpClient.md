# Angular HttpClient 

## 1. What is HttpClient?

`HttpClient` is Angular’s built-in service used to communicate with backend APIs (REST services).

It helps you:

* Send HTTP requests (GET, POST, PUT, PATCH, DELETE)
* Handle JSON automatically
* Work with Observables (RxJS)
* Intercept requests (Auth tokens, logging, etc.)
* Handle errors globally

---

## 2. Enabling HttpClient in Angular 21

In **Angular 15+ (including Angular 21)**, you no longer use `HttpClientModule` inside NgModule for standalone apps.

Instead, use `provideHttpClient()`.

### Step 1: Configure in `main.ts`

```ts
import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { AppComponent } from './app/app.component';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient()
  ]
});
```

That’s it. HttpClient is now available globally.

---

## 3. Creating a Service for API Calls

Best practice: **Never call APIs directly from components**.
Create a dedicated service.

### Step 2: Generate Service

```bash
ng generate service services/user
```

---

## 4. Example Backend API

We will use:

```
https://jsonplaceholder.typicode.com/users
```

This is a free fake REST API.

---

## 5. Basic GET Request

### user.service.ts

```ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface User {
  id: number;
  name: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'https://jsonplaceholder.typicode.com/users';

  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }
}
```

---

## 6. Using the Service in Component

### app.component.ts

```ts
import { Component, OnInit } from '@angular/core';
import { UserService, User } from './services/user.service';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  users: User[] = [];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (err) => {
        console.error('Error fetching users', err);
      }
    });
  }
}
```

---

### app.component.html

```html
<h2>User List</h2>

<ul>
  <li *ngFor="let user of users">
    {{ user.name }} - {{ user.email }}
  </li>
</ul>
```

---

## 7. POST Request Example

### In Service

```ts
createUser(user: User): Observable<User> {
  return this.http.post<User>(this.apiUrl, user);
}
```

### In Component

```ts
addUser() {
  const newUser: User = {
    id: 11,
    name: 'Arun',
    email: 'arun@test.com'
  };

  this.userService.createUser(newUser).subscribe({
    next: (res) => console.log('Created:', res),
    error: (err) => console.error(err)
  });
}
```

---

## 8. PUT and PATCH

### PUT (Replace entire resource)

```ts
updateUser(user: User): Observable<User> {
  return this.http.put<User>(`${this.apiUrl}/${user.id}`, user);
}
```

### PATCH (Partial update)

```ts
patchUser(id: number, partialData: Partial<User>): Observable<User> {
  return this.http.patch<User>(`${this.apiUrl}/${id}`, partialData);
}
```

---

## 9. DELETE Request

```ts
deleteUser(id: number): Observable<void> {
  return this.http.delete<void>(`${this.apiUrl}/${id}`);
}
```

---

## 10. Handling Errors Properly (Best Practice)

Use RxJS `catchError`.

```ts
import { catchError, throwError } from 'rxjs';

getUsers(): Observable<User[]> {
  return this.http.get<User[]>(this.apiUrl).pipe(
    catchError(error => {
      console.error('API Error:', error);
      return throwError(() => new Error('Something went wrong'));
    })
  );
}
```

---

## 11. Sending Headers (JWT Example)

```ts
import { HttpHeaders } from '@angular/common/http';

getSecureData() {
  const headers = new HttpHeaders({
    Authorization: 'Bearer your-token'
  });

  return this.http.get('https://api.example.com/secure', { headers });
}
```

---

## 12. Using Query Parameters

```ts
import { HttpParams } from '@angular/common/http';

searchUsers(name: string) {
  const params = new HttpParams().set('name', name);

  return this.http.get<User[]>(this.apiUrl, { params });
}
```

---

## 13. HTTP Interceptor (Angular 21)

Used for:

* Adding JWT token automatically
* Logging
* Global error handling

### Create interceptor

```bash
ng generate interceptor interceptors/auth
```

### auth.interceptor.ts

```ts
import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');

  if (token) {
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(cloned);
  }

  return next(req);
};
```

### Register it in main.ts

```ts
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptor } from './app/interceptors/auth.interceptor';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(
      withInterceptors([authInterceptor])
    )
  ]
});
```

---

## 14. HttpClient Flow Architecture

![Image](https://www.tutorialspoint.com/angular/images/http_client.jpg)

![Image](https://scaler.com/topics/images/how-angular-interceptor-works.webp)

![Image](https://i.sstatic.net/N6liF.png)

![Image](https://i.sstatic.net/dqZbs.png)

Flow:

1. Component calls Service
2. Service uses HttpClient
3. Interceptor modifies request
4. Backend API processes request
5. Response returns via Observable

---

## 15. Best Practices (Production Level)

* Always use services for HTTP calls
* Use interfaces for typing responses
* Handle errors using `catchError`
* Use interceptors for authentication
* Never subscribe inside services
* Use async pipe when possible
* Avoid memory leaks (unsubscribe or use `takeUntil`)

---

## 16. Using Async Pipe (Recommended)

Instead of manual subscribe:

### Component

```ts
users$ = this.userService.getUsers();
```

### HTML

```html
<ul>
  <li *ngFor="let user of users$ | async">
    {{ user.name }}
  </li>
</ul>
```

---

# Summary

In Angular 21:

* Use `provideHttpClient()` in standalone apps
* Use typed `HttpClient`
* Use RxJS Observables
* Use interceptors for cross-cutting concerns
* Follow service-based architecture

---
