import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, shareReplay, switchMap, tap} from "rxjs";
import {Cache, InvalidateCache, SharedCacheService} from "./shared-cache.service";
import {UserService} from "@outfit-planner-mf/shared/auth";
import {Product} from "../defs";

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private httpClient: HttpClient, private userService: UserService) {
  }

  @Cache("products")
  getProducts(): Observable<Product[]> {
          return this.httpClient.get<Product[]>('/api/products')
    //
    // return this.userService.isUserLoggedIn$.pipe(
    //   switchMap((isLoggedIn) => {
    //     if (isLoggedIn) {
    //       return this.httpClient.get<Product[]>('/api/products')
    //     }
    //     return this.httpClient.get<Product[]>('/api/products/mock')
    //   }))
  }

  @InvalidateCache("products")
  addProduct(product: any): Observable<Product> {
    return this.httpClient.post<Product>('/api/products', product);
  }

  @Cache("productImage")
  getImage(productId: string): Observable<Blob> {
    return this.httpClient.get(`/api/products/${productId}/image`, {
      responseType: 'blob'
    });
  }

}
