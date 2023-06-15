import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
    title = 'Marathon Runners';
    runners: any[] = [];
    htmlString: any;

    constructor(private http: HttpClient, private sanitizer: DomSanitizer) {} // Inject stuff

    ngOnInit() {
        this.getRunners();
        this.getWinner();
    }

    getRunners(): void {
        // Fetch the runners when the component initializes
        this.http.get<any[]>('/marathon/rest/runners')
            .subscribe(data => {
                this.runners = data; // Store the data in the runners property
            });
    }

    getWinner(): void {
        // Fetch the runners when the component initializes
        this.http.get<any>('/marathon/rest/runners/winner/1')
            .subscribe(data => {
                this.htmlString = this.sanitizer.bypassSecurityTrustHtml('The winner is: <b>' + data.winner + '</b>');
            });
    }
}
