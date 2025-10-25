import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-login-component',
  imports: [CommonModule, FormsModule, RouterModule, HttpClientModule],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css',
})
export class LoginComponent {

  username = '';
  password = '';
  errorMsg = '';

  constructor(private api: ApiService, private router: Router) {}

  login() {
    this.errorMsg = '';
    this.api.login(this.username, this.password).subscribe({
      next: (res) => {
        localStorage.setItem('jwt', res.jwt); // store JWT
        localStorage.setItem('user', JSON.stringify(res.user));
        this.router.navigate(['/dashboard']); // navigate after login
      },
      error: (err) => {
        this.errorMsg = err?.error?.message || 'Login failed';
      },
    });
  }

}
