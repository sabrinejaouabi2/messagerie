import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './Component/register/register.component';
import { LoginComponent } from './Component/login/login.component';
import { ChatComponent } from './Component/chat/chat.component';
import { DashboardComponent } from './Component/dashboard/dashboard.component';

const routes: Routes = [
  { path: '', redirectTo: '/connexion', pathMatch: 'full' },
  { path: 'inscription', component: RegisterComponent },
  { path: 'connexion', component: LoginComponent },
  { path: 'chat', component: ChatComponent },
  { path: 'dashboard', component: DashboardComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
