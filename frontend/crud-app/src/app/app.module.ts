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
import { CompareComponent } from './compare/compare.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import {MovieComponent} from "./movie/movie.component";
import {RatingComponent} from "./rating/rating.component";
import {UserComponent} from "./user/user.component";
import {MatFormField} from "@angular/material/form-field";
import { MatInputModule } from '@angular/material/input';
import {FormsModule} from "@angular/forms";
import { MatPaginatorModule } from '@angular/material/paginator';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    CrudComponent,
    CompareComponent,
    DashboardComponent,
    MovieComponent,
    RatingComponent,
    UserComponent
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
    HttpClientModule
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
