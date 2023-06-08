import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Cache, InvalidateCache} from "./shared-cache.service";
import {Product} from "../defs";

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private httpClient: HttpClient) {
  }

  @Cache("products")
  getProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>('/api/products')
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
