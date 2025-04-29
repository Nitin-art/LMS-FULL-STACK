import { Component, OnInit } from '@angular/core';
import { BookService } from '../../services/book.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-book',
  standalone: false,
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {
  books: any[] = [];
  book = { id: null, title: '', author: '', description: '' };
  isEdit = false;
  currentUserRole: string = '';

  constructor(
    private bookService: BookService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.loadBooks();
    this.currentUserRole = localStorage.getItem('currentUserRole') || ''; // Get user role from localStorage
  }

  loadBooks() {
    this.bookService.getAllBooks().subscribe((data) => {
      this.books = data;
    });
  }

  saveBook() {
    if (this.isEdit) {
      this.bookService.updateBook(this.book).subscribe(() => {
        alert('Book Updated Successfully!');
        this.loadBooks();
        this.resetForm();
      });
    } else {
      this.bookService.addBook(this.book).subscribe(() => {
        alert('Book Added Successfully!');
        this.loadBooks();
        this.resetForm();
      });
    }
  }

  addBook() {
    this.isEdit = false;
    this.book = { id: null, title: '', author: '', description: '' };
  }

  editBook(id: number) {
    this.bookService.getBook(id).subscribe((data) => {
      this.book = data;
      this.isEdit = true;
    });
  }

  deleteBook(id: number) {
    if (confirm('Are you sure you want to delete this book?')) {
      this.bookService.deleteBook(id).subscribe(() => {
        alert('Book Deleted Successfully!');
        this.loadBooks();
      });
    }
  }

  resetForm() {
    this.book = { id: null, title: '', author: '', description: '' };
    this.isEdit = false;
  }
}
