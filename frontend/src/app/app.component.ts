import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
    selector: 'my-app',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {
    name= "";
    constructor(private http: HttpClient){ }

    onSubmit(event) {
        this.name = event.target.name.value;
    }

    onClick() {
        //this.http.post('http://localhost:8082/postuse', name);
    }
}