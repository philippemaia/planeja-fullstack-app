import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { AuthResponse, CadastroUsuarioForm, LoginForm } from './dados-auth';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  http = inject(HttpClient);
  baseUrl = 'http://localhost:8080/auth';

  login(dados: LoginForm) : Observable<AuthResponse>{
    return this.http.post<AuthResponse>(`${this.baseUrl}/signin`, dados);
  }

  cadastrar(dados: CadastroUsuarioForm) : Observable<AuthResponse>{
    return this.http.post<AuthResponse>(`${this.baseUrl}/signup`, dados);
  }
}
