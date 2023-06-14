import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    title = 'Marathon Client';
    runners: any[] = [];

    constructor(private http: HttpClient) {} // Inject HttpClient

    ngOnInit() {
        this.getRunners();
    }

    getRunners(): void {
        // Fetch the runners when the component initializes
        this.http.get<any[]>('/marathon/rest/runners')
            .subscribe(data => {
                this.runners = data; // Store the data in the runners property
            });
    }
}
