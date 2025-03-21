import { HttpClient } from "@angular/common/http";
import { inject } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { Customer, MenuItem, OrderItem } from "./models";

export class RestaurantService {

  private http = inject(HttpClient)
  private router = inject(Router)
  protected order!: MenuItem[]
  protected finalOrder: OrderItem[]=[]

  // TODO: Task 2.2
  // You change the method's signature but not the name
  getMenuItems(): Observable<MenuItem[]> {
    return this.http.get<any>('/api/menu')
  }

  // TODO: Task 3.2
  setOrder(sentOrder: MenuItem[]){
    this.order = sentOrder
    console.info('>>> order received here: ', this.order)
  }

  sendOrder(){ // sends existing order to place-order view
    return this.order
  }

  submitOrder(form: any, pushOrder: MenuItem[]){
    const buildOrder = {
      username: form['username'],
      password: form['password'],
      items: pushOrder
    }

    this.http.post<any>('/api/food_order', buildOrder).subscribe({
      next: (response) => {
        console.info(response)
        alert(response['newsId']);
      },
      error: (err) =>{
        console.error('message:', err.error) //grab error json from controller
        alert(err.error['messages']);
      },
      complete:() =>{
        console.info('completed upload');
      }
    })
  }
}
