import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { TipoLancamento } from '../dados-lancamentos';
import { ToastrService } from 'ngx-toastr';
import { LancamentoService } from '../lancamento-service';
import { DetalhesCategoria } from '../../categorias/dados-categoria';
import { DetalhesCartao } from '../../cartoes/dados-cartao';
import { forkJoin } from 'rxjs';
import { Header } from '../../common/components/header/header';
import { CommonModule } from '@angular/common';

interface CadastroLancamentoForm{
  categoriaId: FormControl<string>;
  data: FormControl<string>;
  valor: FormControl<string>;
  tipo: FormControl<TipoLancamento | ''>;
  cartaoId: FormControl<string>;
}

@Component({
  selector: 'app-cadastro-lancamento',
  imports: [Header, ReactiveFormsModule, CommonModule],
  templateUrl: './cadastro-lancamento.html',
  styleUrl: './cadastro-lancamento.scss',
})
export class CadastroLancamento implements OnInit{
  
  form!: FormGroup<CadastroLancamentoForm>;
  toast = inject(ToastrService);
  service = inject(LancamentoService);

  categoriasAtivas: DetalhesCategoria[] = [];
  cartoesAtivos: DetalhesCartao[] = [];

  ngOnInit(): void {
    this.form = new FormGroup<CadastroLancamentoForm>({
      categoriaId: new FormControl<string>('', { nonNullable: true, validators: Validators.required}),
      data: new FormControl<string>('', { nonNullable: true, validators: Validators.required}),
      valor: new FormControl<string>('', { nonNullable: true, validators: Validators.required}),
      tipo: new FormControl<TipoLancamento | ''>('', { nonNullable: true, validators: Validators.required}),
      cartaoId: new FormControl<string>('', { nonNullable: true}),
    });

    this.inicializarDropDowns();
  }

  inicializarDropDowns(){
    forkJoin({
      categorias: this.service.listarCategoriasDisponiveis(),
      cartoes: this.service.listarCartoesDisponiveis()
    }).subscribe({
      next: (resultado)  => {
        this.categoriasAtivas = resultado.categorias;
        this.cartoesAtivos = resultado.cartoes;
      },
      error: () => this.toast.error('Erro ao carregar categorias e cartões')
    })
  }

  handleSubmit(){
    console.log(this.form.value);
  }
}
