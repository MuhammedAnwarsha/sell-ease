import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-dashboard-component',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard-component.html',
  styleUrl: './dashboard-component.css',
})
export class DashboardComponent {
  totalOrders = 0;
  totalProducts = 0;
  totalRevenue = 0;
  ordersByStatus: any = {};
  topProducts: any = {};
  fullname: string = '';
  email: string = '';
  phone: string = '';
  lastLogin: string = '';

  constructor(private api: ApiService, private router: Router) {}

  ngOnInit(): void {
    this.dashboardData();
    this.loadProfile();
  }

  dashboardData() {
    const token = localStorage.getItem('jwt');
    if (token) {
      this.api.getDashboardData(token).subscribe({
        next: (res) => {
          if (res.success) {
            this.totalOrders = res.data.totalOrders;
            this.totalProducts = res.data.totalProducts;
            this.totalRevenue = res.data.totalEarnings;
            this.ordersByStatus = res.data.ordersByStatus;
            this.topProducts = res.data.topProducts;
          }
        },
        error: (err) => {
          console.error('Error fetching dashboard data', err);
        },
      });
    }
  }

  loadProfile() {
    this.api.getUserProfile().subscribe({
      next: (res) => {
        this.fullname = res.fullname;
        this.email = res.email;
        this.phone = res.phone;
        this.lastLogin = res.lastLogin;
      },
    });
  }

  logout(): void {
    localStorage.removeItem('jwt'); // clear JWT token
    this.router.navigate(['/login']); // redirect to login page
  }
}
