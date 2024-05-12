import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CrudComponent } from './crud/crud.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import {BenchmarkComponent} from "./benchmark/benchmark.component";

const routes: Routes = [
  { path: 'crud', component: CrudComponent },
  { path: 'benchmark', component: BenchmarkComponent },
  { path: 'dashboard', component: DashboardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
