import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MessageCardService {

  closed: {[key: string]: boolean} = {};

  setClosed(id: string){
    this.closed[id] = true
  }

  wasClosed(id: string){
    return this.closed[id];
  }

}
