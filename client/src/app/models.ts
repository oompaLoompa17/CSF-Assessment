// You may use this file to create any models
export interface MenuItem{
    id: string,
    name: string,
    description: string,
    price: number
    quantity: number
}

export interface Customer {
    username: string,
    password: string
}

export interface Order {
    username: string,
    password: string,
    items: MenuItem[]
}

export interface OrderItem{
    id: string,
    price: number,
    quantity: number
}