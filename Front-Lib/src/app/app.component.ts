import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.css'
})
export class AppComponent {
  currentUserRole: string;

  constructor() {
    // Retrieve the role from localStorage
    this.currentUserRole = localStorage.getItem('currentUserRole') || '';
  }
}
