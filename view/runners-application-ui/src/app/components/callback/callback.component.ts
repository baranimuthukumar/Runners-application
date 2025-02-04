import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OAuthService } from 'src/app/services/oauth.service';

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.scss']
})
export class CallbackComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private oauthService: OAuthService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const code = params['code'];
      if (code) {
        this.oauthService.exchangeCodeForToken(code).subscribe(
          response => {
            console.log('Access Token:', response.access_token);
            // Save the token in localStorage or handle it as needed
            localStorage.setItem('access_token', response.access_token);
            this.router.navigate(['/']);
          },
          error => {
            console.error('Error exchanging code for token', error);
            this.router.navigate(['/']);
          }
        );
      }
    });
  }
}