import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainFooterComponent } from './main-footer/main-footer.component';
import { MainHeaderComponent } from './main-header/main-header.component';
import { MainContentComponent } from './main-content/main-content.component';
import { AddPersonComponent } from './add-person/add-person.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    MainFooterComponent,
    MainHeaderComponent,
    MainContentComponent,
    AddPersonComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
