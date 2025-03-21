import { Component, EventEmitter, inject, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { RestaurantService } from '../restaurant.service';
import { MenuItem } from '../models';
import { Observable, Subject, Subscription } from 'rxjs';

@Component({
  selector: 'app-menu',
  standalone: false,
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit{
  // TODO: Task 2
  protected router = inject(Router)
  protected restSvc = inject(RestaurantService)
  protected currentOrder!: MenuItem[]
  sub$!: Subscription
  totalCost: number = 0
  totalItems: number = 0

  ngOnInit(): void {
      this.getMenu()
  }

  getMenu(){
    this.sub$ = this.restSvc.getMenuItems().subscribe({
      next: results => {
        this.currentOrder = results
        console.log(this.currentOrder)
        for(let i=0; i < this.currentOrder.length; i++){ // set all quantities to 0
          this.currentOrder[i].quantity = 0
          console.log(this.currentOrder[i])
        }
      },
      error: err => {
        console.log(err);
      },
      complete: () => {
        this.sub$.unsubscribe();
      }
    })
   
  }

  changeQty(itemName: string, change: number){
    const idx = this.currentOrder.findIndex(i => i.name == itemName) 
    if (idx >= 0){
      if(change === -1 && this.currentOrder[idx].quantity === 0){
        console.log(this.currentOrder[idx])
      }
      else{
        this.currentOrder[idx].quantity += change
        console.log(this.currentOrder[idx])
        this.totalItems += change
      }
    }
    this.calculateTotal()
  }

  calculateTotal(){
    for(let i=0; i < this.currentOrder.length; i++){
      this.totalCost += this.currentOrder[i].price*this.currentOrder[i].quantity
    }
  }

  placeOrder(){
    this.restSvc.setOrder(this.currentOrder)
    this.router.navigate(['/place-order'])
  }
 
}
