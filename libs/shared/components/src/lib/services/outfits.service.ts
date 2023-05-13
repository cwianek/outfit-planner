import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, shareReplay, tap} from "rxjs";
import {Cache, InvalidateCache, SharedCacheService} from "./shared-cache.service";
import {CreateOutfitRequest, Outfit, PredictOutfitsRequest, ToggleWearOutfitRequest} from "../defs";

@Injectable({
  providedIn: 'root'
})
export class OutfitsService{

  constructor(private httpClient: HttpClient) {
  }

  @Cache("outfits")
  predictOutfits(request: PredictOutfitsRequest): Observable<Outfit[]> {
    return this.httpClient.post<Outfit[]>('/api/outfits/predictions', request);
  }

  @InvalidateCache("outfits")
  addOutfit(request: CreateOutfitRequest): Observable<Outfit> {
    return this.httpClient.post<Outfit>('/api/outfits', request);
  }

  @InvalidateCache("outfits")
  toggleWear(request: ToggleWearOutfitRequest): Observable<boolean> {
    return this.httpClient.post<boolean>('/api/outfits/toggle-wear', request);
  }

}
