import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/Services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  user = {
    username: '',
    password: ''
  };
  message: string = '';
  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }
  register() {
    this.authService.register(this.user).subscribe(
      response => {
        // Gérer la réponse
        if (response && response.message) {
          this.message = response.message;
        }
      },
      error => {
        // Gestion de l'erreur
        this.message = 'Échec de l\'inscription';
      }
    );
  }
}
