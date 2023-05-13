
export interface Product {
  id: string;
  url: string;
  category: string;
  links: Link[]
}

export interface Outfit{
  id: string;
  products: Product[];
  wornToday?: boolean;
  matchProbability?: number
}

export interface CreateOutfitRequest{
  productsIds: string[]
}

export interface PredictOutfitsRequest{
  geolocation: Geolocation
}

export interface Geolocation{
  latitude: number;
  longitude: number;
}

export interface ToggleWearOutfitRequest {
  id: string;
  geolocation: Geolocation
}

export interface Link {
  href: string;
  rel: string;
}

export enum ProductCategory{
  Shoe = "SHOE",
  Tshirt = "T-SHIRT",
  Trousers = "TROUSERS",
  Jacket = "JACKET",
  Hoodie = "HOODIE",
  Socks = "SOCKS"
}
