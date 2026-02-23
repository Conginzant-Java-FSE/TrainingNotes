# Angular Routing 

Routing in Angular allows you to build **Single Page Applications (SPA)** where navigation between pages happens without reloading the browser.

This tutorial covers:

* What is routing
* Router setup
* Route configuration
* RouterOutlet
* RouterLink
* Route parameters
* Query parameters
* Child routes
* Lazy loading
* Route guards
* Wildcard routes
* Best practices

---

# 1. What is Angular Routing?

Routing enables:

* Navigation between views
* URL-based page rendering
* Bookmarkable URLs
* Back and forward browser support

Example URLs:

```text
http://localhost:4200/home
http://localhost:4200/users
http://localhost:4200/products/10
```

Angular maps these URLs to components.

---

# 2. Enabling Routing in Angular

When creating a project:

```bash
ng new my-app
```

Angular CLI asks:

```
Would you like to add Angular routing? (Yes)
```

If you select Yes, Angular creates:

```text
app-routing.module.ts
```

If not, you can generate it manually:

```bash
ng generate module app-routing --flat --module=app
```

---

# 3. Basic Router Setup

## Step 1: Import RouterModule

In `app-routing.module.ts`:

```ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
```

---

# 4. Creating Components for Routing

Create some components:

```bash
ng generate component home
ng generate component users
ng generate component about
```

---

# 5. Defining Routes

Update routes array:

```ts
import { HomeComponent } from './home/home.component';
import { UsersComponent } from './users/users.component';
import { AboutComponent } from './about/about.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'users', component: UsersComponent },
  { path: 'about', component: AboutComponent }
];
```

---

# 6. RouterOutlet

In `app.component.html`:

```html
<h1>My Application</h1>

<nav>
  <a routerLink="">Home</a>
  <a routerLink="/users">Users</a>
  <a routerLink="/about">About</a>
</nav>

<router-outlet></router-outlet>
```

`<router-outlet>` is where Angular loads routed components.

---

# 7. RouterLink

Used for navigation.

### Basic usage:

```html
<a routerLink="/users">Users</a>
```

### Property binding:

```html
<a [routerLink]="['/users']">Users</a>
```

---

# 8. RouterLinkActive

Highlight active route:

```html
<a routerLink="/users" routerLinkActive="active">
  Users
</a>
```

CSS:

```css
.active {
  font-weight: bold;
}
```

---

# 9. Route Parameters

Used for dynamic URLs.

Example:

```text
/users/10
```

---

## Define Route

```ts
{ path: 'users/:id', component: UsersComponent }
```

---

## Navigate with Parameter

```html
<a [routerLink]="['/users', 10]">User 10</a>
```

---

## Access Parameter in Component

```ts
import { ActivatedRoute } from '@angular/router';

constructor(private route: ActivatedRoute) {}

ngOnInit() {
  const id = this.route.snapshot.paramMap.get('id');
  console.log(id);
}
```

---

# 10. Query Parameters

Example URL:

```text
/users?id=10&role=admin
```

---

## Navigate with Query Params

```html
<a [routerLink]="['/users']" [queryParams]="{ id: 10, role: 'admin' }">
  View User
</a>
```

---

## Read Query Params

```ts
ngOnInit() {
  this.route.queryParams.subscribe(params => {
    console.log(params['id']);
  });
}
```

---

# 11. Redirect Routes

```ts
{ path: '', redirectTo: '/home', pathMatch: 'full' }
```

`pathMatch: 'full'` ensures exact match.

---

# 12. Wildcard Route (404 Page)

```ts
{ path: '**', component: NotFoundComponent }
```

Must be the last route.

---

# 13. Child Routes (Nested Routing)

Example structure:

```text
/users
/users/profile
/users/settings
```

---

## Define Child Routes

```ts
const routes: Routes = [
  {
    path: 'users',
    component: UsersComponent,
    children: [
      { path: 'profile', component: ProfileComponent },
      { path: 'settings', component: SettingsComponent }
    ]
  }
];
```

---

## Add router-outlet inside UsersComponent

```html
<h2>Users Section</h2>
<router-outlet></router-outlet>
```

---

# 14. Programmatic Navigation

Navigate using TypeScript:

```ts
import { Router } from '@angular/router';

constructor(private router: Router) {}

goToUsers() {
  this.router.navigate(['/users']);
}
```

With parameters:

```ts
this.router.navigate(['/users', 10]);
```

---

# 15. Lazy Loading Modules

Improves performance by loading modules only when needed.

---

## Generate Feature Module

```bash
ng generate module admin --route admin --module app.module
```

---

## Manual Configuration

In app-routing.module.ts:

```ts
{
  path: 'admin',
  loadChildren: () =>
    import('./admin/admin.module').then(m => m.AdminModule)
}
```

This loads module only when user visits `/admin`.

---

# 16. Route Guards

Guards protect routes.

Common guards:

| Guard         | Purpose              |
| ------------- | -------------------- |
| CanActivate   | Prevent access       |
| CanDeactivate | Prevent leaving page |
| Resolve       | Preload data         |

---

## Example: CanActivate Guard

Generate guard:

```bash
ng generate guard auth
```

---

## auth.guard.ts

```ts
import { CanActivate } from '@angular/router';

export class AuthGuard implements CanActivate {

  canActivate(): boolean {
    return true; // or false
  }
}
```

---

## Use Guard

```ts
{
  path: 'admin',
  component: AdminComponent,
  canActivate: [AuthGuard]
}
```

---

# 17. Route Data

Pass static data to routes:

```ts
{
  path: 'users',
  component: UsersComponent,
  data: { role: 'admin' }
}
```

Access:

```ts
this.route.snapshot.data['role'];
```

---

# 18. Complete Example Routing Configuration

```ts
const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },

  { path: 'home', component: HomeComponent },

  { path: 'users', component: UsersComponent },

  { path: 'users/:id', component: UsersComponent },

  {
    path: 'admin',
    loadChildren: () =>
      import('./admin/admin.module').then(m => m.AdminModule)
  },

  { path: '**', component: NotFoundComponent }
];
```

---

# 19. Best Practices

1. Use lazy loading for feature modules.
2. Keep routing configuration clean and organized.
3. Use route guards for authentication.
4. Always define wildcard route last.
5. Avoid deeply nested routes unless necessary.

---

# 20. Routing Flow Summary

```text
User clicks link
        ↓
Router matches path
        ↓
Component loaded inside <router-outlet>
        ↓
View updated without page reload
```

---

# Final Summary

Angular routing enables:

* Navigation without page refresh
* Clean URL management
* Dynamic parameters
* Nested routes
* Module-level lazy loading
* Route protection using guards

Routing is essential for building scalable Angular applications.

---
