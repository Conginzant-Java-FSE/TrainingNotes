# Angular Testing Tutorial 
Testing in Angular applications is primarily done using:

* **Karma** – Test Runner
* **Jasmine** – Testing Framework

Angular CLI projects are pre-configured with both.

---

## 1. What is Jasmine?

**Jasmine** is a Behavior-Driven Development (BDD) testing framework for JavaScript.

It provides:

* `describe()` → Test suite
* `it()` → Test case
* `expect()` → Assertion
* `beforeEach()` → Setup logic
* `spyOn()` → Mocking / spying

### Basic Jasmine Example

```ts
describe('Math Utility', () => {

  it('should add two numbers correctly', () => {
    const result = 2 + 3;
    expect(result).toBe(5);
  });

});
```

---

## 2. What is Karma?

**Karma** is a test runner that:

* Launches browsers (Chrome by default)
* Executes test files
* Displays results in terminal/browser
* Watches files and re-runs tests automatically

Angular CLI command:

```bash
ng test
```

This:

* Compiles app
* Opens Chrome
* Runs Jasmine tests using Karma

---

## 3. Angular Testing Architecture

![Image](https://v2.angular.io/resources/images/devguide/architecture/overview2.png)

![Image](https://delftswa.gitbooks.io/desosa2016/content/karma/images-team-karma/d2-karma-structure-model.png)

![Image](https://miro.medium.com/0%2ApiHkKZcRysa-56wS)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/1%2AVtA9S4kMQHk0m7MWH53gww.png)

Flow:

1. Jasmine defines test cases
2. Angular `TestBed` creates testing environment
3. Karma runs tests in browser
4. Results displayed in console

---

# 4. Testing Angular Components

When you generate a component:

```bash
ng generate component login
```

Angular creates:

```
login.component.ts
login.component.spec.ts
```

The `.spec.ts` file contains tests.

---

## Example Component

```ts
import { Component } from '@angular/core';

@Component({
  selector: 'app-counter',
  template: `
    <h2>{{count}}</h2>
    <button (click)="increment()">Increment</button>
  `
})
export class CounterComponent {
  count = 0;

  increment() {
    this.count++;
  }
}
```

---

## Component Test File

```ts
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CounterComponent } from './counter.component';

describe('CounterComponent', () => {

  let component: CounterComponent;
  let fixture: ComponentFixture<CounterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CounterComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(CounterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create component', () => {
    expect(component).toBeTruthy();
  });

  it('should increment count when method called', () => {
    component.increment();
    expect(component.count).toBe(1);
  });

});
```

---

# 5. Testing DOM Interaction

You can test UI behavior.

```ts
it('should increment count on button click', () => {
  const button = fixture.nativeElement.querySelector('button');
  button.click();
  fixture.detectChanges();

  expect(component.count).toBe(1);
});
```

---

# 6. Testing Services

## Example Service

```ts
@Injectable({ providedIn: 'root' })
export class AuthService {

  isLoggedIn(): boolean {
    return true;
  }

}
```

## Service Test

```ts
import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';

describe('AuthService', () => {

  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthService);
  });

  it('should return true', () => {
    expect(service.isLoggedIn()).toBeTrue();
  });

});
```

---

# 7. Using Spies (Mocking Dependencies)

Jasmine provides `spyOn()`.

Example:

```ts
it('should call increment method', () => {
  spyOn(component, 'increment');
  component.increment();
  expect(component.increment).toHaveBeenCalled();
});
```

---

# 8. Testing with Dependency Injection

If component depends on service:

```ts
constructor(private auth: AuthService) {}
```

You can mock service:

```ts
const mockAuth = {
  isLoggedIn: () => false
};

TestBed.configureTestingModule({
  declarations: [NavbarComponent],
  providers: [
    { provide: AuthService, useValue: mockAuth }
  ]
});
```

---

# 9. Testing Asynchronous Code

### Using async/await

```ts
it('should resolve promise', async () => {
  const result = await Promise.resolve(true);
  expect(result).toBeTrue();
});
```

### Using fakeAsync

```ts
import { fakeAsync, tick } from '@angular/core/testing';

it('should wait for timeout', fakeAsync(() => {
  let flag = false;

  setTimeout(() => {
    flag = true;
  }, 1000);

  tick(1000);
  expect(flag).toBeTrue();
}));
```

---

# 10. Code Coverage

Run:

```bash
ng test --code-coverage
```

Generates:

```
/coverage/index.html
```

![Image](https://testing-angular.com/assets/img/code-coverage-flickr-search.png)

![Image](https://i.sstatic.net/kcUGB.png)

![Image](https://i.sstatic.net/luaq2.png)

![Image](https://i.sstatic.net/BsRsE.png)

Shows:

* Statement coverage
* Branch coverage
* Function coverage
* Line coverage

---

# 11. Important Jasmine Matchers

| Matcher            | Description     |
| ------------------ | --------------- |
| toBe()             | strict equality |
| toEqual()          | deep equality   |
| toBeTruthy()       | truthy value    |
| toBeFalse()        | false           |
| toContain()        | contains value  |
| toHaveBeenCalled() | spy called      |
| toThrow()          | expects error   |

---

# 12. Best Practices (Professional Level)

1. Test behavior, not implementation.
2. Avoid testing private methods.
3. Mock external dependencies.
4. Keep tests small and focused.
5. Use meaningful test names.
6. Maintain minimum 80% coverage.
7. Separate unit tests and integration tests.

---

# 13. Folder Structure in Angular Project

```
src/
 ├── app/
 │    ├── counter/
 │    │    ├── counter.component.ts
 │    │    ├── counter.component.spec.ts
 │
karma.conf.js
```

---

# 14. Common Interview Questions

1. Difference between Jasmine and Karma?
2. What is TestBed?
3. What is fixture?
4. What is detectChanges()?
5. How do you test async code?
6. What are spies?
7. How do you mock services?

---

# Summary

| Tool    | Purpose                     |
| ------- | --------------------------- |
| Jasmine | Writing test cases          |
| Karma   | Running test cases          |
| TestBed | Angular testing environment |
| Fixture | Component wrapper           |

----