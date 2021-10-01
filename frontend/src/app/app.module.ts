import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { AppComponent }   from './app.component';
import { MainPageComponent } from './main-page/main-page.component';

@NgModule({
    imports:      [ BrowserModule, FormsModule ],
    declarations: [ AppComponent, MainPageComponent ],
    bootstrap:    [ AppComponent ]
})
export class AppModule { }