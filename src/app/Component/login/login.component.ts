import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/Services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username = '';
  password = '';
  message: string | undefined;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {

  }
  login() {
    // Créer un objet user avec les données de connexion
    const user = {
      username: this.username,
      password: this.password
    };

    this.authService.login(user).subscribe(
      (response: any) => {
        console.log(response.message);  // Affiche "Connexion réussie"
        this.message = response.message;
        // Rediriger l'utilisateur si nécessaire
        this.router.navigate(['/dashboard']); // Exemple de redirection après connexion réussie
      },
      (error) => {
        console.error(error);
        this.message = "Échec de connexion";
      }
    );
  }

}
