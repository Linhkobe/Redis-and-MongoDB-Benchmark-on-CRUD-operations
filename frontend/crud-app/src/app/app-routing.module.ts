import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CrudComponent } from './crud/crud.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import {BenchmarkComponent} from "./benchmark/benchmark.component";
import {CrudRedisComponent} from "./crud-redis/crud-redis.component";

const routes: Routes = [
  { path: 'crud', component: CrudComponent },
  { path: 'benchmark', component: BenchmarkComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path:'crud-redis', component:CrudRedisComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
