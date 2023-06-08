import {Injectable} from '@angular/core';
import {Observable, shareReplay, tap} from "rxjs";
import {SharedComponentsModule} from "../shared-components.module";

type CacheEntry = { [key: string]: Observable<any> }

@Injectable({providedIn: 'root'})
export class SharedCacheService {
  caches: { [key: string]: CacheEntry } = {
    productImage: {},
    products: {},
    outfits: {}
  }

  invalidateCache(key: string) {
    this.caches[key] = {};
  }

}

export const Cache = (target: any): any => {
  return (_: any, __: string, descriptor: PropertyDescriptor): PropertyDescriptor => {
    let method = descriptor.value;

    descriptor.value = function () {
      const sharedCacheService = SharedComponentsModule.injector.get<SharedCacheService>(SharedCacheService)
      const cacheEntry = sharedCacheService.caches[target];

      const cacheKey: string = serializeArgs(...arguments);
      if (cacheEntry[cacheKey] == null) {
        cacheEntry[cacheKey] = method.apply(this, arguments).pipe(
          shareReplay(1)
        );
      }
      return cacheEntry[cacheKey];
    }

    return descriptor;
  }

};

export const InvalidateCache = (target: any): any => {
  return (_: any, __: string, descriptor: PropertyDescriptor): PropertyDescriptor => {
    let method = descriptor.value;

    descriptor.value = function () {
      const sharedCacheService = SharedComponentsModule.injector.get<SharedCacheService>(SharedCacheService)

      return method.apply(this, arguments).pipe(
        tap(() => sharedCacheService.invalidateCache(target))
      )
    }

    return descriptor;
  }
};


const serializeArgs = (...args: any[]): string => {
  return args
    .map((arg: any) => arg.toString())
    .join(':')
}
