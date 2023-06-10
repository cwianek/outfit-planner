export interface ProductModalData{
  title: string,
  categories: string[],
  selectedCategory: string
}

export interface ProductAddedResult {
  category: string,
  image: string
}

export enum CategoriesOrientation{
  Horizontal = 'horizontal',
  Vertical = 'vertical',
}
