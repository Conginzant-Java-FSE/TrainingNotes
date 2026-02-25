# Angular Forms

## 1. Quick overview

Angular provides two primary form approaches:

* **Template-driven forms** — declarative, driven mostly from the template using `ngModel`. Best for simple forms and quick prototypes.
* **Reactive forms** (model-driven) — explicit form model defined in TypeScript using `FormControl`, `FormGroup`, and `FormArray`. Better for complex forms, validations, testability and fine-grained control.

Choose template-driven for small, simple forms; choose reactive for medium/large forms or when you need predictable state management and testability.

---

## 2. Setup (Angular 21 project)

1. Create a new project (if required):

```bash
ng new forms-demo --routing=false --style=scss
cd forms-demo
```

2. Install dependencies — default Angular CLI provides form packages; ensure `@angular/forms` is available (it is included by default with Angular).

3. Module setup:

* For **template-driven** forms import `FormsModule`.
* For **reactive** forms import `ReactiveFormsModule`.

Example `app.module.ts`:

```ts
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, FormsModule, ReactiveFormsModule],
  bootstrap: [AppComponent]
})
export class AppModule {}
```

> Note: If you use standalone components, import `import { provideForms, ReactiveFormsModule } from '@angular/forms'` or list `imports: [ReactiveFormsModule]` in the component `@Component` metadata.

---

## 3. Template-driven forms (step-by-step)

**Use case:** Simple contact form.

### 3.1 Template (HTML)

```html
<form #contactForm="ngForm" (ngSubmit)="onSubmit(contactForm)">
  <label for="name">Name</label>
  <input id="name" name="name" ngModel required minlength="3" #nameCtrl="ngModel" />
  <div *ngIf="nameCtrl.invalid && (nameCtrl.dirty || nameCtrl.touched)">
    <small *ngIf="nameCtrl.errors?.required">Name is required.</small>
    <small *ngIf="nameCtrl.errors?.minlength">Minimum 3 characters.</small>
  </div>

  <label for="email">Email</label>
  <input id="email" name="email" ngModel required email #emailCtrl="ngModel" />
  <div *ngIf="emailCtrl.invalid && (emailCtrl.dirty || emailCtrl.touched)">
    <small *ngIf="emailCtrl.errors?.required">Email is required.</small>
    <small *ngIf="emailCtrl.errors?.email">Enter a valid email.</small>
  </div>

  <button type="submit" [disabled]="contactForm.invalid">Submit</button>
</form>
```

### 3.2 Component (TypeScript)

```ts
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-contact-template',
  templateUrl: './contact-template.component.html'
})
export class ContactTemplateComponent {
  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Form value', form.value);
      // perform submit action
    }
  }
}
```

### 3.3 Notes and best practices (template-driven)

* Use `ngModel` for two‑way binding. Always provide a `name` attribute on inputs.
* Use the template reference variable (e.g., `#emailCtrl="ngModel"`) to access control state in the template.
* Template-driven forms are simpler but harder to unit test because a lot of logic lives in the template.

---

## 4. Reactive forms (step-by-step)

**Use case:** Registration form with nested address group and dynamic phone numbers.

### 4.1 Component (TypeScript) — basic form model

```ts
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';

@Component({
  selector: 'app-register-reactive',
  templateUrl: './register-reactive.component.html'
})
export class RegisterReactiveComponent {
  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      address: this.fb.group({
        street: ['', Validators.required],
        city: ['', Validators.required],
        zip: ['']
      }),
      phones: this.fb.array([])
    });
  }

  get phones(): FormArray {
    return this.form.get('phones') as FormArray;
  }

  addPhone(value = '') {
    this.phones.push(this.fb.control(value, Validators.pattern(/^\+?\d{7,15}$/)));
  }

  removePhone(index: number) {
    this.phones.removeAt(index);
  }

  onSubmit() {
    if (this.form.valid) {
      console.log(this.form.value);
      // send to API
    }
  }
}
```

### 4.2 Template (HTML)

```html
<form [formGroup]="form" (ngSubmit)="onSubmit()">
  <input formControlName="firstName" placeholder="First name" />
  <div *ngIf="form.get('firstName')?.touched && form.get('firstName')?.invalid">
    <small *ngIf="form.get('firstName')?.errors?.required">Required</small>
    <small *ngIf="form.get('firstName')?.errors?.minlength">Min length</small>
  </div>

  <input formControlName="lastName" placeholder="Last name" />

  <input formControlName="email" placeholder="Email" />

  <div formGroupName="address">
    <input formControlName="street" placeholder="Street" />
    <input formControlName="city" placeholder="City" />
  </div>

  <div formArrayName="phones">
    <div *ngFor="let p of phones.controls; let i = index">
      <input [formControlName]="i" placeholder="Phone" />
      <button (click)="removePhone(i)" type="button">Remove</button>
    </div>
    <button type="button" (click)="addPhone()">Add phone</button>
  </div>

  <button type="submit" [disabled]="form.invalid">Register</button>
</form>
```

