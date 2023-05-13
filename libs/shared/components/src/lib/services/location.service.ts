import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, shareReplay, tap} from "rxjs";
import {Cache, InvalidateCache, SharedCacheService} from "./shared-cache.service";
import {CreateOutfitRequest, Geolocation, Outfit, ToggleWearOutfitRequest} from "../defs";

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  getLocation(): Observable<Geolocation> {
    return new Observable(observer => {
      if (window.navigator && window.navigator.geolocation) {
        window.navigator.geolocation.getCurrentPosition(
          (position) => {
            const crd = position.coords
            const geolocation: Geolocation = {
              latitude: crd.latitude,
              longitude: crd.longitude
            }
            observer.next(geolocation);
            observer.complete();
          },
          (error) => observer.error(error)
        );
      } else {
        observer.error('Unsupported Browser');
      }
    });
  }

}
