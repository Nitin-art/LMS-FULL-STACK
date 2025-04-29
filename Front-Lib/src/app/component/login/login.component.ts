import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  credentials = {
    email: '',
    password: ''
  };
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  login() {
    this.authService.login(this.credentials).subscribe(
      (response) => {
        if (response.status === 'Login Successful') {
          alert('Login Successful!');
          this.router.navigate(['/dashboard']);
        } else {
          this.errorMessage = response.status;
          alert(this.errorMessage);
        }
      },
      (error) => {
        this.errorMessage = 'Login failed. Please try again.';
        alert(this.errorMessage);
      }
    );
  }
}