### 4.3 Advantages of reactive forms

* Form model in TypeScript: easier unit testing.
* Predictable state and synchronous access to values.
* Easy to compose complex validations and dynamic controls.

---

## 5. Validation

### 5.1 Built-in validators

Angular provides `Validators.required`, `Validators.minLength`, `Validators.maxLength`, `Validators.email`, `Validators.pattern`, etc.

Use them in reactive forms as shown above, or in template-driven forms using attributes like `required`, `minlength`, and the `email` directive.

### 5.2 Custom synchronous validator

**Reactive example:**

```ts
import { AbstractControl, ValidationErrors } from '@angular/forms';

export function forbiddenNameValidator(forbiddenName: string) {
  return (control: AbstractControl): ValidationErrors | null => {
    return control.value && control.value.toLowerCase() === forbiddenName.toLowerCase()
      ? { forbiddenName: { value: control.value } }
      : null;
  };
}

// usage: this.fb.control('', [forbiddenNameValidator('admin')])
```

### 5.3 Async validator (e.g., check username availability)

```ts
import { AsyncValidatorFn, AbstractControl } from '@angular/forms';
import { Observable, of, map, delay } from 'rxjs';

export function fakeUsernameAvailabilityValidator(): AsyncValidatorFn {
  return (control: AbstractControl): Observable<{ [key: string]: any } | null> => {
    const forbidden = ['taken', 'admin'];
    // Simulate API call
    return of(forbidden.includes(control.value)).pipe(
      delay(300),
      map(isTaken => (isTaken ? { usernameTaken: true } : null))
    );
  };
}

// usage: this.fb.control('', { asyncValidators: [fakeUsernameAvailabilityValidator()] })
```

### 5.4 Cross-field validation

Use a validator on a `FormGroup` to compare multiple controls (e.g., password + confirm password).

```ts
import { FormGroup } from '@angular/forms';

export function passwordMatchValidator(group: FormGroup) {
  const pw = group.get('password')?.value;
  const confirm = group.get('confirmPassword')?.value;
  return pw === confirm ? null : { passwordMismatch: true };
}

// usage: this.fb.group({password: [''], confirmPassword: ['']}, { validators: passwordMatchValidator })
```

---

## 6. FormArray (dynamic controls)

`FormArray` allows dynamic lists of controls (phones, addresses, items). Use `controls`, `push()`, `removeAt()` and iterate in template with `formArrayName` and `[formControlName]`.

---

## 7. Nested forms and child components

**Patterns:**

* **Input FormGroup**: Parent creates the full `FormGroup` and passes a sub-group to child via `@Input()`. Child uses `formGroup` / `formControlName` directives.
* **ControlValueAccessor (CVA)**: Implement CVA to create reusable form controls that behave like native inputs. Use `NG_VALUE_ACCESSOR` provider.

**Recommendation:** For moderately complex UIs, prefer parent-managed `FormGroup` for predictable validation; use CVA for truly reusable, encapsulated controls.

---

## 8. Submitting and retrieving values

* Use `form.value` (or `contactForm.value` for template-driven).
* Use `form.getRawValue()` to get values including disabled controls.
* Use `patchValue()` for partial updates, `setValue()` for full updates.

---

## 9. Testing forms

* For **reactive** forms, unit test the component class without rendering the template: assert validators, control existence, `form.valid` after setting values.
* For **template-driven** forms, use `ComponentFixture` to fill inputs and trigger `ngModel` changes; assert template state and submission behavior.

Example reactive test snippet (Jasmine + TestBed):

```ts
it('form invalid when empty', () => {
  expect(component.form.valid).toBeFalsy();
});

it('email field validity', () => {
  const email = component.form.controls['email'];
  email.setValue('not-a-valid-email');
  expect(email.valid).toBeFalse();
});
```

---

## 10. Accessibility and UX best practices

* Associate `<label for>` with `id` attributes.
* Provide clear, inline error messages and ARIA attributes when needed (e.g., `aria-invalid`, `aria-describedby`).
* Avoid showing all errors on initial render; show after `touched` or `dirty`.
* Disable submit while async validations are running or show progress state.

---

## 11. Performance tips

* Use `OnPush` change detection for form-heavy components where possible.
* Avoid unnecessary `valueChanges` subscriptions without `takeUntil` cleanup.
* For large forms, consider splitting the form into smaller components and use a shared service or parent-managed form group to aggregate state.

---

## 12. Common pitfalls & troubleshooting

* **Missing `name` attribute** in template-driven inputs prevents `ngModel` from registering.
* **Not importing `ReactiveFormsModule`** in a lazy module causes errors like "formGroup directive not found".
* **Forgetting to unsubscribe** from `valueChanges` in components — use `takeUntil` and a `destroy$` observable.

---

## 13. Quick reference (APIs)

* `FormControl`, `FormGroup`, `FormArray`
* Validators: `required`, `minLength`, `maxLength`, `email`, `pattern`
* Methods: `setValue`, `patchValue`, `reset`, `getRawValue`, `addControl`, `removeControl`

---
