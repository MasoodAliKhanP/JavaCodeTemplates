import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

// test
export class AppComponent implements OnInit{
  title = 'my-app';


  ngOnInit(): void { 
    const httpOptions = {
      observe: 'response' as 'response',
      // withCredentials: true,
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Basic ' + btoa('admin:password'),
      }), 
    };

    this.http
    .get<any>('http://pilot2.dev-env.biz/bo/backoffice/', httpOptions)
    .subscribe(
      data => { // json data
          console.log('response body: ', data.body);
          console.log('response header: ', data.headers);
          console.log('response header: ', data.headers.get('x-auth-token'));
      },
      error => {
          console.log('Error: ', error);
      });;
  }

  constructor(private http: HttpClient) { }

  
}
