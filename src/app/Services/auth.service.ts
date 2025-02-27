import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth'; // Remplacez par votre URL d'API
  private currentUserSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);
  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { // Assurez-vous d'injecter HttpClient ici
    const user = JSON.parse(localStorage.getItem('currentUser') || '{}');
    this.currentUserSubject.next(user);
  }

  get currentUser() {
    return this.currentUserSubject.asObservable();
  }

  register(user: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, user, this.httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  login(user: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, {
      username: user.username,
      password: user.password
    }).pipe(
      map(response => {
        // Vérifiez que la réponse contient userId
        if (response && response.userId) {
          localStorage.setItem('currentUser', JSON.stringify(response));  // Sauvegarder les infos dans le localStorage
          this.currentUserSubject.next(response);  // Mettre à jour le sujet de l'utilisateur courant
          return response;
        } else {
          console.error('L\'utilisateur connecté ne possède pas d\'ID.');
          return null; // ou gérez l'erreur autrement
        }
      }),
      catchError(error => {
        console.error('Erreur de connexion:', error);
        return throwError(error);  // Gérer l'erreur ici
      })
    );
  }


  private handleError(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Erreur client: ${error.error.message}`;
    } else {
      errorMessage = `Erreur serveur: Code ${error.status}, Message: ${error.message}`;
    }
    console.error(errorMessage);
    return throwError(errorMessage);
  }

  getFriends(userId: number): Observable<any[]> {
    if (userId) {
      return this.http.get<any[]>(`${this.apiUrl}/users/${userId}/friends`);
    } else {
      console.error('ID utilisateur non défini');
      return throwError('ID utilisateur non défini');
    }
  }

  logout(): void {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}
