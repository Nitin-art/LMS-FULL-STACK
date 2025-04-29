import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-user',
  standalone: false,
  templateUrl: './user.component.html'
})
export class UserComponent implements OnInit {

  users: any[] = [];
  user = { id: 0, name: '', email: '', password: '', role: '' };
  isEdit = false;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe((data) => {
      this.users = data;
    });
  }

  saveUser() {
    if (this.isEdit) {
      this.userService.updateUser(this.user).subscribe(() => {
        alert('User Updated Successfully!');
        this.loadUsers();
        this.resetForm();
      });
    } else {
      this.userService.addUser(this.user).subscribe(() => {
        alert('User Added Successfully!');
        this.loadUsers();
        this.resetForm();
      });
    }
  }

  editUser(user: any) {
    this.user = { ...user };
    this.isEdit = true;
  }

  deleteUser(id: number) {
    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(id).subscribe(() => {
        alert('User Deleted Successfully!');
        this.loadUsers();
      });
    }
  }

  resetForm() {
    this.user = { id: 0, name: '', email: '', password: '', role: '' };
    this.isEdit = false;
  }

}
