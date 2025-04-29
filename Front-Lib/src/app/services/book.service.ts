import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private baseUrl = 'http://localhost:8080/api/books';  // Backend API URL

  constructor(private http: HttpClient) {}

  // Fetch all books
  getAllBooks(): Observable<any> {
    return this.http.get(this.baseUrl);
  }

  // Fetch a single book by ID
  getBook(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  // Add a new book (Admin only)
  addBook(book: any): Observable<any> {
    const currentUserRole = localStorage.getItem('currentUserRole');  // Get user role from localStorage
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post(`${this.baseUrl}?role=${currentUserRole}`, book, { headers });
  }

  // Update book details (Admin only)
  updateBook(book: any): Observable<any> {
    const currentUserRole = localStorage.getItem('currentUserRole');
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.put(`${this.baseUrl}/${book.id}?role=${currentUserRole}`, book, { headers });
  }

  // Delete book (Admin only)
  deleteBook(id: number): Observable<any> {
    const currentUserRole = localStorage.getItem('currentUserRole');
    return this.http.delete(`${this.baseUrl}/${id}?role=${currentUserRole}`);
  }
}
