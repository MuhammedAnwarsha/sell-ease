import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login-component/login-component';
import { SignupComponent } from './auth/signup-component/signup-component';
import { DashboardComponent } from './dashboard/dashboard-component/dashboard-component';
import { AuthGuard } from './auth-guard';

export const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'dashboard', component: DashboardComponent, canActivate:[AuthGuard] },
];
