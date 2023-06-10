import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Geolocation} from "../defs";

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
