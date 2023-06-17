export type GroupedProducts = { [key: string]: Product[] };

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
  Shoes = "SHOES",
  Tshirt = "T-SHIRT",
  Trousers = "TROUSERS",
  Jacket = "JACKET",
  Sweatshirt = "SWEATSHIRT",
  Socks = "SOCKS"
}

export enum ScreenResolutions {
  Large = 992
}
