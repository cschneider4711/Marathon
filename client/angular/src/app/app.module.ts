import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
//import { AppRoutingModule } from './app-routing.module';

@NgModule({
    declarations: [
        AppComponent/*,
        RunnersComponent*/
    ],
    imports: [
        BrowserModule/*,
        AppRoutingModule*/
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
