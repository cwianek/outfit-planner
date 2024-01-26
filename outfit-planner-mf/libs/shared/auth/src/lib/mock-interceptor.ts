import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import {Observable, switchMap, take} from 'rxjs';
import { UserService } from '..';

@Injectable()
export class MockInterceptor implements HttpInterceptor {

  constructor(private userService: UserService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return this.userService.isUserLoggedIn$.pipe(
      take(1),
      switchMap((authenticated: boolean) => {
        if (!authenticated && (request.url.includes("/api"))) {
          const modifiedRequest = request.clone({ url: this.appendWordAfterSecondSlash(request.url, "mock") });
          return next.handle(modifiedRequest);
        }

        return next.handle(request);
      })
    );
  }

  private appendWordAfterSecondSlash(inputUrl: string, wordToAppend: string): string {
    const urlParts: string[] = inputUrl.match(/[^/"]+|"(?:\\"|[^"])+"/g) || [];
    if (urlParts && urlParts.length >= 3) {
      urlParts.splice(2, 0, wordToAppend);
    } else {
      urlParts.push(wordToAppend);
    }
    return urlParts.join('/');
  }
}
