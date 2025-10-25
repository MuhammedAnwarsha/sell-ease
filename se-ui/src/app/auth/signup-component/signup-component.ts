import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-signup-component',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './signup-component.html',
  styleUrl: './signup-component.css',
})
export class SignupComponent {

  fullname = '';
  email = '';
  phone = '';
  password = '';
  errorMsg = '';
  successMessage = '';

  constructor(private api:ApiService, private router: Router) {}

  signup() {
    this.errorMsg = '';
    this.api.signup(this.fullname,this.email,this.phone,this.password).subscribe({
      next: (res) => {
        localStorage.setItem('jwt', res.jwt); // store JWT
        localStorage.setItem('user', JSON.stringify(res.user));
        this.router.navigate(['/dashboard']); // navigate after login
      },
      error: (err) => {
        this.errorMsg = err?.error?.message || 'Signup failed';
      },
    });
  }
}
