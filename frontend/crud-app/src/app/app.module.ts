import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CrudComponent } from './crud/crud.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatTabsModule } from '@angular/material/tabs';
import {MatIconModule} from '@angular/material/icon';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { DashboardComponent } from './dashboard/dashboard.component';
import {MovieComponent} from "./movie/movie.component";
import {RatingComponent} from "./rating/rating.component";
import {UserComponent} from "./user/user.component";
import {MatFormField} from "@angular/material/form-field";
import { MatInputModule } from '@angular/material/input';
import {FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatPaginatorModule } from '@angular/material/paginator';
import { HttpClientModule } from '@angular/common/http';
import { BenchmarkComponent } from './benchmark/benchmark.component';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
  MatTable
} from "@angular/material/table";


import { UserRedisComponent } from './user-redis/user-redis.component';
import {RatingRedisComponent} from "./rating-redis/rating-redis.component";
import {MovieRedisComponent} from "./movie-redis/movie-redis.component";
import {MatTooltip} from "@angular/material/tooltip";

@NgModule({
  declarations: [
    AppComponent,
    CrudComponent,
    DashboardComponent,
    MovieComponent,
    RatingComponent,
    UserComponent,
    BenchmarkComponent,
    RatingRedisComponent,
    MovieRedisComponent,
    UserRedisComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        MatToolbarModule,
        MatButtonModule,
        MatSidenavModule,
        MatListModule,
        MatTabsModule,
        MatIconModule,
        MatFormField,
        MatInputModule,
        FormsModule,
        MatPaginatorModule,
        HttpClientModule,
        MatTable,
        MatColumnDef,
        MatHeaderCell,
        MatCell,
        MatCellDef,
        MatHeaderCellDef,
        MatHeaderRow,
        MatRow,
        MatHeaderRowDef,
        MatRowDef,
        ReactiveFormsModule,
        MatTooltip

    ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
