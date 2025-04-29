import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  user = { name: '', email: '', password: '' };

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    this.authService.register(this.user).subscribe(
      (response: any) => {
        alert(response.message || 'Registration Successful');
        this.router.navigate(['/login']);
      },
      (error: any) => {
        alert(error.error.message || 'Registration Failed');
      }
    );
  }
}
